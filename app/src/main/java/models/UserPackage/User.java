package models.UserPackage;

//import androidx.databinding.BaseObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public abstract class User extends Observable {

    private String userId;
    private String givenName;
    private String familyName;
    private String userName;
    private String phoneNumber;
    private Boolean isCustomer;
    private Boolean isReceptionist;
    private Boolean isHealthcareWorker;
    private JSONObject additionalInfo;
    private String notification;

    /**
     * Creates user object
     *
     * @param userId
     * @param givenName
     * @param familyName
     * @param userName
     * @param phoneNumber
     * @param isCustomer
     * @param isReceptionist
     * @param isHealthcareWorker
     * @param additionalInfo
     */
    public User(String userId, String givenName, String familyName, String userName,
                String phoneNumber, Boolean isCustomer, Boolean isReceptionist,
                Boolean isHealthcareWorker, JSONObject additionalInfo) {
        this.userId = userId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.isCustomer = isCustomer;
        this.isReceptionist = isReceptionist;
        this.isHealthcareWorker = isHealthcareWorker;
        this.additionalInfo = additionalInfo;
    }

    public String getUserId() {
    return userId;
  }

    public Boolean getCustomer() {
    return isCustomer;
  }

    public Boolean getReceptionist() {
    return isReceptionist;
  }

    public Boolean getHealthcareWorker() {
    return isHealthcareWorker;
  }

    public String getNotification() throws JSONException {
        return additionalInfo.getString("notification");
    }

    public void setNotification(String notification) {
        this.notification = notification;
        setChanged();
        notifyObservers(notification);
    }



}
