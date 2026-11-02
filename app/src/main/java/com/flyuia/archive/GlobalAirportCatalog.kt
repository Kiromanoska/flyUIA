package com.flyuia.archive

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.zip.GZIPInputStream

internal var allAirports: List<Airport> by mutableStateOf(airports)
    private set
internal var allAirportByCode: Map<String, Airport> by mutableStateOf(airportByCode)
    private set
internal var sortedAirportCatalog: List<Airport> by mutableStateOf(airports)
    private set

internal suspend fun loadGlobalAirportCatalog(context: Context) {
    val loaded = try {
        withContext(Dispatchers.IO) {
            val json = GZIPInputStream(context.assets.open("airports.json.gz"))
                .bufferedReader(Charsets.UTF_8).use { it.readText() }
            val rows = JSONArray(json)
            val result = ArrayList<Airport>(rows.length())
            for (i in 0 until rows.length()) {
                val row = rows.getJSONObject(i)
                val code = row.optString("code")
                if (code.isBlank()) continue
                result += Airport(
                    city = row.optString("city", code),
                    country = row.optString("country", ""),
                    code = code,
                    name = row.optString("name", code),
                    latitude = if (row.has("latitude") && !row.isNull("latitude")) row.optDouble("latitude") else null,
                    longitude = if (row.has("longitude") && !row.isNull("longitude")) row.optDouble("longitude") else null
                )
            }
            result
        }
    } catch (_: Exception) {
        emptyList()
    }
    if (loaded.isNotEmpty()) {
        allAirports = loaded
        allAirportByCode = loaded.associateBy { it.code }
    }
}

internal suspend fun warmAirportCatalog() {
    val sorted = withContext(Dispatchers.Default) {
        allAirports.sortedWith(compareBy<Airport> { it.countryName }.thenBy { it.city }.thenBy { it.code })
    }
    sortedAirportCatalog = sorted
}
