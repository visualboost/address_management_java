package api;

import api.model.Location;
import com.google.gson.*;
import java.lang.reflect.Type;

public class JsonSerializer {

  private static JsonSerializer instance;

  private Gson gson;

  private JsonSerializer() {
    gson =
      new GsonBuilder()
        .registerTypeAdapter(Location.class, new LocationSerializer())
        .create();
  }

  public Gson getGson() {
    return gson;
  }

  public static JsonSerializer getInstance() {
    if (instance == null) {
      instance = new JsonSerializer();
    }

    return instance;
  }

  public class LocationSerializer
    implements
      com.google.gson.JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(
      Location location,
      Type type,
      JsonSerializationContext jsonSerializationContext
    ) {
      JsonObject locationAsJson = new JsonObject();
      locationAsJson.addProperty("type", "Point");

      JsonArray coordinates = new JsonArray();
      coordinates.add(location.getLongitude());
      coordinates.add(location.getLatitude());
      locationAsJson.add("coordinates", coordinates);
      return locationAsJson;
    }

    @Override
    public Location deserialize(
      JsonElement jsonElement,
      Type type,
      JsonDeserializationContext jsonDeserializationContext
    ) throws JsonParseException {
      JsonArray coordinatesAsArray = jsonElement
        .getAsJsonObject()
        .get("coordinates")
        .getAsJsonArray();
      return new Location(
        coordinatesAsArray.get(0).getAsDouble(),
        coordinatesAsArray.get(1).getAsDouble()
      );
    }
  }
}
