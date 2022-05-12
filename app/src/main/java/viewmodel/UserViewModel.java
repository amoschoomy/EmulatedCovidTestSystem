package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import models.UserPackage.User;
import repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
  private final UserRepository userRepository;

  public UserViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
  }

  public LiveData<ArrayList<Booking>> getAllBookings(String userID, String API_KEY)
      throws JSONException, IOException {
    return userRepository.getAllBookings(userID, API_KEY);
  }

  public User getUser(String API_KEY, String userID) throws JSONException, IOException {
    String userStr = userRepository.getUser(API_KEY,userID);
    return new Gson().fromJson(userStr, User.class);
  }
}
