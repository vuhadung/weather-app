package com.oddle.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.oddle.app.model.City;

@Repository
public class CityRepositoryImpl implements CityRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public City getCityByName(String cityName) {
		Query query = entityManager.createQuery("from City c where c.name like :searchField");
		query.setParameter("searchField", "%" + cityName + "%");
		List<City> res = query.getResultList();
		if (res == null || res.isEmpty())
			return null;
		return res.get(0);
	}

}
