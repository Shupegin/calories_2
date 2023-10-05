package com.example.caloriecounter

import cal.calor.caloriecounter.pojo.SearchFood.ResultFood
import com.google.gson.annotations.SerializedName


data class Foods (

  @SerializedName("results"       ) var result       : ResultFood? = null,
  @SerializedName("max_results"   ) var maxResults   : String?     = null,
  @SerializedName("page_number"   ) var pageNumber   : String?     = null,
  @SerializedName("total_results" ) var totalResults : String?     = null

  )