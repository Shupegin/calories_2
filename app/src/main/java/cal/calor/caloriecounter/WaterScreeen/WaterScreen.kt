package cal.calor.caloriecounter.WaterScreeen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import cal.calor.caloriecounter.banner.App
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WaterScreen(onItem: () -> Unit, viewModel: WaterViewModel) {

    var waters by remember { mutableStateOf(0) }
    var drainedWater by remember { mutableStateOf(0) }




    val waterList = viewModel.waterListView.observeAsState(null)


    val list = waterList.value?.sortedByDescending { App.reverseStringDate(it.dataCurrent) + it.waterId}?.groupBy { it.dataCurrent }


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
                    viewModel.waterListFilter.value = text
                        textSearch = text
                    },

                    onSearch = { text ->
                        active = false
                    viewModel.waterListFilter.value = text
                    },
                    active = active,
                    onActiveChange = {
                        active = false
                    },
                    placeholder = { Text(text = "Пример " + "03.04.2024", fontFamily = sf_ui_display_semiboldFontFamily)},
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
                        Column(modifier = Modifier.background(BackgroundGray)) {
                            Text(text = "Здесь пока ничего нет...",
                                color = Color.White,
                                fontFamily = sfproDisplayThinFontFamily
                            )
                            Text(text = "Добавьте выпитую воду ",
                                color = Color.White,
                                fontFamily = sfproDisplayThinFontFamily)
                        }

                    }

                } else {
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
                                        .background(color = СolorWater),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h6,
                                    fontFamily = sf_ui_display_semiboldFontFamily

                                    )
                            }
                            items(listWater, key = { it.waterId }) { waterModel ->
                                cardWater(waterModel = waterModel, viewModel = viewModel)
                            }

                            item {
                                Text(
                                    text = "Итого:",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontFamily = sf_ui_display_semiboldFontFamily
                                )
                                Row() {
                                    val totalWater = viewModel.getWater(listWater)
                                    waters = totalWater
                                    Spacer(modifier = Modifier.padding(start = 5.dp))
                                    Text(
                                        modifier = Modifier
                                            .shadow(
                                                elevation = 4.dp,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .background(color = СolorWater)
                                            .padding(5.dp),
                                        text = "Выпито воды =  ${waters}",
                                        textAlign = TextAlign.Right,
                                        fontFamily = sfproDisplayThinFontFamily
                                    )

                                    Spacer(Modifier.weight(1f))


                                    val totalWater_2 = viewModel.getDrainedWater(listWater)
                                    drainedWater = totalWater_2
                                    Text(
                                        modifier = Modifier
                                            .shadow(
                                                elevation = 4.dp,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .background(color = СolorWater)
                                            .padding(5.dp),

                                        text = "Слито воды =  ${drainedWater}",
                                        textAlign = TextAlign.Right,
                                        fontFamily = sfproDisplayThinFontFamily

                                    )
                                    Spacer(modifier = Modifier.padding(end = 5.dp))

                                }
                                Spacer(modifier = Modifier.padding(top = 5.dp))
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