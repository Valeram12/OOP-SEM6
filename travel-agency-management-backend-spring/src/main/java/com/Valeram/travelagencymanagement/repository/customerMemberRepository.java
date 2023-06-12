package com.Valeram.travelagencymanagement.repository;

import com.Valeram.travelagencymanagement.model.customerMember;
import com.Valeram.travelagencymanagement.model.Flight;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customerMemberRepository extends JpaRepository<customerMember, Long> {

  long deletecustomerMemberById(Long id);

  List<customerMember> findcustomerMembersBytours(tour tour);

}
