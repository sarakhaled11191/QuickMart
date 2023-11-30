package com.example.quickmart.ui.login

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = H1Color)) {
            append("Don't have an account? ")
        }
        pushStringAnnotation(tag = "register", annotation = "register")
        withStyle(style = SpanStyle(color = GreenColor)) {
            append("register")
        }
        pop()
    }
    LaunchedEffect(true) {
        viewModel.navigator.onEach { loginNavigator ->
            viewModel.setNavigator { null }
            loginNavigator.let {
                when (it) {
                    LoginNavigator.NavigateToShopScreen -> {
                        navController.navigate(
                            "shop"
                        ) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
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
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = H1Color,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Enter your email and password",
            fontSize = 17.sp,
            color = Color.Gray,
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
                    viewModel.login()
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Login",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = text,
            modifier = Modifier
                .clickable { navController.navigate("register") }
                .align(CenterHorizontally)
        )
    }
}

