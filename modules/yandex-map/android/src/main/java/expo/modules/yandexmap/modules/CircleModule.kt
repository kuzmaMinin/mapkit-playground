package expo.modules.yandexmap.modules

import CircleView
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate

class CircleModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("Circle")

        View(CircleView::class) {
            Prop("center") { view: CircleView, center: Coordinate ->
                view.setCenterValue(center)
            }

            Prop("radius") { view: CircleView, radius: Float ->
                view.setRadiusValue(radius)
            }

            Prop("strokeColor") { view: CircleView, color: String ->
                view.setStrokeColorValue(color)
            }

            Prop("strokeWidth") { view: CircleView, width: Float ->
                view.setStrokeWidthValue(width)
            }

            Prop("fillColor") { view: CircleView, color: String ->
                view.setFillColorValue(color)
            }
        }
    }
}
