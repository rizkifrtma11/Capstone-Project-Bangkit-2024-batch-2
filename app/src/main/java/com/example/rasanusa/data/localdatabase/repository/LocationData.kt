package com.example.rasanusa.data.localdatabase.repository

data class LocationData (
    val asal: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Float,
    val foodName: String
)

val locationData = listOf(
    LocationData("Sumatra Utara", 3.597043275463813, 98.65986456346242, 5000f, "Bika Ambon"),
    LocationData("Sulawesi Selatan", -5.1596984192163164, 119.43523927092097, 5000f, "Es Pisang Ijo"),
    LocationData("Jakarta", -6.193471068018878, 106.82555855240794, 5000f, "Kerak Telor"),
    LocationData("Jawa Tengah", -7.021713763419635, 110.46179705550247, 5000f, "Lumpia Semarang"),
    LocationData("Aceh", 5.548159845829125, 95.32399415728443, 5000f, "Mie Aceh"),
    LocationData("Papua", -4.547083534827065, 136.88221662664787, 5000f, "Papeda"),
    LocationData("Sumatera Selatan", -2.9775944408580286, 104.77944009113432, 5000f, "Pempek"),
    LocationData("Sumatra Barat", -0.946468027326545, 100.36873868552257, 5000f, "Rendang"),
    LocationData("Bali ", -8.678168482687711, 115.21059695370762, 5000f, "Sate Lilit"),
    LocationData("Kalimantan Selatan ", -3.3198136624170704, 114.83455496529575, 5000f, "Soto Banjar"),
    LocationData("Jawa Barat", -6.838085431905959, 107.92651089928832, 5000f, "Tahu Sumedang"),
    LocationData("Bogor", -6.579291977446188, 106.80905026271843, 5000f, "Soto Mie Bogor"),
    )