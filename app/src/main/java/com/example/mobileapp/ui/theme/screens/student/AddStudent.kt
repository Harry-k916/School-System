package com.example.mobileapp.ui.theme.screens.student

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mobileapp.R
import com.example.mobileapp.data.StudentViewModel
import com.example.mobileapp.navigation.ROUTE_ADD_STUDENT
import com.example.mobileapp.navigation.ROUTE_VIEW_STUDENT

@Composable
fun Student(navController: NavController){
    val imageUri = rememberSaveable(){
        mutableStateOf<Uri?>(null)
    }
    val painter = rememberImagePainter(
        data = imageUri.value ?: R.drawable.ic_person,
        builder = {
            crossfade(true)
        }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        uri?.let { imageUri.value = it }
    }
    var firstname by remember {
        mutableStateOf(value = "")
    }

    var lastname by remember {
        mutableStateOf(value = "")
    }
    var gender by remember {
        mutableStateOf(value = "")
    }
    var desc by remember {
        mutableStateOf(value = "")
    }

    Column (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ){
        Text(text = "ADD NEW STUDENT",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(Color.Cyan)
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {
                navController.navigate(ROUTE_VIEW_STUDENT)
            }) {
                Text(text = "VIEW ALL STUDENTS")
            }
            val context= LocalContext.current
            Button(onClick = {
                val studentRepository=StudentViewModel(navController, context)

                if (imageUri.value!=null){
                    studentRepository.saveStudent(
                        filePath = imageUri.value!!,
                        firstname=firstname,
                        lastname=lastname,
                        gender=gender,
                        desc=desc
                    )
                    navController.navigate(ROUTE_ADD_STUDENT)
                }else{
                    Toast.makeText(context,"Please select an image",Toast.LENGTH_LONG).show()
                }
            }) {
                Text(text = "SAVE")
            }
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card (
                shape = CircleShape,
                modifier = Modifier
                    .padding(10.dp)
                    .size(200.dp)
            ){
                Image(painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop)
            }
            Text(text = "Attach Picture Here")
        }
        OutlinedTextField(value = firstname,
            onValueChange ={newFirstName -> firstname = newFirstName},
            label = { Text(text = "Enter Your First Name") },
            placeholder = { Text(text = "Please Enter First Name") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = lastname,
            onValueChange = {newLastName->lastname = newLastName},
            label = { Text(text = "Enter Your Last Name") },
            placeholder = { Text(text = "Please Enter Your Last Name") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = gender,
            onValueChange ={newGender->gender = newGender} ,
            label = { Text(text = "Enter Your Gender") },
            placeholder = { Text(text = "Please Enter Your Gender") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = desc,
            onValueChange ={newDesc->desc=newDesc},
            label = { Text(text = "Enter Student Description") },
            placeholder = { Text(text = "Please Enter a brief Descption") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .height(150.dp),
            singleLine = false)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentPreview(){
    Student(rememberNavController())
}