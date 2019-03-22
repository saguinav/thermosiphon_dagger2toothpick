package org.koin.example

import toothpick.config.Module

class DripCoffeeModule : Module() {

    init {
        bind(Heater::class.java).toProviderInstance(HeaterProvider()).providesSingletonInScope()
    }
}

class HeaterProvider : javax.inject.Provider<Heater> {

    override fun get(): Heater {
        return ElectricHeater()
    }
}
