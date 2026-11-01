package com.flyuia.archive

/**
 * Additional curated UI translations. Untranslated phrases intentionally fall back
 * to English rather than displaying a misleading or machine-corrupted translation.
 */
internal fun extendedUiText(source: String, code: String): String? {
    val common = mapOf(
        "es" to mapOf(
            "Главная" to "Inicio", "Мои поездки" to "Mis viajes", "Статус" to "Estado del vuelo",
            "Профиль" to "Perfil", "Назад" to "Atrás", "Расписание" to "Horario",
            "Посадочный талон" to "Tarjeta de embarque", "О flyUIA" to "Acerca de flyUIA",
            "Откуда" to "Origen", "Куда" to "Destino", "Дата вылета" to "Fecha de salida",
            "Пассажиры" to "Pasajeros", "Найти рейсы" to "Buscar vuelos", "Выбрать рейс" to "Seleccionar vuelo",
            "Готово" to "Listo", "Имя" to "Nombre", "Фамилия" to "Apellido",
            "Язык" to "Idioma", "Выберите язык" to "Elige un idioma", "Поиск языка" to "Buscar idiomas",
            "Рекомендуемые" to "Recomendados", "Закрыть" to "Cerrar",
            "В одну сторону" to "Solo ida", "Туда и обратно" to "Ida y vuelta",
            "Весь мир · аэропорты" to "Aeropuertos de todo el mundo",
            "Город, страна, название или код" to "Ciudad, país, nombre o código"
        ),
        "fr" to mapOf(
            "Главная" to "Accueil", "Мои поездки" to "Mes voyages", "Статус" to "Statut du vol",
            "Профиль" to "Profil", "Назад" to "Retour", "Расписание" to "Horaires",
            "Посадочный талон" to "Carte d’embarquement", "О flyUIA" to "À propos de flyUIA",
            "Откуда" to "Départ", "Куда" to "Destination", "Дата вылета" to "Date de départ",
            "Пассажиры" to "Passagers", "Найти рейсы" to "Rechercher des vols", "Выбрать рейс" to "Choisir le vol",
            "Готово" to "Terminé", "Имя" to "Prénom", "Фамилия" to "Nom",
            "Язык" to "Langue", "Выберите язык" to "Choisir une langue", "Поиск языка" to "Rechercher une langue",
            "Рекомендуемые" to "Recommandés", "Закрыть" to "Fermer",
            "В одну сторону" to "Aller simple", "Туда и обратно" to "Aller-retour",
            "Весь мир · аэропорты" to "Aéroports du monde entier",
            "Город, страна, название или код" to "Ville, pays, nom ou code"
        ),
        "de" to mapOf(
            "Главная" to "Startseite", "Мои поездки" to "Meine Reisen", "Статус" to "Flugstatus",
            "Профиль" to "Profil", "Назад" to "Zurück", "Расписание" to "Flugplan",
            "Посадочный талон" to "Bordkarte", "О flyUIA" to "Über flyUIA",
            "Откуда" to "Von", "Куда" to "Nach", "Дата вылета" to "Abflugdatum",
            "Пассажиры" to "Passagiere", "Найти рейсы" to "Flüge suchen", "Выбрать рейс" to "Flug auswählen",
            "Готово" to "Fertig", "Имя" to "Vorname", "Фамилия" to "Nachname",
            "Язык" to "Sprache", "Выберите язык" to "Sprache auswählen", "Поиск языка" to "Sprache suchen",
            "Рекомендуемые" to "Empfohlen", "Закрыть" to "Schließen",
            "В одну сторону" to "Einfache Fahrt", "Туда и обратно" to "Hin- und Rückflug",
            "Весь мир · аэропорты" to "Flughäfen weltweit",
            "Город, страна, название или код" to "Stadt, Land, Name oder Code"
        ),
        "it" to mapOf(
            "Главная" to "Home", "Мои поездки" to "I miei viaggi", "Статус" to "Stato del volo",
            "Профиль" to "Profilo", "Назад" to "Indietro", "Расписание" to "Orari",
            "Посадочный талон" to "Carta d’imbarco", "О flyUIA" to "Informazioni su flyUIA",
            "Откуда" to "Da", "Куда" to "A", "Дата вылета" to "Data di partenza",
            "Пассажиры" to "Passeggeri", "Найти рейсы" to "Cerca voli", "Выбрать рейс" to "Seleziona volo",
            "Готово" to "Fatto", "Имя" to "Nome", "Фамилия" to "Cognome",
            "Язык" to "Lingua", "Выберите язык" to "Scegli una lingua", "Поиск языка" to "Cerca lingua",
            "Рекомендуемые" to "Consigliate", "Закрыть" to "Chiudi",
            "В одну сторону" to "Solo andata", "Туда и обратно" to "Andata e ritorno",
            "Весь мир · аэропорты" to "Aeroporti in tutto il mondo",
            "Город, страна, название или код" to "Città, paese, nome o codice"
        ),
        "pt" to mapOf(
            "Главная" to "Início", "Мои поездки" to "Minhas viagens", "Статус" to "Estado do voo",
            "Профиль" to "Perfil", "Назад" to "Voltar", "Расписание" to "Horários",
            "Посадочный талон" to "Cartão de embarque", "О flyUIA" to "Sobre a flyUIA",
            "Откуда" to "Origem", "Куда" to "Destino", "Дата вылета" to "Data de partida",
            "Пассажиры" to "Passageiros", "Найти рейсы" to "Pesquisar voos", "Выбрать рейс" to "Selecionar voo",
            "Готово" to "Concluído", "Имя" to "Nome", "Фамилия" to "Sobrenome",
            "Язык" to "Idioma", "Выберите язык" to "Escolher idioma", "Поиск языка" to "Pesquisar idioma",
            "Рекомендуемые" to "Recomendados", "Закрыть" to "Fechar",
            "В одну сторону" to "Só ida", "Туда и обратно" to "Ida e volta",
            "Весь мир · аэропорты" to "Aeroportos em todo o mundo",
            "Город, страна, название или код" to "Cidade, país, nome ou código"
        ),
        "pl" to mapOf(
            "Главная" to "Strona główna", "Мои поездки" to "Moje podróże", "Статус" to "Status lotu",
            "Профиль" to "Profil", "Назад" to "Wstecz", "Расписание" to "Rozkład lotów",
            "Посадочный талон" to "Karta pokładowa", "О flyUIA" to "O flyUIA",
            "Откуда" to "Z", "Куда" to "Do", "Дата вылета" to "Data wylotu",
            "Пассажиры" to "Pasażerowie", "Найти рейсы" to "Szukaj lotów", "Выбрать рейс" to "Wybierz lot",
            "Готово" to "Gotowe", "Имя" to "Imię", "Фамилия" to "Nazwisko",
            "Язык" to "Język", "Выберите язык" to "Wybierz język", "Поиск языка" to "Szukaj języka",
            "Рекомендуемые" to "Polecane", "Закрыть" to "Zamknij",
            "В одну сторону" to "W jedną stronę", "Туда и обратно" to "W obie strony",
            "Весь мир · аэропорты" to "Lotniska na całym świecie",
            "Город, страна, название или код" to "Miasto, kraj, nazwa lub kod"
        ),
        "tr" to mapOf(
            "Главная" to "Ana sayfa", "Мои поездки" to "Seyahatlerim", "Статус" to "Uçuş durumu",
            "Профиль" to "Profil", "Назад" to "Geri", "Расписание" to "Uçuş tarifesi",
            "Посадочный талон" to "Biniş kartı", "О flyUIA" to "flyUIA hakkında",
            "Откуда" to "Nereden", "Куда" to "Nereye", "Дата вылета" to "Kalkış tarihi",
            "Пассажиры" to "Yolcular", "Найти рейсы" to "Uçuş ara", "Выбрать рейс" to "Uçuşu seç",
            "Готово" to "Tamam", "Имя" to "Ad", "Фамилия" to "Soyadı",
            "Язык" to "Dil", "Выберите язык" to "Dil seçin", "Поиск языка" to "Dil ara",
            "Рекомендуемые" to "Önerilen", "Закрыть" to "Kapat",
            "В одну сторону" to "Tek yön", "Туда и обратно" to "Gidiş-dönüş",
            "Весь мир · аэропорты" to "Dünya genelindeki havalimanları",
            "Город, страна, название или код" to "Şehir, ülke, ad veya kod"
        ),
        "ro" to mapOf(
            "Главная" to "Acasă", "Мои поездки" to "Călătoriile mele", "Статус" to "Starea zborului",
            "Профиль" to "Profil", "Назад" to "Înapoi", "Расписание" to "Program",
            "Посадочный талон" to "Carte de îmbarcare", "О flyUIA" to "Despre flyUIA",
            "Откуда" to "De la", "Куда" to "Către", "Дата вылета" to "Data plecării",
            "Пассажиры" to "Pasageri", "Найти рейсы" to "Caută zboruri", "Выбрать рейс" to "Alege zborul",
            "Готово" to "Gata", "Имя" to "Prenume", "Фамилия" to "Nume",
            "Язык" to "Limbă", "Выберите язык" to "Alege limba", "Поиск языка" to "Caută limba",
            "Рекомендуемые" to "Recomandate", "Закрыть" to "Închide",
            "В одну сторону" to "Dus", "Туда и обратно" to "Dus-întors",
            "Весь мир · аэропорты" to "Aeroporturi din întreaga lume",
            "Город, страна, название или код" to "Oraș, țară, nume sau cod"
        ),
        "el" to mapOf(
            "Главная" to "Αρχική", "Мои поездки" to "Τα ταξίδια μου", "Статус" to "Κατάσταση πτήσης",
            "Профиль" to "Προφίλ", "Назад" to "Πίσω", "Расписание" to "Πρόγραμμα",
            "Посадочный талон" to "Κάρτα επιβίβασης", "О flyUIA" to "Σχετικά με τη flyUIA",
            "Откуда" to "Από", "Куда" to "Προς", "Дата вылета" to "Ημερομηνία αναχώρησης",
            "Пассажиры" to "Επιβάτες", "Найти рейсы" to "Αναζήτηση πτήσεων", "Выбрать рейс" to "Επιλογή πτήσης",
            "Готово" to "Έγινε", "Имя" to "Όνομα", "Фамилия" to "Επώνυμο",
            "Язык" to "Γλώσσα", "Выберите язык" to "Επιλέξτε γλώσσα", "Поиск языка" to "Αναζήτηση γλώσσας",
            "Рекомендуемые" to "Προτεινόμενα", "Закрыть" to "Κλείσιμο",
            "В одну сторону" to "Απλή μετάβαση", "Туда и обратно" to "Με επιστροφή",
            "Весь мир · аэропорты" to "Αεροδρόμια σε όλο τον κόσμο",
            "Город, страна, название или код" to "Πόλη, χώρα, όνομα ή κωδικός"
        ),
        "nl" to mapOf(
            "Главная" to "Home", "Мои поездки" to "Mijn reizen", "Статус" to "Vluchtstatus",
            "Профиль" to "Profiel", "Назад" to "Terug", "Расписание" to "Dienstregeling",
            "Посадочный талон" to "Instapkaart", "О flyUIA" to "Over flyUIA",
            "Откуда" to "Van", "Куда" to "Naar", "Дата вылета" to "Vertrekdatum",
            "Пассажиры" to "Passagiers", "Найти рейсы" to "Vluchten zoeken", "Выбрать рейс" to "Vlucht kiezen",
            "Готово" to "Klaar", "Имя" to "Voornaam", "Фамилия" to "Achternaam",
            "Язык" to "Taal", "Выберите язык" to "Kies een taal", "Поиск языка" to "Taal zoeken",
            "Рекомендуемые" to "Aanbevolen", "Закрыть" to "Sluiten",
            "В одну сторону" to "Enkele reis", "Туда и обратно" to "Retour",
            "Весь мир · аэропорты" to "Wereldwijde luchthavens",
            "Город, страна, название или код" to "Stad, land, naam of code"
        ),
        "sv" to mapOf(
            "Главная" to "Hem", "Мои поездки" to "Mina resor", "Статус" to "Flygstatus",
            "Профиль" to "Profil", "Назад" to "Tillbaka", "Расписание" to "Tidtabell",
            "Посадочный талон" to "Boardingkort", "О flyUIA" to "Om flyUIA",
            "Откуда" to "Från", "Куда" to "Till", "Дата вылета" to "Avresedatum",
            "Пассажиры" to "Passagerare", "Найти рейсы" to "Sök flyg", "Выбрать рейс" to "Välj flyg",
            "Готово" to "Klart", "Имя" to "Förnamn", "Фамилия" to "Efternamn",
            "Язык" to "Språk", "Выберите язык" to "Välj språk", "Поиск языка" to "Sök språk",
            "Рекомендуемые" to "Rekommenderade", "Закрыть" to "Stäng",
            "В одну сторону" to "Enkelresa", "Туда и обратно" to "Tur och retur",
            "Весь мир · аэропорты" to "Flygplatser världen över",
            "Город, страна, название или код" to "Stad, land, namn eller kod"
        ),
        "ar" to mapOf(
            "Главная" to "الرئيسية", "Мои поездки" to "رحلاتي", "Статус" to "حالة الرحلة",
            "Профиль" to "الملف الشخصي", "Назад" to "رجوع", "Расписание" to "الجدول",
            "Посадочный талон" to "بطاقة الصعود", "О flyUIA" to "حول flyUIA",
            "Откуда" to "من", "Куда" to "إلى", "Дата вылета" to "تاريخ المغادرة",
            "Пассажиры" to "المسافرون", "Найти рейсы" to "البحث عن رحلات", "Выбрать рейс" to "اختيار الرحلة",
            "Готово" to "تم", "Имя" to "الاسم", "Фамилия" to "اسم العائلة",
            "Язык" to "اللغة", "Выберите язык" to "اختر اللغة", "Поиск языка" to "البحث عن لغة",
            "Рекомендуемые" to "موصى به", "Закрыть" to "إغلاق",
            "В одну сторону" to "ذهاب فقط", "Туда и обратно" to "ذهاب وعودة",
            "Весь мир · аэропорты" to "مطارات حول العالم",
            "Город, страна, название или код" to "المدينة أو البلد أو الاسم أو الرمز"
        ),
        "zh" to mapOf(
            "Главная" to "首页", "Мои поездки" to "我的行程", "Статус" to "航班状态",
            "Профиль" to "个人资料", "Назад" to "返回", "Расписание" to "航班时刻表",
            "Посадочный талон" to "登机牌", "О flyUIA" to "关于 flyUIA",
            "Откуда" to "出发地", "Куда" to "目的地", "Дата вылета" to "出发日期",
            "Пассажиры" to "乘客", "Найти рейсы" to "搜索航班", "Выбрать рейс" to "选择航班",
            "Готово" to "完成", "Имя" to "名字", "Фамилия" to "姓氏",
            "Язык" to "语言", "Выберите язык" to "选择语言", "Поиск языка" to "搜索语言",
            "Рекомендуемые" to "推荐", "Закрыть" to "关闭",
            "В одну сторону" to "单程", "Туда и обратно" to "往返",
            "Весь мир · аэропорты" to "全球机场",
            "Город, страна, название или код" to "城市、国家、名称或代码"
        ),
        "ja" to mapOf(
            "Главная" to "ホーム", "Мои поездки" to "予約・旅行", "Статус" to "フライト状況",
            "Профиль" to "プロフィール", "Назад" to "戻る", "Расписание" to "時刻表",
            "Посадочный талон" to "搭乗券", "О flyUIA" to "flyUIAについて",
            "Откуда" to "出発地", "Куда" to "目的地", "Дата вылета" to "出発日",
            "Пассажиры" to "搭乗者", "Найти рейсы" to "フライトを検索", "Выбрать рейс" to "フライトを選択",
            "Готово" to "完了", "Имя" to "名", "Фамилия" to "姓",
            "Язык" to "言語", "Выберите язык" to "言語を選択", "Поиск языка" to "言語を検索",
            "Рекомендуемые" to "おすすめ", "Закрыть" to "閉じる",
            "В одну сторону" to "片道", "Туда и обратно" to "往復",
            "Весь мир · аэропорты" to "世界各地の空港",
            "Город, страна, название или код" to "都市、国、名称、またはコード"
        ),
        "ko" to mapOf(
            "Главная" to "홈", "Мои поездки" to "내 여행", "Статус" to "항공편 상태",
            "Профиль" to "프로필", "Назад" to "뒤로", "Расписание" to "운항 일정",
            "Посадочный талон" to "탑승권", "О flyUIA" to "flyUIA 소개",
            "Откуда" to "출발지", "Куда" to "도착지", "Дата вылета" to "출발일",
            "Пассажиры" to "승객", "Найти рейсы" to "항공편 검색", "Выбрать рейс" to "항공편 선택",
            "Готово" to "완료", "Имя" to "이름", "Фамилия" to "성",
            "Язык" to "언어", "Выберите язык" to "언어 선택", "Поиск языка" to "언어 검색",
            "Рекомендуемые" to "추천", "Закрыть" to "닫기",
            "В одну сторону" to "편도", "Туда и обратно" to "왕복",
            "Весь мир · аэропорты" to "전 세계 공항",
            "Город, страна, название или код" to "도시, 국가, 이름 또는 코드"
        ),
        "hi" to mapOf(
            "Главная" to "होम", "Мои поездки" to "मेरी यात्राएँ", "Статус" to "उड़ान की स्थिति",
            "Профиль" to "प्रोफ़ाइल", "Назад" to "वापस", "Расписание" to "समय-सारणी",
            "Посадочный талон" to "बोर्डिंग पास", "О flyUIA" to "flyUIA के बारे में",
            "Откуда" to "कहाँ से", "Куда" to "कहाँ तक", "Дата вылета" to "प्रस्थान तिथि",
            "Пассажиры" to "यात्री", "Найти рейсы" to "उड़ानें खोजें", "Выбрать рейс" to "उड़ान चुनें",
            "Готово" to "हो गया", "Имя" to "पहला नाम", "Фамилия" to "उपनाम",
            "Язык" to "भाषा", "Выберите язык" to "भाषा चुनें", "Поиск языка" to "भाषा खोजें",
            "Рекомендуемые" to "अनुशंसित", "Закрыть" to "बंद करें",
            "В одну сторону" to "एक तरफ़ा", "Туда и обратно" to "आना-जाना",
            "Весь мир · аэропорты" to "दुनिया भर के हवाई अड्डे",
            "Город, страна, название или код" to "शहर, देश, नाम या कोड"
        )
    )
    return common[code]?.get(source)
}
