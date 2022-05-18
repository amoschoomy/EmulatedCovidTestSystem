package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.data.BookingPackage.Booking;
import models.data.UserPackage.HealthcareWorkerFactory;
import models.data.UserPackage.ReceptionistFactory;
import models.data.UserPackage.User;
import models.data.UserPackage.UserFactory;
import models.repository.UserRepository;

/**
 * User ViewModel class that houses all operations related to Users
 */
public class UserViewModel extends AndroidViewModel {
  private final UserRepository userRepository;
  private MutableLiveData<ArrayList<Booking>> bookings;

  public UserViewModel(@NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
  }

  /**
   * Gets bookings made by user
   * @param userID
   * @param API_KEY
   * @return user bookings
   * @throws JSONException
   * @throws IOException
   */
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
    JSONObject jsonObject = new JSONObject(userStr);
    String id = jsonObject.getString("id");
    String givenName = jsonObject.getString("givenName");
    String familyName = jsonObject.getString("familyName");
    String userName = jsonObject.getString("userName");
    String phoneNumber = jsonObject.getString("phoneNumber");
    Boolean isCustomer = jsonObject.getBoolean("isCustomer");
    Boolean isReceptionist = jsonObject.getBoolean("isReceptionist");
    Boolean isHealthcareWorker = jsonObject.getBoolean("isHealthcareWorker");
    JSONObject additionalInfo = jsonObject.getJSONObject("additionalInfo");
    User user;
    UserFactory uf;
    if (isReceptionist) uf = new ReceptionistFactory();
    else uf = new HealthcareWorkerFactory();
    user = uf.createSpecificUser(id,givenName,familyName,userName,phoneNumber,isCustomer,isReceptionist,isHealthcareWorker,additionalInfo);
    return user;

  }

}
