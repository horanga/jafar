package com.jafar.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.TimeZone;

@SpringBootTest
class ApiApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void test1(){
		TimeZone tz = TimeZone.getDefault();

		System.out.println(new Date());

		System.out.println("TimeZone: " + tz.getDisplayName());
		System.out.println("TimeZone ID: " + tz.getID());
		System.out.println("Offset: " + tz.getRawOffset() / 3600000 + " hours");

	}
}
