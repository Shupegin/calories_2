package cal.calor.caloriecounter

import android.annotation.SuppressLint
import android.util.Log


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onItem: () -> Unit,
    paddingValues: PaddingValues,
    owner: LifecycleOwner
){

    var calories by remember { mutableStateOf(0) }

    var isLoading  by remember { mutableStateOf( false) }

    viewModel.status.observe(owner, Observer {
        isLoading = false
    })
    isLoading = false

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
                        Text(text = dataCurrent.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Сoral),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,)
                }

                item {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.Green
                        )
                    }
                }

                items(listFood, key= {it.food_id},){foodModel ->
                    cardFood(foodModel = foodModel,viewModel)
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd){

                        val totalCalories = viewModel.getCalories(listFood)
                        calories = totalCalories
                        Text(modifier = Modifier.shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp)
                        ).background(color = Color.Green),
                            text = "Cумма калорий = ${calories}",
                            textAlign = TextAlign.Right)
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

