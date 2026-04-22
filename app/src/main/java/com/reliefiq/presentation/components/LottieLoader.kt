package com.reliefiq.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
    // Note: Since we don't have actual raw res files or assets provided,
    // this acts as a wrapper that would normally load a Lottie animation.
    
    val composition by rememberLottieComposition(
        spec = if (animationResId != null) {
            LottieCompositionSpec.RawRes(animationResId)
        } else {
            LottieCompositionSpec.Asset(assetName)
        }
    )
    
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
}
