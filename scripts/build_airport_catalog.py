import csv
import gzip
import json
import sys

source, target = sys.argv[1], sys.argv[2]
allowed = {"large_airport", "medium_airport", "small_airport", "heliport", "seaplane_base", "balloonport"}
rows = []
seen = set()

with open(source, encoding="utf-8-sig", newline="") as stream:
    for row in csv.DictReader(stream):
        if row.get("type") not in allowed:
            continue
        ident = (row.get("ident") or "").strip()
        iata = (row.get("iata_code") or "").strip().upper()
        icao = (row.get("icao_code") or "").strip().upper()
        code = iata or icao or ident
        if not code or code in seen:
            continue
        try:
            lat = float(row["latitude_deg"])
            lon = float(row["longitude_deg"])
        except (ValueError, TypeError, KeyError):
            continue
        seen.add(code)
        rows.append({
            "code": code,
            "city": (row.get("municipality") or "").strip() or (row.get("name") or code).strip(),
            "country": (row.get("iso_country") or "").strip().upper(),
            "name": (row.get("name") or code).strip(),
            "latitude": lat,
            "longitude": lon,
        })

with open(target, "wb") as output:
    with gzip.GzipFile(fileobj=output, mode="wb", compresslevel=9, mtime=0) as compressed:
        compressed.write(json.dumps(rows, ensure_ascii=False, separators=(",", ":")).encode("utf-8"))

print("Generated airport records:", len(rows))
