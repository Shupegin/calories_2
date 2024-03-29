package cal.calor.caloriecounter.WeightScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral

@Composable
fun cardViewWeight(weightPogo: WeightPogo, weightViewModel: WeightViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(6.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color = Green)
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            weightPogo.data.let {
               it?.let { it1 -> Text(text = it1, Modifier
                   .padding(start = 20.dp),
                   fontFamily =  sfproDisplayThinFontFamily
               )}
           }


            weightPogo.weight.let {
                it?.let { it1-> Text(text = it1 + " кг" ,
                    fontFamily =  sfproDisplayThinFontFamily) }
            }

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
    }

}