package BookingPackage;

import org.json.JSONException;

import java.io.IOException;

public interface Booking {
  /**
   * Booking Interface
   *
   * @param customerID
   * @param testingSiteID
   * @param startTime
   * @param notes
   * @param API_KEY
   * @return
   * @throws IOException
   * @throws JSONException
   */
  String create(
      String customerID, String testingSiteID, String startTime, String notes, String API_KEY)
      throws IOException, JSONException;
}
