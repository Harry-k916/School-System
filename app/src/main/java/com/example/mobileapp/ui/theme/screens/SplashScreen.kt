package com.example.mobileapp.ui.theme.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobileapp.R
import com.example.mobileapp.navigation.ROUTE_REGISTER
import com.example.mobileapp.navigation.ROUTE_SPLASH
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.6f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(800)
        navController.navigate(ROUTE_REGISTER){
            popUpTo(ROUTE_SPLASH){
                inclusive=true
            }
        }

    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .wrapContentHeight(Alignment.CenterVertically)
        .wrapContentWidth(Alignment.CenterHorizontally)) {
//        Text(text = "Britam Insuarance",
//            fontSize = 40.sp,
//            color = Color.White,
//            modifier = Modifier.scale(scale.value))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "LOGO",
            modifier = Modifier.scale(scale.value)
                                .size(400.dp))
    }
}