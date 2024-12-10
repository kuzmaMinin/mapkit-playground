package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.geometry.Point
import com.yandex.runtime.image.ImageProvider
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.R
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.ERROR_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("ViewConstructor")
class MarkerView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    var coordinate: Point = Point(0.0, 0.0)
    var iconPath: String? = null

    fun setCoordinate(latLng: Coordinate) {
        coordinate = latLng.toPoint()
    }

    fun setIconSource(iconAsset: String?) {
        if (iconAsset != null) {
            iconPath = iconAsset
        }
    }

    fun setText(text: String) {

    }
}