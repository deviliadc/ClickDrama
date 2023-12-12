package com.devapps.clickdrama.ui.screen.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devapps.clickdrama.R
import com.devapps.clickdrama.ui.theme.ClickDramaTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun AboutScreen(
//    name: String,
//    email: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            AboutHeader(navigateBack)
        }
        item {
            AboutInfoSection()
        }
    }
}


@Composable
fun AboutHeader(
    navigateBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.about),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun AboutInfoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = stringResource(R.string.profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(90.dp))
                    .align(Alignment.Center)
                    .shadow(2.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .padding(24.dp))

        Text(
            text = stringResource(R.string.name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )


        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = stringResource(R.string.email),
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )


//        Spacer(modifier = Modifier.padding(50.dp))
//
////        Text(
////            text = github,
////            fontSize = 20.sp,
////            overflow = TextOverflow.Ellipsis,
////            modifier = Modifier
////                .align(Alignment.CenterHorizontally)
////        )
//
//        Row (
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//        ){
//            IconButton(
//                onClick = {
////                    openUrl("https://www.github.com/username/")
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .size(64.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_github),
//                    contentDescription = "GitHub",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .size(48.dp)
//                )
//            }
//
//            Spacer(
//                modifier = Modifier
//                .padding(16.dp))
//
//            // Icon dan tautan Instagram
//            IconButton(
//                onClick = {
////                    OpenUrl("https://www.instagram.com/username/")
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .size(64.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_instagram),
//                    contentDescription = "Instagram",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .size(48.dp)
//                )
//            }
//
//            Spacer(
//                modifier = Modifier
//                    .padding(16.dp))
//
//            // Icon dan tautan LinkedIn
//            IconButton(
//                onClick = {
////                    OpenUrl("https://www.linkedin.in/username/")
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .size(64.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_linkedin),
//                    contentDescription = "Linkedin",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .size(48.dp)
//                )
//            }
//        }
    }
}

//@Composable
//fun OpenUrl(url: String) {
//    val context = LocalContext.current
//    val uri = Uri.parse(url)
//    val intent = Intent(Intent.ACTION_VIEW, uri)
//    context.startActivity(intent)
//}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    ClickDramaTheme {
        AboutScreen(
            navigateBack = {}
        )
    }
}