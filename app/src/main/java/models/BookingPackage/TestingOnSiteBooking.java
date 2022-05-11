package models.BookingPackage;

import org.json.JSONException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class TestingOnSiteBooking implements Booking {

  private String customerID;
  private String testingSiteID;
  private String testingSiteName;
  private String startTime;
  private String notes;
  private String bookingID;
  private String updatedAt;
  private String status = "INITIATED";

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTestingSiteName() {
    return testingSiteName;
  }

  public void setTestingSiteName(String testingSiteName) {
    this.testingSiteName = testingSiteName;
  }

  public String getCustomerID() {
    return customerID;
  }

  public void setCustomerID(String customerID) {
    this.customerID = customerID;
  }

  public String getTestingSiteID() {
    return testingSiteID;
  }

  public void setTestingSiteID(String testingSiteID) {
    this.testingSiteID = testingSiteID;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getBookingID() {
    return bookingID;
  }

  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public TestingOnSiteBooking(
      String customerID, String testingSiteID, String startTime, String notes) {
    this.customerID = customerID;
    this.testingSiteID = testingSiteID;
    this.startTime = startTime;
    this.notes = notes;
  }

  public BookingMemento save() {
    return new BookingMemento(testingSiteID, startTime, status);
  }

  public void undo(BookingMemento bookingMemento) {
    startTime = bookingMemento.getStartTime();
    testingSiteID = bookingMemento.getTestingSiteID();
    status = bookingMemento.getStatus();
  }

  @Override
  public RequestBody getRequestBody() throws JSONException {
    FormBody.Builder builder =
        new FormBody.Builder()
            .add("customerId", customerID)
            .add("testingSiteId", testingSiteID)
            .add("startTime", startTime);
    if (notes != null) builder.add("notes", notes);
    RequestBody formBody = builder.build();
    return formBody;
  }


}
