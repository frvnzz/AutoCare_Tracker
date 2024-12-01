package at.ac.fhstp.contactsapp.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun main() {

    val time = measureTimeMillis {
        runBlocking {
            printWeatherReport()
        }
    }


    println("It took: $time ms")
}

suspend fun printWeatherReport() {
    coroutineScope {
        val weatherDeferred = async {
            println("Calculating weather")
            delay(2000)
            val weather = "Sunny"
            weather
        }

        val temperatureDeferred = async {
            println("Calculating temperature")
            delay(3000)
            val temperature = "11°C"
            temperature
        }

        launch(Dispatchers.IO) {
            delay(500)
            withContext(Dispatchers.Default) {
                println("Hi there!")
            }
        }

        val weather = weatherDeferred.await()
        val temperature = temperatureDeferred.await()

        println("The weather is $weather and temperature is $temperature")
    }
}