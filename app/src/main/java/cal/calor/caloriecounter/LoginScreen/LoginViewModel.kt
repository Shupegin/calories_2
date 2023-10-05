package cal.calor.caloriecounter.LoginScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth? = null

    private val _error : MutableLiveData<String> = MutableLiveData()
    val error : MutableLiveData<String> =  _error
    private val _user : MutableLiveData<FirebaseUser> = MutableLiveData()
    val user : MutableLiveData<FirebaseUser> = _user

    private val _error_e : MutableLiveData<Boolean> = MutableLiveData()
    val error_e : MutableLiveData<Boolean> = _error_e

    init {
        auth = FirebaseAuth.getInstance()
        auth?.addAuthStateListener {firebaseAuth->
            if(firebaseAuth.currentUser != null ){
                _user.value = firebaseAuth.currentUser
            }
        }
    }


    fun login (email : String, password: String){
        try {
            auth?.signInWithEmailAndPassword(email,password)?.addOnSuccessListener { authResult->

            }?.addOnFailureListener{
                _error.value = it.message
            }
        }catch (e : Exception){
            _error_e.value = true
        }
    }
}