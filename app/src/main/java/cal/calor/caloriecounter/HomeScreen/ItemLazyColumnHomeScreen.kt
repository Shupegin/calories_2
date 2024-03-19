package com.example.caloriecounter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.pojo.FoodModel



@Composable
fun cardFood(foodModel: FoodModel, viewModel : MainViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),


        shape = RoundedCornerShape(25.dp),
        ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {

            Column(modifier = Modifier.width(270.dp)) {

                Text(
                    text = "Название: ${foodModel.food}", modifier = Modifier.padding(start = 5.dp)
                        .width(300.dp),
                    color = Color.White

                )
                Text(
                    text = "Количество грамм = ${foodModel.gramm}",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(300.dp),
                    color = Color.White
                )
                Text(
                    text = "Калории = ${foodModel.calories}",
                    modifier = Modifier.padding(start = 5.dp),
                    color = Color.White
                )

            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clickable {
                        viewModel.deleteFood(foodModel)
                        viewModel.removeInFirebaseDatabase(foodModel)
                    },
                painter = painterResource(id = cal.calor.caloriecounter.R.drawable.delete),
                contentDescription = null,
            )
        }
    }
}






