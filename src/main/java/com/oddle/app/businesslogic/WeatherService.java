package com.oddle.app.businesslogic;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.dao.CityRepository;
import com.oddle.app.dao.WeatherRepository;
import com.oddle.app.model.City;
import com.oddle.app.model.Weather;
import com.oddle.app.vo.OpenWeatherVO;
import com.oddle.app.vo.WeatherVO;

@Service
public class WeatherService implements IWeatherService {

	private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

	@Autowired
	private Environment environment;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private WeatherRepository weatherRepo;

	@Override
	public WeatherVO retrieveFromOpenWeather(String cityName) {
		LOG.debug("enter retrieveFromOpenWeather");
		try {
			URIBuilder builder = new URIBuilder(environment.getRequiredProperty("openweather.api"));
			builder.setParameter("q", cityName).setParameter("units", "metric").setParameter("appid",
					environment.getRequiredProperty("openweather.key"));

			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(builder.build());
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			OpenWeatherVO vo = objectMapper.readValue(new InputStreamReader(content, "UTF-8"), OpenWeatherVO.class);

			LOG.debug("exit retrieveFromOpenWeather");
			// save to DB
			return saveWeatherLog(vo);

		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return null;

		}
	}

	private WeatherVO saveWeatherLog(OpenWeatherVO vo) {
		LOG.debug("enter saveWeatherLog");
		City existCity = cityRepo.getCityByName(vo.getName());
		City cId = null;
		if (existCity == null) {
			City newCity = new City();
			newCity.setName(vo.getName());
			cityRepo.saveAndFlush(newCity);
			cId = newCity;
		} else {
			cId = existCity;
		}
		Weather weather = new Weather();
		weather.setCity(cId);
		weather.setDate(new Timestamp(vo.getDt() * 1000));
		weather.setDes(vo.getWeather().get(0).getMain());
		weather.setTemp(vo.getMain().getTemp());
		weather.setSpeed(vo.getWind().getSpeed());
		weather.setHumidity(vo.getMain().getHumidity());
		weather.setPressure(vo.getMain().getPressure());
		weatherRepo.saveAndFlush(weather);

		WeatherVO ret = new WeatherVO();
		ret.setId(weather.getId().toString());
		ret.setCity(weather.getCity().getName());
		String pattern = "MM/dd/yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		ret.setDate(df.format(weather.getDate()));
		ret.setDes(weather.getDes());
		ret.setTemp(weather.getTemp().toString() + " oC");
		ret.setHumidity(weather.getHumidity().toString() + " %");
		ret.setPressure(weather.getPressure().toString() + " hPa");
		ret.setSpeed(weather.getSpeed().toString() + " m/s");

		LOG.debug("exit saveWeatherLog");
		return ret;
	}

	@Override
	public List<WeatherVO> retrieveByCity(String cityName) {
		LOG.debug("enter retrieveByCity");
		City city = cityRepo.getCityByName(cityName);
		List<WeatherVO> lstVO = new ArrayList<WeatherVO>();
		List<Weather> lst = city.getWeather();
		for (Weather w : lst) {
			WeatherVO ret = new WeatherVO();
			ret.setId(w.getId().toString());
			ret.setCity(w.getCity().getName());
			String pattern = "MM/dd/yyyy HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			ret.setDate(df.format(w.getDate()));
			ret.setDes(w.getDes());
			ret.setTemp(w.getTemp().toString() + " oC");
			ret.setHumidity(w.getHumidity().toString() + " %");
			ret.setPressure(w.getPressure().toString() + " hPa");
			ret.setSpeed(w.getSpeed().toString() + " m/s");
			lstVO.add(ret);

		}

		LOG.debug("exit retrieveByCity");
		return lstVO;
	}

	@Override
	public void deleteWeatherById(Integer id) {
		try {
			weatherRepo.deleteById(id);
			weatherRepo.flush();
		} catch (Exception e) {
			return;
		}
	}

}
