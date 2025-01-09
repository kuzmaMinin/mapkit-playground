package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.AnimatedImageProvider
import com.yandex.runtime.image.ImageProvider
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.R
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.PlacemarkConfig
import expo.modules.yandexmap.view.YandexMapView.Companion.mapObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("ViewConstructor")
class MarkerView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  private var placemarkConfig: PlacemarkConfig = PlacemarkConfig()

  var placemark: PlacemarkMapObject? = null

  override fun onViewRemoved(child: View?) {
    super.onViewRemoved(child)
  }

  override fun onViewAdded(view: View?) {
    view?.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
      view.isVisible = false

      val bitmap = loadBitmapFromView(v, left, top, right, bottom)

      placemarkConfig.imageProvider = ImageProvider.fromBitmap(bitmap)
    }

    super.onViewAdded(view)
  }

  fun updateMarker() {
    placemark = mapObjects?.addPlacemark()?.apply {
      geometry = placemarkConfig.coordinate

      if (placemarkConfig.text != null) {
        setText(placemarkConfig.text!!, placemarkConfig.textStyle)
      }
    }

    if (placemarkConfig.animatedImageProvider != null) {
      placemark?.useAnimation()?.apply {
        setIcon(placemarkConfig.animatedImageProvider!!, placemarkConfig.iconStyle)
      }?.play()
    } else {
      placemark?.setIcon(placemarkConfig.imageProvider!!, placemarkConfig.iconStyle)
    }
  }

  fun setCoordinate(latLng: Coordinate) {
    placemarkConfig.coordinate = latLng.toPoint()
  }

  fun setIconSource(iconAsset: String?, animated: Boolean) {
    if (iconAsset != null && animated) {
      runBlocking {
        placemarkConfig.animatedImageProvider = getAnimatedImageProviderSuspend(iconAsset)
      }

      return
    }

    if (iconAsset != null) {
      runBlocking {
        placemarkConfig.imageProvider = getImageProviderSuspend(iconAsset)
      }
    } else {
      placemarkConfig.imageProvider = ImageProvider.fromResource(context, R.drawable.placemark_icon)
    }
  }

  fun setTextValue(iconText: String?) {
    if (iconText != null && iconText != "") {
      placemarkConfig.text = iconText
    }
  }

  fun setTextStyleValue(style: TextStyle?) {
    if (style != null) {
      placemarkConfig.textStyle = style
    }
  }

  fun setIconStyleValue(style: IconStyle?) {
    if (style != null) {
      placemarkConfig.iconStyle = style
    }

  }

  private suspend fun getImageProviderSuspend(
    iconPath: String,
  ): ImageProvider {
    val bitmap = loadImageFromUriSuspend(iconPath)

    return ImageProvider.fromBitmap(bitmap)
  }

  private suspend fun getAnimatedImageProviderSuspend(iconPath: String): AnimatedImageProvider {
    val byteArray = loadByteArrayFromUriSuspend(iconPath)

    return AnimatedImageProvider.fromByteArray(byteArray)
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

  private suspend fun <T> loadFromUriSuspend(uri: String, processStream: (InputStream) -> T?): T? =
    withContext(Dispatchers.IO) {
      try {
        val url = URL(uri)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream = connection.inputStream

        processStream(inputStream)
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
    }

  private suspend fun loadImageFromUriSuspend(uri: String): Bitmap? =
    loadFromUriSuspend(uri) { inputStream ->
      BitmapFactory.decodeStream(inputStream)
    }

  private suspend fun loadByteArrayFromUriSuspend(uri: String): ByteArray? =
    loadFromUriSuspend(uri) { inputStream ->
      inputStream.readBytes()
    }

}

