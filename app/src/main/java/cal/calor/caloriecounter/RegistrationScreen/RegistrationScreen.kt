package cal.calor.caloriecounter.RegistrationScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.ui.theme.BackgroundGray

@Composable
fun RegistrationScreen(navController: NavController,viewModel: RegistrationViewModel,
                       owner: LifecycleOwner,context : Context){
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    val error = viewModel.error.observeAsState("")
    val user = viewModel.user.observeAsState("")
    val error_e = viewModel.error_e.observeAsState("")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),

        contentAlignment = Alignment.Center
    )

    {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = "Регистрация")
            Spacer(modifier = Modifier.padding(20.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {it.let {
                    email = it
                }},
                label = {
                    Text(
                        text = "Введите email ",
                        style = TextStyle(
                            color = Color.Black,
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Green,
                    cursorColor = Color.Black
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { it.let {
                    password = it
                }},
                label = {
                    Text(
                        text = "Введите пароль",
                        style = TextStyle(
                            color = Color.Black,
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Green,
                    cursorColor = Color.Black
                )
            )

            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {it.let {
                    repeatPassword = it
                }},
                label = {
                    Text(
                        text = "повторите пароль",
                        style = TextStyle(
                            color = Color.Black,
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Green,
                    cursorColor = Color.Black
                )
            )
//            OutlinedTextField(
//                value = calories,
//                onValueChange = {it.let {
//                    calories = it
//                }},
//                label = {
//                    Text(
//                        text = "Введите ориентрировочное колличество каллорий в день",
//                        style = TextStyle(
//                            color = Color.Black,
//                        )
//                    )
//                },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Black,
//                    unfocusedBorderColor = Color.Green,
//                    cursorColor = Color.Black
//                )
//            )
            Button(onClick = {
                if (error_e.value != true){
                    viewModel.singUp(email = email, password = password, calories = calories.toIntOrNull() ?: 0)
                }else{
                    Toast.makeText(context,"Введите пароль или email",Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Регистрация")
            }
        }

    }
}