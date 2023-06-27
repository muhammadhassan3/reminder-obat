package com.muhammhassan.reminderobat.ui.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.component.ButtonAddDrug
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.view.add.drug.AddDrugView
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderView
import com.muhammhassan.reminderobat.ui.view.add.stock.AddStockView
import com.muhammhassan.reminderobat.ui.view.auth.profile.ProfileView
import com.muhammhassan.reminderobat.ui.view.auth.profile.ProfileViewModel
import com.muhammhassan.reminderobat.ui.view.consultation.ConsultationView
import com.muhammhassan.reminderobat.ui.view.consultation.ConsultationViewModel
import com.muhammhassan.reminderobat.ui.view.detail.history.DetailHistoryView
import com.muhammhassan.reminderobat.ui.view.detail.schedule.DetailScheduleView
import com.muhammhassan.reminderobat.ui.view.education.DetailEducationView
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
                    navController.navigate(Screen.AddDrugs.route)
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
                val date by viewModel.date.collectAsState()
                val data by viewModel.data.collectAsState()

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

            composable(Screen.AddDrugs.route) {
                AddDrugView(onNextPressed = { data ->
                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        ArgsName.data, data
                    )
                    navController.navigate(Screen.AddReminder.route)
                }, onBackPressed = {
                    navController.navigateUp()
                })
            }

            composable(Screen.AddReminder.route) {
                val data =
                    navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data)
                        ?: DrugsData()
                AddReminderView(onBackPressed = {
                    navController.navigateUp()
                }, onNextPressed = { finalData ->

                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        ArgsName.data, finalData
                    )
                    navController.navigate(Screen.AddStock.route)
                }, data = data)
            }

            composable(Screen.AddStock.route) {
                val data =
                    navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data)
                        ?: DrugsData()
                AddStockView(onBackPressed = {
                    navController.navigateUp()
                }, onDataSaved = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }, data = data)
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
                val data by viewModel.uiState.collectAsState()
                val dialogData by viewModel.dialogData.collectAsState()
                val isDialogShow by viewModel.isDialogShow.collectAsState()
                EducationView(onNavUp = { navController.navigateUp() },
                    data = data,
                    onItemClicked = { item ->
                        navController.navigate(
                            Screen.DetailEducation.createRoute(
                                item.id, item.title, item.image, item.content
                            )
                        )
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

            composable(
                route = Screen.DetailEducation.route, arguments = listOf(navArgument(ArgsName.id) {
                    type = NavType.StringType
                }, navArgument(ArgsName.imageUrl) {
                    type = NavType.StringType
                    nullable = true
                }, navArgument(ArgsName.title) {
                    type = NavType.StringType
                }, navArgument(ArgsName.content) {
                    type = NavType.StringType
                })
            ) {
                onNavBarChangeColor.invoke(Color.Black)
                val id = it.arguments?.getString(ArgsName.id) ?: ""
                val imageUrl = it.arguments?.getString(ArgsName.imageUrl)
                val title = it.arguments?.getString(ArgsName.title) ?: ""
                val content = it.arguments?.getString(ArgsName.content) ?: ""

                DetailEducationView(
                    onNavUp = {
                        navController.navigateUp()
                    },
                    data = Articles(id = id, image = imageUrl, content = content, title = title),
                )
            }

            composable(route = Screen.Consultation.route) {
                val viewModel = koinViewModel<ConsultationViewModel>()
                val uiState by viewModel.uiState.collectAsState()
                val message = viewModel.messageList
                val dialogData by viewModel.dialogData.collectAsState()
                val isDialogShow by viewModel.isDialogShow.collectAsState()
                val userEmail by viewModel.userEmail.collectAsState()
                val draft by viewModel.message.collectAsState()


                onNavBarChangeColor.invoke(Color.Black)
                ConsultationView(data = message,
                    onNavUp = { navController.navigateUp() },
                    onMessageChange = {},
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
                val name by viewModel.username.collectAsState()
                val email by viewModel.email.collectAsState()
                val isDialogShow by viewModel.isDialogShow.collectAsState()
                val dialogData by viewModel.dialogData.collectAsState()
                ProfileView(
                    userName = name, userEmail = email, onLogOutClick = {
                        viewModel.setDialog(
                            DialogData(title = "Konfirmasi",
                                message = "Apakah kamu yakin akan keluar?",
                                buttonType = ButtonType.YES_NO,
                                onConfirmAction = {
                                    viewModel.logout {
                                        openLoginPage.invoke()
                                    }
                                }, onCancelAction = viewModel::hideDialog)
                        )
                    }, isDialogShow = isDialogShow, dialogData = dialogData
                )
            }
        }
    }

}