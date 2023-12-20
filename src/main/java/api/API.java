package api;

import api.model.AddressData;
import api.model.AddressData;
import api.model.dto.CreateAddressDataDto;
import api.model.dto.UpdateAddressDataDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import okhttp3.*;

public class API {

  public static final String version = "0.0.1";

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
   * Get a AddressData by _id
   **/
  public AddressData getAddressData(String _id) throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
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

    return gson.fromJson(response, AddressData.class);
  }

  /**
   * Get all AddressData
   **/
  public ArrayList<AddressData> getAllAddressData(String sort, int page)
    throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdatas/" + "?sort=" + sort + "&page=" + page)
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
   * Update an existing AddressData
   **/
  public AddressData updateAddressData(String _id, UpdateAddressDataDto body)
    throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .put(
        RequestBody.create(
          gson.toJson(body),
          MediaType.parse("application/json")
        )
      )
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
   * Delete an existing AddressData
   **/
  public AddressData deleteAddressData(String _id) throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
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
   * Creates a new AddressData
   **/
  public AddressData createAddressData(CreateAddressDataDto body)
    throws Exception {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .post(
        RequestBody.create(
          gson.toJson(body),
          MediaType.parse("application/json")
        )
      )
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
   * Get a AddressData by _id
   **/
  public void getAddressData(String _id, AsyncCallback<AddressData> callback) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
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

            callback.onSuccess(gson.fromJson(response, AddressData.class));
          }
        }
      );
  }

  /**
   * Get all AddressData
   **/
  public void getAllAddressData(
    String sort,
    int page,
    AsyncCallback<ArrayList<AddressData>> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdatas/" + "?sort=" + sort + "&page=" + page)
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
   * Update an existing AddressData
   **/
  public void updateAddressData(
    String _id,
    UpdateAddressDataDto body,
    AsyncCallback<AddressData> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .put(
        RequestBody.create(
          gson.toJson(body),
          MediaType.parse("application/json")
        )
      )
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

  /**
   * Delete an existing AddressData
   **/
  public void deleteAddressData(
    String _id,
    AsyncCallback<AddressData> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/" + _id + "/")
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

  /**
   * Creates a new AddressData
   **/
  public void createAddressData(
    CreateAddressDataDto body,
    AsyncCallback<AddressData> callback
  ) {
    Gson gson = JsonSerializer.getInstance().getGson();

    Request request = new Request.Builder()
      .url(getUrl() + "/addressdata/")
      .header("Content-Type", "application/json")
      .header("x-api-key", apiKey)
      .post(
        RequestBody.create(
          gson.toJson(body),
          MediaType.parse("application/json")
        )
      )
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
