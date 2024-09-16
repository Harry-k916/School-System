package com.example.mobileapp.ui.theme.screens.student





import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mobileapp.data.StudentViewModel
import com.example.mobileapp.models.Student
import com.example.mobileapp.navigation.ROUTE_UPDATE_STUDENT


@Composable
fun ViewStudentsScreen(navController: NavHostController){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        var context = LocalContext.current
        var studentRepository = StudentViewModel(navController,context)


        val emptyUploadState = remember { mutableStateOf(Student("","","","","","")) }
        var emptyUploadsListState = remember { mutableStateListOf<Student>() }

        var students = studentRepository.viewStudent(emptyUploadState, emptyUploadsListState)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "All Students",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(){
                items(students){
                    StudentItem(
                        imageUrl = it.imageUrl,
                        firstname = it.firstname,
                        lastname = it.lastname,
                        gender = it.gender,
                        desc = it.desc,
                        id = it.id,
                        navController = navController,
                        studentRepository = studentRepository
                    )
                }
            }
        }
    }

}

@Composable
fun StudentItem(imageUrl:String,firstname:String,lastname:String,gender:String,
               desc:String,id:String,navController: NavHostController,studentRepository:StudentViewModel){
    var showFullText by remember {
        mutableStateOf(false)
    }
    val context= LocalContext.current
    Column (modifier = Modifier.fillMaxWidth(),){
        Card (
            modifier = Modifier
                .padding(10.dp)
                .height(210.dp)
                .animateContentSize(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            )
        ){
            Row (){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Image(modifier = Modifier
                        .width(200.dp)
                        .height(150.dp).padding(10.dp),
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop)
                    Row {
                        Button(
                            onClick = {
                                studentRepository.deleteStudent(context ,id,navController)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "DELETE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick = {
                            navController.navigate(ROUTE_UPDATE_STUDENT+"/$id")
                        },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = "UPDATE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                    }
                }


                Column(modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 15.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    Text(text = "FIRSTNAME",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = firstname,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "LASTNAME",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = lastname,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "GENDER",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = gender,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = "DESCRIPTION",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(modifier = Modifier.clickable {
                        showFullText = !showFullText
                    },
                        text = desc,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (showFullText) 100 else 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }



            }

        }
    }

}