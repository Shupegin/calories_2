package cal.calor.caloriecounter.InternetScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R


@Composable
fun CheckInternetScreen() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column {
            Image(painter = painterResource(id = R.drawable.internet), contentDescription = "Интернет")
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Проверьте интернет")
        }
    }
}