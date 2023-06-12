package com.Valeram.travelagencymanagementbackend.dao;

import com.Valeram.travelagencymanagementbackend.model.customerMember;

import java.util.List;
import java.util.Optional;

public interface customerMemberDao {

  List<customerMember> findAll();

  List<customerMember> findAllByFlightId(Long flightId);

  Optional<customerMember> findById(Long id);

  customerMember insert(customerMember toInsert);

  customerMember update(customerMember toUpdate);

  boolean delete(Long id);

}
