package api.model;

public class AddressData {

  private String _id;
  private String firstname;
  private String lastname;
  private String email;
  private String phonenumber;
  private Address address;

  public AddressData(
    String _id,
    String firstname,
    String lastname,
    String email,
    String phonenumber,
    Address address
  ) {
    this._id = _id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phonenumber = phonenumber;
    this.address = address;
  }

  public String getId() {
    return this._id;
  }

  public void setId(String _id) {
    this._id = _id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhonenumber() {
    return this.phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
