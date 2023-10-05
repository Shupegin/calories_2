package cal.calor.caloriecounter.ProfileScreen

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cal.calor.caloriecounter.LoginScreen.LoginScreen
import coil.compose.rememberImagePainter
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Black900
import com.google.zxing.integration.android.IntentIntegrator


@Composable
fun ProfileScreen(viewModelProf: ProfileViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner,
                  context: Context,
                  navController: NavController
){
    var clientID = ""
    viewModelProf.client.observe(owner, Observer {
         clientID = it
        viewModelProf.generateQR(it)
     })
    var imageQR  =  Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)


    viewModelProf.imageQR.observe(owner, Observer {
        imageQR = it
    })
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),
        contentAlignment = Alignment.TopCenter
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Профиль", color = Black900, fontSize = 50.sp )
            OutlinedTextField(value = "", onValueChange = {})
            Text(text = "Для синхронизации счетчиков", color = Black900, fontSize = 20.sp )
            Text(text = "Ваш ID - $clientID ", color = Black900, fontSize = 15.sp )
            Button(onClick = {
                navController.navigate("Add_food_screen"){
                    popUpTo = navController.graph.startDestinationId
                    launchSingleTop = true
                }
            }) {
                Text(text = "Добавление Блюда")
            }
            CoilImage(image = imageQR)
            Button(onClick = {
                var intentIntegrator = IntentIntegrator(context as Activity?)
                intentIntegrator.setOrientationLocked(true)
                intentIntegrator.setPrompt("Scan a QR code")
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                intentIntegrator.initiateScan()
            }) {
                Text(text = "QR scanner")
            }



        }
    }
}
@Composable
fun CoilImage(image :Bitmap){
    Box(modifier = Modifier
        .height(300.dp)
        .width(300.dp),
        contentAlignment = Alignment.Center
    ){
        val painter = rememberImagePainter(data = "https://yandex.ru/images/search?from=tabbar&img_url=https%3A%2F%2Fassets.publishing.service.gov.uk%2Fgovernment%2Fuploads%2Fsystem%2Fuploads%2Fimage_data%2Ffile%2F104841%2FQR_code_image.jpg&lr=55&pos=6&rpt=simage&text=qr%20code",
            builder = {

            }
        )
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = "some useful description",
        )
    }
}