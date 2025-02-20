package com.example.tmdb.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tmdb.R
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun LoginScreen(modifier: Modifier=Modifier,toSignUp:()->Unit={},longInClick:()->Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)

        )
        OutlinedTextField(
            value = "",
            label = {Text("Username", color = MaterialTheme.colorScheme.onSurface)},
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            label = {Text("Password", color = MaterialTheme.colorScheme.onSurface)},
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Text("Forgot password?",Modifier.align(Alignment.CenterVertically))
            TextButton(onClick = toSignUp) { Text("Sign Up")}
        }
        Button(
            onClick = {longInClick()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Login")
        }
        Spacer(Modifier.height(8.dp))
        Text("Login With",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Card(){
                Row (verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ){
                    Text("Google",Modifier.padding(end = 4.dp))
                    Image(painterResource(R.drawable.goog),
                        contentDescription = null,
                        modifier = Modifier.height(30.dp).width(20.dp))
                }
            }
            Spacer(Modifier.width(8.dp))
            Card(){
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)) {
                    Text("Apple",Modifier.padding(end = 4.dp))
                    Image(painterResource(R.drawable.apple_861x1024),
                        contentDescription = null,
                        modifier = Modifier.height(30.dp).width(20.dp))

                }
            }
        }
    }

}

@Composable
@PreviewLightDark
fun LoginScreenPreview(){
    TMDBTheme {
        LoginScreen(){}
    }
}