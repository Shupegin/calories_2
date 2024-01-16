package cal.calor.caloriecounter.ProfileScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import cal.calor.caloriecounter.ui.theme.Сoral

@Composable
fun cardViewWeight(weightPogo: WeightPogo,profileViewModel: ProfileViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, color = Сoral)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

           weightPogo.data.let {
               it?.let { it1 -> Text(text = it1) }
           }


            weightPogo.weight.let {
                it?.let { it1-> Text(text = it1 + " кг" ) }
            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .clickable {
                      profileViewModel.deleteWeight(weightPogo)
                    },
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null,
            )
        }
    }

}