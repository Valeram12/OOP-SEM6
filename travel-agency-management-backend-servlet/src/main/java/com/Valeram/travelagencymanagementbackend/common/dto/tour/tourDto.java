package com.Valeram.travelagencymanagementbackend.common.dto.tour;

import com.Valeram.travelagencymanagementbackend.common.dto.customermember.customerMemberDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class tourDto {

  private Long id;
  private String departureFrom;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private List<customerMemberDto> customerMemberDtos;

  public void createCrewMembersIfAbsent() {
    if (customerMemberDtos == null) {
      customerMemberDtos = new ArrayList<>();
    }
  }

}
