package app

import app.modem.ModemService

fun main() {
    println("Wiadomo App startuje...")
    val modemService = ModemService()
    val isConnected = modemService.connect()

    if (isConnected) {
        println("✅ Modem działa!")
    } else {
        println("❌ Nie udało się nawiązać połączenia z modemem.")
    }
}
