package com.example.mobileapp.ui.theme.screens.register



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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
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
import com.example.mobileapp.navigation.ROUTE_LOGIN


@Composable
fun Greeting(navController: NavController) {
    val context= LocalContext.current
    var firstname by remember {
        mutableStateOf(value = "")
    }
    var lastname by remember {
        mutableStateOf(value = "")
    }
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back!",
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .height(100.dp))
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = firstname,
            onValueChange = {
                    newFirstName -> firstname = newFirstName
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Enter first name")},
            placeholder = { Text(text = "Please Enter First Name")})
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = lastname,
            onValueChange = {
                    newLastName->lastname = newLastName
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Enter Last Name")},
            placeholder = { Text(text = "Please Enter Last Name")}
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = email,
            onValueChange = {
                    newEmail->email = newEmail
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Enter Email")},
            placeholder = { Text(text = "Please Enter Email")}
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = password,
            onValueChange = {
                    newPassword->password = newPassword
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Enter Password")},
            placeholder = { Text(text = "Please Enter Password")}
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
                         val register = AuthViewModel(navController , context)
                        register.signup(firstname.trim(),lastname.trim(),email.trim(),password.trim())
                        navController.navigate(ROUTE_LOGIN)
        },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Black)) {
            Text(text = "CLICK to REGISTER",
                modifier = Modifier.padding(10.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
        Greeting(rememberNavController())
}