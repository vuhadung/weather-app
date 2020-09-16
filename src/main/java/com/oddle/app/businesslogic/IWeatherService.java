package com.oddle.app.businesslogic;

import java.util.List;

import com.oddle.app.vo.WeatherVO;

public interface IWeatherService {

	public WeatherVO retrieveFromOpenWeather(String cityName);

	public List<WeatherVO> retrieveByCity(String cityName);

	public void deleteWeatherById(Integer id);

}
