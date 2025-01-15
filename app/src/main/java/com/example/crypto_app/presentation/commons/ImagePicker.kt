package com.example.crypto_app.presentation.commons

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.crypto_app.domain.manager.CommonUtil.Companion.getMimeType
import com.example.crypto_app.domain.manager.CommonUtil.Companion.isImageFile
import com.example.crypto_app.domain.manager.CommonUtil.Companion.noRippleClickable


@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    allowMultiple: Boolean = false,
    onMediaPicked: (List<Uri>) -> Unit,
    content: @Composable () -> Unit
) {
    var mediaUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current

    // Launcher for cropping images
    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            val croppedUri = result.uriContent
            if (croppedUri != null) {
                mediaUris = mediaUris + croppedUri
            }
        }
    )

    // Launcher to pick one or multiple media (images or videos)
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = if (allowMultiple) PickMultipleVisualMedia(5) else PickVisualMedia()
    ) { uris ->
        uris?.let {
            mediaUris = emptyList()

            val urisList: List<Uri> = if (allowMultiple) {
                (uris as? List<*>)?.filterIsInstance<Uri>().orEmpty()
            } else {
                listOf(uris as Uri)  // Single URI case
            }


            urisList.forEach { uri ->
//                if (isImageFile(uri, context)) {
//                    cropImageLauncher.launch(
//                        CropImageContractOptions(
//                            uri,
//                            CropImageOptions(imageSourceIncludeCamera = false)
//                        )
//                    )
//                } else {
                    mediaUris = mediaUris + uri
//                }
            }
            onMediaPicked(mediaUris)
        }
    }

    // Box to allow clicking and picking media
    Box(
        modifier = modifier.noRippleClickable(enabled = enabled) {
            mediaPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
        }
    ) {
        content()
    }
}

