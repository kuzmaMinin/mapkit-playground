import {
  Button,
  Text,
  TextInput,
  useWindowDimensions,
  View,
} from "react-native";
import {
  Circle,
  Marker,
  Placement,
  Polygon,
  Polyline,
  YandexMap,
} from "@/modules/yandex-map";
import { useState } from "react";

export default function HomeScreen() {
  const renderMarker = (latitude: number, longitude: number, text: string) => {
    return (
      <Marker
        key={`${latitude}-${longitude}_${text}`}
        coordinate={{ latitude, longitude }}
        //text="Ou sheet from comp"
        textStyle={{
          size: 10,
          color: "red",
          outlineWidth: 1,
          outlineColor: "#000000FF",
          placement: Placement.TOP,
        }}
        onPress={(e) => {
          console.log(e.nativeEvent, "on marker  press");
        }}
      >
        {/*<View
          style={{
            backgroundColor: "green",
            borderColor: "black",
            borderWidth: 2,
            padding: 5,
          }}
        >
          <View style={{ backgroundColor: "blue", padding: 5 }}>
            <View style={{ backgroundColor: "red", padding: 5 }}>
              <Text style={{ fontSize: 20, color: "white" }}>
                {text ?? "Woo"}
              </Text>
            </View>
          </View>
        </View>*/}
      </Marker>
    );
  };

  const addMarker = (latitude: string, longitude: string, text: string) => {
    setMarkers((prevState) => {
      return [
        ...prevState,
        { latitude: +latitude, longitude: +longitude, text },
      ];
    });
  };

  const removeMarker = () => {
    setMarkers((prevState) => {
      const updated = prevState.slice(0, -1);

      return [...updated];
    });
  };

  const [markers, setMarkers] = useState<any[]>([
    { latitude: 55.751225, longitude: 37.62954, text: "first" },
    { latitude: 45.851225, longitude: 26.62954, text: "second" },
    { latitude: 64.751225, longitude: 37.92954, text: "third" },
    { latitude: 59.936046, longitude: 30.326869, text: "fourth" },
    { latitude: 59.938185, longitude: 30.32808, text: "fifth" },
    { latitude: 59.937376, longitude: 30.33621, text: "sixth" },
    { latitude: 59.934517, longitude: 30.335059, text: "seventh" },
  ]);

  const [latitude, setLatitude] = useState<string>("44.77777");
  const [longitude, setLongitude] = useState<string>("35.88888");
  const [title, setTitle] = useState<string>("text");

  const { height, width } = useWindowDimensions();

  return (
    <View style={{ flex: 1 }}>
      <View
        style={{
          position: "absolute",
          bottom: 50,
          right: 50,
          zIndex: 100,
          flexDirection: "row",
          gap: 10,
        }}
      >
        <Button
          title={"+"}
          onPress={() => addMarker(latitude, longitude, title)}
        />
        <Button title={"-"} onPress={removeMarker} />
      </View>

      <View
        style={{
          position: "absolute",
          top: 50,
          left: 20,
          width: "50%",
          zIndex: 10,
        }}
      >
        <TextInput
          value={latitude}
          onChangeText={setLatitude}
          placeholder={"latitude"}
        />
        <TextInput
          value={longitude}
          onChangeText={setLongitude}
          placeholder={"longitude"}
        />
        <TextInput
          value={title}
          onChangeText={setTitle}
          placeholder={"title"}
        />
      </View>

      <YandexMap
        style={{ width, height }}
        onMapReady={(event) => {
          console.log("map ready", event.nativeEvent);
        }}
        // clusterized={true}
        // clusterIcon={require("../../assets/images/placeholder.png")}
      >
        {/*<Marker
          key={"second-3"}
          coordinate={{ latitude: 54.851999, longitude: 25.62999 }}
          icon={require("../../assets/images/smile.png")}
          iconStyle={{
            anchor: { x: 0.5, y: 0.5 },
            rotationType: RotationType.NO_ROTATION,
            zIndex: 10,
            flat: false,
            visible: true,
            tappableArea: {
              min: { x: 0, y: 0 },
              max: { x: 0, y: 0 },
            },
            scale: 0.5,
          }}
          animated={true}
          onPress={(e) => {
            console.log(e, "on marker second-3 press");
          }}
        ></Marker>*/}

        {markers.map((marker, i) =>
          renderMarker(marker.latitude, marker.longitude, marker.text),
        )}

        {/*
        <Marker
          key={"second-5"}
          coordinate={{ latitude: 24.851999, longitude: 25.62999 }}
          icon={require("../../assets/images/smile.png")}
          iconStyle={{
            scale: 0.5,
          }}
          onPress={(e) => {
            console.log(e.nativeEvent, "on marker  press");
          }}
        ></Marker>*/}

        {/*<Polygon
          points={[
            { latitude: 59.936046, longitude: 30.326869 },
            { latitude: 59.938185, longitude: 30.32808 },
            { latitude: 59.937376, longitude: 30.33621 },
            { latitude: 59.934517, longitude: 30.335059 },
          ]}
          innerPoints={[
            { latitude: 59.937487, longitude: 30.330034 },
            { latitude: 59.936688, longitude: 30.33127 },
            { latitude: 59.937116, longitude: 30.33328 },
            { latitude: 59.937704, longitude: 30.331842 },
          ]}
          strokeWidth={1}
          strokeColor={"red"}
          fillColor={"green"}
        ></Polygon>*/}

        {/*<Polyline
          points={[
            { latitude: 59.937487, longitude: 30.326869 },
            { latitude: 58.937487, longitude: 25.326869 },
            { latitude: 50.937487, longitude: 35.326869 },
          ]}
          strokeWidth={5}
          strokeColor={"red"}
          outlineColor={"green"}
          outlineWidth={1}
        ></Polyline>*/}

        {/*<Circle
          center={{ latitude: 59.937487, longitude: 30.326869 }}
          radius={4000}
          fillColor={"green"}
          strokeColor={"red"}
          strokeWidth={20}
        ></Circle>*/}
      </YandexMap>
    </View>
  );
}

