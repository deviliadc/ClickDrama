package com.devapps.clickdrama.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devapps.clickdrama.R
import com.devapps.clickdrama.ui.theme.ClickDramaTheme


@Composable
fun CardDrama(
    id: Int,
    title: String,
    image: Int,
    year: String,
//    rating: String,
    isFavorite: Boolean,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIcon = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder

    Card (
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(170.dp)
            .height(300.dp),
    ){
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(240.dp)
                        .size(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                IconButton(
                    onClick = { onFavoriteIconClicked(id, !isFavorite) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = stringResource(R.string.favorite),
                        tint = if (!isFavorite) Color.Gray else Color.Red,
                    )
                }
            }

            Spacer(
                modifier = modifier
                    .padding(4.dp)
            )

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                overflow = TextOverflow.Ellipsis
            )

            Row {
                Text(
                    text = year,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 6.dp, bottom = 4.dp)
                        .weight(1f)
                )

//                Icon(
//                    imageVector = Icons.Rounded.StarBorder,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                )
//                Text(
//                    text = rating,
//                    modifier = Modifier
//                        .padding(end = 6.dp, bottom = 6.dp)
//                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CardDramaPreview() {
    ClickDramaTheme {
        CardDrama(
            0,
            "Sweet Home",
            R.drawable.sweet_home,
            "2020",
//            "7.3/10",
            true,
            onFavoriteIconClicked = { _, _ ->}
        )
    }
}