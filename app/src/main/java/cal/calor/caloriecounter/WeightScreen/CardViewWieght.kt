package cal.calor.caloriecounter.WeightScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral

@Composable
fun cardViewWeight(weightPogo: WeightPogo, weightViewModel: WeightViewModel, onItem: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color = Green)
    ) {
        Column(modifier = Modifier.clickable {
            weightViewModel.changeData(weightPogo)
            onItem.invoke()
        }) {
            Row(
                modifier = Modifier.padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                weightPogo.data.let {
                    it?.let { it1 -> Text(text = it1, Modifier
                        .padding(start = 20.dp),
                        fontFamily =  sfproDisplayThinFontFamily
                    )}
                }
                Spacer(modifier = Modifier.padding(start = 50.dp))

                weightPogo.weight.let {
                    it?.let { it1-> Text(text = it1 + " кг" ,
                        fontFamily =  sfproDisplayThinFontFamily) }
                }
                Spacer(modifier = Modifier.padding(start = 100.dp))
                Image(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(30.dp)
                        .clickable {
                            weightViewModel.deleteWeight(weightPogo)
                        },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                )
            }

            Box(Modifier.padding(20.dp)){

                Column {
                    Text(text = "Параметры фигуры",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)

                    Row {

                        Column {
                            weightPogo.breast?.let {
                                Text(text = "Грудь ${it} см")
                            }
                            weightPogo.waist?.let {
                                Text(text = "Талия ${it} см" )

                            }
                            weightPogo.biceps?.let {
                                Text(text = "Бицепс ${it} см")
                            }
                        }
                        Spacer(modifier = Modifier.padding(start = 50.dp))
                        Column {
                            weightPogo.belly?.let {
                                Text(text = "Живот ${it} см")

                            }
                            weightPogo.hip?.let {
                                Text(text = "Бедро ${it} см")
                            }
                            weightPogo.buttocks?.let {
                                Text(text = "Ягодицы ${it} см"
                                )
                            }
                        }
                    }
                }



            }
        }
    }

}