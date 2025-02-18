# Expo Yandex MapKit

> [!IMPORTANT]
> Expo >= 52 is required

This module is a wrapper of light version (current version is *4.10.1-lite*) of native
library [Yandex MapKit](https://yandex.ru/dev/mapkit/doc).
There is not all features are supported, but one can observe the [roadmap](#Roadmap).

This module is have not tested for production yet. Before you want to use this module in your prod project be sure that
all functionality works well.
It takes a lot of time to provide all props from native version, but feel free to add a feature request or contact
me [kuzma89klg@gmail.com](mailto:kuzma89klg@gmail.com).

> [!TIP]
> New Architecture is fully supported!

## Get started

1. At current moment there is no npm package you can use, so just grab the ***modules*** folder and paste it into a root of your project.

2. Copy **mapkit.plugin.js** from the example to a root of your project

3. In **app.json** add these lines and provide actual mapkit api key

```json
{
  "plugins": [
    "...",
    [
      "./mapkit.plugin.js",
      {
        "apiKey": "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX"
      }
    ]
  ]
}
```

4. Make a prebuild

```bash
 npx expo prebuld
```

5. Start your app!

```bash
 npx expo run:android
```

## Basic usage

```javascript
import {Marker, YandexMap} from "@/modules/yandex-map";

const {height, width} = useWindowDimensions();

<YandexMap
    // Provide absolute values for width and height     
    style={{width, height}}
>
    <Marker
        coordinate={{latitude: 59.936046, longitude: 30.326869}}
    >
        // It's required to provide a children property to view
        <View>
            <Text>$</Text>
        </View>
    </Marker>

    <Polyline 
      key="unique_polyline"
      points={[
          { latitude: 59.936046, longitude: 30.326869 },
          { latitude: 59.938185, longitude: 30.32808 },
          { latitude: 59.937376, longitude: 30.33621 },
        ]}
    />

    <Polygon
      key="unique_polygon"
      points={[ 
        // ...
      ]}
    />

    <Circle
      key="unique_circle"
      center={{ 
        // ...
      }}
      radius={100}
    />
</>
```

## Supported API

Most property names is equal or similar with origins. If you need more information try to look
into [Yandex MapKit](https://yandex.ru/dev/mapkit/doc) .

### Common

#### ICoordinate

| Property name | Type                    | Description    |
|---------------|-------------------------|----------------|
| `latitude`    | *(required)* **number** | Item latitude  |
| `longitude`   | *(required)* **number** | Item longitude |

### YandexMap

#### Props

| Property name     | Type                                            | Description                                                                         |
|-------------------|-------------------------------------------------|-------------------------------------------------------------------------------------|
| `children`        | *(optional)* **Marker**                         | Map entries as *Marker*, *Circle*, *Polygon*, *Polyline* must be passed as children |
| `mapRef`          | *(optional)* **Ref<MapKitActions>**             | Ref for map component. Use it to fire some actions                                  |
| `clusterized`     | *(optional)* <br/>default **false**             | Use to enable/disable clusterization                                                |
| `clusterStyle`    | *(optional)* **IClusterStyle**                  | Cluster view props                                                                  |
| `clusterConfig`   | *(optional)* **IClusterConfig**                 | Cluster configuration                                                               |
| `initialRegion`   | *(optional)* **IRegion**                        | Region that will be shown when map loaded                                           |
| `scrollEnabled`   | *(optional)* **boolean** <br/>default **false** | Setter to enable map scrolling                                                      |
| `zoomEnabled`     | *(optional)* **boolean** <br/>default **false** | Setter to enable map zooming                                                        |
| `rotationEnabled` | *(optional)* **boolean** <br/>default **false** | Setter to enable map rotation                                                       |
| `tiltEnabled`     | *(optional)* **boolean** <br/>default **false** | Setter to enable  changing of map tilt                                              |

#### Events

| Event name     | Type                                                                               | Description                |
|----------------|------------------------------------------------------------------------------------|----------------------------|
| `onMapReady`   | *(optional)* <br/>**(event: SyntheticEvent<any, { payload: "success" }>) => void** | Fires when map loaded      |
| `onMapTap`     | *(optional)* <br/>**(event: SyntheticEvent<any, ICoordinate>) => void**            | Fires when map tapped      |
| `onMapLongTap` | *(optional)* <br/>**(event: SyntheticEvent<any, ICoordinate>) => void**            | Fires when map long tapped |

#### MapKitActions

| Action name    | Type                                                  | Description           |
|----------------|-------------------------------------------------------|-----------------------|
| `moveToCenter` | *(optional)* <br/>**(params: IRegion) => void**       | Fires when map loaded |
| `fitMarkers`   | *(optional)* <br/>**(points: ICoordinate[]) => void** | Fires when map tapped |

#### IClusterStyle

| Property name     | Type                                        | Description              |
|-------------------|---------------------------------------------|--------------------------|
| `fontSize`        | *(optional)* **number**                     | Cluster font size        |
| `padding`         | *(optional)* **number**                     | Cluster padding          |
| `textColor`       | *(optional)* **string** (f.e. 'red' or HEX) | Cluster text color       |
| `backgroundColor` | *(optional)* **string** (f.e. 'red' or HEX) | Cluster background color |
| `strokeColor`     | *(optional)* **string** (f.e. 'red' or HEX) | Cluster stroke color     |
| `strokeWidth`     | *(optional)* **number**                     | Cluster stroke width     |

#### IClusterConfig

| Property name   | Type                    | Description                        |
|-----------------|-------------------------|------------------------------------|
| `clusterRadius` | *(optional)* **number** | Cluster radius to collapse markers |
| `minZoom`       | *(optional)* **number** | Min zoom clusterization enables    |

#### IRegion

| Property name | Type                    | Description      |
|---------------|-------------------------|------------------|
| `latitude`    | *(required)* **number** | Region latitude  |
| `longitude`   | *(required)* **number** | Region longitude |
| `zoom`        | *(optional)* **number** | Region zoom      |
| `azimuth`     | *(optional)* **number** | Region azimuth   |
| `tilt`        | *(optional)* **number** | Region tilt      |

### Marker

#### Props

| Property name | Type                         | Description                       |
|---------------|------------------------------|-----------------------------------|
| `coordinate`  | *(required)* **ICoordinate** | Marker coordinate                 |
| `children`    | *(required)* **ReactNode**   | React node used as view component |
| `iconStyle`   | *(optional)* **IIconStyle**  | React node used as view component |

#### Events

| Event name | Type                                                               | Description          |
|------------|--------------------------------------------------------------------|----------------------|
| `onPress`  | *(optional)* **(event: SyntheticEvent<any, ICoordinate>) => void** | Marker onPress event |

#### IIConStyle

| Property name  | Type                                                                 | Description                                                       |
|----------------|----------------------------------------------------------------------|-------------------------------------------------------------------|
| `anchor`       | *(optional)* **IPosition**                                           | Marker anchor is rect with width 1.0 and height 1.0               |
| `rotationType` | *(optional)* **RotationType** enum (0 === NO_ROTATION, 1 === ROTATE) | Marker rotation type                                              |
| `zIndex`       | *(optional)* **number**                                              | Marker zIndex                                                     |
| `flat`         | *(optional)* **boolean** <br/>default **false**                      | see docs // TODO: add description                                 |
| `visible`      | *(optional)* **boolean** <br/>default **false**                      | Marker visibility                                                 |
| `scale`        | *(optional)* **number** <br/>default **1**                           | Marker scaling                                                    |
| `tappableArea` | *(optional)* **ITapableArea**                                        | Tappable area extends marker width and height and fires *onPress* |

#### ITapableArea

| Property name | Type                       | Description |
|---------------|----------------------------|-------------|
| `min`         | *(required)* **IPosition** | Min point   |
| `max`         | *(required)* **IPosition** | Max point   |

#### IPosition

| Property name | Type                    | Description |
|---------------|-------------------------|-------------|
| `x`           | *(required)* **number** | X position  |
| `y`           | *(required)* **number** | Y position  |

### Polygon

#### Props

| Property name | Type                                          | Description               |
|---------------|-----------------------------------------------|---------------------------|
| `points`      | *(required)* **ICoordinate[] **               | Polygon points            |
| `innerPoints` | *(optional)* **ICoordinate[]**                | Polygon points to exclude |
| `strokeWidth` | *(optional)* **number**                       | Polygon stroke width      |
| `strokeColor` | *(optional)* **string** (f.e. 'red' or HEX)   | Polygon stroke color      |
| `fillColor`   | *(optional)* **string** (f.e. 'red' or HEX)   | Polygon fill color        |

### Polyline

#### Props

| Property name  | Type                                          | Description            |
|----------------|-----------------------------------------------|------------------------|
| `points`       | *(required)* **ICoordinate[]**                | Polyline points        |
| `strokeWidth`  | *(optional)* **number**                       | Polyline stroke width  |
| `outlineWidth` | *(optional)* **number**                       | Polyline outline width |
| `strokeColor`  | *(optional)* **string** (f.e. 'red' or HEX)   | Polyline stroke color  |
| `outlineColor` | *(optional)* **string** (f.e. 'red' or HEX)   | Polyline outline color |

### Circle

#### Props

| Property name | Type                                          | Description            |
|---------------|-----------------------------------------------|------------------------|
| `center`      | *(required)* **ICoordinate**                  | Circle center position |
| `radius`      | *(required)* **number**                       | Circle radius          |
| `strokeWidth` | *(optional)* **number**                       | Circle stroke width    |
| `strokeColor` | *(optional)* **string** (f.e. 'red' or HEX)   | Circle stroke color    |
| `fillColor`   | *(optional)* **string** (f.e. 'red' or HEX)   | Circle fill color      |

## Roadmap

- [x]  IOS basic usage
- [ ]  Custom cluster view
- [ ]  Draggable markers
- [ ]  Resizable map features
- [ ]  Focus rect
- [ ]  Points of interests
- [ ]  Investigate how to add web support
