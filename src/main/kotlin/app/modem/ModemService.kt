package app.modem

import com.fazecast.jSerialComm.SerialPort
import java.io.InputStream
import java.io.OutputStream

class ModemService {
    private var serialPort: SerialPort? = null
    private var output: OutputStream? = null
    private var input: InputStream? = null

    fun connect(): Boolean {
        val ports = SerialPort.getCommPorts()
        if (ports.isEmpty()) {
            println("🔌 Brak dostępnych portów szeregowych.")
            return false
        }

        println("🧭 Wykryto porty:")
        ports.forEach { println("- ${it.systemPortName}") }

        for (port in ports) {
            // Pomijamy porty Bluetooth, bo to nie są fizyczne modemy
            if (port.systemPortName.contains("Bluetooth", ignoreCase = true)) continue

            if (tryConnectToPort(port)) {
                return true
            }
        }

        println("❌ Nie znaleziono działającego modemu.")
        return false
    }

    private fun tryConnectToPort(port: SerialPort): Boolean {
        return try {
            port.baudRate = 9600
            port.numDataBits = 8
            port.numStopBits = SerialPort.ONE_STOP_BIT
            port.parity = SerialPort.NO_PARITY

            if (!port.openPort()) {
                println("⚠️ Nie można otworzyć portu ${port.systemPortName}")
                return false
            }

            output = port.outputStream
            input = port.inputStream

            // Wysyłamy komendę testową "AT"
            output?.write("AT\r".toByteArray())
            output?.flush()

            Thread.sleep(500) // Czekamy na odpowiedź modemu

            val buffer = ByteArray(1024)
            val len = input?.read(buffer) ?: -1
            val response = buffer.decodeToString(0, len)

            if ("OK" in response) {
                serialPort = port
                println("📶 Połączono z modemem na porcie: ${port.systemPortName}")
                true
            } else {
                println("⚠️ Brak odpowiedzi 'OK' z portu ${port.systemPortName}.")
                port.closePort()
                false
            }
        } catch (e: Exception) {
            println("❌ Błąd podczas próby połączenia z ${port.systemPortName}: ${e.message}")
            port.closePort()
            false
        }
    }
}
