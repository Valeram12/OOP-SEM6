package com.Valeram.travelagencymanagement.controller;

import com.Valeram.travelagencymanagement.controller.dto.IdToLinkUpDto;
import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMemberForm;
import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMemberResponseDto;
import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMemberWithouttoursDto;
import com.Valeram.travelagencymanagement.controller.dto.customermember.customerMembersListDto;
import com.Valeram.travelagencymanagement.controller.dto.tour.tourWithoutcustomerMembersDto;
import com.Valeram.travelagencymanagement.exception.NotFoundException;
import com.Valeram.travelagencymanagement.model.customerMember;
import com.Valeram.travelagencymanagement.model.Flight;
import com.Valeram.travelagencymanagement.service.customerMemberService;
import com.Valeram.travelagencymanagement.service.customerMemberstoursLinkService;
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
public class customerMemberController {

    private final customerMemberService customerMemberService;
    private final customerMemberstoursLinkService linkService;
    private final ModelMapper modelMapper;

    @Autowired
    public customerMemberController(customerMemberService customerMemberService,
                                    customerMemberstoursLinkService linkService,
                                    ModelMapper modelMapper) {
        this.customerMemberService = customerMemberService;
        this.linkService = linkService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/customer-members")
    public customerMembersListDto getListOfCrewMembers() {
        List<customerMember> entities = customerMemberService.findAll();

        List<customerMemberWithouttoursDto> dtos = entities.stream()
                .map(e -> modelMapper.map(e, customerMemberWithouttoursDto.class))
                .toList();

        return new customerMembersListDto(dtos);
    }

    @GetMapping("/customer-members/{customer-member-id}")
    public customerMemberResponseDto getCrewMemberById(@PathVariable("customer-member-id") Long id) {
        Optional<customerMember> customerMemberOptional = customerMemberService.findcustomerMemberById(id);

        if (customerMemberOptional.isEmpty()) {
            throw new NotFoundException(NotFoundException.customer_MEMBER_NOT_FOUND);
        }

        return mapAndFetchFlights(customerMemberOptional.get());
    }

    @PostMapping("/customer-members")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerMemberResponseDto createCustomerMember(@Valid @RequestBody CustomerMemberForm form) {
        CustomerMember toSave = modelMapper.map(form, CustomerMember.class);
        CustomerMember response = customerMemberService.saveCustomerMember(toSave);

        // We don't use mapAndFetchTours() because tourWithoutCustomerMembersDtos list is empty after creation
        return modelMapper.map(response, CustomerMemberResponseDto.class);
    }

    @PutMapping("/customer-members/{customer-member-id}")
    public CustomerMemberResponseDto updateCustomerMember(@PathVariable("customer-member-id") Long id,
                                                          @Valid @RequestBody CustomerMemberForm form) {
        if (!customerMemberService.existsById(id)) {
            throw new NotFoundException(NotFoundException.CUSTOMER_MEMBER_NOT_FOUND);
        }

        CustomerMember toUpdate = modelMapper.map(form, CustomerMember.class);
        toUpdate.setId(id);

        CustomerMember response = customerMemberService.updateCustomerMember(toUpdate);

        return mapAndFetchTours(response);
    }

    @DeleteMapping("/customer-members/{customer-member-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerMember(@PathVariable("customer-member-id") Long id) {
        boolean isDeleted = customerMemberService.deleteCustomerMemberById(id);

        if (!isDeleted) {
            throw new NotFoundException(NotFoundException.CUSTOMER_MEMBER_NOT_FOUND);
        }
    }

    @PostMapping("/customer-members/{customer-member-id}/tours")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerMemberResponseDto linkUpTour(@PathVariable("customer-member-id") Long customerMemberId,
                                                @Valid @RequestBody IdToLinkUpDto tourId) {
        // Existence validation is here
        linkService.linkUpCustomerMemberAndTour(customerMemberId, tourId.getIdToLink());

        Optional<CustomerMember> customerMemberOptional = customerMemberService.findCustomerMemberById(customerMemberId);

        return mapAndFetchTours(customerMemberOptional.get());
    }

    @DeleteMapping("/customer-members/{customer-member-id}/tours/{tour-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkUpTour(@PathVariable("customer-member-id") Long customerMemberId,
                             @PathVariable("tour-id") Long tourId) {
        linkService.unlinkUpCustomerMemberAndTour(customerMemberId, tourId);
    }

    private CustomerMemberResponseDto mapAndFetchTours(CustomerMember customerMember) {
        List<Tour> tourEntities = customerMemberService.findToursOfCustomerMember(customerMember);
        List<TourWithoutCustomerMembersDto> tourDtos = tourEntities.stream()
                .map(e -> modelMapper.map(e, TourWithoutCustomerMembersDto.class))
                .toList();

        CustomerMemberResponseDto resultDto = modelMapper.map(customerMember, CustomerMemberResponseDto.class);
        resultDto.setTours(tourDtos);

        return resultDto;
    }
}