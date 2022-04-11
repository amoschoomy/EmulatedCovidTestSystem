package com.amoschoojs.fit3077;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import TestingFacilityPackage.TestingFacility;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

  ArrayList<TestingFacility> testingFacilities;

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
    name.setText("Testing Facility: " + testingFacility.getName());
    openingTimes.setText("Opening Times: " + testingFacility.getOpeningTimes());
    closingTimes.setText("Closing Times: " + testingFacility.getClosingTimes());
    onSiteBookingStatus.setText("On Site Booking: " + testingFacility.isOnSiteBooking());
    openStatus.setText("Current Status: Open");
    waitingTimes.setText("Waiting Time: " + testingFacility.getWaitingTimes());
    siteType.setText("Testing Site Type: " + testingFacility.getTestingFacilityType());
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
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView carTv;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
