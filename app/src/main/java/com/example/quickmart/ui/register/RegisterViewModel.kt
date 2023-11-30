package com.example.quickmart.ui.register

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var firstName by mutableStateOf("")

    var lastName by mutableStateOf("")

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var isPasswordVisible by mutableStateOf(false)

    val isButtonEnabled: Boolean by derivedStateOf {
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                email.isNotBlank() && isValidEmail(email) &&
                password.isNotBlank() && isPasswordValid(password)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
        return email.matches(emailPattern)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }

    private val initNavigator: RegisterNavigator? by lazy { null }

    private val _navigator = MutableStateFlow(initNavigator)
    val navigator get() = _navigator.asStateFlow().filterNotNull()

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun register() {
        viewModelScope.launch {
            val authUser =
                AuthenticationRepository.createUserByEmailAndPassword(email, password).copy(
                    firstName = firstName,
                    lastName = lastName
                )
            AuthenticationRepository.addUserToFireStore(authUser)
            setNavigator { RegisterNavigator.NavigateToLoginScreen }
        }
    }

    fun setNavigator(builder: () -> RegisterNavigator?) {
        viewModelScope.launch {
            _navigator.emit(builder())
        }
    }
}