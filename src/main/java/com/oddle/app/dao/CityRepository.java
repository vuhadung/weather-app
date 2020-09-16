package com.oddle.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oddle.app.model.City;

public interface CityRepository extends JpaRepository<City, Integer>, CityRepositoryCustom {

}
