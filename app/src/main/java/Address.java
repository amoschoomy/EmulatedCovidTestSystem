public class Address {
  private float latitude;
  private float longitude;
  private int unitNumber;
  private String street;
  private String street2;
  private String suburb;
  private String state;
  private String postcode;

  public String getSuburb() {
    return suburb;
  }

  public void setSuburb(String suburb) {
    this.suburb = suburb;
  }
}
