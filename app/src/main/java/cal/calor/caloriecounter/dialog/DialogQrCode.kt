package cal.calor.caloriecounter.dialog

import android.app.Dialog
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.ui.theme.Black900
import coil.compose.rememberImagePainter

@Composable
fun DialogQrCode(dialogState: MutableState<Boolean>, historyViewModel: HistoryViewModel, owner : LifecycleOwner){


    var clientID = ""
    historyViewModel.client.observe(owner, Observer {
        clientID = it
        historyViewModel.generateQR(it)
    })

    var imageQR  =  Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)

    historyViewModel.imageQR.observe(owner, Observer {
        imageQR = it
    })


    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(modifier = Modifier
                .fillMaxWidth()){
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(text = "Для обмена счетчиков между устройствами",
                        color = Black900,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        )
                    Text(text = "Ваш ID - $clientID ", color = Black900, fontSize = 15.sp )
                    CoilImage(imageQR)
                    Button(onClick = { dialogState.value = false}) {
                        Text(text = "Закрыть")
                    }
                }
            }

        }

    }
}

@Composable
fun CoilImage(image : Bitmap){
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