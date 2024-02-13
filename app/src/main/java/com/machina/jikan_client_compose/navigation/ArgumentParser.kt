package com.machina.jikan_client_compose.navigation

import android.os.Bundle

interface ArgumentParser <T> {

	fun parse(bundle: Bundle?): T
}