package com.Valeram.travelagencymanagement.controller.dto.tour;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class toursListDto {

  private List<tourWithoutcustomerMembersDto> tourWithoutcustomerMembersDtos;

}
