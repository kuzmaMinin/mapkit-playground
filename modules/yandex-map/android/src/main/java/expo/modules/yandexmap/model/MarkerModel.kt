package expo.modules.yandexmap.model

import com.yandex.mapkit.geometry.Point
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record

class Coordinate : Record {
    @Field
    val latitude: Double = 0.0

    @Field
    val longitude: Double = 0.0

    fun toPoint(): Point {
        return Point(latitude, longitude)
    }
}
