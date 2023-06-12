package com.Valeram.travelagencymanagementbackend.service.mapper;

import com.Valeram.travelagencymanagementbackend.common.dto.tour.tourDto;
import com.Valeram.travelagencymanagementbackend.model.tour;
import org.mapstruct.Mapper;

@Mapper
public abstract class FlightMapper {

  public abstract tourDto toDto(tour entity);

  public abstract tour toEntity(tourDto dto);

}
