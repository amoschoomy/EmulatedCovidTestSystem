package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import okhttp3.RequestBody;
import repository.BookingRepository;

public class BookingViewModel extends AndroidViewModel {

  private final BookingRepository bookingRepository;
  private final LiveData<ArrayList<Booking>> allBookings;

  public BookingViewModel(@NonNull Application application) {
    super(application);
    bookingRepository = new BookingRepository(application);
    allBookings = bookingRepository.getAllBookings();
  }

  public String[] createBooking(Booking booking, String API_KEY) throws JSONException, IOException {
    return bookingRepository.create(booking, API_KEY);
  }

  public String[] checkBooking(String code, String API_KEY, Boolean isPin) throws Exception {
    return bookingRepository.check(code, API_KEY, isPin);
  }

  public void updateBooking(String API_KEY, String bookingID, RequestBody requestBody)
      throws IOException {
    bookingRepository.update(API_KEY, bookingID, requestBody);
  }
}
