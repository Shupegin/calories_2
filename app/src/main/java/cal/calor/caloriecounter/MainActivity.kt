package cal.calor.caloriecounter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope



import androidx.navigation.compose.rememberNavController
import cal.calor.caloriecounter.AddNewFoodScreen.AddFoodScreenViewModel
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.InternetScreen.CheckInternetScreen
import cal.calor.caloriecounter.LoginScreen.LoginViewModel
import cal.calor.caloriecounter.PulseScreen.PulseViewModel
import cal.calor.caloriecounter.WeightScreen.WeightViewModel
import cal.calor.caloriecounter.RegistrationScreen.RegistrationViewModel
import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.dialog.dialogUpdateApp
import cal.calor.caloriecounter.internet.ConnectivityObserver
import cal.calor.caloriecounter.internet.NetworkConnectivityObserver
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.CalorieCounterTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.annotations.concurrent.Background
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelLogin: LoginViewModel
    private lateinit var waterViewModel: WaterViewModel
    private lateinit var viewModelRegistration: RegistrationViewModel
    private lateinit var viewModelAddFoodScreen: AddFoodScreenViewModel
    private lateinit var viewModelProf: WeightViewModel
    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var pulseViewModel: PulseViewModel

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE

    private lateinit var firebaseAnalytics : FirebaseAnalytics
    @OptIn(ExperimentalComposeUiApi::class)
    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition",
        "SuspiciousIndentation"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        if(false) {

            if (updateType == AppUpdateType.FLEXIBLE) {
                appUpdateManager.registerListener(installStateUpdateListener)
            }
            checkForAppUpdate()
        }


        setContent {

            CalorieCounterTheme {
                StatusBarColor(BackgroundGray)

                val dialogUpdateAppState = remember {
                    mutableStateOf(false)
                }

                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Available
                )
                if(status == ConnectivityObserver.Status.Unknow) {

                } else if(status == ConnectivityObserver.Status.Available) {
                    firebaseAnalytics = FirebaseAnalytics.getInstance(this)

                    mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
                    viewModelLogin = ViewModelProvider(this)[LoginViewModel::class.java]
                    viewModelRegistration = ViewModelProvider(this)[RegistrationViewModel::class.java]
                    viewModelAddFoodScreen = ViewModelProvider(this)[AddFoodScreenViewModel::class.java]
                    viewModelProf = ViewModelProvider(this)[WeightViewModel::class.java]
                    historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
                    pulseViewModel = ViewModelProvider(this)[PulseViewModel::class.java]
                    waterViewModel = ViewModelProvider(this)[WaterViewModel::class.java]

                    mainViewModel.userListDAO.observe(this, Observer {
                        mainViewModel.loadFirebaseData(it)
                    })
                    mainViewModel.downloadModel()

                    if (true){
                        val toast = mainViewModel.dischargePreference()

                        if (!toast){
                            Toast.makeText(this,"Устанавливается доп библиотека для работы с API",Toast.LENGTH_LONG).show()
                             mainViewModel.recordPreference(true)
                        }
                    }


                    if (true){
                        mainViewModel.management()
                        val version =  mainViewModel.management.observeAsState()

                        val versionName = BuildConfig.VERSION_NAME
                        if (version.value?.version_name != null ){
                            if (versionName.toDouble() < version.value?.version_name!!.toDouble()){

                                dialogUpdateAppState.value = true
                                if (dialogUpdateAppState.value) {
                                    dialogUpdateApp(dialogUpdateAppState)
                                }

                            }
                        }

                    }

                    LoginApplication(
                        viewModel = viewModelLogin,
                        viewModelProf= viewModelProf,
                        historyViewModel = historyViewModel,
                        waterViewModel = waterViewModel,
                        viewModelRegistration = viewModelRegistration,
                        mainViewModel = mainViewModel,
                        pulseViewModel = pulseViewModel,
                        viewModelAddFoodScreen = viewModelAddFoodScreen,
                        owner = this,
                        context = this
                    )
                } else {
                    CheckInternetScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE){
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if(info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType,
                    this,
                    123)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        var intentResult : IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data)
//        if(intentResult != null){
//            var content = intentResult.contents
//            if (content != null){
//                mainViewModel.databaseEntryUser(intentResult.contents.toString())
//            }
//        }else{
//            super.onActivityResult(requestCode, resultCode, data)
//
//        }
//
//
//    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state->
        if(state.installStatus() == InstallStatus.DOWNLOADED){
            Toast.makeText(applicationContext,
                "DownLoad successful.Restarting app in 10 seconds",
                Toast.LENGTH_LONG
            ).show()
            lifecycleScope.launch {
                delay(10.seconds)
                appUpdateManager.completeUpdate()
            }
        }

    }



   private fun checkForAppUpdate(){
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info->
                val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAllowed = when(updateType){
                    AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                    AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                    else -> false
                }
                if (isUpdateAvailable && isUpdateAllowed){
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        this,
                        123
                    )
                }
            }
    }
}

@Composable
fun LoginApplication(viewModel: LoginViewModel,
                     viewModelRegistration: RegistrationViewModel,
                     historyViewModel: HistoryViewModel,
                     waterViewModel: WaterViewModel,
                     viewModelProf: WeightViewModel,
                     pulseViewModel: PulseViewModel,
                     mainViewModel : MainViewModel,
                     viewModelAddFoodScreen : AddFoodScreenViewModel,
                     owner: LifecycleOwner,
                     context: Context){

    val navController = rememberNavController()
    MainScreen(mainViewModel = mainViewModel,viewModelWeight = viewModelProf, historyViewModel = historyViewModel, waterViewModel= waterViewModel , pulseViewModel = pulseViewModel, owner = owner, context = context,navController = navController)

//    NavHost(navController = navController, enterTransition = {EnterTransition.None}, exitTransition = {ExitTransition.None}, startDestination = "login_page", builder ={
////        composable(route = "login_page", content = { LoginScreen(navController = navController,viewModel= viewModel, owner = owner, context = context)})
//        composable(route ="register_page", content = { RegistrationScreen(navController = navController, viewModel= viewModelRegistration,owner = owner, context = context)})
//        composable(route ="activity_main", content = { MainScreen(mainViewModel = mainViewModel,viewModelProf = viewModelProf, historyViewModel = historyViewModel, waterViewModel= waterViewModel , owner = owner, context = context,navController = navController) })
//        composable(route ="Add_food_screen", content = {AddFoodScreen(viewModel= viewModelAddFoodScreen,navController,context)})
//    })

}

@Composable
fun StatusBarColor(color: androidx.compose.ui.graphics.Color) {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()

    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = color.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !darkTheme
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightNavigationBars = !darkTheme
        }
    }
}




