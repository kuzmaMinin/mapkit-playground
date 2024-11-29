import { View } from "react-native";
import { YandexMapView } from "@/modules/yandex-map";

export default function HomeScreen() {
  const markers = [
    { latitude: 55.751225, longitude: 37.62954 },
    { latitude: 55.851225, longitude: 26.62954 },
    { latitude: 54.751225, longitude: 37.92954 },
  ];

  return (
    <View style={{ flex: 1 }}>
      <YandexMapView
        style={{ width: "100%", height: "100%" }}
        markers={markers}
      />
    </View>
  );
}
