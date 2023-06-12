package com.Valeram.travelagencymanagementbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class customerMember {

  private Long id;
  private String name;
  private String surname;
  private Position position;

}
