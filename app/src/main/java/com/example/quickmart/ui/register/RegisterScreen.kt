package com.example.quickmart.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quickmart.R
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = H1Color)) {
            append("Do you have an account? ")
        }
        pushStringAnnotation(tag = "login", annotation = "login")
        withStyle(style = SpanStyle(color = GreenColor)) {
            append("login")
        }
        pop()
    }
    LaunchedEffect(true) {
        viewModel.navigator.onEach { registerNavigator ->
            viewModel.setNavigator { null }
            registerNavigator.let {
                when (it) {
                    RegisterNavigator.NavigateToLoginScreen -> {
                        navController.navigate("login")
                    }
                }
            }
        }.flowOn(EmptyCoroutineContext).launchIn(this)
    }
    Column(
        modifier = Modifier
            .padding(15.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Register",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = H1Color,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Enter your details to continue",
            fontSize = 17.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = viewModel.firstName,
            onValueChange = { viewModel.firstName = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            label = { Text("FirstName") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray,
                textColor = H1Color
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = viewModel.lastName,
            onValueChange = { viewModel.lastName = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            label = { Text("LastName") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray,
                textColor = H1Color
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray,
                textColor = H1Color
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            label = { Text("Password") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        painter = if (viewModel.isPasswordVisible) painterResource(id = R.drawable.hide_password_ic) else painterResource(
                            id = R.drawable.show_password_ic
                        ),
                        contentDescription = if (viewModel.isPasswordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
            visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (viewModel.isButtonEnabled) GreenColor else Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .size(width = 0.dp, height = 70.dp)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    enabled = viewModel.isButtonEnabled
                ) {
                    viewModel.register()
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Register",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = text,
            modifier = Modifier
                .clickable { navController.navigate("login") }
                .align(Alignment.CenterHorizontally)
        )
    }
}