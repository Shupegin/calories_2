package cal.calor.caloriecounter.HistoryScreen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.ui.theme.gray
import cal.calor.caloriecounter.ui.theme.white
import kotlin.math.roundToInt

@Composable
fun BarChart(
    inputList:List<BarchartInput>,
    modifier: Modifier = Modifier,
    showDescription:Boolean
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        val listSum by remember {
            mutableStateOf(inputList.sumOf { it.value })
        }
        inputList.forEach { input ->
            val percentage = input.value/listSum.toFloat()
            Bar(
                modifier = Modifier
                    .height(120.dp * percentage * inputList.size)
                    .width(40.dp),
                primaryColor = input.color,
                percentage = input.value/listSum.toFloat(),
                description = input.description,
                showDescription = showDescription
            )
        }
    }
}

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    percentage:Float,
    description: String,
    showDescription: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            val barWidth = width / 4 * 3
            val barHeight = height / 7 * 7
            val barHeight3DPart = height - barHeight
            val barWidth3DPart = (width - barWidth)*(height*0.002f)

            var path = Path().apply {
                moveTo(0f,height)
                lineTo(barWidth,height)
                lineTo(barWidth,height-barHeight)
                lineTo(0f,height-barHeight)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            path = Path().apply {
                moveTo(barWidth,height-barHeight)
                lineTo(barWidth3DPart+barWidth,0f)
                lineTo(barWidth3DPart+barWidth,barHeight)
                lineTo(barWidth,height)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, gray)
                )
            )
            path = Path().apply {
                moveTo(0f,barHeight3DPart)
                lineTo(barWidth,barHeight3DPart)
                lineTo(barWidth+barWidth3DPart,0f)
                lineTo(barWidth3DPart,0f)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${(percentage*100).roundToInt()} %",
                    barWidth/5f,
                    height + 55f,
                    Paint().apply {
                        this.color = white.toArgb()
                        textSize = 11.dp.toPx()
                        isFakeBoldText = true
                    }
                )
            }
            if(showDescription){
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        description,
                        20f,
                        -10f,
                        Paint().apply {
                            this.color = white.toArgb()
                            textSize = 14.dp.toPx()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }

}

data class BarchartInput(
    val value:Int,
    val description:String,
    val color:Color
)