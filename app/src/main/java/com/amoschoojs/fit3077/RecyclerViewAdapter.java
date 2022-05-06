package com.amoschoojs.fit3077;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

import models.BookingPackage.BookingFacade;
import models.ExceptionPackage.InvalidCredentialsException;
import models.LoginSystemPackage.LoginAuthentication;
import models.TestingFacilityPackage.TestingFacility;
import models.UserPackage.Customer;
import models.UserPackage.Receptionist;
import models.UserPackage.User;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
    implements Filterable {

  ArrayList<TestingFacility> testingFacilities;
  ArrayList<TestingFacility> testingFacilitiesFiltered;

  // Filtering details
  private Filter exampleFilter =
      new Filter() {

        /**
         * Filtering algorithm
         *
         * @param constraint constraint of search
         * @return
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          ArrayList<TestingFacility> filteredList = new ArrayList<>();

          if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(testingFacilitiesFiltered);
          } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (TestingFacility item : testingFacilitiesFiltered) {
              // search by suburb or testing type
              if (item.getAddress().getSuburb().toLowerCase().contains(filterPattern)
                  || item.getTestingFacilityType()
                      .toString()
                      .toLowerCase()
                      .contains(filterPattern)) {
                filteredList.add(item);
              }
            }
          }
          FilterResults results = new FilterResults();
          results.values = filteredList;

          return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
          testingFacilities.clear();
          testingFacilities.addAll((ArrayList) filterResults.values);
          notifyDataSetChanged();
        }
      };

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

    return new ViewHolder(v) {};
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    TestingFacility testingFacility = testingFacilities.get(holder.getAdapterPosition());
    TextView name = holder.itemView.findViewById(R.id.name);
    TextView onSiteBookingStatus = holder.itemView.findViewById(R.id.onsitebooking);
    TextView openingTimes = holder.itemView.findViewById(R.id.open);
    TextView closingTimes = holder.itemView.findViewById(R.id.closed);
    TextView openStatus = holder.itemView.findViewById(R.id.openstatus);
    TextView waitingTimes = holder.itemView.findViewById(R.id.waitingtime);
    TextView siteType = holder.itemView.findViewById(R.id.sitetype);
    TextView suburb = holder.itemView.findViewById(R.id.suburb);
    name.setText("Testing Facility: " + testingFacility.getName());
    openingTimes.setText("Opening Times: " + testingFacility.getOpeningTimes());
    closingTimes.setText("Closing Times: " + testingFacility.getClosingTimes());
    onSiteBookingStatus.setText("On Site Booking: " + testingFacility.isOnSiteBooking());
    openStatus.setText("Current Status: Open");
    waitingTimes.setText("Waiting Time: " + testingFacility.getWaitingTimes());
    siteType.setText("Testing Site Type: " + testingFacility.getTestingFacilityType().toString());
    suburb.setText("Suburb: " + testingFacility.getAddress().getSuburb());
  }

  @Override
  public int getItemCount() {
    if (testingFacilities == null) {
      return 0;
    }
    return testingFacilities.size();
  }

  public void setCars(ArrayList<TestingFacility> testingFacilities) {
    this.testingFacilities = testingFacilities;
    testingFacilitiesFiltered = new ArrayList<>(this.testingFacilities);
  }

  @Override
  public Filter getFilter() {
    return exampleFilter;
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
    }

    /**
     * verifyInput of Booking details
     *
     * @param givenName
     * @param familyName
     * @param username
     * @param password
     * @param phone
     * @return
     */
    private boolean verifyInput(
        String givenName, String familyName, String username, String password, String phone) {
      return (givenName.length() > 0
          && familyName.length() > 0
          && username.length() > 0
          && password.length() > 0
          && phone.length() > 0);
    }

    @Override
    public void onClick(View view) {
      LoginAuthentication loginAuthentication = null;
      try {
        loginAuthentication = LoginAuthentication.getInstance();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (JSONException e) {
        e.printStackTrace();
      }
      int pos = getAdapterPosition();

      // get testing facility position of user click
      TestingFacility testingFacility = testingFacilitiesFiltered.get(pos);
      String testingFacilityID = testingFacility.getId();
      Boolean onSiteBooking = testingFacility.isOnSiteBooking();
      User user = loginAuthentication.getUser(); // retrieve user of application
      View checkBoxView = View.inflate(view.getContext(), R.layout.checkbox, null);
      CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
      checkBox.setText("Home Booking?");

      // onsite booking not allowed for receptionist
      if (user.getReceptionist() && !onSiteBooking) {
        Toast.makeText(view.getContext(), "Sorry On Site Booking not allowed", Toast.LENGTH_SHORT)
            .show();

      } else if (user.getReceptionist()) {
        View formView = View.inflate(view.getContext(), R.layout.enter_details, null);

        new AlertDialog.Builder(view.getContext())
            .setView(formView)
            .setTitle("Make Booking?")
            .setMessage("Are you sure you want to make a booking for a customer ?")
            .setPositiveButton(
                android.R.string.yes,
                (dialog, which) -> {
                  Receptionist r = (Receptionist) user;
                  try {

                    String givenName =
                        ((EditText) formView.findViewById(R.id.givenname)).getText().toString();
                    String familyName =
                        ((EditText) formView.findViewById(R.id.familyname)).getText().toString();
                    String userName =
                        ((EditText) formView.findViewById(R.id.username)).getText().toString();
                    String password =
                        ((EditText) formView.findViewById(R.id.password)).getText().toString();
                    String phoneNumber =
                        ((EditText) formView.findViewById(R.id.phoneno)).getText().toString();
                    if (!verifyInput(givenName, familyName, userName, password, phoneNumber)) {
                      throw new InvalidCredentialsException();
                    }
                    Customer c =
                        r.createCustomer(
                            givenName,
                            familyName,
                            userName,
                            password,
                            phoneNumber,
                            new JSONObject("{}"));
                    String pin =
                        BookingFacade.makeBooking(
                            c,
                            testingFacilityID,
                            null,
                            false,
                            Instant.now().plusSeconds(604800).toString(),
                            view.getContext().getString(R.string.api_key));

                    // notify user the booking pin
                    NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(view.getContext(), "BOOKING CONFIRM")
                            .setContentTitle("Booking Pin")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText(pin)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Toast.makeText(view.getContext(), pin, Toast.LENGTH_LONG).show();
                    NotificationManager notificationManager =
                        (NotificationManager)
                            view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());

                  } catch (InvalidCredentialsException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "Wrong/Empty credentials", Toast.LENGTH_SHORT)
                        .show();
                  } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "Failed, try again", Toast.LENGTH_SHORT)
                        .show();
                  }
                })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.ic_launcher_background)
            .show();
      }

      // customer booking
      else if (user.getCustomer()) {
        new AlertDialog.Builder(view.getContext())
            .setView(checkBoxView)
            .setTitle("Make Booking?")
            .setMessage("Are you sure you want to make a booking ?")
            .setPositiveButton(
                android.R.string.yes,
                (dialog, which) -> {
                  if (!checkBox.isChecked()) { // on site testing booking from home
                    String pin = null;
                    try {
                      pin =
                          BookingFacade.makeBooking(
                              user,
                              testingFacilityID,
                              null,
                              false,
                              Instant.now().plusSeconds(604800).toString(),
                              view.getContext().getString(R.string.api_key));
                    } catch (JSONException e) {
                      e.printStackTrace();
                    } catch (IOException e) {
                      e.printStackTrace();
                    } catch (WriterException e) {
                      e.printStackTrace();
                    }

                    NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(view.getContext(), "BOOKING CONFIRM")
                            .setContentTitle("Booking Pin")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText(pin)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Toast.makeText(view.getContext(), pin, Toast.LENGTH_LONG).show();
                    NotificationManager notificationManager =
                        (NotificationManager)
                            view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());

                  } else { // homebooking here
                    String pin = null;
                    try {
                      pin =
                          BookingFacade.makeBooking(
                              user,
                              testingFacilityID,
                              null,
                              true,
                              Instant.now().plusSeconds(604800).toString(),
                              view.getContext().getString(R.string.api_key));
                    } catch (JSONException e) {
                      e.printStackTrace();
                    } catch (IOException e) {
                      e.printStackTrace();
                    } catch (WriterException e) {
                      e.printStackTrace();
                    }

                    NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(view.getContext(), "BOOKING CONFIRM")
                            .setContentTitle("Booking Pin")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText(pin)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Toast.makeText(view.getContext(), pin, Toast.LENGTH_LONG).show();
                    NotificationManager notificationManager =
                        (NotificationManager)
                            view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());


//                  ((HomeBooking) booking).setQRCode(smsPin);

                    Intent switchActivityIntent = new Intent(itemView.getContext(), QRCodeActivity.class);
                    switchActivityIntent.putExtra("smsPin", pin);
                    itemView.getContext().startActivity(switchActivityIntent);
                  }

                })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.ic_launcher_background)
            .show();
      }
    }
  }
}
