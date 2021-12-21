package com.github.erlendps.core.util;

import java.time.LocalDate;

public class DateChecker {

  public static boolean isChronological(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      return false;
    }
    return start.isEqual(end) || start.isBefore(end);
  }

  public static boolean isBeforeNow(LocalDate start) {
    if (start == null) {
      return false;
    }
    return start.isBefore(LocalDate.now());
  }

  public static boolean isSameDate(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      return false;
    }
    return start.isEqual(end);
  }
}
