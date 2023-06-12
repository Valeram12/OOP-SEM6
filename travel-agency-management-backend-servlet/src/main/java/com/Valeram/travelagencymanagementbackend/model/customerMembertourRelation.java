package com.Valeram.travelagencymanagementbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class customerMembertourRelation {

  private Long id;
  private Long customerMemberId;
  private Long tourId;

  public customerMembertourRelation(Long customerMemberId, Long tourId) {
    this.customerMemberId = customerMemberId;
    this.tourId = tourId;
  }

}
