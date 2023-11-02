package com.example.joystick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.joystick.ui.theme.JOYStickTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JOYStickTheme {
                JoyStick()

            }
        }
    }
}

@Composable
fun JoyStick(){
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Canvas(modifier = Modifier.fillMaxSize()
        .background(color = Color.Cyan)
        .pointerInput(Unit){
            detectDragGestures(
                onDragEnd = {
                    offsetX = 0f
                    offsetY = 0f
                }
            ) { change, dragAmount ->
                change.consume()

                offsetX += dragAmount.x
                offsetY += dragAmount.y

                //Adding constraint to the center circle
                var radious  = size.width/4
                var distance = sqrt(offsetX*offsetX + offsetY*offsetY)
                if(distance > radious){
                    var scalefactor = radious/distance
                    offsetX *= scalefactor
                    offsetY *= scalefactor
                }
            }
        }
    ){
        //often used parameters
        val centerX = size.width/3
        val centerY = (size.height - (size.height/6))
        val rad = size.width/4

        //the outer transparent circle
        drawCircle(color = Color.Gray,
            center = Offset(x = centerX , y = centerY),
            radius = rad,
            alpha = 0.7f
        )

        //the outer white stroke circle
        drawCircle(color = Color.White,
            center = Offset(x = centerX , y = centerY),
            radius = rad,
            style = Stroke(width = 20f),
        )

        //the center circle
        drawCircle(color = Color.White,
            center = Offset(x = centerX + offsetX , y = centerY + offsetY),
            radius = rad/2,
        )



    }
}