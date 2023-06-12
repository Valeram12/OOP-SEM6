package com.Valeram.travelagencymanagement.controller.dto.tour;

import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMemberWithouttoursDto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class tourResponseDto {

  private Long id;
  private String departureFrom;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private List<customerMemberWithouttoursDto> crewMembers;

}
