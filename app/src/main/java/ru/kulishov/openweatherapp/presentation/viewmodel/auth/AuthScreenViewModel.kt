package ru.kulishov.openweatherapp.presentation.viewmodel.auth

import android.accounts.Account
import android.accounts.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kulishov.openweatherapp.domain.model.UiState
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val accountManager: AccountManager
) : BaseViewModel() {
    private val otpKey = "1234"

    private val _uiState = MutableStateFlow<UiState>(UiState.locationEnabled)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _otpState = MutableStateFlow<Boolean>(false)
    val otpState: StateFlow<Boolean> = _otpState.asStateFlow()


    private val _phone = MutableStateFlow<String>("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _otp = MutableStateFlow<String>("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    init {
        val currentState = checkUserAuthState()
        if (currentState) _uiState.value = UiState.Success
    }

    fun setPhone(inp: String) {
        val numericRegex = Regex("[^0-9]")
        val stripped = numericRegex.replace(inp, "")
        _phone.value = if (stripped.length >= 10) {
            stripped.substring(0..9)
        } else {
            stripped
        }
    }

    fun checkUserAuthState(): Boolean =
        accountManager.getAccountsByType("openweatherapp").isNotEmpty()

    fun sendCod() {
        _otpState.value = true
    }

    fun handleOTPVerification(otpCode: String) {
        _uiState.value = UiState.Loading
        launch {
            if (otpCode == otpKey) {
                val account = Account("+7" + phone.value, "openweatherapp")
                val authToken = "OpenWeather Auth Token"

                accountManager.addAccountExplicitly(account, null, null)
                accountManager.setAuthToken(account, "OpenWeather Auth Token", authToken)

                _uiState.value = UiState.Success
            } else {
                _phone.value = ""
                _otpState.value = false
                _uiState.value = UiState.NotPermission
            }
        }


    }

}