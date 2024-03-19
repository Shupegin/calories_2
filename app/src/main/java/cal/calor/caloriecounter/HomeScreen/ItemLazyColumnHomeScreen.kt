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

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.pojo.FoodModel



@Composable
fun cardFood(foodModel: FoodModel, viewModel : MainViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(30.dp),
        ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {

            Column() {

                Text(
                    text = "Название: ${foodModel.food}", modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp)
                )
                Text(
                    text = "Количество грамм = ${foodModel.gramm}",
                    modifier = Modifier.padding(start = 5.dp)
                        .width(270.dp)
                )
                Text(
                    text = "Калории = ${foodModel.calories}",
                    modifier = Modifier.padding(start = 5.dp)
                )

            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(35.dp)
                    .height(35.dp)
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






