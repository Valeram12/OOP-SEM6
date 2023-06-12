package com.Valeram.travelagencymanagementbackend.dao.impl;

import com.Valeram.travelagencymanagementbackend.dao.customerMemberDao;
import com.Valeram.travelagencymanagementbackend.database.DatabaseConnector;
import com.Valeram.travelagencymanagementbackend.exception.DatabaseOperationFailedException;
import com.Valeram.travelagencymanagementbackend.model.customerMember;
import com.Valeram.travelagencymanagementbackend.model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultcustomerMemberDao implements customerMemberDao {

  @Override
  public List<customerMember> findAll() {
    String sql = "SELECT * FROM customer_members ORDER BY id";

    try (Connection connection = DatabaseConnector.getConnection();
         Statement statement = connection.createStatement()) {
      List<customerMember> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery(sql)) {
        while (rs.next()) {
          result.add(extractcustomerMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find all customer members. Error message: {}", e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private customerMember extractcustomerMember(ResultSet rs) throws SQLException {
    customerMember customerMember = new customerMember();
    customerMember.setId(rs.getLong("id"));
    customerMember.setName(rs.getString("name"));
    customerMember.setSurname(rs.getString("surname"));
    customerMember.setPosition(Position.valueOf(rs.getString("position")));

    return customerMember;
  }

  @Override
  public List<customerMember> findAllByFlightId(Long flightId) {
    String sql = """
      SELECT cm.*
      FROM customer_members cm
      INNER JOIN customer_members_tours cmf
      ON cmf.fk_customer_member_id = cm.id
      WHERE cmf.fk_tour_id = ?
      ORDER BY cm.id
    """;

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, flightId);
      List<customerMember> result = new ArrayList<>();

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          result.add(extractcustomerMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find crew members by tour id: {}. Error message: {}", flightId, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public Optional<customerMember> findById(Long id) {
    String sql = "SELECT * FROM customer_members WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      Optional<customerMember> result = Optional.empty();

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          result = Optional.of(extractcustomerMember(rs));
        }
      }

      return result;
    } catch (Exception e) {
      log.warn("Unable to find crew member with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  @Override
  public customerMember insert(customerMember toInsert) {
    String sql = "INSERT INTO customer_members (name, surname, position) VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, toInsert);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extractcustomerMember(rs);
      }
    } catch (Exception e) {
      log.warn("Unable to insert crew member: {}. Error message: {}", toInsert, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, customerMember customerMember)
      throws SQLException {
    statement.setString(1, customerMember.getName());
    statement.setString(2, customerMember.getSurname());
    statement.setString(3, customerMember.getPosition().toString());
  }

  @Override
  public customerMember update(customerMember toUpdate) {
    String sql = "UPDATE customer_members SET name = ?, surname = ?, position = ? WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setParamsToUpdate(statement, toUpdate);
      statement.executeUpdate();

      return findById(toUpdate.getId()).orElseThrow(() -> {
        throw new NoSuchElementException(String.format("Crew member with id: %d not found", toUpdate.getId()));
      });
    } catch (Exception e) {
      log.warn("Unable to update crew member: {}. Error message: {}", toUpdate, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToUpdate(PreparedStatement statement, customerMember customerMember)
      throws SQLException {
    statement.setString(1, customerMember.getName());
    statement.setString(2, customerMember.getSurname());
    statement.setString(3, customerMember.getPosition().toString());
    statement.setLong(4, customerMember.getId());
  }

  @Override
  public boolean delete(Long id) {
    String sql = "DELETE FROM customer_members WHERE id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn("Unable to delete crew member with id: {}. Error message: {}", id, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}