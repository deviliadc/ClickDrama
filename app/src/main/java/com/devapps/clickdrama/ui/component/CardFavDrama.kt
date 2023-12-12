package com.devapps.clickdrama.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun CardFavDrama(
    id: Int,
    image: Int,
    title: String,
    year: String,
    genre: String,
    isFavorite: Boolean,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIcon = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )

            Spacer(
                modifier = modifier
                    .padding(5.dp)
            )

            Column (
                modifier = Modifier
                    .weight(1f)
            ){

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                Text(
                    text = year,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                Text(
                    text = genre,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(
                modifier = modifier
                    .padding(5.dp)
            )

            IconButton(onClick = { onFavoriteIconClicked(id, !isFavorite) }) {
                Icon(
                    imageVector = favoriteIcon,
                    contentDescription = "Favorite",
                    tint = if (!isFavorite) Color.Gray else Color.Red,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CardFavDramaPreview() {
    ClickDramaTheme {
        CardFavDrama(
            0,
            R.drawable.sweet_home,
            "Sweet Home",
            "2020",
            "Drama, Fantasy, Horror, Thriller",
            true,
            onFavoriteIconClicked = {_, _ ->},
        )
    }
}