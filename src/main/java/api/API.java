package api;

import api.model.AddressData;
import api.model.AddressData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import okhttp3.*;

public class API {

  public static final String version = "0.0.4";

  private static API instance;

  private String apiKey;

  private String domain;

  private int port = 0;

  private OkHttpClient httpClient;

  private API() {
    this.httpClient = new OkHttpClient();
  }

  public static API getInstance() {
    if (instance == null) {
      instance = new API();
    }

    return instance;
  }

  public API domain(String domain) {
    this.domain = domain;
    return getInstance();
  }

  public API apiKey(String apiKey) {
    this.apiKey = apiKey;
    return getInstance();
  }

  public String getApiKey() {
    return apiKey;
  }

  public API port(int port) {
    this.port = port;
    return getInstance();
  }

  public String getDomain() {
    return domain;
  }

  public int getPort() {
    return port;
  }

  public String getUrl() {
    String domain = this.domain;
    if (this.port > 0) {
      domain += ":" + port;
    }

    return domain;
  }

  public OkHttpClient getHttpClient() {
    return httpClient;
  }

  public void setHttpClient(OkHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Get all AddressData
   **/
  public ArrayList<AddressData> getAllAddressData() throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdatas/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .get()
      .build();

    Response httpResponse = this.httpClient.newCall(request).execute();

    int statusCode = httpResponse.code();
    String response = httpResponse.body().string();

    if (statusCode < 200 || statusCode >= 300) {
      throw new HttpException(statusCode, response);
    }

    Type type = new TypeToken<ArrayList<AddressData>>() {}.getType();
    return gson.fromJson(response, type);
  }

  /**
   * Delete an existing AddressData
   **/
  public AddressData deleteByFirstname(String firstname) throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + "?firstname=" + firstname)
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .delete()
      .build();

    Response httpResponse = this.httpClient.newCall(request).execute();

    int statusCode = httpResponse.code();
    String response = httpResponse.body().string();

    if (statusCode < 200 || statusCode >= 300) {
      throw new HttpException(statusCode, response);
    }

    return gson.fromJson(response, AddressData.class);
  }

  /**
   * Get all AddressData
   **/
  public void getAllAddressData(
    AsyncCallback<ArrayList<AddressData>> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdatas/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .get()
      .build();

    this.httpClient.newCall(request)
      .enqueue(
        new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
            callback.onError(e);
          }

          @Override
          public void onResponse(Call call, Response httpResponse)
            throws IOException {
            int statusCode = httpResponse.code();
            String response = httpResponse.body().string();

            if (statusCode < 200 || statusCode >= 300) {
              callback.onError(new HttpException(statusCode, response));
            }

            Type type = new TypeToken<ArrayList<AddressData>>() {}.getType();
            callback.onSuccess(gson.fromJson(response, type));
          }
        }
      );
  }

  /**
   * Delete an existing AddressData
   **/
  public void deleteByFirstname(
    String firstname,
    AsyncCallback<AddressData> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + "?firstname=" + firstname)
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .delete()
      .build();

    this.httpClient.newCall(request)
      .enqueue(
        new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
            callback.onError(e);
          }

          @Override
          public void onResponse(Call call, Response httpResponse)
            throws IOException {
            int statusCode = httpResponse.code();
            String response = httpResponse.body().string();

            if (statusCode < 200 || statusCode >= 300) {
              callback.onError(new HttpException(statusCode, response));
            }

            callback.onSuccess(gson.fromJson(response, AddressData.class));
          }
        }
      );
  }

  public interface AsyncCallback<T> {
    public void onSuccess(T response);

    public void onError(Exception error);
  }
}
