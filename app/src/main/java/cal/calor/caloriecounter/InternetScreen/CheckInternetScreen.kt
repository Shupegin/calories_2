package cal.calor.caloriecounter.InternetScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Сoral


@Composable
fun CheckInternetScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),
        contentAlignment = Alignment.Center
    ){
        Column(verticalArrangement =  Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Проверьте соединение с интернетом!", color = Сoral)
            Text(text = "Приложение обновится автоматически", color = Сoral)
        }

    }


}