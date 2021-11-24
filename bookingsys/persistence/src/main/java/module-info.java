module bookingsys.persistence {
  requires transitive bookingsys.core;
  requires transitive com.fasterxml.jackson.databind;

  exports com.github.erlendps.persistence;
  exports com.github.erlendps.persistence.internal;
}