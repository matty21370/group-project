package com.mocha.shopwebsite.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
	

	// User findUserByUsername(String username);

	 List<Item> findByUserId(Integer userId);

}
