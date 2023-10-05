package cal.calor.caloriecounter

import android.annotation.SuppressLint


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onItem: () -> Unit,
    paddingValues: PaddingValues
){

    var calories by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray)

    ){
        val foodList = viewModel.foodListDAO.observeAsState(listOf())
        val list = foodList.value.asReversed().groupBy { it.dataCurrent }

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 55.dp),
        ){

            list?.forEach{(dataCurrent,listFood)->
                stickyHeader{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .background(color = Сoral),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = dataCurrent.toString(), style = MaterialTheme.typography.h6)
                    }
                }
                items(listFood, key= {it.food_id},){foodModel ->
                    cardFood(foodModel = foodModel,viewModel)
                }
                item() {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd,


                        ){
                        Card(modifier = Modifier,
                            shape = RoundedCornerShape(12.dp),
                            backgroundColor = Color.Green,
                            elevation =  10.dp

                        ) {
                            Box(modifier = Modifier
                                .padding(6.dp),
                            ) {
                                val totalCalories = viewModel.getCalories(listFood)
                                calories  = totalCalories
                                Text(text = "Сумма калорий = $totalCalories ")
                            }
                        }
                    }

                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter

        ){
            FloatingActionButton(onClick = {
                onItem.invoke()
            }) {
                Icon(imageVector = Icons.Default.Add,contentDescription = null)
            }
        }
    }

}

