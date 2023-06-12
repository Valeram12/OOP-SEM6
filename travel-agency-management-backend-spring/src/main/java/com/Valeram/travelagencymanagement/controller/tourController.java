package com.Valeram.travelagencymanagement.controller;

import com.Valeram.travelagencymanagement.controller.dto.IdToLinkUpDto;
import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMemberWithouttoursDto;
import com.Valeram.travelagencymanagement.controller.dto.tour.tourForm;
import com.Valeram.travelagencymanagement.controller.dto.tour.tourResponseDto;
import com.Valeram.travelagencymanagement.controller.dto.tour.tourWithoutcustomerMembersDto;
import com.Valeram.travelagencymanagement.controller.dto.tour.toursListDto;
import com.Valeram.travelagencymanagement.model.customerMember;
import com.Valeram.travelagencymanagement.model.Flight;
import com.Valeram.travelagencymanagement.service.customerMemberstoursLinkService;
import com.Valeram.travelagencymanagement.service.tourService;
import com.Valeram.travelagencymanagement.exception.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tourController {

    private final tourService tourService;
    private final customerMemberstoursLinkService linkService;
    private final ModelMapper modelMapper;

    @Autowired
    public tourController(tourService tourService,
                          customerMemberstoursLinkService linkService,
                          ModelMapper modelMapper) {
        this.tourService = tourService;
        this.linkService = linkService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/flights")
    public toursListDto getListOfFlights() {
        List<Flight> entities = tourService.findAll();

        List<tourWithoutcustomerMembersDto> dtos = entities.stream()
                .map(e -> modelMapper.map(e, tourWithoutcustomerMembersDto.class))
                .toList();

        return new toursListDto(dtos);
    }

    @GetMapping("/tours")
    public ToursListDto getListOfTours() {
        List<Tour> entities = tourService.findAll();

        List<TourWithoutCustomerMembersDto> dtos = entities.stream()
                .map(e -> modelMapper.map(e, TourWithoutCustomerMembersDto.class))
                .toList();

        return new ToursListDto(dtos);
    }

    @GetMapping("/tours/{tour-id}")
    public TourResponseDto getTourById(@PathVariable("tour-id") Long id) {
        Optional<Tour> tourOptional = tourService.findTourById(id);

        if (tourOptional.isEmpty()) {
            throw new NotFoundException(NotFoundException.TOUR_NOT_FOUND);
        }

        return mapAndFetchCustomerMembers(tourOptional.get());
    }

    @PostMapping("/tours")
    @ResponseStatus(HttpStatus.CREATED)
    public TourResponseDto createTour(@Valid @RequestBody TourForm form) {
        Tour toSave = modelMapper.map(form, Tour.class);
        Tour response = tourService.saveTour(toSave);

        // We don't use mapAndFetchCustomerMembers() because customer members list is empty after creation
        return modelMapper.map(response, TourResponseDto.class);
    }

    @PutMapping("/tours/{tour-id}")
    public TourResponseDto updateTour(@PathVariable("tour-id") Long id,
                                      @Valid @RequestBody TourForm form) {
        if (!tourService.existsById(id)) {
            throw new NotFoundException(NotFoundException.TOUR_NOT_FOUND);
        }

        Tour toUpdate = modelMapper.map(form, Tour.class);
        toUpdate.setId(id);

        Tour response = tourService.updateTour(toUpdate);

        return mapAndFetchCustomerMembers(response);
    }

    @DeleteMapping("/tours/{tour-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable("tour-id") Long id) {
        boolean isDeleted = tourService.deleteTourById(id);

        if (!isDeleted) {
            throw new NotFoundException(NotFoundException.TOUR_NOT_FOUND);
        }
    }

    @PostMapping("/tours/{tour-id}/customer-members")
    @ResponseStatus(HttpStatus.CREATED)
    public TourResponseDto linkUpCustomerMember(@PathVariable("tour-id") Long tourId,
                                                @Valid @RequestBody IdToLinkUpDto customerMemberId) {
        // Existence validation is here
        linkService.linkUpCustomerMemberAndTour(customerMemberId.getIdToLink(), tourId);

        Optional<Tour> tourOptional = tourService.findTourById(tourId);

        return mapAndFetchCustomerMembers(tourOptional.get());
    }

    @DeleteMapping("/tours/{tour-id}/customer-members/{customer-member-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkUpCustomerMember(@PathVariable("tour-id") Long tourId,
                                       @PathVariable("customer-member-id") Long customerMemberId) {
        linkService.unlinkUpCustomerMemberAndTour(customerMemberId, tourId);
    }

    private TourResponseDto mapAndFetchCustomerMembers(Tour tour) {
        List<CustomerMember> customerMemberEntities = tourService.findCustomerMembersOfTour(tour);
        List<CustomerMemberWithoutToursDto> customerMembersDtos = customerMemberEntities.stream()
                .map(e -> modelMapper.map(e, CustomerMemberWithoutToursDto.class))
                .toList();

        TourResponseDto resultDto = modelMapper.map(tour, TourResponseDto.class);
        resultDto.setCustomerMembers(customerMembersDtos);

        return resultDto;
    }


}