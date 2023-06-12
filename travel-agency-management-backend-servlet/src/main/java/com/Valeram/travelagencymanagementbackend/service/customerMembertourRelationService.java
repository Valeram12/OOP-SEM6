package com.Valeram.travelagencymanagementbackend.service;

import com.Valeram.travelagencymanagementbackend.common.dto.relation.customerMemberFlightRelationDto;

public interface customerMembertourRelationService {

  boolean create(customerMemberFlightRelationDto toCreate);

  boolean exists(customerMemberFlightRelationDto relation);

  boolean delete(customerMemberFlightRelationDto relation);

}
