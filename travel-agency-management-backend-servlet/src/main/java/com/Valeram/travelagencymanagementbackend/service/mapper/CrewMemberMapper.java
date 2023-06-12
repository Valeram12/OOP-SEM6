package com.Valeram.travelagencymanagementbackend.service.mapper;

import com.Valeram.travelagencymanagementbackend.common.dto.customermember.customerMemberDto;
import com.Valeram.travelagencymanagementbackend.model.customerMember;
import org.mapstruct.Mapper;

@Mapper
public abstract class CrewMemberMapper {

  public abstract customerMemberDto toDto(customerMember entity);

  public abstract customerMember toEntity(customerMemberDto dto);

}
