package com.Valeram.travelagencymanagementbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.Valeram.travelagencymanagementbackend.dao.customerMemberDao;
import com.Valeram.travelagencymanagementbackend.common.dto.customermember.customerMemberDto;
import com.Valeram.travelagencymanagementbackend.model.customerMember;
import com.Valeram.travelagencymanagementbackend.model.Position;
import com.Valeram.travelagencymanagementbackend.service.impl.DefaultcustomerMemberService;
import com.Valeram.travelagencymanagementbackend.service.mapper.CrewMemberMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class customerMemberServiceTest {

  private final customerMemberDao customerMemberDao = Mockito.mock(customerMemberDao.class);
  private final CrewMemberService service = new DefaultcustomerMemberService(customerMemberDao);
  private final CrewMemberMapper mapper = Mappers.getMapper(CrewMemberMapper.class);

  @Test
  void findAllWorksProperly() {
    List<customerMember> entities = List.of(
        customerMember.builder()
            .id(1L)
            .name("Test name 1")
            .surname("Test surname 1")
            .position(Position.PILOT)
            .build(),

        customerMember.builder()
            .id(2L)
            .name("Test name 2")
            .surname("Test surname 2")
            .position(Position.OPERATOR)
            .build()
    );

    Mockito.when(customerMemberDao.findAll()).thenReturn(entities);

    List<customerMemberDto> actual = service.findAll();
    List<customerMemberDto> pilots = actual.stream()
        .filter(cm -> Objects.equals(cm.getPosition(), Position.PILOT))
        .collect(Collectors.toList());

    List<customerMemberDto> operators = actual.stream()
        .filter(cm -> Objects.equals(cm.getPosition(), Position.OPERATOR))
        .collect(Collectors.toList());

    assertEquals(2, actual.size());
    assertEquals(1, pilots.size());
    assertEquals(1, operators.size());
    assertEquals(1L, pilots.get(0).getId());
    assertEquals(2L, operators.get(0).getId());
  }

  @Test
  void findAllByFlightIdWorksProperly() {
    Long flightId = 1L;
    List<customerMember> entities = List.of(
        customerMember.builder()
            .id(1L)
            .name("Test name 1")
            .surname("Test surname 1")
            .position(Position.PILOT)
            .build(),

        customerMember.builder()
            .id(2L)
            .name("Test name 2")
            .surname("Test surname 2")
            .position(Position.OPERATOR)
            .build()
    );

    Mockito.when(customerMemberDao.findAllByFlightId(flightId)).thenReturn(entities);

    List<customerMemberDto> actual = service.findAllByFlightId(flightId);

    List<customerMemberDto> pilots = actual.stream()
        .filter(cm -> Objects.equals(cm.getPosition(), Position.PILOT))
        .collect(Collectors.toList());

    List<customerMemberDto> operators = actual.stream()
        .filter(cm -> Objects.equals(cm.getPosition(), Position.OPERATOR))
        .collect(Collectors.toList());

    assertEquals(2, actual.size());
    assertEquals(1, pilots.size());
    assertEquals(1, operators.size());
    assertEquals(1L, pilots.get(0).getId());
    assertEquals(2L, operators.get(0).getId());
  }

  @Test
  void findByIdWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(customerMemberDao.findById(id)).thenReturn(Optional.empty());

    Optional<customerMemberDto> actual = service.findById(id);

    assertTrue(actual.isEmpty());
  }

  @Test
  void findByIdWhenProvidedExistentId() {
    Long id = 1L;
    customerMember found = customerMember.builder()
        .id(1L)
        .name("Test name 1")
        .surname("Test surname 1")
        .position(Position.PILOT)
        .build();

    Mockito.when(customerMemberDao.findById(id)).thenReturn(Optional.of(found));

    Optional<customerMemberDto> actual = service.findById(id);

    assertTrue(actual.isPresent());
    assertEquals(1L, actual.get().getId());
    assertEquals("Test name 1", actual.get().getName());
    assertEquals("Test surname 1", actual.get().getSurname());
    assertEquals(Position.PILOT, actual.get().getPosition());
  }

  @Test
  void createWorksProperly() {
    customerMemberDto toCreate = customerMemberDto.builder()
        .name("Test name 1")
        .surname("Test surname 1")
        .position(Position.PILOT)
        .build();

    customerMemberDto expected = customerMemberDto.builder()
        .id(1L)
        .name("Test name 1")
        .surname("Test surname 1")
        .position(Position.PILOT)
        .build();

    Mockito.when(customerMemberDao.insert(mapper.toEntity(toCreate)))
        .thenReturn(mapper.toEntity(expected));

    customerMemberDto actual = service.create(toCreate);

    assertEquals(expected, actual);
  }

  @Test
  void updateWorksProperly() {
    customerMemberDto toUpdate = customerMemberDto.builder()
        .id(1L)
        .name("Updated test name 1")
        .surname("Updated test surname 1")
        .position(Position.PILOT)
        .build();

    customerMemberDto expected = customerMemberDto.builder()
        .id(1L)
        .name("Updated test name 1")
        .surname("Updated test surname 1")
        .position(Position.PILOT)
        .build();

    Mockito.when(customerMemberDao.update(mapper.toEntity(toUpdate)))
        .thenReturn(mapper.toEntity(expected));

    customerMemberDto actual = service.update(toUpdate);

    assertEquals(expected, actual);
  }

  @Test
  void deleteWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(customerMemberDao.delete(id)).thenReturn(false);

    boolean actual = service.delete(id);

    assertFalse(actual);
  }

  @Test
  void deleteWhenProvidedExistentId() {
    Long id = 1L;
    Mockito.when(customerMemberDao.delete(id)).thenReturn(true);

    boolean actual = service.delete(id);

    assertTrue(actual);
  }

}