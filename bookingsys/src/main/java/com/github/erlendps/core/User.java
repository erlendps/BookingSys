package com.github.erlendps.core;

import com.github.erlendps.email.EmailSenderHelper;
import com.github.erlendps.util.DateChecker;
import com.github.erlendps.util.StringBuilding;
import com.github.erlendps.util.StringValidation;
import org.springframework.mail.MailException;

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

  public void makeNewBookable(String name, Group group) {
    if (group == null || !groups.contains(group)) {
      throw new IllegalStateException("You are not in this group.");
    }
    Bookable bookable = new Bookable(name, group);
    group.addBookable(bookable);
  }

  public void deleteBookable(Group group, Bookable bookable) {
    if (group == null || !groups.contains(group)) {
      throw new IllegalStateException("You are not in this group.");
    }
    group.removeBookable(bookable);
  }

  /**
   * Receives the notification.
   *
   * @param booking the booking
   * @param msg Enum message
   */
  @Override
  public void receiveNotification(AbstractBooking booking, Message msg) {
    String text = null;
    if (msg == Message.NEW_BOOKING) {
      if (booking instanceof SingleDayBooking res) {
        if (this == res.getUser()) {
          text = StringBuilding.singleDayBookerString(res);
        } else text = StringBuilding.singleDayNonBookersString(this, res);
      } else if (booking instanceof MultipleDayBooking res) {
        if (this == res.getUser()) {
          text = StringBuilding.multipleDayBookerString(res);
        } else text = StringBuilding.multipleDayNonBookersString(this, res);
      }
    } else if (msg == Message.REMOVED_BOOKING) {
      if (booking instanceof SingleDayBooking res) {
        if (this == res.getUser()) {
          text = StringBuilding.removeSingleDayBookerString(res);
        } else text = StringBuilding.removeSingleDayNonBookersString(this, res);
      } else if (booking instanceof MultipleDayBooking res) {
        if (this == res.getUser()) {
        text = StringBuilding.removeMultipleDayBookerString(res);
        } else text = StringBuilding.removeMultipleDayNonBookersString(this, res);
      }
    }
    if (text == null) throw new IllegalStateException("Not a valid booking");
    try {
      EmailSenderHelper.sendSimpleMail(getEmail(), "Change in " + booking.getGroup().getName(), text);
    } catch (MailException e) {
      throw new IllegalStateException("Error sending mail, email probably does not exist.");
    }
  }
}


