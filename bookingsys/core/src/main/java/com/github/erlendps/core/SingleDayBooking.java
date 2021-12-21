package com.github.erlendps.core;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class SingleDayBooking extends AbstractBooking {

  public SingleDayBooking(User user, Bookable bookable, LocalDate date) {
    super(user, bookable, date, date);
  }

  protected SingleDayBooking() {}

  public LocalDate getDate() {
    return super.startDate;
  }
}