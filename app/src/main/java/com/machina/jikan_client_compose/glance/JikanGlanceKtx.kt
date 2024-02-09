package com.machina.jikan_client_compose.glance

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext

val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

@Composable
fun glanceStringResource(@StringRes id: Int, vararg args: Any): String {
	return LocalContext.current.getString(id, args)
}