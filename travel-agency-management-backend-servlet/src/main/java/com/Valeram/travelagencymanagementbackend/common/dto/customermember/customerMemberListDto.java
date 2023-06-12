package com.Valeram.travelagencymanagementbackend.common.dto.customermember;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class customerMemberListDto {

  private List<customerMemberDto> customerMemberDtoList;

}
