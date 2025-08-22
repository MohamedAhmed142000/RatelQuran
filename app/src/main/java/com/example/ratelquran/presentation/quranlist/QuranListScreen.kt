package com.example.ratelquran.presentation.quranlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.presentation.lastread.LastReadViewModel
import com.example.ratelquran.presentation.quranlist.juzlist.JuzListScreen
import com.example.ratelquran.presentation.quranlist.surahlist.SurahListScreen
import kotlinx.coroutines.launch


// تعريف data class أو enum للتبويبات لتنظيم أفضل
data class TabItem(val title: String, val icon: ImageVector? = null)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuranListScreen(
    onSurahClick: (SurahInfo) -> Unit,
    navController: NavController, lastReadViewModel: LastReadViewModel = hiltViewModel()
) {
    val tabs = remember {
        listOf(
            TabItem("السور", Icons.Filled.Book),
            TabItem("الأجزاء", Icons.Filled.List)
        )
    }
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "📖 رتــــــــــــــل",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }, actions = {
                    IconButton(onClick = {
                        lastReadViewModel.loadLastRead { lastRead ->
                            if (lastRead != null) {
                                navController.navigate(
                                    "details/surah/${lastRead.surahNumber}/${lastRead.surahName}/${lastRead.ayahNumber}"
                                )
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("لم يتم حفظ آخر قراءة")
                                }
                            }
                        }
                    })

                     {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "book"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )

                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                height = 3.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, tabItem ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = { Text(tabItem.title) },
                                icon = {
                                    tabItem.icon?.let {
                                        Icon(
                                            imageVector = it,
                                            contentDescription = tabItem.title
                                        )
                                    }
                                },
                                selectedContentColor = MaterialTheme.colorScheme.primary, // لون النص/الأيقونة للتبويب النشط
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant // لون النص/الأيقونة للتبويب غير النشط
                            )
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { page ->
                        when (page) {
                            0 -> SurahListScreen(
                                onSurahClick = onSurahClick, navController = navController
                            )

                            1 -> JuzListScreen(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
