package com.example.quickmart.ui.login

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.db.QuickMartDao
import com.example.quickmart.data.db.entities.UserEntity
import com.example.quickmart.data.repository.AuthenticationRepository
import com.example.quickmart.utils.appUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var dao: QuickMartDao
    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var isPasswordVisible by mutableStateOf(false)

    val isButtonEnabled: Boolean by derivedStateOf {
        email.isNotBlank() && isValidEmail(email) &&
                password.isNotBlank() && isPasswordValid(password)
    }

    fun initDao(daoParam: QuickMartDao) {
        dao = daoParam
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
        return email.matches(emailPattern)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }

    private val initNavigator: LoginNavigator? by lazy { null }

    private val _navigator = MutableStateFlow(initNavigator)
    val navigator get() = _navigator.asStateFlow().filterNotNull()

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun login() {
        viewModelScope.launch {
            val id = AuthenticationRepository.loginUserByEmailAndPassword(
                email, password
            )
            if (id != null) {
                val user = AuthenticationRepository.getUserDataFromServer(id)
                appUser = user
                val entitiy = UserEntity(
                    id = user.id!!,
                    firstName = user.firstName!!,
                    lastName = user.lastName!!,
                    email = user.email!!,
                    isAdmin = user.admin,
                    profilePic = user.profilePic
                )
                AuthenticationRepository.writeUserDataToRoomDb(dao, entitiy)
                setNavigator { LoginNavigator.NavigateToShopScreen }
            }
        }
    }

    fun setNavigator(builder: () -> LoginNavigator?) {
        viewModelScope.launch {
            _navigator.emit(builder())
        }
    }
}