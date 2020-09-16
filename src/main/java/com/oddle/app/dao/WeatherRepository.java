package com.oddle.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oddle.app.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

}
