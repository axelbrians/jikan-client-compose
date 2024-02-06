package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Toolbar(
	title: String,
	modifier: Modifier = Modifier,
	showSeparator: Boolean = true,
	showTitleInCenter: Boolean = false,
	leftContent: @Composable (ToolbarContentScope.() -> Unit)? = null,
	rightContent: @Composable (ToolbarContentScope.() -> Unit)? = null,
	belowContent: @Composable (ColumnScope.() -> Unit)? = null
) {
	val impl = remember { ToolbarContentScopeImpl() }

	Surface(modifier = modifier) {
		Column(modifier = Modifier.fillMaxWidth()) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 4.dp)
					.heightIn(min = 56.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				val titlePositionModifier = if (showTitleInCenter) Modifier.weight(1f) else Modifier
				Row(
					modifier = Modifier
						.widthIn(48.dp)
						.then(titlePositionModifier),
					verticalAlignment = Alignment.CenterVertically
				) {
					leftContent?.let { impl.it() }
				}

				ToolbarTitle(title = title, modifier = Modifier.weight(1f))

				Row(
					modifier = Modifier
						.widthIn(48.dp)
						.then(titlePositionModifier),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.End
				) {
					rightContent?.let { impl.it() }
				}
			}

			belowContent?.let { it() }

			if (showSeparator) {
				ToolbarSeparator()
			}
		}
	}
}

@Preview
@Composable
private fun Toolbar_Preview() {
	Toolbar(
		title = "Ini toolbar",
		leftContent = {
			this.ToolbarIconBack(onClick = { /*TODO*/ })

			Text(text = "text")
		}
	)
}

interface ToolbarContentScope {
	@Composable
	fun ToolbarIconBack(
		onClick: () -> Unit,
		modifier: Modifier = Modifier
	) {
		error("Not yet implemented")
	}
}

class ToolbarContentScopeImpl: ToolbarContentScope {
	@Composable
	override fun ToolbarIconBack(onClick: () -> Unit, modifier: Modifier) {
		ToolbarIcon(
			onClick = onClick,
			imageVector = Icons.Filled.ArrowBack,
			contentDescription = "Navigate Up",
			modifier = modifier
		)
	}
}

//@Composable
//fun ToolbarContentScope.ToolbarIconBack(
//	onClick: () -> Unit,
//	modifier: Modifier = Modifier
//) {
//	ToolbarIcon(
//		onClick = onClick,
//		imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//		contentDescription = "Navigate Up",
//		modifier = modifier
//	)
//}

@Composable
fun ToolbarIconClose(
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	ToolbarIcon(
		onClick = onClick,
		imageVector = Icons.Default.Close,
		contentDescription = null,
		modifier = modifier
	)
}

@Composable
fun ToolbarIconMore(
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	ToolbarIcon(
		onClick = onClick,
		imageVector = Icons.Default.MoreVert,
		contentDescription = "More Menu",
		modifier = modifier
	)
}



@Composable
fun ToolbarTitle(
	title: String,
	modifier: Modifier = Modifier
) {
	Text(
		text = title,
		modifier = modifier,
		overflow = TextOverflow.Ellipsis,
		maxLines = 1,
		textAlign = TextAlign.Center,
		fontSize = 18.sp,
		fontWeight = FontWeight.Bold
	)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarIcon(
	onClick: () -> Unit,
	imageVector: ImageVector,
	contentDescription: String?,
	modifier: Modifier = Modifier,
	tint: Color = Color.Black
) {
	androidx.compose.material3.Icon(
		imageVector = imageVector,
		contentDescription = contentDescription,
		tint = tint,
		modifier = modifier
			.clip(CircleShape)
			.padding(12.dp)
			.size(24.dp)
			.clickable(onClick = onClick)
	)
}

@Composable
fun ToolbarSeparator(modifier: Modifier = Modifier) {
	Divider(
		modifier = modifier
			.height(1.dp)
			.fillMaxWidth(),
		color = Color.Cyan
	)
}
