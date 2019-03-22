package org.koin.example

import toothpick.Toothpick
import toothpick.Toothpick.setConfiguration
import toothpick.configuration.Configuration.forProduction
import javax.inject.Inject
import javax.inject.Provider

interface CoffeeApp {
    fun maker(): CoffeeMaker
}

class ToothpickCoffeeApp @Inject constructor(private val makerProvider: Provider<CoffeeMaker>) : CoffeeApp {

    override fun maker(): CoffeeMaker {
        return makerProvider.get()
    }
}

fun createToothpickCoffeeApp(): CoffeeApp {
    setConfiguration(forProduction())
    val scope = Toothpick.openScope("app")
    scope.installModules(PumpModule(), DripCoffeeModule())
    return scope.getInstance(ToothpickCoffeeApp::class.java)
}

fun main() {
    lateinit var coffeeShop: CoffeeApp
    measure("startup") {
        coffeeShop = createToothpickCoffeeApp()
    }
    measure("execution #1") {
        coffeeShop.maker().brew()
    }
    measure("execution #2") {
        coffeeShop.maker().brew()
    }
}

fun measure(msg: String, code: () -> Unit) {
    val start = System.nanoTime()
    code()
    val time = (System.nanoTime() - start) / 1000000.0
    println("$msg - $time ms")
}
