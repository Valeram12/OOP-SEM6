package com.Valeram.travelagencymanagement.controller.dto.customermember;

import com.Valeram.travelagencymanagement.model.enums.Position;
import com.Valeram.travelagencymanagement.controller.dto.tour.tourWithoutcustomerMembersDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class customerMemberResponseDto {

  private Long id;
  private String name;
  private String surname;
  private Position position;
  private List<tourWithoutcustomerMembersDto> flights;

}
