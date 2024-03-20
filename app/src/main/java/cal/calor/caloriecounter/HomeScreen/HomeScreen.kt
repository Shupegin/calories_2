package cal.calor.caloriecounter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.room.util.query
import cal.calor.caloriecounter.banner.App
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FrequentlyChangedStateReadInComposition")
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onItem: () -> Unit,
    paddingValues: PaddingValues,
    owner: LifecycleOwner
){

    var calories by remember { mutableStateOf(0) }

    var isLoading  by remember { mutableStateOf( false) }

    var openSearchBar by remember {
        mutableStateOf(1)
    }

    viewModel.status.observe(owner) { isLoading = it }
    val foodList = viewModel.foodListView.observeAsState(null)
    val deleted = remember {
        mutableStateListOf<FoodModel>()
    }
    val temp = ArrayList<FoodModel>()
    if (foodList.value != null) {
        temp.addAll(foodList.value!!)
    }
    for (m in deleted) {
        if (!temp.contains(m)) {
            temp.add(m)
        }
    }

    println("deleted count:" + deleted.count())
    val list = temp.sortedByDescending { App.reverseStringDate(it.dataCurrent) + it.food_id + it.calories }?.groupBy { it.dataCurrent }


    var textSearch  by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()


    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex  == 0
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),


    ){

        Column (modifier = Modifier
            .fillMaxSize()
            ) {

            AnimatedVisibility(visible = isScrolled) {


                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = SearchBarDefaults.colors(Color.White.copy(alpha = 0.7f)),



                    query = textSearch,
                    onQueryChange = {text->
                        viewModel.foodListFilter.value = text
                        textSearch = text
                    },

                    onSearch = { text->
                        active = false
                        viewModel.foodListFilter.value = text
                    },
                    active = active,
                    onActiveChange = {
                        active = false
                    },
                    placeholder = {
                        Text(text = "Пример " + "03.04.2024",
                            fontFamily =  sf_ui_display_semiboldFontFamily )},
                    leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")},


                ) {

                }
            }




//            val ssVisibleState = remember {
//                MutableTransitionState(false)
//            }.apply { targetState = true }


            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (list == null || isLoading) {
                    CircularProgressIndicator(color = Сoral)
                }
            }
            if (list != null) {
                if (list.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.background(BackgroundGray)) {
                            Text(text = "Здесь пока ничего нет...",
                                color = Color.White,
                                fontFamily = sfproDisplayThinFontFamily
                            )
                            Text(text = "Добавьте съеденную еду ",
                                color = Color.White,
                                fontFamily = sfproDisplayThinFontFamily
                            )
                        }

                    }

                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 55.dp),
                            state = listState
                        ) {
                            list.forEach { (dataCurrent, listFood) ->
                                stickyHeader {
                                    Text(
                                        text = dataCurrent.toString(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = Сoral),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.h6,
                                        fontFamily =  sf_ui_display_semiboldFontFamily
                                    )
                                }
                                items( items = listFood, key = { it.food_id }, ) { foodModel ->
                                    AnimatedVisibility(
                                        visible = !deleted.contains(foodModel),
                                        enter = scaleIn() + expandVertically(
                                            animationSpec = tween( durationMillis =  1000 )
                                        ),
                                        exit = scaleOut() + shrinkVertically (
                                            animationSpec = tween( durationMillis =  1000 )
                                        )
                                    ) {
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(6.dp),
                                            shape = RoundedCornerShape(30.dp),
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(12.dp)
                                            ) {

                                                Column() {

                                                    Text(
                                                        text = "Название: ${foodModel.food}",
                                                        modifier = Modifier.padding(start = 5.dp)
                                                            .width(270.dp),
                                                        fontFamily = sfproDisplayThinFontFamily
                                                    )
                                                    Text(
                                                        text = "Количество грамм = ${foodModel.gramm}",
                                                        modifier = Modifier.padding(start = 5.dp)
                                                            .width(270.dp),
                                                        fontFamily = sfproDisplayThinFontFamily
                                                    )
                                                    Text(
                                                        text = "Калории = ${foodModel.calories}",
                                                        modifier = Modifier.padding(start = 5.dp),
                                                        fontFamily = sfproDisplayThinFontFamily
                                                    )

                                                }
                                                Image(
                                                    modifier = Modifier
                                                        .padding(end = 5.dp)
                                                        .width(35.dp)
                                                        .height(35.dp)
                                                        .clickable {
                                                            deleted.add(foodModel)
                                                            viewModel.deleteFood(foodModel)
                                                            viewModel.removeInFirebaseDatabase(foodModel)
                                                        },
                                                    painter = painterResource(id = R.drawable.delete),
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                    }
                                }
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 5.dp),
                                        Arrangement.End
                                    ) {

                                        val totalCalories = viewModel.getCalories(listFood)
                                        calories = totalCalories
                                        Text(
                                            modifier = Modifier
                                                .shadow(
                                                    elevation = 4.dp,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .background(color = Сoral)
                                                .padding(5.dp),
                                            text = "Сумма калорий = ${calories}",
                                            textAlign = TextAlign.Right,
                                            fontFamily = sfproDisplayThinFontFamily
                                        )

                                        Spacer(modifier = Modifier.padding(end = 5.dp))
                                    }
                                }
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


