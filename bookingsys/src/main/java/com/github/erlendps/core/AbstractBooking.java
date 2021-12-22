package com.github.erlendps.core;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class AbstractBooking {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;
  @OneToOne
  protected Bookable bookable;
  @OneToOne
  protected User user;
  protected LocalDate startDate;
  protected LocalDate endDate;

  public AbstractBooking(User user, Bookable bookable, LocalDate startDate, LocalDate endDate) {
    if (user == null)
      throw new IllegalArgumentException("User is null");
    if (bookable == null)
      throw new IllegalArgumentException("Bookable is null");
    if (startDate == null || endDate == null)
      throw new IllegalArgumentException("Start date or end date is null.");
    this.user = user;
    this.bookable = bookable;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  protected AbstractBooking() {}
}
