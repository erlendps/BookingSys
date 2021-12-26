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

  public Group(String name) {
    this.name = name;
  }

  protected Group() {}

  public void notifyListeners(AbstractBooking booking, Message msg) {
    members.forEach(member -> member.receiveNotification(booking, msg));
  }

  public void addMember(User user) {
    members.add(user);
  }

  public String getName() {
    return name;
  }
}
