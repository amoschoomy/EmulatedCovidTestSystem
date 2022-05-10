package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;

import java.io.IOException;

import models.BookingPackage.Booking;
import okhttp3.RequestBody;
import repository.BookingRepository;

public class BookingViewModel extends AndroidViewModel {

  private final BookingRepository bookingRepository;

  public BookingViewModel(@NonNull Application application) {
    super(application);
    bookingRepository = new BookingRepository(application);
  }

  public String[] createBooking(Booking booking, String API_KEY) throws JSONException, IOException {
    return bookingRepository.create(booking, API_KEY);
  }

  public String[] checkBooking(String code, String API_KEY, Boolean isPin) throws Exception {
    return bookingRepository.check(code, API_KEY, isPin);
  }

  public String updateBooking(String API_KEY, String bookingID, RequestBody requestBody)
      throws IOException, JSONException {
    return bookingRepository.update(API_KEY, bookingID, requestBody);
  }
}
