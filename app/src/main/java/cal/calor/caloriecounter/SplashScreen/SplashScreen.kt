package cal.calor.caloriecounter.SplashScreen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true) {
        delay(1500L)
        navController.navigate("main_screen")
    }
    Box(modifier = Modifier.fillMaxSize().background(BackgroundGray)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Счетчик калорий",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource(id = R.drawable.logokcal),
                    "",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                )
            }



        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter){

            Text(text = "Он тоже такой один",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }


}