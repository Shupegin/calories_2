package cal.calor.caloriecounter.ProfileScreen

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import cal.calor.caloriecounter.dialog.DialogQrCode
import cal.calor.caloriecounter.dialog.DialogWeight
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Black900
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood
import com.google.zxing.integration.android.IntentIntegrator


@Composable
fun ProfileScreen(viewModelProf: ProfileViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner,
                  context: Context,
                  navController: NavController
){
    var datavalue by remember { mutableStateOf("") }

    val dialogStateWeight = remember {
        mutableStateOf(false)
    }
    val weightList = viewModelProf.wieghtListDAO.observeAsState(listOf())

    val list = weightList.value.asReversed()

    if (dialogStateWeight.value){
        DialogWeight(
            dialogState = dialogStateWeight,
            profileViewModel= viewModelProf,
            owner = owner)
    }

    datavalue = viewModelProf.getCurrentDate()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),
        contentAlignment = Alignment.TopCenter
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Текущая дата = $datavalue",
                modifier = Modifier.padding(top = 20.dp),
                color = Сoral,
                fontSize = 20.sp,

                style = MaterialTheme.typography.body1 )
            Spacer(modifier = Modifier.padding(top = 10.dp))


            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp),
            ){
                items(list, key= { it.id!! },){ weightList ->
                    cardViewWeight(weightPogo = weightList, profileViewModel =viewModelProf )
                }
            }

        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter

        ){
            Button(onClick = {dialogStateWeight.value = true

            }) {
                Text(text = "Взвеситься")
            }
        }
    }
}
