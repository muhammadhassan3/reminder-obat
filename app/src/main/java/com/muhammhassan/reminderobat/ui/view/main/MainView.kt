package com.muhammhassan.reminderobat.ui.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.component.ButtonAddDrug
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.view.auth.profile.ProfileView
import com.muhammhassan.reminderobat.ui.view.auth.profile.ProfileViewModel
import com.muhammhassan.reminderobat.ui.view.consultation.ConsultationView
import com.muhammhassan.reminderobat.ui.view.consultation.ConsultationViewModel
import com.muhammhassan.reminderobat.ui.view.detail.history.DetailHistoryView
import com.muhammhassan.reminderobat.ui.view.detail.schedule.DetailScheduleView
import com.muhammhassan.reminderobat.ui.view.education.EducationView
import com.muhammhassan.reminderobat.ui.view.education.EducationViewModel
import com.muhammhassan.reminderobat.ui.view.home.HomeView
import com.muhammhassan.reminderobat.ui.view.home.HomeViewModel
import com.muhammhassan.reminderobat.ui.view.progress.ProgressView
import com.muhammhassan.reminderobat.utils.DialogData
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainView(
    onNavBarChangeColor: (color: Color) -> Unit,
    openLoginPage: () -> Unit,
    navigateToAddDrugs: () -> Unit,
    navigateToDetailEducation: (value: Articles) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (route == Screen.Home.route) {
                ButtonAddDrug(modifier = Modifier, onAddButtonClicked = {
                    navigateToAddDrugs.invoke()
                }, onProgressButtonClicked = {
                    navController.navigate(Screen.Progress.route)
                })
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        NavHost(
            modifier = modifier.padding(padding),
            navController = navController,
            startDestination = Screen.Home.route
        ) {

            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = koinViewModel()
                val date by viewModel.date.collectAsStateWithLifecycle()
                val data by viewModel.data.collectAsStateWithLifecycle()

                onNavBarChangeColor.invoke(MaterialTheme.colors.primaryVariant)
                HomeView(onItemClick = { id ->
                    navController.navigate(Screen.DetailSchedule.createRoute(id))
                }, date = date, data = data, onEducationClicked = {
                    navController.navigate(Screen.Education.route)
                }, onConsultationClicked = {
                    navController.navigate(Screen.Consultation.route)
                }, onProfileClicked = {
                    navController.navigate(Screen.Profile.route)
                })
            }

            composable(
                route = Screen.DetailSchedule.route, arguments = listOf(navArgument(ArgsName.id) {
                    type = NavType.LongType
                })
            ) {
                val id = it.arguments?.getLong(ArgsName.id) ?: 0
                DetailScheduleView(onNavigateUp = { navController.navigateUp() }, id = id)
            }

            composable(Screen.Progress.route) {
                ProgressView(onNavigateUp = {
                    navController.navigateUp()
                }, onItemClick = {
                    navController.navigate(Screen.DetailProgress.createRoute(it))
                })
            }
            composable(
                route = Screen.DetailProgress.route, arguments = listOf(navArgument(ArgsName.id) {
                    defaultValue = 0
                    type = NavType.LongType
                })
            ) {
                val id = it.arguments?.getLong(ArgsName.id) ?: 0
                DetailHistoryView(onNavigateUp = {
                    navController.navigateUp()
                }, drugsId = id)
            }

            composable(
                route = Screen.Education.route
            ) {
                onNavBarChangeColor.invoke(MaterialTheme.colors.primaryVariant)
                val viewModel = koinViewModel<EducationViewModel>()
                val data by viewModel.uiState.collectAsStateWithLifecycle()
                val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                EducationView(
                    onNavUp = { navController.navigateUp() },
                    data = data,
                    onItemClicked = { item ->
                        navigateToDetailEducation.invoke(item)
                    },
                    onErrorResponse = {
                        viewModel.setError(it, action = {
                            navController.navigateUp()
                        })
                    },
                    dialogData = dialogData,
                    isDialogShow = isDialogShow
                )
            }

            composable(route = Screen.Consultation.route) {
                val viewModel = koinViewModel<ConsultationViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val message = viewModel.messageList
                val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()
                val draft by viewModel.message.collectAsStateWithLifecycle()


                onNavBarChangeColor.invoke(Color.Black)
                ConsultationView(
                    data = message,
                    onNavUp = { navController.navigateUp() },
                    onMessageChange = viewModel::setDraft,
                    dialogData = dialogData,
                    isDialogShow = isDialogShow,
                    userEmail = userEmail,
                    onSendClicked = viewModel::onMessageSend,
                    draft = draft,
                    uiState = uiState
                )
            }

            composable(route = Screen.Profile.route) {
                val viewModel = koinViewModel<ProfileViewModel>()
                val name by viewModel.username.collectAsStateWithLifecycle()
                val email by viewModel.email.collectAsStateWithLifecycle()
                val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                ProfileView(
                    userName = name, userEmail = email, onLogOutClick = {
                        viewModel.setDialog(
                            DialogData(
                                title = "Konfirmasi",
                                message = "Apakah kamu yakin akan keluar?",
                                buttonType = ButtonType.YES_NO,
                                onConfirmAction = {
                                    viewModel.logout {
                                        openLoginPage.invoke()
                                    }
                                }, onCancelAction = viewModel::hideDialog
                            )
                        )
                    }, isDialogShow = isDialogShow, dialogData = dialogData
                )
            }
        }
    }

}