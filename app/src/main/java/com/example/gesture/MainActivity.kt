package com.example.gesture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gesture.ui.theme.GestureTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.geometry.Offset





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*PointerEvents()*/
                    Tap()

                }
            }
        }
    }
}

@Composable
fun PointerEvents() {
    var msg by remember { mutableStateOf("作者：許閔慈") }
    Column {
        Text("\n" + msg)
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            msg = "${event.type}, ${event.changes.first().position}"
                        }
                    }
                }
        )
    }
}


@Composable
fun Tap() {
    var msg by remember { mutableStateOf("TAP相關手勢實例") }
    var count by remember { mutableStateOf(0) }
    var offset1 by remember { mutableStateOf(Offset.Zero) }
    var offset2 by remember { mutableStateOf(Offset.Zero) }
    var PU = arrayListOf(R.drawable.pu0, R.drawable.pu1,
        R.drawable.pu2, R.drawable.pu3,
        R.drawable.pu4, R.drawable.pu5)
    var Number by remember { mutableStateOf(3) }




    Column {
        Text("\n" + msg + "\n計數:" + count.toString(),
            Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = {count = 0}
                )
            }
        )
        Box(modifier = Modifier
            .background(Color.Cyan)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, dragAmount -> offset2+=dragAmount},
                    onDragStart = {
                        offset1 = it
                        offset2 = it },
                    onDragEnd = {
                        if (offset2.x >= offset1.x) {
                            msg = "長按後向右拖曳"
                            Number++
                            if (Number > 5) {
                                Number = 0
                            }
                        } else {
                            msg = "長按後向左拖曳"
                            Number--
                            if (Number < 0) {
                                Number = 5
                            }
                        }
                    }
                        )
                    }

        ){
            Text("")
        }


        Image(
            painter = painterResource(id = PU[Number]),
            contentDescription = "靜宜之美",
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {msg = "後觸發onTap(短按)"
                                count++},
                        onDoubleTap = {msg = "雙擊"
                                      count+=3},
                        onLongPress = {msg = "長按"},
                        onPress = {msg = "先觸發onPress(按下)"}
        )


                }

        )

    }
}