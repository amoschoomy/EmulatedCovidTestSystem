package models.BookingPackage;

import java.util.HashMap;
import java.util.Stack;

public class BookingCaretaker {

  private static BookingCaretaker instance;
  HashMap<Booking, Stack<Booking.BookingMemento>> bookingMementos = new HashMap<>();

  private BookingCaretaker() {}

  public static BookingCaretaker getInstance() {

    if (instance == null) {
      instance = new BookingCaretaker();
    }
    return instance;
  }

  public void save(TestingOnSiteBooking booking) {

    if (bookingMementos.containsKey(booking)) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking);
      mementos.push(booking.save());
    } else {
      Stack<TestingOnSiteBooking.BookingMemento> mementos = new Stack<>();
      mementos.push(booking.save());
      bookingMementos.put(booking, mementos);
    }
  }

  public void revert(TestingOnSiteBooking booking) throws Exception {
    if (bookingMementos.containsKey(booking)) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking);
      if (!mementos.empty()) {
        booking.undo(mementos.pop());
      } else {
        throw new Exception("No undo");
      }
    } else {
      throw new Exception("No undo");
    }
  }
}
