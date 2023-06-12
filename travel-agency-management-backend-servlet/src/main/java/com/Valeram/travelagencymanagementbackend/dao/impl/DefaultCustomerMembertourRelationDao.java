package com.Valeram.travelagencymanagementbackend.dao.impl;

import com.Valeram.travelagencymanagementbackend.dao.customerMembertourRelationDao;
import com.Valeram.travelagencymanagementbackend.database.DatabaseConnector;
import com.Valeram.travelagencymanagementbackend.exception.DatabaseOperationFailedException;
import com.Valeram.travelagencymanagementbackend.model.customerMembertourRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCustomerMembertourRelationDao implements customerMembertourRelationDao {

  @Override
  public customerMembertourRelation insert(customerMembertourRelation relation) {
    String sql = "INSERT INTO customer_members_tours (fk_customer_member_id, fk_tour_id) VALUES (?, ?)";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      setParamsToInsert(statement, relation);
      statement.executeUpdate();

      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();

        return extractcustomerMembertourRelation(rs);
      }

    } catch (Exception e) {
      log.warn("Unable to insert customerMembertourRelation: {}. Error message: {}", relation, e.getMessage());
      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setParamsToInsert(PreparedStatement statement, customerMembertourRelation relation)
      throws SQLException {
    statement.setLong(1, relation.getCustomerMemberId());
    statement.setLong(2, relation.getTourId());
  }

  private customerMembertourRelation extractcustomerMembertourRelation(ResultSet rs)
      throws SQLException {
    customerMembertourRelation relation = new customerMembertourRelation();
    relation.setId(rs.getLong("id"));
    relation.setCustomerMemberId(rs.getLong("fk_customer_member_id"));
    relation.setTourId(rs.getLong("fk_tourId_id"));

    return relation;
  }

  @Override
  public boolean existsBycustomerMemberAndtourIds(Long customerMemberId, Long tourId) {
    String sql = "SELECT * FROM customer_members_tours WHERE fk_customer_member_id = ? AND fk_tour_id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setcustomerMemberAndtourIds(statement, customerMemberId, tourId);

      try (ResultSet rs = statement.executeQuery()) {
        return rs.next();
      }

    } catch (Exception e) {
      log.warn(
          "Unable to determine relation existence between crew member with id: {} and tour with id: {}. "
          + "Error message: {}",
              customerMemberId,
              tourId,
          e.getMessage()
      );

      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

  private void setcustomerMemberAndtourIds(PreparedStatement statement, Long customerMemberId, Long tourId)
      throws SQLException {
    statement.setLong(1, customerMemberId);
    statement.setLong(2, tourId);
  }

  @Override
  public boolean deleteBycustomerMemberAndtourIds(Long customerMemberId, Long tourId) {
    String sql = "DELETE FROM crew_members_flights WHERE fk_customer_member_id = ? AND fk_tour_id = ?";

    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      setcustomerMemberAndtourIds(statement, customerMemberId, tourId);

      return statement.executeUpdate() > 0;
    } catch (Exception e) {
      log.warn(
          "Unable to delete customerMembertourRelation by crew member id: {} and tour id: {}. "
          + "Error message: {}",
          customerMemberId,
          tourId,
          e.getMessage()
      );

      throw new DatabaseOperationFailedException(e.getMessage());
    }
  }

}
