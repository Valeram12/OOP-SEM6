package com.Valeram.travelagencymanagementbackend.service;

import com.Valeram.travelagencymanagementbackend.common.dto.tour.tourDto;

import java.util.List;
import java.util.Optional;

public interface tourService {

  List<tourDto> findAll();

  List<tourDto> findAllByCrewMemberId(Long crewMemberId);

  Optional<tourDto> findById(Long id);

  tourDto create(tourDto toCreate);

  tourDto update(tourDto toUpdate);

  boolean delete(Long id);

}
