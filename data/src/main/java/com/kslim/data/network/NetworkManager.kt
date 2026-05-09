package com.kslim.data.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkManager @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    private val _state =
        MutableStateFlow<NetworkConnectionState>(NetworkConnectionState.NetworkDisconnected)
    val state = _state.asStateFlow()

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun registerNetworkCallback() {
        if (networkCallback != null) return

        _state.value = when (isConnected()) {
            true -> NetworkConnectionState.NetworkConnected
            false -> NetworkConnectionState.NetworkDisconnected
        }

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _state.value = NetworkConnectionState.NetworkConnected
            }

            override fun onLost(network: Network) {
                _state.value = NetworkConnectionState.NetworkDisconnected
            }
        }.also { callback ->
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    connectivityManager.registerDefaultNetworkCallback(callback)
                }

                else -> {
                    val request = NetworkRequest.Builder().build()
                    connectivityManager.registerNetworkCallback(request, callback)
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        return runCatching {
            val network = connectivityManager.activeNetwork ?: return false
            val capability = connectivityManager.getNetworkCapabilities(network) ?: return false
            capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }.getOrElse {
            Log.e("NetworkManager", "NetworkManager isConnected Exception ${it.message}")
            false
        }
    }


    fun unRegisterNetworkCallback() {
        if (networkCallback == null) return

        runCatching {
            networkCallback?.let { connectivityManager.unregisterNetworkCallback(it) }
        }.getOrElse {
            Log.e("NetworkManager", "NetworkManager unRegisterNetworkCallback Exception ${it.message}")
        }
        networkCallback = null
    }
}

sealed interface NetworkConnectionState {
    data object NetworkConnected : NetworkConnectionState
    data object NetworkDisconnected : NetworkConnectionState
}