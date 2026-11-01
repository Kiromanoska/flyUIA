package com.flyuia.archive

import java.util.Locale

internal data class LanguageOption(
    val code: String,
    val englishName: String,
    val nativeName: String
)

internal val allLanguages: List<LanguageOption> = Locale.getISOLanguages()
    .map { code ->
        val locale = Locale(code)
        LanguageOption(
            code = code,
            englishName = locale.getDisplayLanguage(Locale.ENGLISH).ifBlank { code },
            nativeName = locale.getDisplayLanguage(locale).ifBlank { locale.getDisplayLanguage(Locale.ENGLISH) }
        )
    }
    .distinctBy { it.code }
    .sortedBy { it.englishName.lowercase(Locale.ROOT) }

internal fun recommendedLanguageCodes(countryCode: String = Locale.getDefault().country): List<String> {
    val regional = when (countryCode.uppercase(Locale.ROOT)) {
        "UA" -> listOf("uk", "ru", "en")
        "BG" -> listOf("bg", "en", "ru")
        "PT", "BR" -> listOf("pt", "en", "es")
        "ES", "MX", "AR", "CO", "CL", "PE" -> listOf("es", "en", "pt")
        "IT" -> listOf("it", "en", "de")
        "DE", "AT", "CH" -> listOf("de", "en", "fr")
        "FR", "BE" -> listOf("fr", "en", "de")
        "TR" -> listOf("tr", "en", "ar")
        "PL" -> listOf("pl", "en", "uk")
        "RO", "MD" -> listOf("ro", "en", "ru")
        "GR", "CY" -> listOf("el", "en", "tr")
        "US", "GB", "IE", "AU", "NZ", "CA" -> listOf("en", "es", "fr")
        "CN", "TW", "HK" -> listOf("zh", "en", "yue")
        "JP" -> listOf("ja", "en", "zh")
        "KR" -> listOf("ko", "en", "ja")
        "IN" -> listOf("hi", "en", "bn", "ta")
        "AE", "SA", "EG", "MA" -> listOf("ar", "en", "fr")
        "IL" -> listOf("he", "ar", "en")
        "NL" -> listOf("nl", "en", "de")
        "SE", "NO", "DK", "FI", "IS" -> listOf("en", "sv", "no", "da", "fi")
        "RS", "HR", "BA", "ME" -> listOf("sr", "hr", "bs", "en")
        else -> emptyList()
    }
    val deviceLanguage = Locale.getDefault().language
    return (regional + deviceLanguage + "en").distinct().filter { code ->
        allLanguages.any { it.code == code }
    }
}
