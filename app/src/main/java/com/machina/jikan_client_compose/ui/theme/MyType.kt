package com.machina.jikan_client_compose.ui.theme

import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.normal
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurfaceLight

object MyType {

  object Body1 {
    private val body1 = Type.Typography.body1

    object Normal {
      private val normal = body1.normal()

      val OnDarkSurface = normal.onDarkSurface()
      val OnDarkSurfaceLight = normal.onDarkSurfaceLight()
    }

    object Bold {
      private val bold = body1.bold()

      val OnDarkSurface = bold.onDarkSurface()
      val OnDarkSurfaceLight = bold.onDarkSurfaceLight()
    }

  }

  object Body2 {
    private val body2 = Type.Typography.body2

    object Normal {
      private val normal = body2.normal()

      val OnDarkSurface = normal.onDarkSurface()
      val OnDarkSurfaceLight = normal.onDarkSurfaceLight()
    }

    object Bold {
      private val bold = body2.bold()

      val OnDarkSurface = bold.onDarkSurface()
      val OnDarkSurfaceLight = bold.onDarkSurfaceLight()
    }

  }




}

