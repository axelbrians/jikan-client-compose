package com.machina.jikan_client_compose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.machina.jikan_client_compose.R

object MyIcons {

  object Outlined {
    const val DoubleCheck = R.drawable.ic_double_check_outlined
    const val Clock4 = R.drawable.ic_clock_outlined
  }

  object Filled {
    const val ChevronDown = R.drawable.ic_circle_chevron_down_filled

    @Composable
    fun grid3(): Painter {
      return painterResource(id = R.drawable.ic_grid_3x2_solid)
    }

    @Composable
    fun grid4(): Painter {
      return painterResource(id = R.drawable.ic_grid_4x2_solid)
    }

    private var grid3x2: Painter? = null
    val Grid3x2: Painter
      @Composable
      get() {
        if (grid3x2 != null) {
          return grid3x2!!
        }
        grid3x2 = painterResource(id = R.drawable.ic_grid_3x2_solid)
        return grid3x2!!
      }

    private var grid4x2: Painter? = null
    val Grid4x2: Painter
      @Composable
      get() {
        if (grid4x2 != null) {
          return grid4x2!!
        }
        grid4x2 = painterResource(id = R.drawable.ic_grid_4x2_solid)
        return grid4x2!!
      }



    private var test: Painter? = null
    val Test: @Composable () -> Painter
      get() = {
        if (test != null) {
          test!!
        }
        test = painterResource(id = R.drawable.ic_filter_solid)
        test!!
      }
  }

  object Solid {
    const val Filter = R.drawable.ic_filter_solid
  }

}