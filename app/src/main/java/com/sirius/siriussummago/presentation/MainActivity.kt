package com.sirius.siriussummago.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo
import com.sirius.siriussummago.presentation.dataModels.SummarySubject
import com.sirius.siriussummago.presentation.mdeditor.NoteViewModel
import com.sirius.siriussummago.presentation.screens.MainScreen
import com.sirius.siriussummago.presentation.screens.MainScreenDataProvider
import com.sirius.siriussummago.presentation.screens.SummariesListScreen
import com.sirius.siriussummago.presentation.screens.SummariesListScreenDataProvider
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import com.sirius.siriussummago.presentation.viewModel.SharedViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val noteViewModel: NoteViewModel by viewModel()
    val sharedViewModel: SharedViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SummaGoTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainActivityScreens.Main.name
                ) {
                    composable(MainActivityScreens.Main.name) {
                        MainScreen(
                            navController = navController,
                            dataProvider = object : MainScreenDataProvider {
                                override fun getLastSummaries(): Flow<List<BaseSummaryInfo>> =
                                    sharedViewModel.allSummaries


                                override fun getAdvice(): Flow<AnnotatedString> = flow {
                                    emit(buildAnnotatedString {
                                        pushStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold))
                                        append("Работайте с конспектами")

                                        pushStyle(style = SpanStyle(fontWeight = FontWeight.Normal))
                                        append(" — регулярно пересматривайте записи и материалы лекций, это облегчит подготовку к экзаменам.")
                                    })
                                }
                            })
                    }

                    composable(MainActivityScreens.SummariesList.name) {
                        SummariesListScreen(object : SummariesListScreenDataProvider {
                            override fun getSubjects(): Flow<List<SummarySubject>> {
                                return flow {

                                }
                            }

                            override fun getSummaries(): Flow<List<BaseSummaryInfo>> {
                                return flow {

                                }
                            }

                        })
                    }
                }
            }
        }
    }
}

enum class MainActivityScreens {
    Main,
    SummariesList
}