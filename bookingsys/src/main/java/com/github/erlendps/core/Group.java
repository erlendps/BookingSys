package com.github.erlendps.core;

import javax.persistence.*;
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
}
