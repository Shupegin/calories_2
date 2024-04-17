package cal.calor.caloriecounter.uiElements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray

@Composable
fun ClickableGlowingCard(
    glowingColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    cornersRadius: Dp = 0.dp,
    glowingRadius: Dp = 20.dp,
    xShifting: Dp = 0.dp,
    yShifting: Dp = 0.dp,
    onClick:() -> Unit = {},
    mainViewModel: MainViewModel,
    owner: LifecycleOwner,
    content: @Composable BoxScope.() -> Unit
) {

    var switch by remember {
        mutableStateOf(false)
    }

    mainViewModel.color.observe(owner, Observer {
        switch = it
    })



    val bgColor: Color by animateColorAsState(if (switch) glowingColor else Color.Transparent,
        animationSpec = tween(500, easing = LinearEasing)
    )

    Box(
        modifier = modifier
            .drawBehind {
                val canvasSize = size
                drawContext.canvas.nativeCanvas.apply {

                    drawCircle(
                        color = bgColor,
                        radius = 80f,
                    )

                    drawRoundRect(
                        0f, // Left
                        0f, // Top
                        canvasSize.width, // Right
                        canvasSize.height, // Bottom
                        cornersRadius.toPx(), // Radius X
                        cornersRadius.toPx(), // Radius Y
                        Paint().apply {
                            color = containerColor.toArgb()
                            isAntiAlias = true



//                            setShadowLayer(
//                                glowingRadius.toPx(),
//                                xShifting.toPx(), yShifting.toPx(),
//                                bgColor.copy(alpha = 0.85f).toArgb(),
//                            )
                        }
                    )


                }
            }
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(cornersRadius))
            .clickable { onClick() }){
            content()
        }
    }
}