package TestingFacilityPackage;

/** Serialise JSON to address class */
public class Address {
  private float latitude;
  private float longitude;
  private int unitNumber;
  private String street;
  private String street2;
  private String suburb;
  private String state;
  private String postcode;

  public Address(
      float latitude,
      float longitude,
      int unitNumber,
      String street,
      String street2,
      String suburb,
      String state,
      String postcode) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.unitNumber = unitNumber;
    this.street = street;
    this.street2 = street2;
    this.suburb = suburb;
    this.state = state;
    this.postcode = postcode;
  }

  @Override
  public String toString() {
    return "Address{"
        + "latitude="
        + latitude
        + ", longitude="
        + longitude
        + ", unitNumber="
        + unitNumber
        + ", street='"
        + street
        + '\''
        + ", street2='"
        + street2
        + '\''
        + ", suburb='"
        + suburb
        + '\''
        + ", state='"
        + state
        + '\''
        + ", postcode='"
        + postcode
        + '\''
        + '}';
  }

  public String getSuburb() {
    return suburb;
  }

  public void setSuburb(String suburb) {
    this.suburb = suburb;
  }
}
