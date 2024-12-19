package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import com.yandex.mapkit.mapview.MapView

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

  init {
    mapView = MapView(context)
    addView(mapView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
  }

  override fun onViewRemoved(child: View?) {
    // TODO: handle removing marker
    super.onViewRemoved(child)
  }


  override fun onViewAdded(child: View?) {
    super.onViewAdded(child)

    if (child is MarkerView) {
      child.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
        child.isVisible = false

        mapView?.map?.mapObjects?.addPlacemark()?.apply {
          geometry = child.coordinate

          setIcon(child.imageProvider)

          if (child.iconStyle != null) {
            setIconStyle(child.iconStyle!!)
          }

          if (child.text != null) {
            setText(child.text!!, child.textStyle)
          }
        }
      }
    } else {
      // TODO: handle throwing error if !is MarkerView
      // throw IllegalArgumentException("Child view must be a MarkerView")
    }
  }

  companion object {
    var mapView: MapView? = null
  }
}


