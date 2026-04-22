package com.reliefiq.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    animationResId: Int? = null,
    assetName: String = "loading.json" // Placeholder
) {
    // Attempt to load Lottie composition, but handle the case where assets are missing
    val composition by rememberLottieComposition(
        spec = if (animationResId != null) {
            LottieCompositionSpec.RawRes(animationResId)
        } else {
            LottieCompositionSpec.Asset(assetName)
        }
    )
    
    if (composition != null) {
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        // Fallback to a standard progress indicator if the Lottie asset is missing
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
