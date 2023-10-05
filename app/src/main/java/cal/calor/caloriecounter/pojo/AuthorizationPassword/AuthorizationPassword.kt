package cal.calor.caloriecounter.pojo.AuthorizationPassword

import com.google.gson.annotations.SerializedName


class AuthorizationPassword {
  @SerializedName("access_token" ) var accessToken : String? = null
  @SerializedName("token_type"   ) var tokenType   : String? = null
  @SerializedName("expires_in"   ) var expiresIn   : Int?    = null
  @SerializedName("error"   ) var error   : String?    = null
}