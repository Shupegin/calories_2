package cal.calor.caloriecounter.WaterScreeen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.WaterModel_2

@Composable
fun cardWater(waterModel: WaterModel_2, viewModel : WaterViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(25.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
            Column() {
                Text(
                    text = "Вода: ",
                    modifier = Modifier.width(270.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Выпито воды = ${waterModel.water_is_drunk} мл",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp)
                )

                Text(
                    text = "Слито воды = ${waterModel.drained_of_water} мл",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp)
                )

            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(35.dp)
                    .height(35.dp)
                    .clickable {
                        viewModel.deleteWater(waterModel)

                    },
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null,
            )
        }
    }
}