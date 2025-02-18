import android.annotation.SuppressLint
import android.content.Context
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.map.CircleMapObject
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.model.CircleConfig
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.utils.ColorUtils
import expo.modules.yandexmap.view.YandexMapView.Companion.mapObjects

@SuppressLint("ViewConstructor")
class CircleView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    var circleMapObject: CircleMapObject? = null

    private var circleConfig: CircleConfig = CircleConfig()

    fun setCenterValue(center: Coordinate) {
        circleConfig.center = center.toPoint()
    }

    fun setRadiusValue(radius: Float) {
        circleConfig.radius = radius
    }

    fun setStrokeWidthValue(width: Float) {
        circleConfig.strokeWidth = width
    }

    fun setStrokeColorValue(color: String) {
        circleConfig.strokeColor = ColorUtils.parseColor(color)
    }

    fun setFillColorValue(color: String) {
        circleConfig.fillColor = ColorUtils.parseColor(color)
    }

    fun updateCircle() {
        val circle = Circle(
            circleConfig.center!!,
            circleConfig.radius!!
        )

        circleMapObject = mapObjects?.addCircle(circle)?.apply {
            if (circleConfig.strokeWidth != null) {
                strokeWidth = circleConfig.strokeWidth!!
            }

            if (circleConfig.strokeColor != null) {
                strokeColor = circleConfig.strokeColor!!
            }

            if (circleConfig.fillColor != null) {
                fillColor = circleConfig.fillColor!!
            }
        }

    }
}