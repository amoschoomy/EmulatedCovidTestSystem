package com.amoschoojs.fit3077;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import models.BookingPackage.TestingOnSiteBooking;
import okhttp3.FormBody;
import okhttp3.RequestBody;
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
    Button modifyButton = holder.itemView.findViewById(R.id.modifybutton);
    Button cancelButton = holder.itemView.findViewById(R.id.cancelbutton);
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

    cancelButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            new AlertDialog.Builder(view.getContext())
                .setTitle("Cancel Booking?")
                .setMessage("Are you sure you want to cancel a booking ?")
                .setPositiveButton(
                    android.R.string.yes,
                    (dialog, which) -> {
                      FormBody.Builder builder = new FormBody.Builder().add("status", "CANCELLED");
                      RequestBody formBody = builder.build();
                      try {
                        String updatedAt =
                            bookingViewModel.updateBooking(
                                holder.itemView.getContext().getString(R.string.api_key),
                                booking.getBookingID(),
                                formBody);
                        booking.setUpdatedAt(updatedAt);
                        notifyDataSetChanged();
                      } catch (IOException | JSONException e) {
                        e.printStackTrace();
                      }
                      Toast.makeText(view.getContext(), "Lol OK", Toast.LENGTH_LONG).show();
                    })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_launcher_background)
                .show();
          }
        });

    modifyButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent i = new Intent(view.getContext(), SearchTestingSite.class);
            i.putExtra("key", booking.getBookingID());
            Log.d("myTag", booking.getBookingID());
            view.getContext().startActivity(i);
          }
        });
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
