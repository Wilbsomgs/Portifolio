package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"admin.password=senha-de-teste", "spring.datasource.url=jdbc:h2:mem:teste"})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
