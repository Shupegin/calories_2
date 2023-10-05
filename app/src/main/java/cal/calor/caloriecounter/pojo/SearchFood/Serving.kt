package cal.calor.caloriecounter.pojo.SearchFood

import com.google.gson.annotations.SerializedName


data class Serving (

  @SerializedName("calcium"                 ) var calcium                : String? = null,
  @SerializedName("calories"                ) var calories               : String? = null,
  @SerializedName("carbohydrate"            ) var carbohydrate           : String? = null,
  @SerializedName("cholesterol"             ) var cholesterol            : String? = null,
  @SerializedName("fat"                     ) var fat                    : String? = null,
  @SerializedName("fiber"                   ) var fiber                  : String? = null,
  @SerializedName("iron"                    ) var iron                   : String? = null,
  @SerializedName("measurement_description" ) var measurementDescription : String? = null,
  @SerializedName("metric_serving_amount"   ) var metricServingAmount    : String? = null,
  @SerializedName("metric_serving_unit"     ) var metricServingUnit      : String? = null,
  @SerializedName("monounsaturated_fat"     ) var monounsaturatedFat     : String? = null,
  @SerializedName("number_of_units"         ) var numberOfUnits          : String? = null,
  @SerializedName("polyunsaturated_fat"     ) var polyunsaturatedFat     : String? = null,
  @SerializedName("potassium"               ) var potassium              : String? = null,
  @SerializedName("protein"                 ) var protein                : String? = null,
  @SerializedName("saturated_fat"           ) var saturatedFat           : String? = null,
  @SerializedName("serving_description"     ) var servingDescription     : String? = null,
  @SerializedName("serving_id"              ) var servingId              : String? = null,
  @SerializedName("serving_url"             ) var servingUrl             : String? = null,
  @SerializedName("sodium"                  ) var sodium                 : String? = null,
  @SerializedName("sugar"                   ) var sugar                  : String? = null,
  @SerializedName("vitamin_a"               ) var vitaminA               : String? = null,
  @SerializedName("vitamin_c"               ) var vitaminC               : String? = null

)