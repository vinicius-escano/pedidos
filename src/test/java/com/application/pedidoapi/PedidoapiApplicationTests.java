package com.application.pedidoapi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest()
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
class PedidoapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
