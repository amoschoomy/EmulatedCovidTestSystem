package models.BookingPackage;

import android.util.Log;

import java.util.HashMap;
import java.util.Stack;

public class BookingCaretaker {

  private static BookingCaretaker instance;
  HashMap<String, Stack<Booking.BookingMemento>> bookingMementos = new HashMap<>();

  private BookingCaretaker() {}

  public static BookingCaretaker getInstance() {

    if (instance == null) {
      instance = new BookingCaretaker();
    }
    return instance;
  }

  public void save(TestingOnSiteBooking booking) {

    if (bookingMementos.containsKey(booking.getBookingID())) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking.getBookingID());
      mementos.push(booking.save());

    } else {
      Stack<TestingOnSiteBooking.BookingMemento> mementos = new Stack<>();
      mementos.push(booking.save());
      bookingMementos.put(booking.getBookingID(), mementos);
    }
  }

  public void revert(TestingOnSiteBooking booking) throws Exception {
    if (bookingMementos.containsKey(booking.getBookingID())) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking.getBookingID());
      if (!mementos.empty()) {
        booking.undo(mementos.pop());
      } else {
        throw new Exception("No undo");
      }
    } else {
      Log.d("myTag", "no stack?!");
      throw new Exception("No undo");
    }
  }
}
