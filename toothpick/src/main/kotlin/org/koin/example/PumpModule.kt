package org.koin.example

import toothpick.config.Module

class PumpModule : Module() {

    init {
        bind(Pump::class.java).to(Thermosiphon::class.java)
    }
}
