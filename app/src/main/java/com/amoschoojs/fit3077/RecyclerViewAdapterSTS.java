package com.amoschoojs.fit3077;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import models.BookingPackage.BookingCaretaker;
import models.BookingPackage.HomeBooking;
import models.BookingPackage.TestingOnSiteBooking;
import models.ExceptionPackage.InvalidCredentialsException;
import models.LoginSystemPackage.LoginAuthentication;
import models.TestingFacilityPackage.TestingFacility;
import models.UserPackage.Customer;
import models.UserPackage.Receptionist;
import models.UserPackage.User;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import viewmodel.BookingViewModel;

public class RecyclerViewAdapterSTS extends RecyclerView.Adapter<RecyclerViewAdapterSTS.ViewHolder>
    implements Filterable {

  ArrayList<TestingFacility> testingFacilities;
  ArrayList<TestingFacility> testingFacilitiesFiltered;
  BookingViewModel bookingViewModel;
  String bookingID;
  Activity activity;
  BookingCaretaker bookingCaretaker = BookingCaretaker.getInstance();
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

  public RecyclerViewAdapterSTS(Application application, String bookingID, Activity activity) {
    bookingViewModel = new BookingViewModel(application);
    this.bookingID = bookingID;
    this.activity = activity;
  }

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
      if (bookingID != null) { // modify logic
        int pos = getAdapterPosition();

        // get testing facility position of user click
        TestingFacility testingFacility = testingFacilitiesFiltered.get(pos);
        String testingFacilityID = testingFacility.getId();
        Toast.makeText(view.getContext(), "selected testing facility", Toast.LENGTH_SHORT).show();
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener =
                    new TimePickerDialog.OnTimeSetListener() {
                      @Override
                      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String startTime = calendar.getTime().toInstant().toString();
                        FormBody.Builder builder =
                            new FormBody.Builder()
                                .add("testingSiteId", testingFacilityID)
                                .add("startTime", startTime);
                        RequestBody formBody = builder.build();
                        try {
                          bookingViewModel.updateBooking(
                              view.getContext().getString(R.string.api_key), bookingID, formBody);
                          Toast.makeText(view.getContext(), "updated booking", Toast.LENGTH_SHORT)
                              .show();
                          activity.finish();
                        } catch (IOException e) {
                          e.printStackTrace();
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    };
                new TimePickerDialog(
                        view.getContext(),
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false)
                    .show();
              }
            };

        new DatePickerDialog(
                view.getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            .show();
        //        ((Activity)(view.getContext())).finish();

      } else {
        LoginAuthentication loginAuthentication = null;
        try {
          loginAuthentication = LoginAuthentication.getInstance();
        } catch (IOException | JSONException e) {
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
        String[] time = {null};
        {
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
                      final Calendar calendar = Calendar.getInstance();
                      DatePickerDialog.OnDateSetListener dateSetListener =
                          new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                DatePicker view, int year, int month, int dayOfMonth) {
                              calendar.set(Calendar.YEAR, year);
                              calendar.set(Calendar.MONTH, month);
                              calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                              TimePickerDialog.OnTimeSetListener timeSetListener =
                                  new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(
                                        TimePicker view, int hourOfDay, int minute) {
                                      calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                      calendar.set(Calendar.MINUTE, minute);

                                      Customer c = null;
                                      try {
                                        c =
                                            r.createCustomer(
                                                givenName,
                                                familyName,
                                                userName,
                                                password,
                                                phoneNumber,
                                                new JSONObject("{}"));
                                      } catch (JSONException e) {
                                        e.printStackTrace();
                                      } catch (IOException e) {
                                        e.printStackTrace();
                                      } catch (InvalidCredentialsException e) {
                                        e.printStackTrace();
                                      }
                                      Booking booking =
                                          new TestingOnSiteBooking(
                                              c.getUserId(),
                                              testingFacilityID,
                                              calendar.getTime().toInstant().toString(),
                                              null);
                                      // and time
                                      String[] returned = new String[0];
                                      try {
                                        returned =
                                            bookingViewModel.createBooking(
                                                booking,
                                                view.getContext().getString(R.string.api_key));
                                      } catch (JSONException e) {
                                        e.printStackTrace();
                                      } catch (IOException e) {
                                        e.printStackTrace();
                                      }
                                      String pin = returned[0];
                                      String bookingID = returned[1];
                                      // notify user the booking pin
                                      NotificationCompat.Builder builder =
                                          new NotificationCompat.Builder(
                                                  view.getContext(), "BOOKING CONFIRM")
                                              .setContentTitle("Booking Pin")
                                              .setSmallIcon(R.drawable.ic_launcher_background)
                                              .setContentText(pin)
                                              .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                      Toast.makeText(view.getContext(), pin, Toast.LENGTH_LONG)
                                          .show();
                                      NotificationManager notificationManager =
                                          (NotificationManager)
                                              view.getContext()
                                                  .getSystemService(Context.NOTIFICATION_SERVICE);
                                      notificationManager.notify(1, builder.build());
                                    }
                                  };

                              new TimePickerDialog(
                                      view.getContext(),
                                      timeSetListener,
                                      calendar.get(Calendar.HOUR_OF_DAY),
                                      calendar.get(Calendar.MINUTE),
                                      false)
                                  .show();
                            }
                          };

                      new DatePickerDialog(
                              view.getContext(),
                              dateSetListener,
                              calendar.get(Calendar.YEAR),
                              calendar.get(Calendar.MONTH),
                              calendar.get(Calendar.DAY_OF_MONTH))
                          .show();

                    } catch (InvalidCredentialsException e) {
                      e.printStackTrace();
                      Toast.makeText(
                              view.getContext(), "Wrong/Empty credentials", Toast.LENGTH_SHORT)
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
                    final String[] pin = {null};
                    final String[] bookingID = {null};
                    final Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                          @Override
                          public void onDateSet(
                              DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            TimePickerDialog.OnTimeSetListener timeSetListener =
                                new TimePickerDialog.OnTimeSetListener() {
                                  @Override
                                  public void onTimeSet(
                                      TimePicker view, int hourOfDay, int minute) {
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);
                                    Booking booking =
                                        new TestingOnSiteBooking(
                                            user.getUserId(),
                                            testingFacilityID,
                                            calendar.getTime().toInstant().toString(),
                                            null);
                                    String[] returned = new String[0];
                                    try {
                                      returned =
                                          bookingViewModel.createBooking(
                                              booking,
                                              view.getContext().getString(R.string.api_key));
                                    } catch (JSONException jsonException) {
                                      jsonException.printStackTrace();
                                    } catch (IOException ioException) {
                                      ioException.printStackTrace();
                                    }
                                    pin[0] = returned[0];
                                    bookingID[0] = returned[1];
                                    NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(
                                                view.getContext(), "BOOKING CONFIRM")
                                            .setContentTitle("Booking Pin")
                                            .setSmallIcon(R.drawable.ic_launcher_background)
                                            .setContentText(pin[0])
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                    Toast.makeText(view.getContext(), pin[0], Toast.LENGTH_LONG)
                                        .show();
                                    NotificationManager notificationManager =
                                        (NotificationManager)
                                            view.getContext()
                                                .getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(1, builder.build());
                                  }
                                };

                            new TimePickerDialog(
                                    view.getContext(),
                                    timeSetListener,
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    false)
                                .show();
                          }
                        };

                    new DatePickerDialog(
                            view.getContext(),
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
                  } else { // homebooking here

                    final Calendar calendar = Calendar.getInstance();
                    String[] finalPin = {null};
                    String[] finalBookingID = {null};
                    DatePickerDialog.OnDateSetListener dateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                          @Override
                          public void onDateSet(
                              DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            TimePickerDialog.OnTimeSetListener timeSetListener =
                                new TimePickerDialog.OnTimeSetListener() {
                                  @Override
                                  public void onTimeSet(
                                      TimePicker view, int hourOfDay, int minute) {
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);
                                    Booking booking =
                                        new HomeBooking(
                                            user.getUserId(),
                                            testingFacilityID,
                                            calendar.getTime().toInstant().toString(),
                                            null);
                                    // time
                                    String[] returned = new String[0];
                                    try {
                                      returned =
                                          bookingViewModel.createBooking(
                                              booking,
                                              view.getContext().getString(R.string.api_key));
                                    } catch (JSONException jsonException) {
                                      jsonException.printStackTrace();
                                    } catch (IOException ioException) {
                                      ioException.printStackTrace();
                                    }
                                    finalPin[0] = returned[0];
                                    finalBookingID[0] = returned[1];
                                    NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(
                                                view.getContext(), "BOOKING CONFIRM")
                                            .setContentTitle("Booking Pin")
                                            .setSmallIcon(R.drawable.ic_launcher_background)
                                            .setContentText(finalPin[0])
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                    Toast.makeText(
                                            view.getContext(), finalPin[0], Toast.LENGTH_LONG)
                                        .show();
                                    NotificationManager notificationManager =
                                        (NotificationManager)
                                            view.getContext()
                                                .getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(1, builder.build());
                                    Intent switchActivityIntent =
                                        new Intent(itemView.getContext(), QRCodeActivity.class);
                                    switchActivityIntent.putExtra("smsPin", finalPin[0]);
                                    itemView.getContext().startActivity(switchActivityIntent);
                                  }
                                };

                            new TimePickerDialog(
                                    view.getContext(),
                                    timeSetListener,
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    false)
                                .show();
                          }
                        };

                    new DatePickerDialog(
                            view.getContext(),
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
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
}
