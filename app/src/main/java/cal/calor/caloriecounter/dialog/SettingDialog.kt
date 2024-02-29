package cal.calor.caloriecounter.dialog

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import cal.calor.caloriecounter.MainActivity
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.ui.theme.BackgroundBottom
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Black900
import cal.calor.caloriecounter.ui.theme.ColorGreyAgat
import cal.calor.caloriecounter.ui.theme.СolorWater
import java.util.Calendar
import java.util.concurrent.TimeUnit


@Composable
fun SettingDialog(dialogState: MutableState<Boolean>, applicationContext: Context){
    var notificationOn = false
    try {
//        val workManager = WorkManager.getInstance(applicationContext)
//        val infos: List<WorkInfo> =
//            workManager.getWorkInfosForUniqueWork("UpdateTaskWorker").get()
//
//        if (infos.size == 1) {
//            val info = infos.get(0)
//            notificationOn = info.state == WorkInfo.State.BLOCKED || info.state == WorkInfo.State.ENQUEUED || info.state == WorkInfo.State.RUNNING
//        }
    } catch (_: Exception) { }

    var switchON by remember {
        mutableStateOf(notificationOn)
    }

    var timeValue by remember {
        mutableStateOf("2:00")
    }

    var minutesValue by remember {
        mutableStateOf("00")
    }

    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = "Настройка уведомлений",
                        color = Black900,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )

                    Text(text = "Время работы уведомлений")

                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){

                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Старт")
                            Text(text = "7:00")
                        }

                        Column (horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Стоп")
                            Text(text = "22:00")
                        }

                    }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "Частота уведомлений")
                        androidx.compose.material.TextField(
                            modifier = Modifier.width(80.dp),
                            value = timeValue,
                            onValueChange = {
                                it.let {
                                    timeValue = it
                                }
                            },

                            maxLines = 1,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = СolorWater,
                                unfocusedBorderColor = СolorWater,
                                cursorColor = СolorWater
                            ),

                        )


                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "активировать воду")



                        val thumbContent: (@Composable () -> Unit) =
                            {
                                Icon(
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                    imageVector = if (switchON) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null // icon is not focusable, no need for content description
                                )
                            }

                        Switch(modifier = Modifier.size(30.dp), checked = switchON,
                            onCheckedChange = {switchState ->
                                switchON = switchState

                                val alarmManager =
                                    applicationContext.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

                                val intent = AlarmReceiver.newIntent(applicationContext)
                                val pendingIntent = PendingIntent.getBroadcast(
                                    applicationContext, 100, intent,
                                    PendingIntent.FLAG_MUTABLE
                                )

                                if (switchState){

                                    Toast.makeText(applicationContext,"Start", Toast.LENGTH_LONG).show()


                                    val calendar = Calendar.getInstance()
                                    calendar.add(Calendar.SECOND, 30)



                                    alarmManager.setExactAndAllowWhileIdle(
                                        AlarmManager.RTC_WAKEUP,
                                        calendar.timeInMillis,
                                        pendingIntent
                                    )
                                } else {
                                    alarmManager.cancel(pendingIntent);

                                    Toast.makeText(applicationContext,"Stop", Toast.LENGTH_LONG).show()
                                }
                                              },

                            thumbContent= thumbContent,
                            colors =  SwitchDefaults.colors(
                                checkedThumbColor = СolorWater,
                                checkedTrackColor = СolorWater,
                                checkedBorderColor = ColorGreyAgat,
                                uncheckedBorderColor = BackgroundGray,
                                uncheckedThumbColor = BackgroundBottom,
                                uncheckedTrackColor= ColorGreyAgat,

                            )
                        )

                    }




//                    Switch(
//                        checked = switchON,
//                        onCheckedChange = { switchState ->
//                            switchON = switchState
//                        },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = Color(0xFFF64668),
//                            checkedTrackColor = Color(0xFFFE9677)
//                        )
//                    )


                    Button(onClick = { dialogState.value = false }) {
                        Text(text = "Закрыть")
                    }
                }
            }

        }
    }
}
class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("NotificationPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            val notificationManager = ContextCompat.getSystemService(
                it,
                NotificationManager::class.java
            ) as NotificationManager

            createNotificationChannel(notificationManager)


            val resultIntent = Intent(context, MainActivity::class.java)
            val resultPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notification = NotificationCompat.Builder(it, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Включено")
                .setContentText("Выпейте воды")
                .setContentIntent(resultPendingIntent)
                .build()

            notificationManager.notify(1, notification)

        }

    }

    private fun createNotificationChannel (notificationManager: NotificationManager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {
        private const val CHANNEL_ID = "Channel_id"
        private const val CHANNEL_NAME = "Channel_name"

        fun newIntent(context: Context) : Intent{
            return Intent(context, AlarmReceiver::class.java)

        }
    }
}