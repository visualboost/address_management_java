package api.model;

import api.API;
import api.HttpException;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteFile {

  private String _id;
  private String path;
  private String mimetype;
  private int fileSize;

  public RemoteFile(String _id, String path, String mimetype, int fileSize) {
    this._id = _id;
    this.path = path;
    this.mimetype = mimetype;
    this.fileSize = fileSize;
  }

  public String getId() {
    return _id;
  }

  public void setId(String _id) {
    this._id = _id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getMimetype() {
    return mimetype;
  }

  public void setMimetype(String mimetype) {
    this.mimetype = mimetype;
  }

  public int getFileSize() {
    return fileSize;
  }

  public void setFileSize(int fileSize) {
    this.fileSize = fileSize;
  }

  public String getDownloadUrl(API api) {
    return api.getUrl() + "/file/" + this.getId();
  }

  public byte[] download(API api) throws Exception {
    Request request = new Request.Builder()
      .url(getDownloadUrl(api))
      .header("x-api-key", api.getApiKey())
      .get()
      .build();

    Response httpResponse = api.getHttpClient().newCall(request).execute();

    int statusCode = httpResponse.code();
    byte[] response = httpResponse.body().bytes();

    if (statusCode < 200 || statusCode >= 300) {
      String message = httpResponse.body().string();
      throw new HttpException(statusCode, message);
    }

    return response;
  }

  public File download(API api, String downloadPath) throws Exception {
    byte[] content = download(api);

    File downloadedFile = new File(downloadPath);
    if (!downloadedFile.exists()) {
      downloadedFile.createNewFile();
    }

    try (OutputStream os = Files.newOutputStream(downloadedFile.toPath())) {
      os.write(content);
    }

    return downloadedFile;
  }
}
