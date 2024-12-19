package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.ImageProvider
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.R
import expo.modules.yandexmap.model.Coordinate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("ViewConstructor")
class MarkerView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  var coordinate: Point = Point(0.0, 0.0)
  var text: String? = null
  var textStyle: TextStyle = TextStyle()
  var iconStyle: IconStyle? = null
  var animated: Boolean = false

  var imageProvider: ImageProvider = ImageProvider.fromResource(context, R.drawable.placemark_icon)

  override fun onViewAdded(view: View?) {
    super.onViewAdded(view)

    view?.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
      val bitmap = loadBitmapFromView(v, left, top, right, bottom)

      imageProvider = ImageProvider.fromBitmap(bitmap)
    }
  }

  fun setCoordinate(latLng: Coordinate) {
    coordinate = latLng.toPoint()
  }

  fun setIconSource(iconAsset: String?) {
    if (iconAsset != null) {
      runBlocking {
        imageProvider = getImageProviderSuspend(iconAsset)
      }
    }
  }

  fun setTextValue(iconText: String?) {
    if (iconText != null && iconText != "") {
      text = iconText
    }
  }

  fun setTextStyleValue(style: TextStyle?) {
    if (style != null) {
      textStyle = style
    }
  }

  fun setIconStyleValue(style: IconStyle?) {
    if (style != null) {
      iconStyle = style
    }

  }

  fun setAnimatedValue(value: Boolean) {
    if (value) {
      animated = true
    }
  }

  private suspend fun getImageProviderSuspend(
    iconPath: String,
  ): ImageProvider {
    val bitmap = loadImageFromUriSuspend(iconPath)

    return ImageProvider.fromBitmap(bitmap)
  }

  private fun loadBitmapFromView(
    view: View,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ): Bitmap {
    val width = if (right - left <= 0) 100 else right - left
    val height = if (bottom - top <= 0) 100 else bottom - top
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bitmap)

    view.draw(c)

    return bitmap
  }

  private suspend fun loadImageFromUriSuspend(uri: String): Bitmap? = withContext(Dispatchers.IO) {
    try {
      val url = URL(uri)
      val connection = url.openConnection() as HttpURLConnection
      connection.doInput = true
      connection.connect()
      val inputStream = connection.inputStream

      BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }
}