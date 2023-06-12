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
public class tourService {

    private final TourRepository tourRepository;
    private final CustomerMemberRepository customerMemberRepository;

    @Autowired
    public TourService(TourRepository tourRepository,
                       CustomerMemberRepository customerMemberRepository) {
        this.tourRepository = tourRepository;
        this.customerMemberRepository = customerMemberRepository;
    }

    @Transactional
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Optional<Tour> findTourById(Long id) {
        return tourRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return tourRepository.existsById(id);
    }

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public List<CustomerMember> findCustomerMembersOfTour(Tour tour) {
        return customerMemberRepository.findCustomerMembersByTours(tour);
    }

    @Transactional
    public Tour updateTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Transactional
    public boolean deleteTourById(Long id) {
        return tourRepository.deleteTourById(id) > 0L;
    }

}
