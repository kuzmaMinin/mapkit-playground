package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.ClusterConfigModel
import expo.modules.yandexmap.model.ClusterStyleModel
import expo.modules.yandexmap.model.FocusRect
import expo.modules.yandexmap.model.Position
import expo.modules.yandexmap.view.YandexMapView

class YandexMapModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("YandexMap")

        View(YandexMapView::class) {
            Events("onMapReady", "onClusterPress", "onMapTap", "onMapLongTap")

            Prop("clusterized") { view: YandexMapView, clusterized: Boolean ->
                view.setClusterizedValue(clusterized)
            }

            Prop("clusterStyle") { view: YandexMapView, style: ClusterStyleModel ->
                view.setClusterStyleValue(style.toClusterStyle())
            }

            Prop("clusterConfig") { view: YandexMapView, config: ClusterConfigModel ->
                view.setClusterConfigValue(config)
            }

            Prop("initialRegion") { view: YandexMapView, position: Position ->
                view.setInitialRegion(position)
            }

            Prop("scrollEnabled") { view: YandexMapView, isEnabled: Boolean ->
                view.setScrollEnabled(isEnabled)
            }

            Prop("zoomEnabled") { view: YandexMapView, isEnabled: Boolean ->
                view.setZoomEnabled(isEnabled)
            }

            Prop("rotationEnabled") { view: YandexMapView, isEnabled: Boolean ->
                view.setRotationEnabled(isEnabled)
            }

            Prop("tiltEnabled") { view: YandexMapView, isEnabled: Boolean ->
                view.setTiltEnabled(isEnabled)
            }
            // TODO: implement after focus on object
            Prop("focusRect") { view: YandexMapView, rect: FocusRect ->
                val tlX = rect.topLeft.x
                val tlY = rect.topLeft.y
                val brX = rect.bottomRight.x
                val brY = rect.bottomRight.y

                view.setFocusRect(tlX, tlY, brX, brY)
            }

            AsyncFunction("moveToCenter") { view: YandexMapView, position: Position ->
                view.moveCamera(position)
            }

            AsyncFunction("fitMarkers") { view: YandexMapView, points: List<Position> ->
                val markers = points.map { it.toPoint() }

                view.fitMarkers(markers)
            }
        }
    }
}
