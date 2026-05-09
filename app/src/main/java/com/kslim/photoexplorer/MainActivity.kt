package com.kslim.photoexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kslim.data.network.NetworkConnectionState
import com.kslim.data.network.NetworkManager
import com.kslim.presentation.PhotoExplorerApp
import com.kslim.presentation.common.LocalNetworkStatus
import com.kslim.presentation.theme.PhotoExplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var networkManager: NetworkManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        networkManager.registerNetworkCallback()
        setContent {
            val networkState by networkManager.state.collectAsStateWithLifecycle()
            val isConnected = networkState is NetworkConnectionState.NetworkConnected
            PhotoExplorerTheme {
                CompositionLocalProvider(
                    LocalNetworkStatus provides isConnected
                ) {
                    PhotoExplorerApp()
                }
            }
        }
    }

    override fun onDestroy() {
        networkManager.unRegisterNetworkCallback()
        super.onDestroy()
    }
}