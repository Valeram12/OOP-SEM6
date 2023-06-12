package com.Valeram.travelagencymanagementbackend.service.impl;

import org.mapstruct.factory.Mappers;

public class DefaultCustomerMemberTourRelationService implements CustomerMemberTourRelationService {

  private final CustomerMemberTourRelationDao relationDao;
  private final CustomerMemberTourRelationMapper mapper;

  public DefaultCustomerMemberTourRelationService() {
    this(new DefaultCustomerMemberTourRelationDao());
  }

  public DefaultCustomerMemberTourRelationService(CustomerMemberTourRelationDao relationDao) {
    this.relationDao = relationDao;
    this.mapper = Mappers.getMapper(CustomerMemberTourRelationMapper.class);
  }

  @Override
  public boolean create(CustomerMemberTourRelationDto toCreate) {
    if (exists(toCreate)) {
      return false;
    }

    relationDao.insert(mapper.toEntity(toCreate));

    return true;
  }

  @Override
  public boolean exists(CustomerMemberTourRelationDto relation) {
    return relationDao.existsByCustomerMemberAndTourIds(relation.getCustomerMemberId(), relation.getTourId());
  }

  @Override
  public boolean delete(CustomerMemberTourRelationDto relation) {
    return relationDao.deleteByCustomerMemberAndTourIds(relation.getCustomerMemberId(), relation.getTourId());
  }

}
