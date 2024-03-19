package cal.calor.caloriecounter.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.ui.theme.Brown
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily


@Composable
fun dialogInfo(dialogState: MutableState<Boolean>, context: Context, historyViewModel: HistoryViewModel){

    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },

        ) {

        Card(modifier = Modifier
            .fillMaxWidth(),

            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, color = Brown)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Информация",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center )

                Text(text = "Привет! Буду рад, если приложение оказалось для вас полезным." +
                        " Если у вас есть пожелания и предложения, пишите ALT7024@yandex.ru, буду рад обратной связи!" +
                        " Поддержать нас можно: номер карты",
                    fontFamily = sfproDisplayThinFontFamily,
                    textAlign = TextAlign.Center )


                Row {
                    Text(text = "2200 7001 6254 5817",
                        fontFamily = sf_ui_display_semiboldFontFamily)
                    Spacer(modifier = Modifier.padding(start = 10.dp))

                    Button(onClick = { historyViewModel.saveCardInClipboard(context) },
                        modifier= Modifier.size(20.dp),

                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.copy_icons),
                            contentDescription = null
                        )
                    }
                }
                Text(text = "Дмитрий Ш",
                    fontFamily = sfproDisplayThinFontFamily)


                Button(onClick = {
                    dialogState.value = false
                }) {
                    Text(text = "Закрыть ",
                        fontFamily = sf_ui_display_semiboldFontFamily
                    )
                }
            }

        }

    }

}