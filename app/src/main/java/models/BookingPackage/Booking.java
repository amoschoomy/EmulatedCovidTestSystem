package models.BookingPackage;

import org.json.JSONException;

import okhttp3.RequestBody;

public interface Booking {

  RequestBody getRequestBody() throws JSONException;
}
