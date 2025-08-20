package com.example.mviproductsapp

import com.example.mviproductsapp.peresentation.feature.details.view.DetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mviproductsapp.peresentation.feature.details.view_model.DetailsViewModel
import com.example.mviproductsapp.peresentation.feature.home.view.HomeScreen
import com.example.mviproductsapp.peresentation.feature.home.view_model.HomeViewModel
import com.example.mviproductsapp.utils.internet.InternetConnectivityViewModel
import com.example.productsapp.utils.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()
            val internetConnectivityViewModel by
                viewModels<InternetConnectivityViewModel>()
            NavHost(
                navController = navigationController,
                startDestination = NavigationRoutes.HomeScreen
            ) {
                composable<NavigationRoutes.HomeScreen> {
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        navigationController = navigationController,
                        isInternetConnected = internetConnectivityViewModel.isConnected
                    )
                }
                composable<NavigationRoutes.DetailsScreen> {
                    val detailsViewModel = hiltViewModel<DetailsViewModel>()
                    DetailsScreen(
                        detailsViewModel = detailsViewModel,
                        isInternetConnected = internetConnectivityViewModel.isConnected,
                        onBackClick = {
                            navigationController.popBackStack()
                        })
                }
            }
        }
    }
}
