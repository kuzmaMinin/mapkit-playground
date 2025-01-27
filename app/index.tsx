import { useWindowDimensions, View, StyleSheet } from "react-native";
import { MapKitActions, Marker, YandexMap } from "@/modules/yandex-map";
import React, { useEffect, useRef, useState } from "react";
import { FontAwesome } from "@expo/vector-icons";
import * as Font from "expo-font";

interface IMark {
  latitude: number;
  longitude: number;
  id: string;
}

export default function MapScreen() {
  const [markers, setMarkers] = useState<IMark[]>([
    { latitude: 59.936046, longitude: 30.326869, id: "fourth" },
    { latitude: 59.938185, longitude: 30.32808, id: "fifth" },
    { latitude: 59.937376, longitude: 30.33621, id: "sixth" },
    { latitude: 59.934517, longitude: 30.335059, id: "seventh" },
  ]);

  const [isLoaded, setIsLoaded] = useState(false);

  const mapkitRef = useRef<MapKitActions>(null);

  useEffect(() => {
    const loadFont = async () => {
      await Font.loadAsync({
        ...FontAwesome.font,
      });

      setIsLoaded(true);
    };

    loadFont();
  }, []);

  const renderMarker = (latitude: number, longitude: number, text: string) => {
    return (
      <Marker
        key={`${latitude}-${longitude}_${text}`}
        coordinate={{ latitude, longitude }}
        onPress={(e) => {
          const { latitude, longitude } = e.nativeEvent;
          mapkitRef.current?.moveToCenter({ latitude, longitude });
        }}
      >
        <View style={styles.marker}>
          <FontAwesome name="map-marker" size={24} color="white" />
        </View>
      </Marker>
    );
  };

  const addMarker = (latitude: number, longitude: number, id: string) => {
    setMarkers((prevState) => [...prevState, { latitude, longitude, id }]);
  };

  const { height, width } = useWindowDimensions();

  return (
    <View style={{ flex: 1 }}>
      {isLoaded && (
        <YandexMap
          style={{ width, height }}
          onMapReady={(event) => {
            let data = markers.map((marker) => ({
              latitude: marker.latitude,
              longitude: marker.longitude,
            }));

            mapkitRef.current?.fitMarkers(data);
          }}
          clusterized={true}
          mapRef={mapkitRef}
          onMapLongTap={(e) => {
            const { latitude, longitude } = e.nativeEvent;
            addMarker(latitude, longitude, Date.now().toString());
          }}
        >
          {markers.map((marker, i) =>
            renderMarker(marker.latitude, marker.longitude, marker.id),
          )}
        </YandexMap>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  marker: {
    paddingHorizontal: 10,
    paddingVertical: 5,
    backgroundColor: "#528BEF",
    borderRadius: 50,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
  },
});
