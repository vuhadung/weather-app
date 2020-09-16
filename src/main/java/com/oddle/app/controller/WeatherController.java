package com.oddle.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oddle.app.businesslogic.IWeatherService;
import com.oddle.app.vo.WeatherVO;

@Controller
@RequestMapping("/")
public class WeatherController {

	@Autowired
	private IWeatherService service;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model, HttpServletRequest req) {
		model.addAttribute("message", "");
		String cityName = req.getParameter("q");
		if (cityName == null || cityName.isEmpty()) {
			model.addAttribute("message", "Please enter a city!");
			return "weather";
		}

		WeatherVO vo = service.retrieveFromOpenWeather(cityName);
		if (vo == null) {
			model.addAttribute("message", "Not a valid city!");
			return "weather";
		}

		model.addAttribute("weather", vo);
		return "weather";
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String retrieve(ModelMap model, @PathVariable("name") String name) {
		model.addAttribute("message", "");
		List<WeatherVO> lst = service.retrieveByCity(name);
		if (lst == null || lst.isEmpty()) {
			model.addAttribute("message", "Please try again later!");
			return "history";
		}

		else {
			model.addAttribute("message", "Weather logs for " + name);
			model.addAttribute("weather", lst);
			return "history";
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> delete(ModelMap model, @PathVariable("id") Integer id) {
		model.addAttribute("message", "");
		service.deleteWeatherById(id);
		return new ResponseEntity<String>("", HttpStatus.OK);

	}
}
