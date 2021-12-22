package com.github.erlendps.core;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Embeddable
@Entity
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @ElementCollection
  private Collection<User> members = new ArrayList<>();
  private String name;

  public void notifyListeners(User user, Bookable bookable,
                              LocalDate start, LocalDate end, Message msg) {
    members.forEach(member -> member.receiveNotification(user, bookable, start, end, msg));
  }

  public String getName() {
    return name;
  }
}
