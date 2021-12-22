package com.github.erlendps.util;

import com.github.erlendps.core.*;

import java.time.LocalDate;

public class StringBuilding {

  public static String toWeekday(LocalDate date) {
    String firstLetter = date.getDayOfWeek().toString().substring(0, 1);
    String rest = date.getDayOfWeek().toString().substring(1).toLowerCase();
    return firstLetter + rest;
  }

  private static String buildStartString(User user, Group group) {
    return "Hello " + user.getFirstName() + " there has been a change in group " +
            group.getName() + "!\n";
  }

  public static String singleDayStringNonBookersString(User nonBooker, SingleDayBooking booking) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildStartString(nonBooker, booking.getGroup()));
    sb.append(booking.getBookable().getName()).append(" has been booked ");
    sb.append("on ").append(toWeekday(booking.getDate())).append(" ").append(booking.getDate());
    sb.append(" by ").append(booking.getUser().getFirstName()).append(".");
    sb.append("\nView the changes here:\n");
    sb.append("\nHave a nice day!");
    return sb.toString();
  }

  public static String singleDayStringBookerString(SingleDayBooking booking) {
    StringBuilder sb = new StringBuilder();
    sb.append("Hello ").append(booking.getUser().getFirstName()).append("!\n");
    sb.append("You have booked ").append(booking.getBookable().getName());
    sb.append(" on ").append(toWeekday(booking.getDate())).append(" ").append(booking.getDate());
    sb.append(" in group ").append(booking.getGroup().getName()).append(".");
    sb.append("\nView the changes here:\n");
    sb.append("\nHave a nice day!");
    return sb.toString();
  }

  public static String multipleDayStringNonBookersString(User nonBooker,
                                                        MultipleDayBooking booking) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildStartString(nonBooker, booking.getGroup()));
    sb.append(booking.getBookable().getName()).append(" has been booked ");
    sb.append("from ").append(toWeekday(booking.getStartDate())).append(" ").append(booking.getStartDate());
    sb.append(" to ").append(toWeekday(booking.getEndDate())).append(" ").append(booking.getEndDate());
    sb.append(" by ").append(booking.getUser().getFirstName()).append(".");
    sb.append("\nView the changes here:\n");
    sb.append("\nHave a nice day!");
    return sb.toString();
  }

  public static String multipleDayStringBookerString(MultipleDayBooking booking) {
    StringBuilder sb = new StringBuilder();
    sb.append("Hello ").append(booking.getUser().getFirstName()).append("!\n");
    sb.append("You have booked ").append(booking.getBookable().getName());
    sb.append(" from ").append(toWeekday(booking.getStartDate())).append(" ").append(booking.getStartDate());
    sb.append(" to ").append(toWeekday(booking.getEndDate())).append(" ").append(booking.getEndDate());
    sb.append(" in group ").append(booking.getGroup().getName()).append(".");
    sb.append("\nView the changes here:\n");
    sb.append("\nHave a nice day!");
    return sb.toString();
  }
}
