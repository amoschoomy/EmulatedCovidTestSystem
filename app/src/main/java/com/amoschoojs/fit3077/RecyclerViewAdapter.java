package com.amoschoojs.fit3077;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

import BookingPackage.MakeBookingFacade;
import LoginSystemPackage.LoginAuthentication;
import TestingFacilityPackage.TestingFacility;
import UserPackage.Receptionist;
import UserPackage.User;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
    implements Filterable {

  ArrayList<TestingFacility> testingFacilities;
  ArrayList<TestingFacility> testingFacilitiesFiltered;
  private Filter exampleFilter =
      new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          ArrayList<TestingFacility> filteredList = new ArrayList<>();

          if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(testingFacilitiesFiltered);
          } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (TestingFacility item : testingFacilitiesFiltered) {
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
    //        Log.d("myTag",Integer.toString(testingFacilities.size()));
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
      TestingFacility testingFacility = testingFacilitiesFiltered.get(pos);
      String testingFacilityID = testingFacility.getId();
      Boolean onSiteBooking = testingFacility.isOnSiteBooking();
      User user = loginAuthentication.getUser();
      View checkBoxView = View.inflate(view.getContext(), R.layout.checkbox, null);
      CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
      checkBox.setText("Home Booking?");

      if (user.getReceptionist() && !onSiteBooking) {
        Toast.makeText(view.getContext(), "Sorry On Site Booking not allowed", Toast.LENGTH_SHORT)
            .show();

      } else if (user.getReceptionist()) {
        new AlertDialog.Builder(view.getContext())
            .setView(View.inflate(view.getContext(), R.layout.enter_details, null))
            .setTitle("Make Booking?")
            .setMessage("Are you sure you want to make a booking for a customer ?")
            .setPositiveButton(
                android.R.string.yes,
                (dialog, which) -> {
                  Receptionist r = (Receptionist) user;
                  // TODO: Await receptionist method

                })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.ic_launcher_background)
            .show();
      } else if (user.getCustomer()) {
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
                          MakeBookingFacade.makeBooking(
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
                    }
                    Toast.makeText(view.getContext(), pin, Toast.LENGTH_SHORT).show();

                  } else {
                    Toast.makeText(view.getContext(), "HomeBooking selectedd", Toast.LENGTH_SHORT)
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
