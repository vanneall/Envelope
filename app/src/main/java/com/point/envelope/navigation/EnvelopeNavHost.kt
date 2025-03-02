package com.point.envelope.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun EnvelopeNavHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AllChats,
        modifier = modifier,
    ) {

        composable<Screen.Authorization> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Authorization")
            }
        }

        composable<Screen.Registration> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Registration")
            }
        }

        composable<Screen.Chat> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chat")
            }
        }

        composable<Screen.AllChats> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "All chats")
            }
        }

        composable<Screen.Profile> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Profile")
            }
        }
    }
}