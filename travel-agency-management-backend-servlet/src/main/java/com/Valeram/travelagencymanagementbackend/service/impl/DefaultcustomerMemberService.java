package com.Valeram.travelagencymanagementbackend.service.impl;

import com.Valeram.travelagencymanagementbackend.dao.customerMemberDao;
import com.Valeram.travelagencymanagementbackend.service.mapper.CrewMemberMapper;
import com.Valeram.travelagencymanagementbackend.common.dto.customermember.customerMemberDto;
import com.Valeram.travelagencymanagementbackend.dao.impl.DefaultcustomerMemberDao;
import com.Valeram.travelagencymanagementbackend.model.customerMember;
import com.Valeram.travelagencymanagementbackend.service.CrewMemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;

public class DefaultCustomerMemberService implements CustomerMemberService {

    private final CustomerMemberDao customerMemberDao;
    private final CustomerMemberMapper mapper;

    public DefaultCustomerMemberService() {
        this(new DefaultCustomerMemberDao());
    }

    public DefaultCustomerMemberService(CustomerMemberDao customerMemberDao) {
        this.customerMemberDao = customerMemberDao;
        this.mapper = Mappers.getMapper(CustomerMemberMapper.class);
    }

    @Override
    public List<CustomerMemberDto> findAll() {
        return customerMemberDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerMemberDto> findAllByTourId(Long tourId) {
        return customerMemberDao.findAllByTourId(tourId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerMemberDto> findById(Long id) {
        return customerMemberDao.findById(id).map(mapper::toDto);
    }

    @Override
    public CustomerMemberDto create(CustomerMemberDto toCreate) {
        CustomerMember entity = mapper.toEntity(toCreate);

        return mapper.toDto(customerMemberDao.insert(entity));
    }

    @Override
    public CustomerMemberDto update(CustomerMemberDto toUpdate) {
        CustomerMember entity = mapper.toEntity(toUpdate);

        return mapper.toDto(customerMemberDao.update(entity));
    }

    @Override
    public boolean delete(Long id) {
        return customerMemberDao.delete(id);
    }

}
