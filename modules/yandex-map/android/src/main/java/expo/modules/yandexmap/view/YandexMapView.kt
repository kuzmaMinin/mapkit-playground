package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import androidx.core.view.isVisible
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import expo.modules.yandexmap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

  init {
    mapView = MapView(context)
    addView(mapView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
  }


  override fun onViewAdded(child: View?) {
    super.onViewAdded(child)

    if (child is MarkerView) {
      child.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
        child.isVisible = false

        CoroutineScope(Dispatchers.Main).launch {
          try {
            val imageProvider = getImageProviderSuspend(child, v, left, top, right, bottom)

            mapView?.map?.mapObjects?.addPlacemark()?.apply {
              geometry = child.coordinate

              setIcon(imageProvider)
            }
          } catch (e: Exception) {
            e.printStackTrace()
          }
        }
      }
    } else {
      // TODO: handle throwing error if !is MarkerView
      // throw IllegalArgumentException("Child view must be a MarkerView")
    }
  }

  private suspend fun getImageProviderSuspend(
    child: MarkerView,
    v: View,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ): ImageProvider {
    if (child.iconPath != null) {
      val bitmap = loadImageFromUriSuspend(child.iconPath!!)

      if (bitmap != null) {
        return ImageProvider.fromBitmap(bitmap)
      }
    }

    return if (child.childCount > 0) {
      val bitmap = loadBitmapFromView(v, left, top, right, bottom)

      ImageProvider.fromBitmap(bitmap)
    } else {
      ImageProvider.fromResource(context, R.drawable.placemark_icon)
    }
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

  companion object {
    var mapView: MapView? = null
  }
}


