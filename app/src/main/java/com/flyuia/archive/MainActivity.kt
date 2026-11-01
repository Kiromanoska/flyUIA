package com.flyuia.archive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF063B70)
private val Blue = Color(0xFF087CC1)
private val Sky = Color(0xFFEAF5FC)
private val Gold = Color(0xFFFFD329)
private val Muted = Color(0xFF71869A)
private val TextInk = Color(0xFF17324D)

private data class FlightRoute(val from: String, val to: String, val number: String, val time: String, val duration: String, val fare: Int)
private data class Itinerary(val first: FlightRoute, val second: FlightRoute? = null) {
    val fare: Int get() = first.fare + (second?.fare ?: 0)
    val duration: String get() = if (second == null) first.duration else "${first.duration} + ${second.duration}"
}

private val routes = listOf(
    FlightRoute("KBP","WAW","PS 801","08:10","1h 35m",95), FlightRoute("KBP","PRG","PS 807","10:20","2h 05m",125),
    FlightRoute("KBP","BUD","PS 811","12:15","1h 40m",105), FlightRoute("KBP","VIE","PS 815","14:30","2h 00m",135),
    FlightRoute("KBP","BER","PS 821","07:45","2h 10m",145), FlightRoute("KBP","FRA","PS 825","09:50","2h 35m",165),
    FlightRoute("KBP","CDG","PS 831","11:25","3h 05m",185), FlightRoute("KBP","AMS","PS 835","15:10","2h 55m",175),
    FlightRoute("KBP","FCO","PS 841","08:55","2h 50m",155), FlightRoute("KBP","BCN","PS 845","13:40","3h 35m",195),
    FlightRoute("WAW","LHR","PS 901","07:20","2h 35m",110), FlightRoute("WAW","FCO","PS 905","09:30","2h 25m",90),
    FlightRoute("WAW","BCN","PS 909","12:10","3h 00m",105), FlightRoute("WAW","ATH","PS 913","16:25","2h 35m",120),
    FlightRoute("PRG","CDG","PS 921","08:00","1h 50m",85), FlightRoute("PRG","FCO","PS 925","11:10","1h 55m",80),
    FlightRoute("VIE","DXB","PS 931","10:40","5h 35m",245), FlightRoute("VIE","LIS","PS 935","13:00","3h 45m",165),
    FlightRoute("BER","AMS","PS 941","08:15","1h 30m",75), FlightRoute("BER","LHR","PS 945","12:50","2h 00m",95),
    FlightRoute("FRA","JFK","PS 951","10:30","8h 45m",420), FlightRoute("FRA","YYZ","PS 955","13:15","8h 35m",405),
    FlightRoute("FRA","SIN","PS 959","21:40","12h 10m",520), FlightRoute("CDG","NRT","PS 961","13:20","12h 15m",590),
    FlightRoute("CDG","JFK","PS 965","17:10","8h 20m",390), FlightRoute("AMS","BKK","PS 971","14:45","11h 20m",475),
    FlightRoute("FCO","ATH","PS 981","09:05","2h 00m",85), FlightRoute("FCO","DXB","PS 985","15:30","5h 40m",220),
    FlightRoute("BCN","LIS","PS 991","08:35","1h 55m",70), FlightRoute("MAD","LIS","PS 995","10:15","1h 20m",65),
    FlightRoute("IST","DXB","PS 1001","11:00","4h 25m",180), FlightRoute("IST","TBS","PS 1005","14:10","2h 15m",95),
    FlightRoute("IST","BKK","PS 1009","21:25","9h 20m",330), FlightRoute("DXB","SIN","PS 1013","03:30","7h 20m",290),
    FlightRoute("DXB","JFK","PS 1017","08:45","14h 10m",610), FlightRoute("DOH","BKK","PS 1021","09:30","6h 25m",255),
    FlightRoute("ATH","CAI","PS 1025","12:40","1h 55m",100), FlightRoute("SOF","FCO","PS 1029","07:50","1h 40m",80),
    FlightRoute("VAR","IST","PS 1033","10:05","1h 00m",65), FlightRoute("BEG","ZAG","PS 1037","16:10","1h 05m",55),
    FlightRoute("BUD","AMS","PS 1041","13:35","2h 05m",115), FlightRoute("LHR","JFK","PS 1045","10:00","7h 55m",360),
    FlightRoute("LIS","YYZ","PS 1049","11:20","7h 50m",345), FlightRoute("NRT","BKK","PS 1053","09:40","6h 40m",270)
)

