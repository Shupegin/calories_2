package cal.calor.caloriecounter.PulseScreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.WaterModel_2
import cal.calor.caloriecounter.pojo.pulse.PulsePojo
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily

@Composable
fun cardPulse(pulseModel: PulsePojo, pulseViewModel: PulseViewModel,onItem: () -> Unit){

    Card(modifier = Modifier.clickable {
        pulseViewModel.changeData(pulseModel)
        onItem.invoke()
    }
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(25.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)) {
            Column() {
                Text(
                    text = "Давление/Пульс: ",
                    modifier = Modifier.width(270.dp),
                    textAlign = TextAlign.Center,
                    fontFamily =  sfproDisplayThinFontFamily
                )
                Text(
                    text = "Время: ${pulseModel.time} ",
                    modifier = Modifier.width(270.dp),
                    textAlign = TextAlign.Center,
                    fontFamily =  sfproDisplayThinFontFamily
                )
                Text(
                    text = "Показатели давления:",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp),
                    fontFamily =  sfproDisplayThinFontFamily
                )

                Text(
                    text = "Верхнее = ${pulseModel.pressureTop}",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp),
                    fontFamily =  sfproDisplayThinFontFamily
                )

                Text(
                    text = "Нижнее = ${pulseModel.pressureLower}",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp),
                    fontFamily =  sfproDisplayThinFontFamily
                )

                Text(
                    text = "Показатели пульса = ${pulseModel.pulse}",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp),
                    fontFamily =  sfproDisplayThinFontFamily
                )

            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(35.dp)
                    .height(35.dp)
                    .clickable {
                        pulseViewModel.deletePulse(pulseModel)

                    },
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
            )
        }
    }
}