package models.BookingPackage;

import org.json.JSONException;

import okhttp3.RequestBody;

public interface Booking {

  RequestBody getRequestBody() throws JSONException;

  class BookingMemento {
    private String testingSiteID;
    private String startTime;
    private String status;
    private String testingSiteName;

    public BookingMemento(
        String testingSiteID, String startTime, String status, String testingSiteName) {
      this.testingSiteID = testingSiteID;
      this.startTime = startTime;
      this.status = status;
      this.testingSiteName = testingSiteName;
    }

    public String getTestingSiteName() {
      return testingSiteName;
    }

    public String getTestingSiteID() {
      return testingSiteID;
    }

    public String getStartTime() {
      return startTime;
    }

    public String getStatus() {
      return status;
    }
  }
}
