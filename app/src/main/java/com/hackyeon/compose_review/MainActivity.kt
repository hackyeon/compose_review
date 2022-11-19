package com.hackyeon.compose_review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            var isFavorite by rememberSaveable {
//                mutableStateOf(false)
//            }
//            ImageCard(
//                modifier = Modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(16.dp),
//                isFavorite = isFavorite) {
//                isFavorite = it
//            }

//            val navController = rememberNavController()
//            NaviContainer(navController = navController)

            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    val viewModel = viewModel<MainViewModel>()

    Column() {
        Text(text = "Hello World")
        Button(onClick = {}) {
            Text(text = "클릭")
        }
    }
}

@Composable
fun StateTest() {
    val viewModel = viewModel<MainViewModel>()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = viewModel.data.value, fontSize = 30.sp)
        Button(onClick = {
            viewModel.setData()
        }) {
            Text(text = "변경")
        }
    }
}

@Composable
fun NaviContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
            NaviFirst(navController)
        }
        composable("second") {
            NaviSecond(navController)
        }
        composable("third/{text}") { backStack ->
            NaviThird(navController,backStack.arguments?.getString("text")?: "")
        }
    }
}

@Composable
fun NaviFirst(navController: NavHostController) {
    val (text, setText) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "first")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("second")
        }) {
            Text(text = "second")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = text, onValueChange = setText)
        Button(onClick = {
            if(text.isNotBlank()) {
                navController.navigate("third/$text")
            }
        }) {
            Text(text = "third")
        }
    }

}

@Composable
fun NaviSecond(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "second")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "back button")
        }
    }
}

@Composable
fun NaviThird(navController: NavHostController,text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "third")
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "back button")
        }
    }
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScaffoldTest() {
    val (text, setValue) = remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            TextField(value = text, onValueChange = {
                setValue(it)
            })
            Button(onClick = {
                keyboardController?.hide()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(text)
                }
            }) {
                Text(text = "클릭")
            }
        }
    }


}


@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    callback: (Boolean) -> Unit
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.height(200.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.s1),
                contentDescription = "s1",
                contentScale = ContentScale.Crop,

            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    callback(!isFavorite)
                }) {
                    Icon(
                        imageVector = if(isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun LazyTest() {
    LazyColumn(
        modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item { 
            Text(text = "header")
        }
        items(50) { index ->
            Text(
                text = "Text: $index")
        }
        item { 
            Text(text = "Footer")
        }
    }
}

@Composable
fun ColumnScroll() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        for(i in 1..100) {
            Text(text = "123")
        }
    }
}

@Composable
fun BoxTest() {
    Box(
        modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Text(text = "Box")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(text = "Test")
        }
    }
}

@Composable
fun Greeting() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello")
        Text(text = "World")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BoxTest()
}