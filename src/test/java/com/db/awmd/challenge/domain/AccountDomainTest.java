package com.db.awmd.challenge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AccountDomainTest {
	
	@Test
	public void createAccount(){
		
		Account account = new Account ("Debit-123", new BigDecimal("0.00"));
		assertThat(account.getAccountId()).isEqualTo("Debit-123");
		assertThat(account.getBalance()).isEqualTo(new BigDecimal("0.00"));
		
	}

}
