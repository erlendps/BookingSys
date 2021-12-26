package com.github.erlendps.rest;

import com.github.erlendps.core.Bookable;
import com.github.erlendps.core.BookableType;
import com.github.erlendps.core.Group;
import com.github.erlendps.core.User;
import com.github.erlendps.email.EmailSenderHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class Controller {

  @GetMapping("/sendmail")
  public String sendMail() {
    User user = new User("Erlend", "Skaaden", "erlendskaaden@gmail.com");
    User user2 = new User("Berlend", "Baaden", "hiimmadturtle@gmail.com");
    Group group = new Group("test");
    group.addMember(user);
    group.addMember(user2);
    Bookable bookable = new Bookable("TestBookable", BookableType.BOAT, group);
    user.makeBooking(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), bookable);
    return "Sent successfully";
  }
}
