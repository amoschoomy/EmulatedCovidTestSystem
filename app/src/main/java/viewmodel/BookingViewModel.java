package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import models.BookingPackage.Booking;
import repository.BookingRepository;

public class BookingViewModel extends AndroidViewModel {

  private final BookingRepository bookingRepository;
  private final LiveData<ArrayList<Booking>> allBookings;

  public BookingViewModel(@NonNull Application application) {
    super(application);
    bookingRepository = new BookingRepository(application);
    allBookings = bookingRepository.getAllBookings();
  }
}
