package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.view.PolygonView

class PolygonModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("Polygon")

        View(PolygonView::class) {
            Prop("points") { view: PolygonView, points: List<Coordinate> ->
                view.setPointsValue(points)
            }

            Prop("innerPoints") { view: PolygonView, points: List<Coordinate> ->
                view.setInnerPointsValue(points)
            }

            Prop("strokeColor") { view: PolygonView, color: String ->
                view.setStrokeColorValue(color)
            }

            Prop("fillColor") { view: PolygonView, color: String ->
                view.setFillColorValue(color)
            }

            Prop("strokeWidth") { view: PolygonView, width: Float ->
                view.setStrokeWidthValue(width)
            }
        }
    }
}