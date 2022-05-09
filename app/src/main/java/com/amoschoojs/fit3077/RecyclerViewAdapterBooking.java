package com.amoschoojs.fit3077;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.BookingPackage.Booking;
import models.BookingPackage.TestingOnSiteBooking;
import viewmodel.BookingViewModel;

public class RecyclerViewAdapterBooking
    extends RecyclerView.Adapter<RecyclerViewAdapterBooking.ViewHolder> {
  BookingViewModel bookingViewModel;
  ArrayList<Booking> bookings;

  public RecyclerViewAdapterBooking(Application application) {
    bookingViewModel = new BookingViewModel(application);
  }

  public void setBookings(ArrayList<Booking> bookings) {
    this.bookings = bookings;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_booking, parent, false);

    return new RecyclerViewAdapterBooking.ViewHolder(v) {};
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    TestingOnSiteBooking booking = (TestingOnSiteBooking) bookings.get(holder.getAdapterPosition());
    TextView testingSiteName = holder.itemView.findViewById(R.id.testingsitecard);
    TextView startTime = holder.itemView.findViewById(R.id.starttimecard);
    TextView status = holder.itemView.findViewById(R.id.statuscard);
    TextView updatedAt = holder.itemView.findViewById(R.id.updatedAtcard);
    testingSiteName.setText(booking.getTestingSiteName());
    startTime.setText(booking.getStartTime());
    try {
      String[] array =
          bookingViewModel.checkBooking(
              booking.getBookingID(),
              holder.itemView.getContext().getString(R.string.api_key),
              false);
      status.setText(array[1]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    updatedAt.setText(booking.getUpdatedAt());
  }

  @Override
  public int getItemCount() {
    return bookings.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {}
  }
}
