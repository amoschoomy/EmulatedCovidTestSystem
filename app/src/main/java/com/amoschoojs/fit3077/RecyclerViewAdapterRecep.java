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
import models.BookingPackage.BookingCaretaker;
import models.BookingPackage.TestingOnSiteBooking;
import models.LoginSystemPackage.LoginAuthentication;
import models.UserPackage.User;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import viewmodel.BookingViewModel;
import viewmodel.UserViewModel;

public class RecyclerViewAdapterRecep
        extends RecyclerView.Adapter<RecyclerViewAdapterRecep.ViewHolder> {
    BookingViewModel bookingViewModel;
    ArrayList<Booking> bookings;
    BookingCaretaker caretaker = new BookingCaretaker();

    public RecyclerViewAdapterRecep(Application application) {
        bookingViewModel = new BookingViewModel(application);
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_receptionist, parent, false);

        return new RecyclerViewAdapterRecep.ViewHolder(v) {};
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestingOnSiteBooking booking = (TestingOnSiteBooking) bookings.get(holder.getAdapterPosition());
        TextView testingSiteName = holder.itemView.findViewById(R.id.testingsiteinput);
        TextView startTime = holder.itemView.findViewById(R.id.startTimeInput);
        TextView status = holder.itemView.findViewById(R.id.statusInput);
        TextView updatedAt = holder.itemView.findViewById(R.id.updateInput);
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

        // set visibility of buttons
        Button modifyBtn = holder.itemView.findViewById(R.id.modifyBtn);
        Button cancelBtn = holder.itemView.findViewById(R.id.cancelBtn);
        Button deleteBtn = holder.itemView.findViewById(R.id.deleteBtn);
        Button processBtn = holder.itemView.findViewById(R.id.processBtn);

        // get user + user model
        LoginAuthentication loginAuthentication = null;
        try {
            loginAuthentication = LoginAuthentication.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User user = loginAuthentication.getUser();

        if (user.getHealthcareWorker()) {
            modifyBtn.setVisibility(View.GONE);
            cancelBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }

        if (user.getReceptionist()) {
            processBtn.setVisibility(View.GONE);
        }

        processBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), OnSiteTestingActivity.class);
                        i.putExtra("bookingId", booking.getBookingID());
                        Log.d("myTag", booking.getBookingID());
                        view.getContext().startActivity(i);
                    }
                });

        // Cancel Booking
        cancelBtn.setOnClickListener(
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
                                                caretaker.save(booking);
                                                String updatedAt =
                                                        bookingViewModel.updateBooking(
                                                                holder.itemView.getContext().getString(R.string.api_key),
                                                                booking.getBookingID(),
                                                                formBody);
                                                booking.setUpdatedAt(updatedAt);
                                                booking.setStatus("CANCELLED");
                                                notifyDataSetChanged();
                                                cancelBtn.setEnabled(false);
                                            } catch (IOException | JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(view.getContext(), "Booking Cancelled", Toast.LENGTH_LONG).show();
                                        })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(R.drawable.ic_launcher_background)
                                .show();
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