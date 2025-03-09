package com.example.lottiedemo3

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.Lottie
import com.example.lottiedemo3.ui.theme.LottieDemo3Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottieDemo3Theme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.outlineVariant,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                            title = {
                                Text("Dynamic Lottie")
                            }
                        )
                    }
                ){innerPadding->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color =MaterialTheme.colorScheme.outline
                    ) {
                        //https://assets4.lottiefiles.com/temp/lf20_fw4v8s.json
                        LottieFromUrl(
                            url = "https://jep-asset.akamaized.net/cms/assets/JFS_Dynamic_JSON_Greeting_V2.json",
                            "", ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LottieDemo3Theme {
        Greeting("Android")
    }
}