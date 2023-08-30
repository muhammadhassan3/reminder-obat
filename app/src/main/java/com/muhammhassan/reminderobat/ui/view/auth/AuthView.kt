package com.muhammhassan.reminderobat.ui.view.auth

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.view.auth.login.LoginView
import com.muhammhassan.reminderobat.ui.view.auth.login.LoginViewModel
import com.muhammhassan.reminderobat.ui.view.auth.register.RegisterView
import com.muhammhassan.reminderobat.ui.view.auth.register.RegisterViewModel
import com.muhammhassan.reminderobat.utils.DialogData
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthView(
    navigateToMainActivity: () -> Unit,
    containerDialogShow: Boolean,
    containerDialogData: DialogData,
    navigateToEmailForm: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current

    if (containerDialogShow) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false, dismissOnClickOutside = false
            ), content = {
                DialogContent(
                    message = containerDialogData.message,
                    title = containerDialogData.title,
                    buttonType = containerDialogData.buttonType,
                    onNeutralClicked = containerDialogData.onNeutralAction
                )
            }, onDismissRequest = containerDialogData.onNeutralAction
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier.padding(16.dp)
    ) {
        composable(route = Screen.Login.route) {
            val viewModel = koinViewModel<LoginViewModel>()
            val email by viewModel.email.collectAsStateWithLifecycle()
            val password by viewModel.password.collectAsStateWithLifecycle()
            val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
            val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
            val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()

            val data by viewModel.uiState.collectAsStateWithLifecycle()

            LoginView(
                onEmailChanged = viewModel::setEmail,
                email = email,
                password = password,
                onPasswordChanged = viewModel::setPassword,
                onSaveClicked = viewModel::login,
                onRegisterClicked = {
                    navController.navigate(Screen.Register.route)
                },
                dialogData = dialogData,
                data = data,
                isDialogShow = isDialogShow,
                onSuccessResponse = navigateToMainActivity,
                onErrorResponse = viewModel::setErrorMessage,
                onResetPasswordClicked = navigateToEmailForm,
                onPhoneNumberChanged = viewModel::setPhoneNumber,
                phoneNumber = phoneNumber
            )
        }

        composable(
            route = Screen.Register.route
        ) {
            val viewModel = koinViewModel<RegisterViewModel>()
            val name by viewModel.name.collectAsStateWithLifecycle()
            val email by viewModel.email.collectAsStateWithLifecycle()
            val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
            val password by viewModel.password.collectAsStateWithLifecycle()
            val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()
            val dialog by viewModel.dialogData.collectAsStateWithLifecycle()
            val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()

            val data by viewModel.uiState.collectAsStateWithLifecycle()
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
                },
                phoneNumber = phoneNumber,
                onPhoneNumberChanged = viewModel::setPhoneNumber
            )
        }
    }
}