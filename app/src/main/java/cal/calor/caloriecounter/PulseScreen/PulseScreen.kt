package cal.calor.caloriecounter.PulseScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.WaterScreeen.cardWater
import cal.calor.caloriecounter.WeightScreen.cardViewWeight
import cal.calor.caloriecounter.banner.App
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.СolorWater

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PulseScreen(pulseViewModel: PulseViewModel,owner: LifecycleOwner, onItem: () -> Unit, onItemChage: () -> Unit){
    var datavalue by remember { mutableStateOf("") }

    datavalue = pulseViewModel.getCurrentDate()

    val weightList = pulseViewModel.pulseListView.observeAsState(null)
    val list = weightList.value?.sortedByDescending { App.reverseStringDate(it.data) + it.time}?.groupBy { it.data }


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
        .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
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
                    onQueryChange = { text ->
                        pulseViewModel.pulseListFilter.value = text
                        textSearch = text
                    },

                    onSearch = { text ->
                        active = false
                        pulseViewModel.pulseListFilter.value = text
                    },
                    active = active,
                    onActiveChange = {
                        active = false
                    },
                    placeholder = { Text(text = "Пример " + "03.04.2024",
                        fontFamily = sf_ui_display_semiboldFontFamily
                    ) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    },

                    ) {

                }
            }





            if (list != null) {
                if (list.size == 0) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center

                    ) {
                        Column(modifier = Modifier) {
                            Text(text = "Здесь пока ничего нет...",
                                color = Color.White,
                                fontFamily = sfproDisplayThinFontFamily
                            )
                            Text(text = "Добавьте сегодняшнее давление ",
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
                            list?.forEach { (dataCurrent, listWater) ->
                                stickyHeader {
                                    Text(
                                        text = dataCurrent.toString(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = ColorRed),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.h6,
                                        fontFamily = sf_ui_display_semiboldFontFamily
                                        )
                                }

                                items(listWater, key = { it.id!! },) { pulseList ->
                                    Spacer(modifier = Modifier.padding(top = 5.dp))
                                    cardPulse(
                                        pulseModel = pulseList,
                                        pulseViewModel = pulseViewModel,
                                        onItem = onItemChage
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter

        ) {
            FloatingActionButton(onClick = {
                onItem.invoke()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}