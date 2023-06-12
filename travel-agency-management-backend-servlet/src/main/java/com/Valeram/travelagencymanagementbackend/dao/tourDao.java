package com.Valeram.travelagencymanagementbackend.dao;

import com.Valeram.travelagencymanagementbackend.model.tour;

import java.util.List;
import java.util.Optional;

public interface tourDao {

  List<tour> findAll();

  List<tour> findAllBycustomerMemberId(Long crewMemberId);

  Optional<tour> findById(Long id);

  tour insert(tour toInsert);

  tour update(tour toUpdate);

  boolean delete(Long id);

}
