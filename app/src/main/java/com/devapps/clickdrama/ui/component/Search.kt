package com.devapps.clickdrama.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devapps.clickdrama.R
import com.devapps.clickdrama.ui.theme.ClickDramaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    onQueryChange: (String) -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.serach)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(50.dp),
                placeholder = { Text(text = stringResource(R.string.serach))},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 20.dp)
            )

            Spacer(
                modifier = modifier
                    .padding(5.dp)
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(24.dp))
            ) {
                IconButton(
                    onClick = { navigateToAbout() },
                    modifier = Modifier
                        .size(48.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = stringResource(R.string.profile),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    ClickDramaTheme{
        Search(
            query = "",
            onQueryChange = {},
            navigateToAbout = {}
        )
    }
}