package com.Valeram.travelagencymanagementbackend.dao;

import com.Valeram.travelagencymanagementbackend.model.customerMembertourRelation;

public interface customerMembertourRelationDao {

  customerMembertourRelation insert(customerMembertourRelation relation);

  boolean existsBycustomerMemberAndtourIds(Long customerMemberId, Long tourId);

  boolean deleteBycustomerMemberAndtourIds(Long customerMemberId, Long tourId);

}
