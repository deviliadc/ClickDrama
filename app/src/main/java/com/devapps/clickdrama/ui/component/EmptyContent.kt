package com.devapps.clickdrama.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.devapps.clickdrama.R
import com.devapps.clickdrama.ui.theme.ClickDramaTheme

@Composable
fun EmptyContent(
    contentText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = contentText,
        )
    }
}


@Preview
@Composable
fun EmptyContentPreview() {
    ClickDramaTheme {
        EmptyContent(
            contentText = stringResource(R.string.empty_content),
        )
    }
}