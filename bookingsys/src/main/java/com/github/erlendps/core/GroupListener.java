package com.github.erlendps.core;

import java.time.LocalDate;

public interface GroupListener {

  /**
   * Receives the notification.
   *
   * @param before the state before the change
   * @param after the state after the change
   */
  void receiveNotification(User user, Bookable bookable,
                           LocalDate start, LocalDate end, Message msg);

}
