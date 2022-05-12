package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import models.UserPackage.User;
import repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
  private final UserRepository userRepository;
  private MutableLiveData<ArrayList<Booking>> bookings;

  public UserViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
  }

  public MutableLiveData<ArrayList<Booking>> retrieveBookings(String userID, String API_KEY)
      throws JSONException, IOException {
    if (bookings == null) {
      bookings = getAllBookings(userID, API_KEY);
    }
    return bookings;
  }

  private MutableLiveData<ArrayList<Booking>> getAllBookings(String userID, String API_KEY)
      throws JSONException, IOException {
    return (MutableLiveData<ArrayList<Booking>>) userRepository.getAllBookings(userID, API_KEY);
  }

  public User getUser(String API_KEY, String userID) throws JSONException, IOException {
    String userStr = userRepository.getUser(API_KEY,userID);
    return new Gson().fromJson(userStr, User.class);
  }
}
