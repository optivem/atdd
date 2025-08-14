package com.optivem.atdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles("e2e") // TODO: VJ: Maybe delete this later
class AtddApplicationTests {

	@Test
	void contextLoads() {
	}

}
