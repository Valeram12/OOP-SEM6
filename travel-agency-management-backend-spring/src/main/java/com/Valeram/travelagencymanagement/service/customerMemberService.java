package com.Valeram.travelagencymanagement.service;

import com.Valeram.travelagencymanagement.repository.customerMemberRepository;
import com.Valeram.travelagencymanagement.repository.FlightRepository;
import com.Valeram.travelagencymanagement.model.Flight;
import com.Valeram.travelagencymanagement.model.customerMember;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class customerMemberService {

    private final CustomerMemberRepository customerMemberRepository;
    private final TourRepository tourRepository;

    @Autowired
    public CustomerMemberService(CustomerMemberRepository customerMemberRepository,
                                 TourRepository tourRepository) {
        this.customerMemberRepository = customerMemberRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional
    public CustomerMember saveCustomerMember(CustomerMember customerMember) {
        return customerMemberRepository.save(customerMember);
    }

    public Optional<CustomerMember> findCustomerMemberById(Long id) {
        return customerMemberRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return customerMemberRepository.existsById(id);
    }

    public List<CustomerMember> findAll() {
        return customerMemberRepository.findAll();
    }

    public List<Tour> findToursOfCustomerMember(CustomerMember customerMember) {
        return tourRepository.findToursByCustomerMembers(customerMember);
    }

    @Transactional
    public CustomerMember updateCustomerMember(CustomerMember toUpdate) {
        return customerMemberRepository.save(toUpdate);
    }

    @Transactional
    public boolean deleteCustomerMemberById(Long id) {
        return customerMemberRepository.deleteCustomerMemberById(id) > 0L;
    }
}


}
