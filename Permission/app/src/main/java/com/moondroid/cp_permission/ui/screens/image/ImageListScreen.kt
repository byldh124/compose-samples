@file:OptIn(ExperimentalMaterial3Api::class)

package com.moondroid.cp_permission.ui.screens.image

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.moondroid.cp_permission.permission.rememberPermissionHelper
import com.moondroid.cp_permission.ui.theme.Typography
import com.moondroid.cp_permission.ui.widget.BaseLayout
import kotlinx.coroutines.launch

@Composable
fun ImageListScreen(navigateUp: () -> Unit) {
    val context = LocalContext.current
    val images = remember { mutableStateOf(emptyList<Media>()) }
    val fetchMore = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val permissionHelper = rememberPermissionHelper {
        fetchMore.value = fetchMore(context)
        scope.launch {
            images.value = getImages(context.contentResolver)
        }
    }

    LaunchedEffect(Unit) {
        images.value = getImages(context.contentResolver)
        fetchMore.value = fetchMore(context)
    }

    BaseLayout("IMAGE LIST", navigateUp) {
        Column {
            if (fetchMore.value) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionHelper.checkCustomPermission(arrayOf(READ_MEDIA_IMAGES))
                            }
                        },
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("더 많은 사진 설정이 가능합니다.", style = Typography.bodySmall)
                        Text("설정하기", style = Typography.bodyMedium, color = Color.Blue)
                    }
                }
            }

            Spacer(Modifier.height(5.dp))

            LazyVerticalGrid(modifier = Modifier.padding(horizontal = 10.dp), columns = GridCells.Fixed(3)) {
                items(images.value.size) {
                    val image = images.value[it]
                    AsyncImage(
                        modifier = Modifier
                            .padding(2.dp)
                            .aspectRatio(1.0f),
                        model = image.uri,
                        contentDescription = "이미지",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

private fun fetchMore(context: Context): Boolean =
    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            (ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_DENIED &&
                    ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED))