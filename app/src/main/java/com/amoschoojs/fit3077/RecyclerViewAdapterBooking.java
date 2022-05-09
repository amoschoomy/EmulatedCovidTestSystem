package com.amoschoojs.fit3077;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.BookingPackage.Booking;
import viewmodel.BookingViewModel;

public class RecyclerViewAdapterBooking
    extends RecyclerView.Adapter<RecyclerViewAdapterBooking.ViewHolder> {
  BookingViewModel bookingViewModel;
  ArrayList<Booking> bookings;

  public RecyclerViewAdapterBooking(Application application) {
    bookingViewModel = new BookingViewModel(application);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_booking, parent, false);

    return new RecyclerViewAdapterBooking.ViewHolder(v) {};
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {}

  @Override
  public int getItemCount() {
    return 0;
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
