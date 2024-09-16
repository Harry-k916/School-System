package com.example.mobileapp.ui.theme.screens.login



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.R
import com.example.mobileapp.data.AuthViewModel
import com.example.mobileapp.navigation.ROUTE_DASHBOARD


@Composable
fun LoginScreen(navController: NavController){
    val context= LocalContext.current
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "LOGIN HERE",
            fontSize = 20.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription ="Emobilis Logo",
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .height(80.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = email,
            onValueChange = {
                    newEmail->email=newEmail
            },
            label = { Text(text = "Enter Email")},
            placeholder = { Text(text = "Please enter email")},
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = password,
            onValueChange = {newPassword->password = newPassword},
            label = { Text(text = "Enter Password")},
            placeholder = { Text(text = "Please enter password")},
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
                         val mylogin = AuthViewModel(navController, context)
                        mylogin.login(email.trim(),password.trim())
                        navController.navigate(ROUTE_DASHBOARD)
        },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Cyan)) {
            Text(text = "CLICKtoLOGIN",
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp))
        }
    }




}








@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}
