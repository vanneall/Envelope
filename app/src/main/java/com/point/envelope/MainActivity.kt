package com.point.envelope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.point.auth.authorization.presenter.mvi.AuthState
import com.point.auth.authorization.presenter.ui.AuthorizationScreen
import com.point.auth.registration.presenter.mvi.RegState
import com.point.auth.registration.presenter.ui.RegistrationScreen
import com.point.envelope.ui.theme.EnvelopeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnvelopeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationScreen(
                        state = RegState(),
                        onAction = {},
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding))
                }
            }
        }
    }
}

@Preview
@Composable
fun a() {
    Text("ewew")
}