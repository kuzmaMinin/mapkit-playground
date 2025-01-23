package expo.modules.yandexmap.utils

import android.graphics.Color

object ColorUtils {
    fun parseColor(colorString: String): Int? {
        return try {
            Color.parseColor(colorString)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
}