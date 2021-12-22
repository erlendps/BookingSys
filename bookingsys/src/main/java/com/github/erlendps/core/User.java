package com.github.erlendps.core;

import com.github.erlendps.util.DateChecker;
import com.github.erlendps.util.StringBuilding;
import com.github.erlendps.util.StringValidation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

/**
 * User class
 */
@Embeddable
@Entity
public class User implements GroupListener {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String firstName;
  private String lastName;
  private String email;
  @OneToMany
  private Collection<Group> groups = new HashSet<>();
  @OneToMany
  private Collection<AbstractBooking> bookings = new HashSet<>();

  /**
   * Constructor for User class.
   *
   * @param firstName User firstName
   * @param lastName User lastName
   * @param email User email
   */
  public User(final String firstName, final String lastName, final String email) {
    validateName(firstName.strip(), lastName.strip());
    validateEmail(email.strip());
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  protected User() {}

  /**
   * Helper method to validate name.
   *
   * @param firstName firstname
   * @param lastName lastname
   *
   * @throws IllegalArgumentException if firstName or lastName is invalid
   */
  private void validateName(final String firstName, final String lastName) {
    if (!StringValidation.matchName(firstName)) {
      throw new IllegalArgumentException("First name is not a valid name!");
    }
    if (!StringValidation.matchName(lastName)) {
      throw new IllegalArgumentException("Last name is not a valid name!");
    }
  }

  /**
   * Helper method that validates email.
   *
   * @param email email to check
   *
   * @throws IllegalArgumentException if the email is not valid
   */
  private void validateEmail(String email) {
    if (!StringValidation.matchEmail(email)) {
      throw new IllegalArgumentException("Email is not valid");
    }
  }

  public Collection<AbstractBooking> getBookings() {
    return new HashSet<>(bookings);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    validateEmail(email);
    this.email = email;
  }

  /**
   * Adds a booking on the bookable object.
   *
   * @param startDate the start date
   * @param endDate the end date
   * @param bookable the object to book
   */
  public void makeBooking(LocalDate startDate, LocalDate endDate, Bookable bookable) {
    if (DateChecker.isBeforeNow(startDate)) {
      throw new IllegalStateException("Start date is before now.");
    }
    if (!DateChecker.isChronological(startDate, endDate)) {
      throw new IllegalStateException("Start date is after end date");
    }
    if (bookable == null) {
      throw new IllegalArgumentException("Bookable is null");
    }
    AbstractBooking booking;
    if (DateChecker.isSameDate(startDate, endDate)) {
      booking = new SingleDayBooking(this, bookable, startDate);
    }
    else {
      booking = new MultipleDayBooking(this, bookable, startDate, endDate);
    }
    bookings.add(booking);
    bookable.addBooking(booking);
  }


  /**
   * Receives the notification.
   *
   * @param bookable
   * @param start
   * @param end
   * @param msg
   */
  @Override
  public void receiveNotification(User user, Bookable bookable,
                                  LocalDate start, LocalDate end, Message msg) {
    
  }
}


