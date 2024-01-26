package com.stephenhouser.android_keys

import android.view.KeyEvent
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class KeyCode(
    val keyCode: Int,
    val scanCode: Int,
    val action: Int,
    val modifiers: Int,
    val deviceId: Int,
    val displayLabel: Char,
    val description: String
)

class MainViewModel : ViewModel() {
    var keys = mutableStateListOf<KeyCode>()

    fun addKeyEvent(event: KeyEvent) {
        val keyCode = KeyCode(
            event.keyCode,
            event.scanCode,
            event.action,
            event.modifiers,
            event.deviceId,
            event.displayLabel,
            event.toString())

        keys.add(0, keyCode)

        if (event.keyCode == 0x4000000A) {
            // TODO: Uncomment to use the Rigol scope native library to light a panel LED
            // NOTE: It seems this will start the FPGA and scope, it then kills the process
            // So, will have to wait for another day to get LEDs working.
            // You will also have to uncoment loading the library over in API.kt
            // API.getInstance().UI_PostInt32Int32(11, MessageID.MSG_APP_UTILITY_LED, 18, 0);
        }
    }

    fun clearKeys() {
        keys.clear()
    }
}