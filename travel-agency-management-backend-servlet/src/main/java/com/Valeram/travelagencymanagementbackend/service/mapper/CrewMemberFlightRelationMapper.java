package com.Valeram.travelagencymanagementbackend.service.mapper;

import com.Valeram.travelagencymanagementbackend.common.dto.relation.customerMemberFlightRelationDto;
import com.Valeram.travelagencymanagementbackend.model.customerMembertourRelation;
import org.mapstruct.Mapper;

@Mapper
public abstract class CrewMemberFlightRelationMapper {

  public abstract customerMemberFlightRelationDto toDto(customerMembertourRelation entity);

  public abstract customerMembertourRelation toEntity(customerMemberFlightRelationDto dto);

}
