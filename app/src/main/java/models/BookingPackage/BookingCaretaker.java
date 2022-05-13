package models.BookingPackage;

import java.util.HashMap;
import java.util.Stack;

import models.ExceptionPackage.InvalidUndoException;

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

  public void save(Booking booking) {

    if (bookingMementos.containsKey(booking.getBookingID())) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking.getBookingID());
      mementos.push(booking.save());

    } else {
      Stack<TestingOnSiteBooking.BookingMemento> mementos = new Stack<>();
      mementos.push(booking.save());
      bookingMementos.put(booking.getBookingID(), mementos);
    }
  }

  public void revert(Booking booking) throws Exception {
    if (bookingMementos.containsKey(booking.getBookingID())) {
      Stack<Booking.BookingMemento> mementos = bookingMementos.get(booking.getBookingID());
      if (!mementos.empty()) {
        booking.undo(mementos.pop());
      } else {
        throw new InvalidUndoException("No undo");
      }
    } else {
      throw new InvalidUndoException("No undo");
    }
  }
}
