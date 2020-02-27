package com.manegow.safeami.ui.login


import androidx.lifecycle.*
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.*

import com.manegow.safeami.MyApplication
import com.manegow.safeami.data.MaterialDialogContent

import com.manegow.safeami.R
import com.manegow.safeami.data.AuthType
import com.manegow.safeami.data.AuthUiModel
import com.manegow.safeami.util.Event
import kotlinx.coroutines.*
import com.manegow.safeami.data.Result
import com.manegow.safeami.util.safeApiCall

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val firebaseAuth: FirebaseAuth, application: MyApplication) : AndroidViewModel(application) {

     val uiState: LiveData<AuthUiModel> get() = _uiState

    private val resources = application.resources
    private val _uiState = MutableLiveData<AuthUiModel>()

    //Facebook Login Logic

    val loginManager: LoginManager = LoginManager.getInstance()
    private val mCallbackManager = CallbackManager.Factory.create()
    private val mFacebookCallbackManager = object : FacebookCallback<LoginResult>{
        override fun onSuccess(result: LoginResult?) {
            val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
            handleFacebookCredential(credential)
        }

        override fun onCancel() {
            viewModelScope.launch {
                emitUiState(error = Event(
                    AuthType.FACEBOOK to MaterialDialogContent(
                        R.string.try_again, R.string.operation_cancelled_content,
                        R.string.operation_cancelled, R.string.cancel
                    )
                )
                )
            }
        }

        override fun onError(error: FacebookException?) {
            viewModelScope.launch {
                sendErrorState(AuthType.FACEBOOK)
            }
        }
    }

    init {
        loginManager.registerCallback(mCallbackManager, mFacebookCallbackManager)
    }

    private fun handleFacebookCredential(authCredential: AuthCredential){
        viewModelScope.launch {
            emitUiState(showProgress = true)
            safeApiCall { Result.Success(signInWithCredential(authCredential)!!) }.also {
                if (it is Result,Success  && it.data.user != null) emitUiState(success = true)
                else if (it is Result.Error) handleErrorStateForSignInCredential(
                    it.exception, AuthType.FACEBOOK
                )
            }
        }
    }

    private suspend fun handleErrorStateForSignInCredential(
        exception: Exception, authType: AuthType
    ) {
        if (exception is FirebaseAuthUserCollisionException) {
            val email = exception.email
            if (email != null) fetchAllProviderForEmail(email)
            else sendErrorState(authType)
        } else sendErrorState(authType)
    }

    fun fetchAllProviderForEmail(email: String) {
        viewModelScope.launch {
            safeApiCall { Result.Success(firebaseAuth.fetchSignInMethodsForEmail(email)) }.also {
                if (it is Result.Success && it.data.signInMethods != null)
                    emitUiState(
                        linkProvider = Event(
                            it.data.signInMethods!! to MaterialDialogContent(
                                R.string.select, null, R.string.user_collision, R.string.cancel,
                                String.format(
                                    resources.getString(R.string.auth_user_collision_message), email
                                )
                            )
                        )
                    )
                else sendErrorState(AuthType.EMAIL.apply { authValue = email })
            }
        }
    }

    @Throws(Exception::class)
    private suspend fun signInWithCredential(authCredential: AuthCredential): AuthResult? {
        return firebaseAuth.signInWithCredential(authCredential).result
    }

    private suspend fun emitUiState(
        showProgress: Boolean = false,
        error: Event<Pair<AuthType, MaterialDialogContent>>? = null,
        success: Boolean = false,
        linkProvider: Event<Pair<List<String>, MaterialDialogContent>>? = null)
    = withContext(Dispatchers.Main){
        AuthUiModel(showProgress, error, success, linkProvider).also{
            _uiState.value = it
        }
    }



    /*
    fun handleGoogleAccess(data: Intent, context: Context){
         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_PRIVATE_CLIENT_ID)
            .requestEmail()
            .build()

        val mGoogleSignClient by lazy {
            GoogleSignIn.getClient(context, gso)
        }

        coroutineScope.launch {
            safeApiCall {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                val authResult =
                    signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))!!
                Result.Success(authResult)
            }.also {
                if (it is Result.Success && it.data.user != null)
                    emitUiState(success = true)
                else sendErrorState(AuthType.GOOGLE)
            }
        }
    }

    fun handleFacebookAccessToken(token: AccessToken, context: Context, auth: FirebaseAuth) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            println(user.displayName)
            println(user.uid)
        } else {
            println("NONE")
        }
    }

*/


}

