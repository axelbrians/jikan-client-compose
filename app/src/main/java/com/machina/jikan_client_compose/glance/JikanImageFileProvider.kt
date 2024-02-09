package com.machina.jikan_client_compose.glance

import androidx.core.content.FileProvider
import com.machina.jikan_client_compose.R

/**
 * It is possible to use FileProvider directly instead of extending it.
 * However, this is not reliable and will causes crashes on some devices.
 *
 * source: https://developer.android.com/reference/androidx/core/content/FileProvider
 * */
class JikanImageFileProvider: FileProvider(R.xml.jikan_image_file_provider)