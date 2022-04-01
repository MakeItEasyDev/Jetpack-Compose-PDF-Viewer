package com.jetpack.composepdfviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.composepdfviewer.ui.theme.ComposePDFViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePDFViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Compose PDF Viewer",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        ComposePDFViewer()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposePDFViewer() {
    var isLoading by remember { mutableStateOf(false) }
    var currentLoadingPage by remember { mutableStateOf<Int?>(null) }
    var pageCount by remember { mutableStateOf<Int?>(null) }

    Box {
        PdfViewer(
            modifier = Modifier.fillMaxSize(),
            pdfResId = R.raw.samplepdf,
            loadingListener = { loading, currentPage, maxPage ->
                isLoading = loading
                if (currentPage != null) currentLoadingPage = currentPage
                if (maxPage != null) pageCount = maxPage
            }
        )
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    progress = if (currentLoadingPage == null || pageCount == null) 0f
                    else currentLoadingPage!!.toFloat() / pageCount!!.toFloat()
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 5.dp)
                        .padding(horizontal = 30.dp),
                    text = "${currentLoadingPage ?: "-"} pages loaded/${pageCount ?: "-"} total pages"
                )
            }
        }
    }
}























