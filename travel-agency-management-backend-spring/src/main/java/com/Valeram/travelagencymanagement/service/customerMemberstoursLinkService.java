package com.Valeram.travelagencymanagement.service;

import com.Valeram.travelagencymanagement.exception.NotFoundException;
import com.Valeram.travelagencymanagement.exception.ValidException;
import com.Valeram.travelagencymanagement.repository.customerMemberRepository;
import com.Valeram.travelagencymanagement.repository.FlightRepository;
import com.Valeram.travelagencymanagement.model.Flight;
import com.Valeram.travelagencymanagement.model.customerMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class customerMemberstoursLinkService {

    private final CustomerMemberRepository customerMemberRepository;
    private final TourRepository tourRepository;

    @Autowired
    public CustomerMembersToursLinkService(CustomerMemberRepository customerMemberRepository,
                                           TourRepository tourRepository) {
        this.customerMemberRepository = customerMemberRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional
    public void linkUpCustomerMemberAndTour(Long customerMemberId, Long tourId) {
        CustomerMember customerMember = customerMemberRepository
                .findById(customerMemberId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundException.CUSTOMER_MEMBER_NOT_FOUND_TO_LINK_UP)
                );

        Tour tour = tourRepository
                .findById(tourId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundException.TOUR_NOT_FOUND_TO_LINK_UP)
                );

        boolean linkExists = checkLinkExistence(customerMember, tour);

        if (linkExists) {
            throw new ValidException(ValidException.LINK_ALREADY_EXISTS);
        }

        tour.getCustomerMembers().add(customerMember);
        customerMember.getTours().add(tour);
    }

    @Transactional
    public void unlinkUpCustomerMemberAndTour(Long customerMemberId, Long tourId) {
        CustomerMember customerMember = customerMemberRepository
                .findById(customerMemberId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundException.CUSTOMER_MEMBER_NOT_FOUND_TO_UNLINK_UP)
                );

        Tour tour = tourRepository
                .findById(tourId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundException.TOUR_NOT_FOUND_TO_UNLINK_UP)
                );

        tour.getCustomerMembers().remove(customerMember);
        boolean isUnlinked = customerMember.getTours().remove(tour);

        if (!isUnlinked) {
            throw new NotFoundException(NotFoundException.LINK_IS_ABSENT);
        }
    }

    private boolean checkLinkExistence(CustomerMember customerMember, Tour tour) {
        return customerMember.getTours().contains(tour);
    }

}