/*val points = listOf(
    Point(59.936046, 30.326869),
    Point(59.938185, 30.32808),
    Point(59.937376, 30.33621),
    Point(59.934517, 30.335059),
)*/

/*val points = listOf(
    Point(59.936046, 30.326869),
    Point(59.938185, 30.32808),
    Point(59.937376, 30.33621),
    Point(59.934517, 30.335059),
)
val innerPoints = listOf(
    Point(59.937487, 30.330034),
    Point(59.936688, 30.33127),
    Point(59.937116, 30.33328),
    Point(59.937704, 30.331842),
)*/

// test markers
{
  /*<Marker
          key={"second"}
          coordinate={{ latitude: 55.851555, longitude: 26.62555 }}
        ></Marker>*/
}

{
  /*<Marker
          key={"second-2"}
          coordinate={{ latitude: 54.851777, longitude: 25.62777 }}
        ></Marker>*/
}

{
  /*        <Marker
          key={"second-3"}
          coordinate={{ latitude: 53.851999, longitude: 23.62999 }}
        ></Marker>*/
}

{
  /*        <Marker
          key={"second-3"}
          coordinate={{ latitude: 54.851999, longitude: 25.62999 }}
          iconSource={require("../../assets/images/placeholder.png")}
        ></Marker>*/
}

{
  /* <Marker
          key={"third"}
          coordinate={{ latitude: 54.751225, longitude: 37.92954 }}
          style={{
            height: 120,
            width: 120,
          }}
          text="Ou sheet"
          textStyle={{
            size: 15,
            color: "green",
            outlineWidth: 3,
            outlineColor: "blue",
            placement: Placement.BOTTOM,
            offset: 1,
            offsetFromIcon: true,
            textOptional: false,
          }}
          iconSource={require("../../assets/images/placeholder.png")}
          iconStyle={{
            anchor: { x: 0.5, y: 0.5 },
            rotationType: RotationType.NO_ROTATION,
            zIndex: 10,
            flat: false,
            visible: true,
            tappableArea: {
              min: { x: 0, y: 0 },
              max: { x: 0, y: 0 },
            },
            scale: 0.5,
          }}
        ></Marker>*/
}
