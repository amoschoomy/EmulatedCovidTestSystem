package BookingPackage;

import org.json.JSONException;

import java.io.IOException;

public interface Booking {
  String create(
      String customerID, String testingSiteID, String startTime, String notes, String API_KEY)
      throws IOException, JSONException;
}
