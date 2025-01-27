package expo.modules.yandexmap.utils

object DimensionUtils {
    fun fromDpToPxInt(px: Int, scale: Float): Int {
        return (px * scale + 0.5f).toInt()
    }
}