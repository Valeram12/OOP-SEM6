package com.Valeram.travelagencymanagement.repository;

import com.Valeram.travelagencymanagement.model.customerMember;
import com.Valeram.travelagencymanagement.model.Flight;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface tourRepository extends JpaRepository<tour, Long> {

  long deletetourById(Long id);

  List<Flight> findtourtsBycustomerMembers(customerMember customerMember);

}
