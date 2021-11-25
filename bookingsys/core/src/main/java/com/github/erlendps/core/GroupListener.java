package com.github.erlendps.core;

public interface GroupListener {

  /**
   * Receives the notification.
   *
   * @param before the state before the change
   * @param after the state after the change
   */
  void receiveNotification(Object before, Object after);

}
