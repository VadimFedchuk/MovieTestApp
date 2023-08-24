package com.development.movietestapp.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.development.movietestapp.NavigationRoute
import com.development.movietestapp.R
import com.development.movietestapp.ui.theme.DarkBlue
import com.development.movietestapp.utils.PreferencesManager
import com.development.movietestapp.utils.IMAGE_SIZE
import com.development.movietestapp.utils.USER_AVATAR_KEY
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.ProfileManager
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {
        // nothing to do. handled in FacebookCallback
    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val imageUri =
                        ProfileManager.getInstance().currentProfile?.getProfilePictureUri(
                            IMAGE_SIZE, IMAGE_SIZE
                        )
                    preferencesManager.saveData(USER_AVATAR_KEY, imageUri.toString())
                    navigateToMain(navController)
                }

                override fun onCancel() {}

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_text), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                launcher.launch(listOf("public_profile"))
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, bottom = 40.dp)
                .height(52.dp)
        ) {
            Text(
                stringResource(R.string.login).uppercase(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(500),
                )
            )
        }
    }
}

private fun navigateToMain(navController: NavHostController) {
    navController.navigate(NavigationRoute.HOME.route) {
        popUpTo(NavigationRoute.LOGIN.route) {
            inclusive = true
        }
    }
}