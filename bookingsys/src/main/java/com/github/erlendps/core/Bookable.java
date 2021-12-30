package com.github.erlendps.core;

import com.github.erlendps.util.StringValidation;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;

/**
 * Bookable class, is the object that you can book, e.g a cabin/boat.
 */
@Embeddable
@Entity
public class Bookable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private Group group;
  @OneToMany
  private final Map<LocalDate, AbstractBooking> bookingMap = new HashMap<>();

  protected Bookable() {}

  /**
   * Constructor for bookable.
   *
   * @param name name of the object
   * @param group group it belongs to
   *
   * @throws IllegalArgumentException if name does not match regex or if group is null
   */
  public Bookable(String name, Group group) {
    if (!StringValidation.matchName(name)) {
      throw new IllegalArgumentException("Name for bookable is not valid.");
    }
    if (group == null) {
      throw new IllegalArgumentException("Bookable is not connected to any group.");
    }
    this.name = name;
    this.group = group;
  }

  public final String getName() {
    return name;
  }
  public void setName(String name) {
    if (!StringValidation.matchName(name)) {
      throw new IllegalArgumentException("Name for bookable is not valid.");
    }
    this.name = name;
  }

  public Group getGroup() {
    return group;
  }

  public void addBooking(AbstractBooking booking) {
    if (booking == null) {
      throw new IllegalArgumentException("Booking is null.");
    }
    if (isAvailable(booking.getStartDate(), booking.getEndDate())) {
      this.put(booking);
      group.notifyListeners(booking, Message.NEW_BOOKING);
    } else throw new IllegalStateException("Bookable is not available");
  }

  public void put(AbstractBooking booking) {
    if (booking instanceof SingleDayBooking res) {
      bookingMap.put(res.getDate(), booking);
    }
    else if (booking instanceof MultipleDayBooking res) {
      Iterator<LocalDate> bookingIterator = res.iterator();
      while (bookingIterator.hasNext()) {
        LocalDate currentDate = bookingIterator.next();
        bookingMap.put(currentDate, booking);
      }
    } else {
      throw new IllegalArgumentException("Not a valid input object.");
    }
  }

  public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
    Iterator<LocalDate> dateIterator = startDate.datesUntil(endDate.plusDays(1)).iterator();
    while (dateIterator.hasNext()) {
      if (bookingMap.containsKey(dateIterator.next())) {
        return false;
      }
    }
    return true;
  }

  public boolean isAvailable(LocalDate start) {
    return isAvailable(start, start);
  }

  public void removeBooking(AbstractBooking booking) {
    if (booking == null) {
      throw new IllegalArgumentException("Booking is null");
    }
    if (booking instanceof SingleDayBooking res) {
      if (bookingMap.get(res.getDate()) == res) {
        bookingMap.remove(res.getDate());
      }
    }
    else if (booking instanceof MultipleDayBooking res) {
      Iterator<LocalDate> dateIterator = res.iterator();
      while (dateIterator.hasNext()) {
        if (bookingMap.get(dateIterator.next()) != res) {
          throw new IllegalArgumentException("Error occurred. Specified booking does not match" +
                  "with this bookable's bookingMap.");
        }
      }
      dateIterator = res.iterator();
      while (dateIterator.hasNext()) {
        bookingMap.remove(dateIterator.next());
      }
    }
    group.notifyListeners(booking, Message.REMOVED_BOOKING);
  }
}