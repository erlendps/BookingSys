package com.github.erlendps.core;

public class StringValidation {

  /**
   * Helper method that matches a name to regex.
   *
   * @param name name to match
   *
   * @return true if name matches, false otherwise
   */
  public static boolean matchName(final String name) {
    String regex = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØ"
            + "ÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{2,40}$";
    return name.matches(regex);
  }

  /**
   * Checks email format, not if it actually exists.
   *
   * @param email email string to check
   *
   * @return true if email matches regex, false otherwise
   */
  public static boolean matchEmail(final String email) {
    String regex = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    return email.matches(regex);
  }
}
