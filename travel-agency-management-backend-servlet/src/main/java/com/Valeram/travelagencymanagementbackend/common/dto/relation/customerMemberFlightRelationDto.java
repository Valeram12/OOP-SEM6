package com.Valeram.travelagencymanagementbackend.common.dto.relation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class customerMemberFlightRelationDto {

  private Long customerMemberId;
  private Long flightId;

}
