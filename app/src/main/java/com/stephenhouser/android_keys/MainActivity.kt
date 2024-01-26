package com.stephenhouser.android_keys

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephenhouser.android_keys.ui.theme.AndroidKeysTheme

class MainActivity : ComponentActivity() {
    val viewModel = MainViewModel()

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val key = event.toString()
        Log.d("KeyDown", "Key: $keyCode, $key")
        viewModel.addKeyEvent(KeyEvent(event))
        return super.onKeyUp(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val key = event.toString()
        Log.d("KeyUp", "Key: $keyCode, $key")
        viewModel.addKeyEvent(KeyEvent(event))
        return super.onKeyUp(keyCode, event)
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidKeysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.background(Color.LightGray)
                    ) {
                        LazyColumn {
                            stickyHeader {
                                Row(
                                    modifier = Modifier.background(color = Color.Blue)
                                ) {
                                    Text(
                                        "Android Key Reporter",
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        modifier = Modifier
                                            .weight(2f)

                                    )
                                    Button(onClick = { viewModel.clearKeys() }) {
                                        Text("Clear")
                                    }
                                }
                            }
                            items(viewModel.keys) {key ->
                                KeyListItem(key = key)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun KeyListItem(key: KeyCode) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(color = Color.White)
            .padding(5.dp)
            .shadow(elevation = 1.dp)
    ) {
        Text(if (key.action == 0) "Up" else "Dn")
        Text(
            "${key.displayLabel}",
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            "key=0x${key.keyCode.toHexString()}",
            color = Color.Blue,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            "scan=0x${key.scanCode.toHexString()}",
            color = Color.Blue,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        ModifierText(
            text = "L-Shift",
            isOn = key.modifiers and KeyEvent.META_SHIFT_LEFT_ON > 0
        )
        ModifierText(
            text = "R-Shift",
            isOn = key.modifiers and KeyEvent.META_SHIFT_RIGHT_ON > 0
        )
        ModifierText(text = "L-Ctrl", isOn = key.modifiers and KeyEvent.META_CTRL_LEFT_ON > 0)
        ModifierText(text = "R-Ctrl", isOn = key.modifiers and KeyEvent.META_CTRL_RIGHT_ON > 0)
        ModifierText(text = "L-Alt", isOn = key.modifiers and KeyEvent.META_ALT_LEFT_ON > 0)
        ModifierText(text = "R-Alt", isOn = key.modifiers and KeyEvent.META_ALT_RIGHT_ON > 0)
    }
}

@Composable
fun ModifierText(text: String, isOn: Boolean) {
    if (isOn) {
        Text(text,
            modifier = Modifier
                .background(color = Color.Green)
                .border(border = BorderStroke(width = 1.dp, Color.LightGray))
                .padding(5.dp)
        )
    } else {
        Text(text,
            modifier = Modifier
                .border(border = BorderStroke(width = 1.dp, Color.LightGray))
                .padding(5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeyListItemPreview() {
    AndroidKeysTheme {
        KeyListItem(KeyCode(10,
            scanCode = 10,
            action = 0,
            10 or KeyEvent.META_SHIFT_LEFT_ON,
            10,
            'A',
            "key"))
    }
}