package com.example.lottiedemo3

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RestrictedApi")
@Composable
fun LottieFromUrl(url: String, userName: String, cashbackAmount: String) {
    val statusBarColor = MaterialTheme.colorScheme.outlineVariant
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
        onDispose { }
    }
    var modifiedJson by remember {
        mutableStateOf<String?>(null)
    }
    var stringText by remember {
        mutableStateOf("")
    }
    var textFieldValue by remember {
        mutableStateOf("")
    }
    var firstScreen by remember {
        mutableStateOf(true)
    }
    var textFieldValue2 by remember {
        mutableStateOf("")
    }
    var selectedColor by remember { mutableStateOf(intArrayOf(0, 0, 0)) }
    var bgColor by remember {
        mutableStateOf(Color.Black)
    }
    var selectedColor2 by remember { mutableStateOf(intArrayOf(0, 0, 0)) }


    val floatArray = convertToFloatArray(selectedColor)
    val floatArray2 = convertToFloatArray(selectedColor2)

    var count by remember { mutableStateOf(15) }

    var count2 by remember { mutableStateOf(15) }



    Log.d(
        "SelectedColor",
        "${selectedColor.get(0)} ${selectedColor.get(1)} ${selectedColor.get(2)}"
    )

    LaunchedEffect(
        modifiedJson,
        url,
        textFieldValue,
        textFieldValue2,
        selectedColor,
        selectedColor2
    ) {
        val lottieJson = fetchLottieJsonFromUrl(url)
        modifiedJson = lottieJson?.let {
            modifyLottieJson(
                it,
                { string ->
                    stringText = string
                },
                textFieldValue,
                textFieldValue2,
                floatArray,
                floatArray2,
                count.toString(),
                count2.toString()
            )
        }
        Log.d(
            "ColorsValue",
            "${selectedColor.get(0)}+${selectedColor.get(1)} + ${selectedColor.get(2)}"
        )

    }

    Log.d(
        "Modifier", modifiedJson.toString
            ()
    )
    modifiedJson?.let {
        Log.d(
            "Modified",
            it
        )
        var sliderPosition by remember { mutableStateOf(0f) }
        var sliderPosition2 by remember { mutableStateOf(0f) }

        val colors = listOf(
            Color.Red,
            Color.Yellow,
            Color.Green,
            Color.Cyan,
            Color.Blue,
            Color.Magenta,
            Color.Red
        )

        val composition by rememberLottieComposition(
            LottieCompositionSpec.Url(url)
        )

        val modifiedComposition by rememberLottieComposition(
            LottieCompositionSpec.JsonString(
                it
            ), url, modifiedJson!!
        )
        if (firstScreen) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                LottieAnimation(
//                modifier = Modifier .align(Alignment.CenterHorizontally),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp)
                ) {
                    TextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text("Enter the title", color = Color.Black) },
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier. width(300.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "",
                            tint = Color.DarkGray,
                            modifier = Modifier.clickable {
                                count++
                            })
                        Text(
                            "$count",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = Color.DarkGray,
                            modifier = Modifier.clickable {
                                if (count > 0)
                                    count--
                            })

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                //Text(text = stringText)
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(35.dp)
                        .background(
                            brush = Brush.horizontalGradient(colors),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Slider(
                    value = sliderPosition,
                    onValueChange = { newPosition ->
                        sliderPosition = newPosition
                        val color = lerpColor(colors, newPosition)
                        selectedColor = (color.toIntArray())
                    },
                    valueRange = 0f..1f,
                    modifier = Modifier.width(300.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp)
                ) {

                    TextField(
                        value = textFieldValue2,
                        onValueChange = { textFieldValue2 = it },
                        label = { Text("Enter the subtitle", color = Color.Black) },
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier. width(300.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        )
                    )


                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "",
                            tint = Color.DarkGray,
                            modifier = Modifier.clickable {
                                count2++
                            })
                        Text(
                            "$count2",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = Color.DarkGray,
                            modifier = Modifier.clickable {
                                if (count2 > 0)
                                    count2--
                            })

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(35.dp)
                        .background(
                            brush = Brush.horizontalGradient(colors),
                            shape = RoundedCornerShape(20.dp)
                        )
                )

                Slider(
                    value = sliderPosition2,
                    onValueChange = { newPosition ->
                        sliderPosition2 = newPosition
                        val color = lerpColor(colors, newPosition)
                        selectedColor2 = (color.toIntArray())
                        bgColor = color
                        Log.d("ColorValue", "${selectedColor.get(0)}")
                    },
                    valueRange = 0f..1f,
                    modifier = Modifier.width(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { firstScreen = false },
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ), enabled = textFieldValue.isNotEmpty() || textFieldValue2.isNotEmpty()) {
                    Text(text = "Click",style = MaterialTheme.typography.labelLarge)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                LottieAnimation(
                    composition = modifiedComposition,
                    iterations = LottieConstants.IterateForever,
                    //fontMap =fontMap
                )
                Button(onClick = {
                    firstScreen = true
                    textFieldValue = ""
                    textFieldValue2 = ""
                    sliderPosition = 0f
                    sliderPosition2 = 0f
                    selectedColor = intArrayOf(0, 0, 0)
                    selectedColor2 = intArrayOf(0, 0, 0)
                    count = 15
                    count2 = 15
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )) {
                    Text(text = "Back",style = MaterialTheme.typography.labelLarge)
                }
            }
        }

    }
}
fun lerpColor(colors: List<Color>, position: Float): Color {
    // Clamp the position value to the range 0f..1f
    val clampedPosition = position.coerceIn(0f, 1f)

    // Calculate segment count
    val segmentCount = colors.size - 1
    val segment = (clampedPosition * segmentCount).toInt()

    // Segment position relative to its start
    val segmentPosition = (clampedPosition * segmentCount) - segment

    // Get start and end colors
    val startColor = colors[segment]
    val endColor = colors.getOrNull(segment + 1) ?: colors.last()

    // Linearly interpolate between the start and end colors
    return Color(
        red = lerp(startColor.red, endColor.red, segmentPosition),
        green = lerp(startColor.green, endColor.green, segmentPosition),
        blue = lerp(startColor.blue, endColor.blue, segmentPosition),
        alpha = 1f
    )
}
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

