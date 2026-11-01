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

class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { App() } }
}

@Composable
fun App() {
 val ctx = androidx.compose.ui.platform.LocalContext.current
 val prefs = remember { ctx.getSharedPreferences("flyuia", 0) }
 var tab by remember { mutableStateOf("Home") }
 var page by remember { mutableStateOf("") }
 var from by remember { mutableStateOf("Kyiv (KBP)") }
 var to by remember { mutableStateOf("Odesa (ODS)") }
 var date by remember { mutableStateOf("15 Nov 2026") }
 var round by remember { mutableStateOf(false) }
 var passengers by remember { mutableIntStateOf(1) }
 var booking by remember { mutableStateOf(prefs.getBoolean("booking", false)) }
 var first by remember { mutableStateOf(prefs.getString("first", "") ?: "") }
 var last by remember { mutableStateOf(prefs.getString("last", "") ?: "") }
 MaterialTheme(colorScheme = lightColorScheme(primary = Navy, secondary = Gold)) {
  Scaffold(containerColor = Color(0xFFF5F8FB), bottomBar = {
   if (page.isEmpty()) NavigationBar(containerColor = Color.White) {
    listOf("Home" to Icons.Default.Home, "Bookings" to Icons.Default.Luggage, "Status" to Icons.Default.Flight, "Profile" to Icons.Default.Person).forEach { (name, icon) ->
     NavigationBarItem(selected = tab == name, onClick = { tab = name }, icon = { Icon(icon, null) }, label = { Text(name, fontSize = 10.sp) })
    }
   }
  }) { pad ->
   Column(Modifier.fillMaxSize().padding(pad)) {
    if (page.isNotEmpty()) {
     Row(Modifier.fillMaxWidth().background(Navy).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
      Icon(Icons.Default.ArrowBack, "Back", tint = Color.White, modifier = Modifier.clickable { page = "" })
      Spacer(Modifier.width(14.dp)); Text(page, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
     }
     when (page) {
      "Check-in" -> CheckIn { booking = true; prefs.edit().putBoolean("booking", true).apply(); page = "Boarding pass" }
      "Boarding pass" -> Pass(first, last)
      "Panorama Club" -> Panorama()
      "Flight schedule" -> Schedule()
      "Search results" -> SimplePage("Search results", "This is a demonstration search. No live fares or reservations are available.")
      else -> SimplePage("About flyUIA", "Independent archival recreation. Not an official UIA service.")
     }
    } else when (tab) {
     "Home" -> Home(from, to, date, round, passengers, { from = it }, { to = it }, { val x=from; from=to; to=x }, { date=it }, { round=it }, { passengers=(passengers+1).coerceAtMost(9) }, { passengers=(passengers-1).coerceAtLeast(1) }, { page="Search results" }, { page="Check-in" }, { page="Flight schedule" })
     "Bookings" -> Bookings(booking, { page="Check-in" }, { page="Boarding pass" }, { booking=false; prefs.edit().putBoolean("booking",false).apply() })
     "Status" -> Status()
     else -> Profile(first,last,{ first=it; prefs.edit().putString("first",it).apply() },{ last=it; prefs.edit().putString("last",it).apply() },{ page="Panorama Club" },{ page="About flyUIA" })
    }
   }
  }
 }
}

@Composable fun Header() {
 Row(Modifier.fillMaxWidth().background(Navy).padding(20.dp), verticalAlignment=Alignment.CenterVertically) {
  Column(Modifier.weight(1f)) { Text("flyUIA", color=Color.White, fontSize=29.sp, fontWeight=FontWeight.Black); Text("UKRAINE INTERNATIONAL AIRLINES", color=Color(0xFFB9D8EF), fontSize=8.sp, letterSpacing=1.4.sp) }
  Box(Modifier.size(36.dp).background(Gold, RoundedCornerShape(50)), contentAlignment=Alignment.Center) { Icon(Icons.Default.Flight, null, tint=Navy) }
 }
}
@Composable fun Disclaimer() {
 Row(Modifier.fillMaxWidth().background(Color(0xFFFFF4D6), RoundedCornerShape(10.dp)).padding(11.dp), verticalAlignment=Alignment.Top) {
  Icon(Icons.Default.Info, null, tint=Navy, modifier=Modifier.size(18.dp)); Spacer(Modifier.width(8.dp))
  Text("ARCHIVAL DEMO · Not an official UIA service. No real bookings, payments, check-in or live flight data.", color=Navy, fontSize=11.sp)
 }
}
@Composable fun Home(from:String,to:String,date:String,round:Boolean,count:Int,sf:(String)->Unit,st:(String)->Unit,swap:()->Unit,sd:(String)->Unit,sr:(Boolean)->Unit,plus:()->Unit,minus:()->Unit,search:()->Unit,checkin:()->Unit,schedule:()->Unit) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
  Header()
  Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(14.dp)) {
   Disclaimer(); Text("Where to next?", color=Navy, fontSize=25.sp, fontWeight=FontWeight.Bold)
   Card(colors=CardDefaults.cardColors(containerColor=Color.White), shape=RoundedCornerShape(18.dp)) {
    Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(11.dp)) {
     Text("BOOK A FLIGHT", color=Blue, fontWeight=FontWeight.Bold, fontSize=12.sp)
     OutlinedTextField(from,sf,Modifier.fillMaxWidth(),label={Text("From")},leadingIcon={Icon(Icons.Default.FlightTakeoff,null)},singleLine=true)
     Text("⇅ Swap airports", color=Blue, modifier=Modifier.align(Alignment.End).clickable { swap() })
     OutlinedTextField(to,st,Modifier.fillMaxWidth(),label={Text("To")},leadingIcon={Icon(Icons.Default.FlightLand,null)},singleLine=true)
     Row(verticalAlignment=Alignment.CenterVertically) { FilterChip(!round,{sr(false)},{Text("One way")}); Spacer(Modifier.width(8.dp)); FilterChip(round,{sr(true)},{Text("Round trip")}) }
     OutlinedTextField(date,sd,Modifier.fillMaxWidth(),label={Text("Departure date")},leadingIcon={Icon(Icons.Default.CalendarMonth,null)},singleLine=true)
     Row(verticalAlignment=Alignment.CenterVertically) {
      Column(Modifier.weight(1f)) { Text("Passengers",fontWeight=FontWeight.SemiBold); Text("$count adult(s)",color=Muted,fontSize=12.sp) }
      IconButton(onClick=minus) { Icon(Icons.Default.RemoveCircleOutline,null) }; IconButton(onClick=plus) { Icon(Icons.Default.AddCircleOutline,null) }
     }
     Button(onClick=search,Modifier.fillMaxWidth().height(50.dp)) { Icon(Icons.Default.Search,null); Spacer(Modifier.width(8.dp)); Text("Search flights") }
    }
   }
   Row(horizontalArrangement=Arrangement.spacedBy(10.dp)) {
    ActionTile("Check-in",Icons.Default.FactCheck,checkin,Modifier.weight(1f))
    ActionTile("Timetable",Icons.Default.Schedule,schedule,Modifier.weight(1f))
   }
   Text("Travel with UIA spirit",color=Navy,fontWeight=FontWeight.Bold,fontSize=18.sp)
   Text("A nostalgic recreation of the passenger experience.",color=Muted,fontSize=13.sp)
  }
 }
}
@Composable fun ActionTile(label:String,icon:androidx.compose.ui.graphics.vector.ImageVector,click:()->Unit,modifier:Modifier) {
 Card(modifier.clickable { click() },colors=CardDefaults.cardColors(containerColor=Sky)) { Column(Modifier.fillMaxWidth().padding(16.dp),horizontalAlignment=Alignment.CenterHorizontally) { Icon(icon,null,tint=Navy); Text(label,color=Navy,fontWeight=FontWeight.SemiBold) } }
}
@Composable fun Page(title:String,subtitle:String?=null,body:@Composable ColumnScope.()->Unit) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
  Disclaimer(); Text(title,color=Navy,fontSize=25.sp,fontWeight=FontWeight.Bold); if(subtitle!=null) Text(subtitle,color=Muted,fontSize=13.sp); body()
 }
}
@Composable fun Bookings(has:Boolean,checkin:()->Unit,pass:()->Unit,remove:()->Unit) {
 Page("My bookings","Your trips in one place") {
  if(has) Card(colors=CardDefaults.cardColors(containerColor=Color.White)) { Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
   Text("DEMO BOOKING",color=Blue,fontWeight=FontWeight.Bold); Text("Kyiv  →  Odesa",color=Navy,fontSize=23.sp,fontWeight=FontWeight.Bold); Text("PS 057 · 15 Nov 2026",color=Muted); Text("Reference: DEMO26")
   Button(onClick=checkin,Modifier.fillMaxWidth()) { Text("Check in (demo)") }; OutlinedButton(onClick=pass,Modifier.fillMaxWidth()) { Text("View boarding pass") }; TextButton(onClick=remove) { Text("Remove demo booking") }
  } } else { Text("No bookings yet. Create a sample booking through demo check-in.",color=Muted); Button(onClick=checkin) { Text("Try demo check-in") } }
 }
}
@Composable fun CheckIn(done:()->Unit) {
 var code by remember { mutableStateOf("") }; var surname by remember { mutableStateOf("") }
 Page("Online check-in","Preview only — no airline connection") {
  OutlinedTextField(code,{code=it.uppercase()},Modifier.fillMaxWidth(),label={Text("Booking reference")})
  OutlinedTextField(surname,{surname=it},Modifier.fillMaxWidth(),label={Text("Passenger surname")})
  Button(onClick=done,Modifier.fillMaxWidth(),enabled=code.isNotBlank()&&surname.isNotBlank()) { Text("Continue demo check-in") }
 }
}
@Composable fun Pass(first:String,last:String) {
 Page("Boarding pass","Sample pass — not valid for travel") {
  Card(colors=CardDefaults.cardColors(containerColor=Color.White),shape=RoundedCornerShape(18.dp)) {
   Column(Modifier.padding(18.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
    Text("flyUIA     DEMO PASS",color=Navy,fontSize=22.sp,fontWeight=FontWeight.Black); HorizontalDivider()
    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("KBP\nKYIV",color=Navy,fontSize=24.sp,fontWeight=FontWeight.Black); Icon(Icons.Default.Flight,null,tint=Gold); Text("ODS\nODESA",color=Navy,fontSize=24.sp,fontWeight=FontWeight.Black) }
    HorizontalDivider(); Text("FLIGHT     PS 057       DATE     15 NOV 2026",color=Ink); Text("PASSENGER: ${first.ifBlank { "DEMO" }} ${last.ifBlank { "PASSENGER" }}",color=Ink)
    Box(Modifier.fillMaxWidth().height(85.dp).background(Sky,RoundedCornerShape(8.dp)),contentAlignment=Alignment.Center) { Text("▥ ▦ ▥ ▦ ▥ ▦ ▥\nSAMPLE BARCODE",color=Navy) }
    Text("NOT A VALID TRAVEL DOCUMENT",color=Color(0xFFB45309),fontWeight=FontWeight.Bold,fontSize=12.sp)
   }
  }
 }
}
@Composable fun Status() {
 var flight by remember { mutableStateOf("PS 057") }
 Page("Flight status","Illustrative information — not live") {
  OutlinedTextField(flight,{flight=it},Modifier.fillMaxWidth(),label={Text("Flight number")})
  Card(colors=CardDefaults.cardColors(containerColor=Color.White)) { Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(9.dp)) { Text(flight.uppercase(),color=Blue,fontWeight=FontWeight.Bold); Text("Kyiv (KBP)  →  Odesa (ODS)",color=Navy,fontWeight=FontWeight.Bold,fontSize=18.sp); Text("Scheduled · 10:35 → 11:40",color=Ink); AssistChip(onClick={},label={Text("SAMPLE DATA")}) } }
 }
}
@Composable fun Profile(first:String,last:String,sf:(String)->Unit,sl:(String)->Unit,club:()->Unit,about:()->Unit) {
 Page("My profile","Passenger details saved on this device") {
  OutlinedTextField(first,sf,Modifier.fillMaxWidth(),label={Text("First name")}); OutlinedTextField(last,sl,Modifier.fillMaxWidth(),label={Text("Surname")})
  Button(onClick=club,Modifier.fillMaxWidth()) { Icon(Icons.Default.CardMembership,null); Spacer(Modifier.width(8.dp)); Text("Panorama Club") }
  OutlinedButton(onClick=about,Modifier.fillMaxWidth()) { Text("About flyUIA") }
 }
}
@Composable fun Panorama() {
 Page("Panorama Club","Former UIA loyalty programme · local preview") {
  Card(colors=CardDefaults.cardColors(containerColor=Navy),shape=RoundedCornerShape(18.dp)) { Column(Modifier.padding(22.dp)) { Text("PANORAMA CLUB",color=Color.White,fontWeight=FontWeight.Bold); Text("2,450",color=Gold,fontSize=36.sp,fontWeight=FontWeight.Black); Text("DEMO MILES",color=Color.White); Text("Classic member · Sample account",color=Color.White) } }
  Text("Illustrative balance only. No loyalty account connection.",color=Muted)
 }
}
@Composable fun Schedule() {
 Page("Flight schedule","Sample timetable — not an operating schedule") {
  listOf("PS 057" to "Kyiv → Odesa · 10:35–11:40","PS 081" to "Kyiv → Lviv · 13:20–14:35","PS 101" to "Kyiv → Warsaw · 16:10–17:45").forEach { (n,r) ->
   Card(colors=CardDefaults.cardColors(containerColor=Color.White)) { Row(Modifier.fillMaxWidth().padding(16.dp),verticalAlignment=Alignment.CenterVertically) { Icon(Icons.Default.Flight,null,tint=Blue); Spacer(Modifier.width(12.dp)); Column { Text(n,color=Navy,fontWeight=FontWeight.Bold); Text(r,color=Muted,fontSize=12.sp) } } }
  }
 }
}
@Composable fun SimplePage(title:String,text:String) { Page(title) { Text(text,color=Muted) } }
