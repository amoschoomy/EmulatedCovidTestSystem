package com.amoschoojs.fit3077;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
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
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import models.BookingPackage.Booking;
import models.BookingPackage.BookingCaretaker;
import models.BookingPackage.TestingOnSiteBooking;
import models.LoginSystemPackage.LoginAuthentication;
import models.TestingFacilityPackage.TestingFacility;
import models.UserPackage.User;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import viewmodel.BookingViewModel;
import viewmodel.TestingFacilityViewModel;
import viewmodel.UserViewModel;

public class RecyclerViewAdapterRecep
        extends RecyclerView.Adapter<RecyclerViewAdapterRecep.ViewHolder>{
    BookingViewModel bookingViewModel;
    UserViewModel userViewModel;
//    TestingFacilityViewModel testingFacilityViewModel;
    ArrayList<Booking> bookings;
    BookingCaretaker caretaker = BookingCaretaker.getInstance();

    public RecyclerViewAdapterRecep(Application application, BookingViewModel bookingViewModel, UserViewModel userViewModel) {
        this.bookingViewModel = bookingViewModel;
        this.userViewModel = userViewModel;
//        testingFacilityViewModel = new TestingFacilityViewModel(application);
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
        Booking booking = bookings.get(holder.getAdapterPosition());
        TextView testingSiteName = holder.itemView.findViewById(R.id.testingsiteinput);
        TextView startTime = holder.itemView.findViewById(R.id.startTimeInput);
        TextView status = holder.itemView.findViewById(R.id.statusInput);
        TextView updatedAt = holder.itemView.findViewById(R.id.updateInput);

        Button modifyBtn = holder.itemView.findViewById(R.id.modifyBtn);
        Button cancelBtn = holder.itemView.findViewById(R.id.cancelBtn);
        Button deleteBtn = holder.itemView.findViewById(R.id.deleteBtn);
        Button processBtn = holder.itemView.findViewById(R.id.processBtn);
        Button undoBtn = holder.itemView.findViewById(R.id.undoBtn);

        try {
            String[] array =
                    bookingViewModel.checkBooking(
                            booking.getBookingID(),
                            holder.itemView.getContext().getString(R.string.api_key),
                            false);
            booking.setStatus(array[1]);
            booking.setStartTime(array[3]);
            booking.setTestingSiteName(array[2]);
            booking.setUpdatedAt(array[4]);
            testingSiteName.setText(booking.getTestingSiteName());
            startTime.setText(booking.getStartTime());
//            if (booking.getStatus().equals("CANCELLED")) {
//                Log.d("myTag", "reached");
//                cancelBtn.setEnabled(false);
//            }
            status.setText(array[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updatedAt.setText(booking.getUpdatedAt());

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

        // set visibility of buttons
        if (user.getHealthcareWorker()) {
            modifyBtn.setVisibility(View.GONE);
            cancelBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            undoBtn.setVisibility(View.GONE);
        }

        if (user.getReceptionist()) {
            processBtn.setVisibility(View.GONE);
        }

        // Process Booking
        processBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        caretaker.save(booking);
                        Intent i = new Intent(view.getContext(), OnSiteTestingActivity.class);
                        i.putExtra("bookingId", booking.getBookingID());
                        Log.d("myTag", booking.getBookingID());
                        view.getContext().startActivity(i);
//                        sendNotification(view);
                    }
                });

        // Modify Booking
        modifyBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        caretaker.save(booking);
                        Intent i = new Intent(view.getContext(), SearchTestingSite.class);
                        Gson gson = new Gson();
                        String myJson = gson.toJson(booking);
                        i.putExtra("key", myJson);
                        view.getContext().startActivity(i);
//                        sendNotification(view);
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
//                                                cancelBtn.setEnabled(false);


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

        // Undo Cancel/Modify Booking
        undoBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            caretaker.revert(booking);
                            FormBody.Builder builder =
                                    new FormBody.Builder()
                                            .add("status", booking.getStatus())
                                            .add("testingSiteId", booking.getTestingSiteID())
                                            .add("startTime", booking.getStartTime());
                            RequestBody formBody = builder.build();
                            String updatedAt =
                                    bookingViewModel.updateBooking(
                                            view.getContext().getString(R.string.api_key),
                                            booking.getBookingID(),
                                            formBody);

                            booking.setUpdatedAt(updatedAt);
                            notifyDataSetChanged();

//                            sendNotification(view);
//                            cancelBtn.setEnabled(true);
                        } catch (Exception e) {
                            Toast.makeText(view.getContext(), "No undo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Delete Booking
        deleteBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Delete Booking?")
                                .setMessage("Are you sure you want to delete a booking ?")
                                .setPositiveButton(
                                        android.R.string.yes,
                                        (dialog, which) -> {
                                            try {
                                                int pos = holder.getAdapterPosition();
                                                String deleted =
                                                        bookingViewModel.deleteBooking(
                                                                holder.itemView.getContext().getString(R.string.api_key),
                                                                booking.getBookingID());
                                                if (deleted.equals("204")){
                                                    bookings.remove(pos);
//                                                    notifyItemRemoved(pos);
                                                    notifyDataSetChanged();
//                                                    sendNotification(view);


                                                    Toast.makeText(view.getContext(), "Booking Deleted", Toast.LENGTH_LONG).show();
                                                } else if (deleted.equals("409")){
                                                    Toast.makeText(view.getContext(), "A booking cannot be deleted if it has any associated COVID tests.", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    Toast.makeText(view.getContext(), "Failed to delete", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (IOException | JSONException e) {
                                                e.printStackTrace();
                                            }

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