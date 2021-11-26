package com.github.erlendps.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

/**
 * User class
 */
public class User implements GroupListener {
  private final String firstName;
  private final String lastName;
  private String email;
  private Collection<Group> groups = new HashSet<>();
  private Collection<Booking> bookings = new HashSet<>();

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

  /**
   * Helper method to validate name.
   *
   * @param firstName firstname
   * @param lastName lastname
   *
   * @throws IllegalArgumentException if firstName or lastName is invalid
   */
  private void validateName(final String firstName, final String lastName) {
    if (!matchName(firstName)) {
      throw new IllegalArgumentException("First name is not a valid name!");
    }
    if (!matchName(lastName)) {
      throw new IllegalArgumentException("Last name is not a valid name!");
    }
  }

  /**
   * Helper method that matches a name to regex.
   *
   * @param name name to match
   *
   * @return true if name matches, false otherwise
   */
  private boolean matchName(final String name) {
    String regex = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØ"
            + "ÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{2,40}$";
    return name.matches(regex);
  }

  /**
   * Helper method that validates email.
   *
   * @param email email to check
   *
   * @throws IllegalArgumentException if the email is not valid
   */
  private void validateEmail(String email) {
    // using unicode
    String regex = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    if (!email.matches(regex)) {
      throw new IllegalArgumentException("Email is not valid");
    }
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
  public void addBooking(LocalDate startDate, LocalDate endDate, Bookable bookable) {

  }
  /**
   * Receives the notification.
   *
   * @param before the state before the change
   * @param after  the state after the change
   */
  @Override
  public void receiveNotification(Object before, Object after) {
    
  }
}

