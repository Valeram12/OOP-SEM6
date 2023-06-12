package com.Valeram.travelagencymanagementbackend.dao.impl;

import com.Valeram.travelagencymanagementbackend.dao.tourDao;
import com.Valeram.travelagencymanagementbackend.database.DatabaseConnector;
import com.Valeram.travelagencymanagementbackend.exception.DatabaseOperationFailedException;
import com.Valeram.travelagencymanagementbackend.model.tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaulttourDao implements tourDao {

  @Override
  public List<tour> findAll() {
    String sql = "SELECT * FROM tours ORDER BY id";

    try (Connection connection = DatabaseConnector.getConnection();
         Statement statement = connection.createStatement()) {
      List<tour> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery(sql)) {
        while (rs.next()) {
          result.add(extracttour(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all tours. Error message: {}", e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private tour extracttour(ResultSet rs) throws SQLException {
    tour tour = new tour();
    tour.setId(rs.getLong("id"));
    tour.setDepartureFrom(rs.getString("departure_from"));
    tour.setDestination(rs.getString("destination"));
    tour.setDepartureTime(rs.getObject("departure_time", LocalDateTime.class));
    tour.setArrivalTime(rs.getObject("arrival_time", LocalDateTime.class));

    return tour;
  }

  @Override
  public List<tour> findAllBycustomerMemberId(Long customerMemberId) {
    String sql = """
      SELECT f.*
      FROM tours f
      INNER JOIN customer_members_tours cmf
      ON cmf.fk_flight_id = f.id
      WHERE cmf.fk_crew_member_id = ?
      ORDER BY f.id
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, customerMemberId);
      List<tour> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          result.add(extracttour(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all tours by customer member id: {}. Error message: {}", customerMemberId, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public Optional<tour> findById(Long id) {
    String sql = "SELECT * FROM tours WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      Optional<tour> result = Optional.empty();

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          result = Optional.of(extracttour(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find tour by id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public tour insert(tour toInsert) {
    String sql = "INSERT INTO tours (departure_from, destination, departure_time, arrival_time) VALUES (?, ?, ?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, toInsert);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extracttour(rs);
      }

    } catch (Exception e) {
      log.warn("Unable to insert tour: {}. Error message: {}", toInsert, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, tour tour) throws SQLException {
    statement.setString(1, tour.getDepartureFrom());
    statement.setString(2, tour.getDestination());
    statement.setObject(3, tour.getDepartureTime());
    statement.setObject(4, tour.getArrivalTime());
  }

  @Override
  public tour update(tour toUpdate) {
    String sql = """
      UPDATE tours
      SET departure_from = ?, destination = ?, departure_time = ?, arrival_time = ?
      WHERE id = ?
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setParamsToUpdate(statement, toUpdate);
      statement.executeUpdate();

      return findById(toUpdate.getId()).orElseThrow(() -> {
        throw new NoSuchElementException(String.format("tour with id: %d not found", toUpdate.getId()));
      });
    } catch (Exception e) {
      log.warn("Unable to update tour: {}. Error message: {}", toUpdate, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToUpdate(PreparedStatement statement, tour tour) throws SQLException {
    statement.setString(1, tour.getDepartureFrom());
    statement.setString(2, tour.getDestination());
    statement.setObject(3, tour.getDepartureTime());
    statement.setObject(4, tour.getArrivalTime());
    statement.setObject(5, tour.getId());
  }

  @Override
  public boolean delete(Long id) {
    String sql = "DELETE FROM tours WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn("Unable to delete tour with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}