private fun airport(code: String) = allAirportByCode[code]?.label ?: code
private fun tier(points: Int) = when {
    points >= 12000 -> "Platinum"
    points >= 5000 -> "Gold"
    points >= 1000 -> "Silver"
    else -> "Classic"
}
private fun discount(points: Int) = when {
    points >= 12000 -> 12
    points >= 5000 -> 8
    points >= 1000 -> 5
    else -> 0
}
private fun nextTier(points: Int) = when {
    points >= 12000 -> 12000
    points >= 5000 -> 12000
    points >= 1000 -> 5000
    else -> 1000
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadGlobalAirportCatalog(this)
        setContent { FlyUIAApp() }
    }
}

@Composable
fun FlyUIAApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = remember { context.getSharedPreferences("flyuia", 0) }
    var tab by remember { mutableStateOf("Главная") }
    var page by remember { mutableStateOf("") }
    var from by remember { mutableStateOf("KBP") }
    var to by remember { mutableStateOf("FCO") }
    var travelDate by remember { mutableStateOf("15 Nov 2026") }
    var passengers by remember { mutableIntStateOf(1) }
    var roundTrip by remember { mutableStateOf(false) }
    var points by remember { mutableIntStateOf(prefs.getInt("club_points", 0)) }
    var earned by remember { mutableIntStateOf(prefs.getInt("club_earned", 0)) }
    var first by remember { mutableStateOf(prefs.getString("first", "") ?: "") }
    var last by remember { mutableStateOf(prefs.getString("last", "") ?: "") }
    var savedFlight by remember { mutableStateOf(prefs.getString("saved_flight", "") ?: "") }
    var savedFare by remember { mutableIntStateOf(prefs.getInt("saved_fare", 0)) }
    var savedPoints by remember { mutableIntStateOf(prefs.getInt("saved_points", 0)) }
    var tripCompleted by remember { mutableStateOf(prefs.getBoolean("trip_completed", false)) }

    MaterialTheme(colorScheme = lightColorScheme(primary = Navy, secondary = Gold)) {
        Scaffold(
            containerColor = Color(0xFFF5F8FB),
            bottomBar = {
                if (page.isEmpty()) NavigationBar(containerColor = Color.White) {
                    listOf("Главная" to Icons.Default.Home, "Мои поездки" to Icons.Default.Luggage, "Статус" to Icons.Default.Flight, "Профиль" to Icons.Default.Person).forEach { (name, icon) ->
                        NavigationBarItem(selected = tab == name, onClick = { tab = name }, icon = { Icon(icon, null) }, label = { Text(name, fontSize = 10.sp) })
                    }
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                if (page.isNotEmpty()) {
                    Row(Modifier.fillMaxWidth().background(Navy).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White, modifier = Modifier.clickable { page = "" })
                        Spacer(Modifier.width(14.dp))
                        Text(page, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    }
                    when (page) {
                        "Результаты поиска" -> SearchResults(from, to, passengers, points) { flight ->
                            savedFlight = if (flight.second == null) "${airport(flight.first.from)} → ${airport(flight.first.to)} · ${flight.first.number}" else "${airport(flight.first.from)} → ${airport(flight.first.to)} → ${airport(flight.second!!.to)} · ${flight.first.number} + ${flight.second!!.number}"
                            savedFare = flight.fare
                            savedPoints = flight.fare * 5
                            tripCompleted = false
                            prefs.edit().putString("saved_flight", savedFlight).putInt("saved_fare", savedFare).putInt("saved_points", savedPoints).putBoolean("trip_completed", false).apply()
                            page = "Подтверждение поездки"
                        }
                        "Подтверждение поездки" -> BookingSummary(savedFlight, savedFare, savedPoints, discount(points), { page = "" })
                        "Panorama Club" -> PanoramaClub(points, earned) { newPoints ->
                            points = newPoints
                            earned += newPoints
                            prefs.edit().putInt("club_points", points).putInt("club_earned", earned).apply()
                        }
                        "Расписание" -> FlightSchedule()
                        "Посадочный талон" -> BoardingPass(first, last, savedFlight)
                        "О flyUIA" -> About()
                        else -> About()
                    }
                } else when (tab) {
                    "Главная" -> HomeScreen(
                        from, to, travelDate, passengers, roundTrip,
                        { from = it }, { to = it }, { val swap = from; from = to; to = swap },
                        { travelDate = it }, { passengers = (passengers + 1).coerceAtMost(9) },
                        { passengers = (passengers - 1).coerceAtLeast(1) }, { roundTrip = it },
                        { page = "Результаты поиска" }, { page = "Расписание" }
                    )
                    "Мои поездки" -> MyTrips(savedFlight, savedFare, savedPoints, tripCompleted,
                        { page = "Посадочный талон" },
                        {
                            if (!tripCompleted && savedPoints > 0) {
                                points += savedPoints
                                earned += savedPoints
                                tripCompleted = true
                                prefs.edit().putInt("club_points", points).putInt("club_earned", earned).putBoolean("trip_completed", true).apply()
                            }
                        },
                        {
                            savedFlight = ""; savedFare = 0; savedPoints = 0; tripCompleted = false
                            prefs.edit().remove("saved_flight").putInt("saved_fare", 0).putInt("saved_points", 0).putBoolean("trip_completed", false).apply()
                        })
                    "Статус" -> FlightStatus()
                    else -> ProfileScreen(first, last, { first = it; prefs.edit().putString("first", it).apply() }, { last = it; prefs.edit().putString("last", it).apply() }, { page = "Panorama Club" }, { page = "О flyUIA" })
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Row(Modifier.fillMaxWidth().background(Navy).padding(horizontal = 20.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text("flyUIA", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Black)
            Text("UKRAINE INTERNATIONAL AIRLINES", color = Color(0xFFB9D8EF), fontSize = 8.sp, letterSpacing = 1.4.sp)
        }
        Box(Modifier.size(38.dp).background(Gold, RoundedCornerShape(50)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Flight, null, tint = Navy) }
    }
}

@Composable
private fun Screen(title: String, subtitle: String? = null, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(title, color = Navy, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        if (subtitle != null) Text(subtitle, color = Muted, fontSize = 13.sp)
        content()
    }
}

@Composable
private fun AirportPicker(label: String, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember(selected) { mutableStateOf(allAirportByCode[selected]?.label ?: selected) }
    Column {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it; expanded = true },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true,
            trailingIcon = { IconButton(onClick = { expanded = !expanded }) { Icon(Icons.Default.ExpandMore, null) } }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.heightIn(max = 300.dp).background(Color.White)) {
            allAirports.filter { it.label.contains(query, true) || it.code.contains(query, true) || it.name.contains(query, true) }.take(12).forEach { item ->
                DropdownMenuItem(text = { Text(item.label) }, onClick = { onSelect(item.code); query = item.label; expanded = false })
            }
        }
    }
}

@Composable
private fun HomeScreen(
    from: String, to: String, date: String, passengers: Int, round: Boolean,
    setFrom: (String) -> Unit, setTo: (String) -> Unit, swap: () -> Unit, setDate: (String) -> Unit,
    plus: () -> Unit, minus: () -> Unit, setRound: (Boolean) -> Unit, search: () -> Unit, schedule: () -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Header()
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text("Открой мир вместе с flyUIA", color = Navy, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("ПОИСК АВИАБИЛЕТОВ", color = Blue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    AirportPicker("Откуда", from, setFrom)
                    Text("⇅ Поменять направление", color = Blue, modifier = Modifier.align(Alignment.End).clickable { swap() })
                    AirportPicker("Куда", to, setTo)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FilterChip(selected = !round, onClick = { setRound(false) }, label = { Text("В одну сторону") })
                        Spacer(Modifier.width(8.dp))
                        FilterChip(selected = round, onClick = { setRound(true) }, label = { Text("Туда и обратно") })
                    }
                    OutlinedTextField(date, setDate, Modifier.fillMaxWidth(), label = { Text("Дата вылета") }, leadingIcon = { Icon(Icons.Default.CalendarMonth, null) }, singleLine = true)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text("Пассажиры", fontWeight = FontWeight.SemiBold); Text("$passengers взрослый(ых)", color = Muted, fontSize = 12.sp) }
                        IconButton(onClick = minus) { Icon(Icons.Default.RemoveCircleOutline, null) }
                        IconButton(onClick = plus) { Icon(Icons.Default.AddCircleOutline, null) }
                    }
                    Button(onClick = search, Modifier.fillMaxWidth().height(50.dp), enabled = from != to) {
                        Icon(Icons.Default.Search, null); Spacer(Modifier.width(8.dp)); Text("Найти рейсы")
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickTile("Расписание", Icons.Default.Schedule, schedule, Modifier.weight(1f))
                QuickTile("${allAirports.size} аэропортов мира", Icons.Default.Public, schedule, Modifier.weight(1f))
            }
            Text("Популярные направления", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            listOf("KBP" to "FCO", "WAW" to "BCN", "FRA" to "JFK", "IST" to "DXB").forEach { (a, b) ->
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text("${airport(a)}  →  ${airport(b)}", color = Navy, fontWeight = FontWeight.Bold); Text("Популярный маршрут", color = Muted, fontSize = 11.sp) }
                        Icon(Icons.Default.FlightTakeoff, null, tint = Blue)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickTile(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, action: () -> Unit, modifier: Modifier) {
    Card(modifier.clickable { action() }, colors = CardDefaults.cardColors(containerColor = Sky)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(icon, null, tint = Navy); Spacer(Modifier.width(7.dp)); Text(label, color = Navy, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SearchResults(from: String, to: String, passengers: Int, points: Int, choose: (Itinerary) -> Unit) {
    val direct = routes.filter { it.from == from && it.to == to }.map { Itinerary(it) }
    val connecting = if (direct.isNotEmpty()) emptyList() else {
        routes.filter { it.from == from }.flatMap { first ->
            routes.filter { it.from == first.to && it.to == to }.map { second -> Itinerary(first, second) }
        }.take(6)
    }
    val results = direct + connecting
    Screen("Рейсы: ${airport(from)} → ${airport(to)}", "$passengers пассажир(а) · стоимость указана за одного") {
        if (results.isEmpty()) {
            Text("На выбранную дату вариантов пока нет. Попробуйте другой город или направление.", color = Muted)
            Text("Доступно аэропортов: ${allAirports.size} · направлений: ${routes.size}", color = Blue, fontWeight = FontWeight.SemiBold)
        } else {
            Text("Найдено вариантов: ${results.size}", color = Blue, fontWeight = FontWeight.SemiBold)
            results.forEach { item ->
                val total = (item.fare * passengers * (100 - discount(points)) / 100)
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(if (item.second == null) "ПРЯМОЙ РЕЙС" else "С ПЕРЕСАДКОЙ", color = Blue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.weight(1f)); Text("€$total", color = Navy, fontSize = 23.sp, fontWeight = FontWeight.Black)
                        }
                        Text(if (item.second == null) "${airport(item.first.from)}  →  ${airport(item.first.to)}" else "${airport(item.first.from)}  →  ${airport(item.first.to)}  →  ${airport(item.second!!.to)}", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text("${item.first.number}${item.second?.let { " · ${it.number}" } ?: ""}  ·  ${item.duration}", color = Muted, fontSize = 12.sp)
                        if (discount(points) > 0) Text("С учётом скидки Panorama Club: ${discount(points)}%", color = Color(0xFF16834A), fontSize = 12.sp)
                        Button(onClick = { choose(item) }, Modifier.fillMaxWidth()) { Text("Выбрать рейс") }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingSummary(route: String, fare: Int, pointsToEarn: Int, clubDiscount: Int, close: () -> Unit) {
    Screen("Ваш маршрут", "Проверьте детали перед сохранением поездки") {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(route, color = Navy, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("Стоимость до скидки: €$fare", color = TextInk)
                Text("Уровневая скидка Panorama Club: $clubDiscount%", color = Muted)
                Text("Баллы к начислению после поездки: $pointsToEarn", color = Blue, fontWeight = FontWeight.SemiBold)
                Text("Маршрут сохранён в разделе «Мои поездки».", color = Color(0xFF16834A))
                Button(onClick = close, Modifier.fillMaxWidth()) { Text("Готово") }
            }
        }
    }
}

@Composable
private fun MyTrips(route: String, fare: Int, pendingPoints: Int, completed: Boolean, pass: () -> Unit, complete: () -> Unit, remove: () -> Unit) {
    Screen("Мои поездки", "Сохранённые маршруты") {
        if (route.isBlank()) {
            Text("У вас пока нет сохранённых поездок.", color = Muted)
        } else {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("FLYUIA · ITINERARY", color = Blue, fontWeight = FontWeight.Bold)
                    Text(route, color = Navy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Стоимость: €$fare", color = TextInk)
                    Text(if (completed) "Поездка завершена · баллы начислены" else "Ожидает завершения поездки · $pendingPoints баллов", color = Muted)
                    OutlinedButton(onClick = pass, Modifier.fillMaxWidth()) { Text("Посмотреть маршрут") }
                    if (!completed) Button(onClick = complete, Modifier.fillMaxWidth()) { Text("Завершить поездку и начислить баллы") }
                    TextButton(onClick = remove) { Text("Удалить маршрут") }
                }
            }
        }
    }
}

@Composable
private fun BoardingPass(first: String, last: String, route: String) {
    Screen("Детали поездки", "Информация о сохранённом маршруте") {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("flyUIA", color = Navy, fontSize = 25.sp, fontWeight = FontWeight.Black)
                HorizontalDivider()
                Text(route.ifBlank { "Маршрут не выбран" }, color = Navy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Пассажир: ${first.ifBlank { "Имя" }} ${last.ifBlank { "Фамилия" }}", color = TextInk)
                Text("Сведения о регистрации и посадочном талоне появятся после подключения системы бронирования.", color = Muted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun FlightStatus() {
    var query by remember { mutableStateOf("") }
    Screen("Статус рейса", "Поиск по номеру рейса") {
        OutlinedTextField(query, { query = it.uppercase() }, Modifier.fillMaxWidth(), label = { Text("Номер рейса") }, singleLine = true)
        val matches = routes.filter { query.isBlank() || it.number.contains(query, true) }
        matches.take(10).forEach { flight ->
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(flight.number, color = Blue, fontWeight = FontWeight.Bold)
                        Text("${airport(flight.from)} → ${airport(flight.to)}", color = Navy, fontWeight = FontWeight.SemiBold)
                        Text("Плановое время: ${flight.time} · ${flight.duration}", color = Muted, fontSize = 11.sp)
                    }
                    Icon(Icons.Default.Flight, null, tint = Blue)
                }
            }
        }
        Text("Фактическое табло аэропортов пока не подключено.", color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun ProfileScreen(first: String, last: String, setFirst: (String) -> Unit, setLast: (String) -> Unit, club: () -> Unit, about: () -> Unit) {
    Screen("Профиль", "Данные пассажира") {
        OutlinedTextField(first, setFirst, Modifier.fillMaxWidth(), label = { Text("Имя") })
        OutlinedTextField(last, setLast, Modifier.fillMaxWidth(), label = { Text("Фамилия") })
        Button(onClick = club, Modifier.fillMaxWidth()) { Icon(Icons.Default.CardMembership, null); Spacer(Modifier.width(8.dp)); Text("Panorama Club") }
        OutlinedButton(onClick = about, Modifier.fillMaxWidth()) { Text("О flyUIA") }
    }
}

@Composable
private fun PanoramaClub(points: Int, lifetimeEarned: Int, addPoints: (Int) -> Unit) {
    val currentTier = tier(points)
    val next = nextTier(points)
    val progress = if (next == 12000 && points >= 12000) 1f else (points.toFloat() / next).coerceIn(0f, 1f)
    Screen("Panorama Club", "Больше путешествий — больше привилегий") {
        Card(colors = CardDefaults.cardColors(containerColor = Navy), shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("PANORAMA CLUB", color = Color.White, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                Text(currentTier.uppercase(), color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(points.toString(), color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Black)
                Text("баллов на вашем счёте", color = Color.White)
                LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth(), color = Gold, trackColor = Color(0xFF426487))
                Text(if (points >= 12000) "Максимальный уровень достигнут" else "До следующего уровня: ${(next - points).coerceAtLeast(0)} баллов", color = Color.White, fontSize = 12.sp)
            }
        }
        Text("Ваши привилегии", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        listOf(
            Triple("Classic", "0 баллов", "Базовое участие · специальные предложения"),
            Triple("Silver", "1 000 баллов", "Скидка 5% · приоритетные предложения"),
            Triple("Gold", "5 000 баллов", "Скидка 8% · дополнительный багаж по условиям тарифа"),
            Triple("Platinum", "12 000 баллов", "Скидка 12% · приоритетное обслуживание и расширенные привилегии")
        ).forEach { (name, threshold, benefit) ->
            Card(colors = CardDefaults.cardColors(containerColor = if (name == currentTier) Sky else Color.White)) {
                Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(name, color = Navy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.weight(1f))
                        Text(threshold, color = Blue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Text(benefit, color = Muted, fontSize = 12.sp)
                }
            }
        }
        Text("Как накапливать баллы", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("5 баллов за каждый 1 € стоимости перелёта. Баллы начисляются после завершения поездки. Уровень зависит от текущего баланса.", color = Muted)
        Text("Всего заработано за всё время: $lifetimeEarned баллов", color = TextInk, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FlightSchedule() {
    Screen("Маршрутная сеть", "${allAirports.size} аэропортов · ${routes.size} направлений") {
        allAirports.forEach { item ->
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) { Text(item.city, color = Navy, fontWeight = FontWeight.SemiBold); Text(item.country, color = Muted, fontSize = 11.sp) }
                    Text(item.code, color = Blue, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun About() {
    Screen("О flyUIA", "Ukraine International Airlines") {
        Text("flyUIA объединяет направления, поиск перелётов, управление поездками и программу лояльности Panorama Club.", color = TextInk)
        Text("Маршрутная сеть и тарифы в приложении представлены как встроенный каталог. Онлайн-бронирование, оплата, регистрация и оперативное табло требуют подключения авиационных систем.", color = Muted, fontSize = 12.sp)
    }
}
