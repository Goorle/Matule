package com.example.matule.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.matule.R
import com.example.matule.presentation.navigation.Routes
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.SubTextDark


@Composable
fun BottomAppBar(
    navHostController: NavHostController
) {
    val bottomBarItemColors = NavigationBarItemColors(
        selectedIconColor = Accent,
        selectedTextColor = Color.Unspecified,
        selectedIndicatorColor = Color.Unspecified,
        unselectedIconColor = SubTextDark,
        unselectedTextColor = Color.Unspecified,
        disabledIconColor = SubTextDark,
        disabledTextColor = Color.Unspecified
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Block
    ) {
        NavigationBarItem(
            selected = currentRoute == Routes.Home.route,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Home screen",
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = bottomBarItemColors
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Favorite.route,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.favorite_outlined),
                    contentDescription = "Favorite Screen",
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = bottomBarItemColors
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Notification.route,
            onClick = {},
            icon =  {
                Icon(
                    painter = painterResource(R.drawable.notification),
                    contentDescription = "Notification Screen",
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = bottomBarItemColors
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Profile.route,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Screen",
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = bottomBarItemColors
        )
    }

}