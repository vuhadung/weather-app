package com.oddle.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "weather_log")
public class Weather implements Serializable {

	private static final long serialVersionUID = 4247583718295348289L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	@Column(name = "date_time")
	private Date date;

	@Column(name = "temperature")
	private Float temp;

	@Column(name = "temp_des")
	private String des;

	@Column(name = "speed")
	private Float speed;

	@Column(name = "humidity")
	private Float humidity;

	@Column(name = "pressure")
	private Float pressure;

}
