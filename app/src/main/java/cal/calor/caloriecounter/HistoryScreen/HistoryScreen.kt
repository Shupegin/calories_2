package cal.calor.caloriecounter.HistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
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
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine


@Composable
fun HistoryScreen(viewModel: MainViewModel,
                  historyViewModel: HistoryViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner){


    var dbUid = viewModel.userListDAO.observeAsState(listOf())
    viewModel.loadFirebaseData(dbUid.value)

    var pointList : List<Point> = ArrayList()
    historyViewModel.listPoint.observe(owner, Observer {
        pointList = it
    })





    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .fillMaxSize()
        .background(BackgroundGray),

    ){

        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                DropMenu(viewModel = viewModel)
                VerticalProgressBar(viewModel = viewModel, owner = owner)
                Text(text = "График калорий", fontSize = 25.sp)
                  Chart(pointList = pointList)

        }


    }


}

@Composable
private fun LinerChartScreen() {
    val step = 5
    val pointsData = listOf(
        Point(0f,40f),
        Point(1f,90f),
        Point(2f,0f),
        Point(3f,60f),
        Point(4f,10f),

        )
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size -1 )
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(step)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData {i-> val yscale = 100/step
            (i* yscale).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val lineCharData = LineChartData(
        linePlotData =  LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(color = MaterialTheme.colorScheme.tertiary),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(alpha =  0.5f,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant)

    )
    LineChart(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp),
        lineChartData = lineCharData )
}













