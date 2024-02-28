package com.example.bitreminder.Helpers

sealed class ConnectionStatus {

    object Available: ConnectionStatus()
    object Unavailable: ConnectionStatus()

}
