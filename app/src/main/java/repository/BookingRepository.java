package repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

import models.BookingPackage.Booking;

public class BookingRepository {

  public BookingRepository(Application application) {}

  public LiveData<ArrayList<Booking>> getAllBookings() {
    // api call here
    return new LiveData<ArrayList<Booking>>() {
      @Override
      public void observe(
          @NonNull LifecycleOwner owner, @NonNull Observer<? super ArrayList<Booking>> observer) {
        super.observe(owner, observer);
      }
    };
  }
}
