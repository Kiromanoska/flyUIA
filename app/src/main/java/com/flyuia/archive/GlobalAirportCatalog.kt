package com.flyuia.archive

import android.content.Context
import org.json.JSONArray
import java.util.zip.GZIPInputStream

internal var allAirports: List<Airport> = airports
    private set
internal var allAirportByCode: Map<String, Airport> = airportByCode
    private set

internal fun loadGlobalAirportCatalog(context: Context) {
    try {
        val json = GZIPInputStream(context.assets.open("airports.json.gz"))
            .bufferedReader(Charsets.UTF_8).use { it.readText() }
        val rows = JSONArray(json)
        val loaded = ArrayList<Airport>(rows.length())
        for (i in 0 until rows.length()) {
            val row = rows.getJSONObject(i)
            val code = row.optString("code")
            if (code.isBlank()) continue
            loaded += Airport(
                city = row.optString("city", code),
                country = row.optString("country", ""),
                code = code,
                name = row.optString("name", code),
                latitude = if (row.has("latitude") && !row.isNull("latitude")) row.optDouble("latitude") else null,
                longitude = if (row.has("longitude") && !row.isNull("longitude")) row.optDouble("longitude") else null
            )
        }
        if (loaded.isNotEmpty()) {
            allAirports = loaded
            allAirportByCode = loaded.associateBy { it.code }
        }
    } catch (_: Exception) {
        // Keep the existing offline directory if the generated asset is unavailable.
    }
}
