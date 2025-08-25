package ru.kulishov.openweatherapp.auth

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle

class OpenWeatherAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {
    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String?,
        requiredFeatures: Array<String>?,
        options: Bundle?
    ): Bundle {
        val result = Bundle()
        result.putString(AccountManager.KEY_ACCOUNT_NAME, "openweatherapp")
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, "openweatherapp")
        return result
    }

    // Другие обязательные методы (getAuthToken, etc.) можно оставить пустыми или реализовать по необходимости
    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle?
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun addAccountFromCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        accountCredentials: Bundle?
    ): Bundle? {
        return super.addAccountFromCredentials(response, account, accountCredentials)
    }

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle? {
        TODO("Not yet implemented")
    }

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle? {
        TODO("Not yet implemented")
    }

    override fun finishSession(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        sessionBundle: Bundle?
    ): Bundle? {
        return super.finishSession(response, accountType, sessionBundle)
    }

    override fun getAccountCredentialsForCloning(
        response: AccountAuthenticatorResponse?,
        account: Account?
    ): Bundle? {
        return super.getAccountCredentialsForCloning(response, account)
    }

    override fun getAccountRemovalAllowed(
        response: AccountAuthenticatorResponse?,
        account: Account?
    ): Bundle? {
        return super.getAccountRemovalAllowed(response, account)
    }

    override fun getAuthTokenLabel(p0: String?): String? {
        return null
    }

    override fun hasFeatures(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Array<out String?>?
    ): Bundle? {
        return null
    }

    override fun isCredentialsUpdateSuggested(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        statusToken: String?
    ): Bundle? {
        return super.isCredentialsUpdateSuggested(response, account, statusToken)
    }

    override fun startAddAccountSession(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String?>?,
        options: Bundle?
    ): Bundle? {
        return super.startAddAccountSession(
            response,
            accountType,
            authTokenType,
            requiredFeatures,
            options
        )
    }

    override fun startUpdateCredentialsSession(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle? {
        return super.startUpdateCredentialsSession(response, account, authTokenType, options)
    }

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle? {
        return null
    }
}
