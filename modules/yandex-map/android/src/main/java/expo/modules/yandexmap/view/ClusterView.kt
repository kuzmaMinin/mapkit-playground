package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.core.view.setPadding
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.R
import expo.modules.yandexmap.model.DEFAULT_CLUSTER_STROKE_COLOR
import expo.modules.yandexmap.model.DEFAULT_CLUSTER_STROKE_WIDTH
import expo.modules.yandexmap.utils.ColorUtils
import expo.modules.yandexmap.utils.DimensionUtils

@SuppressLint("ViewConstructor")
class ClusterView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    private val textView: TextView
    private val backgroundDrawable: GradientDrawable

    private val scale = resources.displayMetrics.density

    init {
        inflate(context, R.layout.cluster_layout, this)

        textView = findViewById(R.id.cluster_text)
        backgroundDrawable = textView.background as GradientDrawable
    }

    fun setPadding(padding: Int?) {
        if (padding != null) {
            textView.setPadding(DimensionUtils.fromDpToPxInt(padding, scale))
        }
    }

    fun setFontSize(size: Float?) {
        if (size != null) {
            textView.textSize = size
        }
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setTextColor(color: Int?) {
        if (color != null) {
            textView.setTextColor(color)
        }
    }

    fun setBackground(color: Int?) {
        if (color != null) {
            backgroundDrawable.setColor(color)
        }
    }

    fun setStroke(width: Float?, color: Int?) {
        val strokeWidth =
            DimensionUtils.fromDpToPxInt(width?.toInt() ?: DEFAULT_CLUSTER_STROKE_WIDTH, scale)
        val strokeColor = color ?: ColorUtils.parseColor(DEFAULT_CLUSTER_STROKE_COLOR)!!

        backgroundDrawable.setStroke(strokeWidth, strokeColor)
    }
}