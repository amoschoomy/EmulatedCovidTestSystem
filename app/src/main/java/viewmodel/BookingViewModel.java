package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import models.data.BookingPackage.Booking;
import models.repository.BookingRepository;
import okhttp3.RequestBody;

/**
 * Booking ViewModel class that houses all operations related to Bookings
 */
public class BookingViewModel extends AndroidViewModel {

  private final BookingRepository bookingRepository;
  private MutableLiveData<ArrayList<Booking>> bookings;

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

  public String deleteBooking(String API_KEY, String bookingID)
          throws IOException, JSONException {
    return bookingRepository.delete(API_KEY, bookingID);
  }

  /**
   * Gets all bookings made
   * @param API_KEY
   * @return bookings
   * @throws JSONException
   * @throws IOException
   */
  public MutableLiveData<ArrayList<Booking>> getBookings(String API_KEY)
          throws JSONException, IOException {
    bookings = inGetBookings(API_KEY);
    return bookings;
  }

  private MutableLiveData<ArrayList<Booking>> inGetBookings(String API_KEY)
          throws JSONException, IOException {
    return (MutableLiveData<ArrayList<Booking>>) bookingRepository.getBookings(API_KEY);
  }


}
