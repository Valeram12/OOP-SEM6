package com.Valeram.travelagencymanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tourWithoutcustomerMembersDtos")
public class Tour {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departure_from", nullable = false)
  private String departureFrom;

  @Column(name = "destination", nullable = false)
  private String destination;

  @Column(name = "departure_time", nullable = false)
  private LocalDateTime departureTime;

  @Column(name = "arrival_time", nullable = false)
  private LocalDateTime arrivalTime;

  @ManyToMany(mappedBy = "tours")
  private Set<CustomerMember> customerMembers = new HashSet<>();

  @PreRemove
  private void removeTourFromCustomerMembers() {
    for (CustomerMember customerMember : customerMembers) {
      customerMember.getTours().remove(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Tour tour = (Tour) o;

    return Objects.equals(id, tour.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
