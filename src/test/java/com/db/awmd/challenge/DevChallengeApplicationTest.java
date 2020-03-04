package com.db.awmd.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DevChallengeApplicationTest {
	
	@Test
	   public void main() {
		DevChallengeApplication.main(new String[] {});
	   }

}
