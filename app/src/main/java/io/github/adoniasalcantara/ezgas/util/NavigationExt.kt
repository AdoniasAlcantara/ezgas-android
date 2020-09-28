package io.github.adoniasalcantara.ezgas.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

// Check if destination contains the target action before navigating
fun NavController.navigateSafe(direction: NavDirections) {
    this.currentDestination?.getAction(direction.actionId) ?: return
    this.navigate(direction)
}