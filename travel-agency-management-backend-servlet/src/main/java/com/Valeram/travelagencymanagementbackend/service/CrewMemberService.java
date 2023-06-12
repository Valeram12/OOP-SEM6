package com.Valeram.travelagencymanagementbackend.service;

import com.Valeram.travelagencymanagementbackend.common.dto.customermember.customerMemberDto;

import java.util.List;
import java.util.Optional;

public interface CrewMemberService {

  List<customerMemberDto> findAll();

  List<customerMemberDto> findAllByFlightId(Long flightId);

  Optional<customerMemberDto> findById(Long id);

  customerMemberDto create(customerMemberDto toCreate);

  customerMemberDto update(customerMemberDto toUpdate);

  boolean delete(Long id);

}
