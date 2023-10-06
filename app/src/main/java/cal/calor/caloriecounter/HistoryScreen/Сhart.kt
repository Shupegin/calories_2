package cal.calor.caloriecounter.HistoryScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart

import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine


@Composable
fun Chart(pointList : List<Point>){
    val steps = 10
    val max = getMax(pointList)
    val min = getMin(pointList)

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .backgroundColor(BackgroundGray)
        .steps(pointList.size)
        .labelData { i-> i.toString() }
        .labelAndAxisLinePadding(10.dp)
        .startPadding(100.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(BackgroundGray)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i->
            val yScale = (max- min) / steps.toFloat()
            String.format("%.1f",((i * yScale) + min))

        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointList,
                    LineStyle(color = Color.Green, width = 5.0f),
                    IntersectionPoint(Color.Green,3.dp),
                    SelectionHighlightPoint(Color.Red),
                    ShadowUnderLine(Color.Green),
                    SelectionHighlightPopUp(Color.Green),
                )
            ),
        ),
        xAxisData= xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.Black),
        backgroundColor = BackgroundGray

    )

    LineChart(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp), lineChartData = lineChartData)



}

private  fun getMax(list: List<Point>):Float{
    var max = 0F
    list.forEach{point ->
        if (max < point.y) {
            max = point.y
        }
    }
    return max
}

private  fun getMin(list: List<Point>):Float{
    var min = 1000F
    list.forEach{point ->
        if (min > point.y) {
            min = point.y
        }
    }
    return min
}

