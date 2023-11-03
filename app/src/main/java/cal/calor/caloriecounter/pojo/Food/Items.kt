package com.example.example

import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("name"                  ) var name                : String? = null,
  @SerializedName("calories"              ) var calories            : Double? = null,
  @SerializedName("serving_size_g"        ) var servingSizeG        : Double?  = null,
  @SerializedName("fat_total_g"           ) var fatTotalG           : Double? = null,
  @SerializedName("fat_saturated_g"       ) var fatSaturatedG       : Double? = null,
  @SerializedName("protein_g"             ) var proteinG            : Double? = null,
  @SerializedName("sodium_mg"             ) var sodiumMg            : Double? = null,
  @SerializedName("potassium_mg"          ) var potassiumMg         : Double? = null,
  @SerializedName("cholesterol_mg"        ) var cholesterolMg       : Double? = null,
  @SerializedName("carbohydrates_total_g" ) var carbohydratesTotalG : Double? = null,
  @SerializedName("fiber_g"               ) var fiberG              : Double? = null,
  @SerializedName("sugar_g"               ) var sugarG              : Double? = null

)