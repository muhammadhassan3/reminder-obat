package com.muhammhassan.reminderobat.ui.view.auth

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.view.auth.login.LoginView
import com.muhammhassan.reminderobat.ui.view.auth.login.LoginViewModel
import com.muhammhassan.reminderobat.ui.view.auth.register.RegisterView
import com.muhammhassan.reminderobat.ui.view.auth.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthView(
    navigateToMainActivity: () -> Unit,
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier.padding(16.dp)
    ) {
        composable(route = Screen.Login.route) {
            val viewModel = koinViewModel<LoginViewModel>()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val isDialogShow by viewModel.isDialogShow.collectAsState()
            val dialogData by viewModel.dialogData.collectAsState()

            val data by viewModel.uiState.collectAsState()

            LoginView(
                onEmailChanged = viewModel::setEmail,
                email = email,
                password = password,
                onPasswordChanged = viewModel::setPassword,
                onSaveClicked = {
                    viewModel.login()
                },
                onRegisterClicked = {
                    navController.navigate(Screen.Register.route)
                },
                dialogData = dialogData,
                data = data,
                isDialogShow = isDialogShow,
                onSuccessResponse = navigateToMainActivity, onErrorResponse = viewModel::setErrorMessage
            )
        }

        composable(
            route = Screen.Register.route
        ) {
            val viewModel = koinViewModel<RegisterViewModel>()
            val name by viewModel.name.collectAsState()
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val confirmPassword by viewModel.confirmPassword.collectAsState()
            val dialog by viewModel.dialogData.collectAsState()
            val isDialogShow by viewModel.isDialogShow.collectAsState()

            val data by viewModel.uiState.collectAsState()
            RegisterView(onNavUp = { navController.navigateUp() },
                onNameChanged = viewModel::setName,
                onEmailChanged = viewModel::setEmail,
                onPasswordChanged = viewModel::setPassword,
                onSaveClicked = { viewModel.save() },
                onConfirmPasswordChanged = viewModel::setConfirmPassword,
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                isDialogShow = isDialogShow,
                dialogData = dialog,
                data = data,
                onErrorResponse = viewModel::setError,
                onSuccessResponse = {
                    navController.navigateUp()
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                })
        }
    }
}