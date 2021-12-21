package com.github.erlendps.core;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Entity
public class MultipleDayBooking extends AbstractBooking implements Iterable<LocalDate> {

  public MultipleDayBooking(User user, Bookable bookable, LocalDate startDate, LocalDate endDate) {
    super(user, bookable, startDate, endDate);
  }

  protected MultipleDayBooking() {}

  public LocalDate getStartDate() {
    return super.startDate;
  }

  public LocalDate getEndDate() {
    return super.endDate;
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<LocalDate> iterator() {
    return getStartDate().datesUntil(getEndDate().plusDays(1)).iterator();
  }
}
