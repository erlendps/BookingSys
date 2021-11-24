module bookingsys.rest {
  requires com.fasterxml.jackson.databind;

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;

  requires bookingsys.core;
  requires bookingsys.persistence;

  opens com.github.erlendps.rest to spring.beans, spring.context, spring.web;
}