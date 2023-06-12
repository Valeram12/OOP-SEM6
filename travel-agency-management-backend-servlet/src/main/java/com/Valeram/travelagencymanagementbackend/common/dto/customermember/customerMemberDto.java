package com.Valeram.travelagencymanagementbackend.common.dto.customermember;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.Valeram.travelagencymanagementbackend.common.dto.tour.tourDto;
import com.Valeram.travelagencymanagementbackend.model.Position;

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
public class customerMemberDto {

  private Long id;
  private String name;
  private String surname;
  private Position position;
  private List<tourDto> flights;

  public void createtoursIfAbsent() {
    if (flights == null) {
      flights = new ArrayList<>();
    }
  }

}
