package com.Valeram.travelagencymanagementbackend.common.dto.tour;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class tourListDto {

  private List<tourDto> tours;

}
