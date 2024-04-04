package cal.calor.caloriecounter


import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import cal.calor.caloriecounter.banner.App
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FrequentlyChangedStateReadInComposition",
    "CoroutineCreationDuringComposition"
)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onItem: () -> Unit,
    onItemAdd: () -> Unit,
    paddingValues: PaddingValues,
    owner: LifecycleOwner,
    context: Context
){
    var ADVERTISER_ID = "c4708079-d26b-4f72-962f-bbe6381e0005"
    var calories by remember { mutableStateOf(0) }

    var isLoading  by remember { mutableStateOf( false) }

    var openSearchBar by remember {
        mutableStateOf(1)
    }

    viewModel.status.observe(owner) { isLoading = it }

    val foodList = viewModel.foodListView.observeAsState(null)

    val list = foodList.value?.sortedByDescending { App.reverseStringDate(it.dataCurrent) + it.food_id + it.calories }?.groupBy { it.dataCurrent }


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


    Box(
        modifier = Modifier
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
                                items(items = listFood, key = { it.food_id }) { foodModel ->
                                    cardFood(foodModel = foodModel, viewModel)
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
                                            text = "Cумма калорий = ${calories}",
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
                if (false) {
                    Row {
                        FloatingActionButton(onClick = {
                            onItem.invoke()
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.padding(start = 20.dp))
                        FloatingActionButton(onClick = {
                            onItemAdd.invoke()},
                            backgroundColor = Color.Green) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                } else {
                    FloatingActionButton(onClick = {
                        onItem.invoke()
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
        }
    }
}