fun Color.toIntArray(): IntArray {
    return intArrayOf(
        (red * 255).toInt().coerceIn(0, 255),
        (green * 255).toInt().coerceIn(0, 255),
        (blue * 255).toInt().coerceIn(0, 255)
    )
}
fun modifyLottieJson(lottieJson: String, textString :(String) -> Unit={}, value: String, value2 : String, colorArray : FloatArray, colorArray2: FloatArray,setCount : String,setCount2: String ): String {
    val jsonObject = JSONObject(lottieJson)
    Log.d("JSON", jsonObject.toString())
    val layersArray = jsonObject.getJSONArray("layers")

    for (i in 0 until layersArray.length()) {
        val layer = layersArray.getJSONObject(i)
        if (layer.getInt("ty") == 5 && layer.has("t")) {
            val textObject = layer.getJSONObject("t")
            if (textObject.has("d")) {
                val dObject = textObject.getJSONObject("d")
                if (dObject.has("k")) {
                    val layerName = layer.getString("nm")
                    val textValue = dObject.getJSONArray("k")
                    for (j in 0 until textValue.length()) {
                        val textItem = textValue.getJSONObject(j)
                        if (textItem.has("s")) {
                            val styleObject = textItem.getJSONObject("s")
                            when (layerName) {
                                ".manheading" -> {
                                    val originalText = styleObject.getString("t")
                                    val updatedText = originalText.replace(
                                        originalText,
                                        value
                                    )
                                    styleObject.put("t", updatedText)
                                    styleObject.put("s", setCount)
                                    val manheadingColor = JSONArray()
                                    manheadingColor.put(colorArray[0])
                                    manheadingColor.put(colorArray[1])
                                    manheadingColor.put(colorArray[2])
                                    styleObject.put("fc", manheadingColor)
                                }
                                ".subcopy" -> {
                                    val originalText = styleObject.getString("t")
                                    val updatedSubtext = originalText.replace(
                                        originalText, value2
                                    )
                                    styleObject.put("t", updatedSubtext)
                                    styleObject.put("s", setCount2)
                                    val copyColor = JSONArray()
                                    copyColor.put(colorArray2[0])
                                    copyColor.put(colorArray2[1])
                                    copyColor.put(colorArray2[2])
                                    styleObject.put("fc", copyColor)
                                }
                            }
                            textItem.put("s", styleObject)
                        }
                    }
                }
            }
        }
    }
    return jsonObject.toString()
}

suspend fun fetchLottieJsonFromUrl(url: String): String? = withContext(Dispatchers.IO)
{
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    try {
        client.newCall(request).execute().body?.string()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun convertToFloatArray(colorArray: IntArray): FloatArray {
    return floatArrayOf(
        colorArray[0] / 255f,
        colorArray[1] / 255f,
        colorArray[2] / 255f
    )
}