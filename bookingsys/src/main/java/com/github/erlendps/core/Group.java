package com.github.erlendps.core;

import com.github.erlendps.util.StringValidation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Embeddable
@Entity
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @ElementCollection
  private final Collection<User> members = new HashSet<>();
  @ElementCollection
  private final Collection<Bookable> bookables = new HashSet<>();
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

  public void removeMember(User user) {
    members.remove(user);
  }

  public void addBookable(Bookable bookable) {
    if (bookable == null) {
      throw new IllegalArgumentException("Bookable is null.");
    }
    bookables.add(bookable);
  }

  public void removeBookable(Bookable bookable) {
    bookables.remove(bookable);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name is null.");
    }
    if (StringValidation.matchName(name)) {
      this.name = name;
    }
    else throw new IllegalArgumentException("Not a valid name");
  }
}
