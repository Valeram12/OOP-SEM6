package com.Valeram.travelagencymanagementbackend.service.impl;

import com.Valeram.travelagencymanagementbackend.dao.tourDao;
import com.Valeram.travelagencymanagementbackend.common.dto.tour.tourDto;
import com.Valeram.travelagencymanagementbackend.dao.impl.DefaulttourDao;
import com.Valeram.travelagencymanagementbackend.model.tour;
import com.Valeram.travelagencymanagementbackend.service.tourService;
import com.Valeram.travelagencymanagementbackend.service.mapper.FlightMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class DefaultTourService implements tourService {

  private final tourDao tourDao;
  private final FlightMapper mapper;

  public DefaultTourService() {
    this(new DefaulttourDao());
  }

  public DefaultTourService(tourDao tourDao) {
    this.tourDao = tourDao;
    this.mapper = Mappers.getMapper(FlightMapper.class);
  }

  @Override
  public List<tourDto> findAll() {
    return tourDao.findAll().stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<tourDto> findAllByCrewMemberId(Long crewMemberId) {
    return tourDao.findAllBycustomerMemberId(crewMemberId).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<tourDto> findById(Long id) {
    return tourDao.findById(id).map(mapper::toDto);
  }

  @Override
  public tourDto create(tourDto toCreate) {
    tour entity = mapper.toEntity(toCreate);

    return mapper.toDto(tourDao.insert(entity));
  }

  @Override
  public tourDto update(tourDto toUpdate) {
    tour entity = mapper.toEntity(toUpdate);

    return mapper.toDto(tourDao.update(entity));
  }

  @Override
  public boolean delete(Long id) {
    return tourDao.delete(id);
  }

}
