package api.model;

import api.model.Location;

public class Address {

  private String _id;
  private int housenumber;
  private String street;
  private Location location;

  public Address(
    String _id,
    int housenumber,
    String street,
    Location location
  ) {
    this._id = _id;
    this.housenumber = housenumber;
    this.street = street;
    this.location = location;
  }

  public String getId() {
    return this._id;
  }

  public void setId(String _id) {
    this._id = _id;
  }

  public int getHousenumber() {
    return this.housenumber;
  }

  public void setHousenumber(int housenumber) {
    this.housenumber = housenumber;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
