package com.Valeram.travelagencymanagementbackend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.Valeram.travelagencymanagementbackend.dao.tourDao;
import com.Valeram.travelagencymanagementbackend.common.dto.tour.tourDto;
import com.Valeram.travelagencymanagementbackend.model.tour;
import com.Valeram.travelagencymanagementbackend.service.impl.DefaultTourService;
import com.Valeram.travelagencymanagementbackend.service.mapper.FlightMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class tourServiceTest {

  private final tourDao tourDao = Mockito.mock(tourDao.class);
  private final tourService service = new DefaultTourService(tourDao);
  private final FlightMapper mapper = Mappers.getMapper(FlightMapper.class);

  @Test
  void findAllWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    List<tour> entities = List.of(
        tour.builder()
            .departureFrom("Kyiv")
            .destination("Lviv")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build(),

        tour.builder()
            .departureFrom("Krakow")
            .destination("Zurich")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build()
    );

    Mockito.when(tourDao.findAll()).thenReturn(entities);

    List<tourDto> actual = service.findAll();
    boolean hasFlightFromKyiv = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDepartureFrom(), "Kyiv"));

    boolean hasFlightToZurich = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDestination(), "Zurich"));

    assertEquals(2, actual.size());
    assertTrue(hasFlightFromKyiv);
    assertTrue(hasFlightToZurich);
    assertEquals(departureTime, actual.get(0).getDepartureTime());
    assertEquals(arrivalTime, actual.get(0).getArrivalTime());
  }

  @Test
  void findAllByCrewMemberIdWorksProperly() {
    Long crewMemberId = 1L;
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    List<tour> entities = List.of(
        tour.builder()
            .departureFrom("Kyiv")
            .destination("Lviv")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build(),

        tour.builder()
            .departureFrom("Krakow")
            .destination("Zurich")
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .build()
    );

    Mockito.when(tourDao.findAllBycustomerMemberId(crewMemberId)).thenReturn(entities);

    List<tourDto> actual = service.findAllByCrewMemberId(crewMemberId);
    boolean hasFlightFromKyiv = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDepartureFrom(), "Kyiv"));

    boolean hasFlightToZurich = actual.stream()
        .anyMatch(f -> Objects.equals(f.getDestination(), "Zurich"));

    assertEquals(2, actual.size());
    assertTrue(hasFlightFromKyiv);
    assertTrue(hasFlightToZurich);
    assertEquals(departureTime, actual.get(0).getDepartureTime());
    assertEquals(arrivalTime, actual.get(0).getArrivalTime());
  }

  @Test
  void findByIdWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(tourDao.findById(id)).thenReturn(Optional.empty());

    Optional<tourDto> actual = service.findById(id);

    assertTrue(actual.isEmpty());
  }

  @Test
  void findByIdWhenProvidedExistentId() {
    Long id = 1L;
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    tour found = tour.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(tourDao.findById(id)).thenReturn(Optional.of(found));

    Optional<tourDto> actual = service.findById(id);

    assertTrue(actual.isPresent());
    assertEquals(1L, actual.get().getId());
    assertEquals("Kyiv", actual.get().getDepartureFrom());
    assertEquals("Lviv", actual.get().getDestination());
    assertEquals(departureTime, actual.get().getDepartureTime());
    assertEquals(arrivalTime, actual.get().getArrivalTime());
  }

  @Test
  void createWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    tourDto toCreate = tourDto.builder()
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    tourDto expected = tourDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(tourDao.insert(mapper.toEntity(toCreate)))
        .thenReturn(mapper.toEntity(expected));

    tourDto actual = service.create(toCreate);

    assertEquals(expected, actual);
  }

  @Test
  void updateWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);
    tourDto toUpdate = tourDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    tourDto expected = tourDto.builder()
        .id(1L)
        .departureFrom("Kyiv")
        .destination("Lviv")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Mockito.when(tourDao.update(mapper.toEntity(toUpdate)))
        .thenReturn(mapper.toEntity(expected));

    tourDto actual = service.update(toUpdate);

    assertEquals(expected, actual);
  }

  @Test
  void deleteWhenProvidedNonExistentId() {
    Long id = 1L;
    Mockito.when(tourDao.delete(id)).thenReturn(false);

    boolean actual = service.delete(id);

    assertFalse(actual);
  }

  @Test
  void deleteWhenProvidedExistentId() {
    Long id = 1L;
    Mockito.when(tourDao.delete(id)).thenReturn(true);

    boolean actual = service.delete(id);

    assertTrue(actual);
  }

}