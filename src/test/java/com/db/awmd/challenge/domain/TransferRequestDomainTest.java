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
public class TransferRequestDomainTest {
	
	@Test
	public void createTransferRequest(){
		TransferRequest TransferRequest = new TransferRequest("Debit-123","Credit-123",new BigDecimal("20.00"));
		assertThat(TransferRequest.getFromAccountId()).isEqualTo("Debit-123");
		assertThat(TransferRequest.getToAccountId()).isEqualTo("Credit-123");
		assertThat(TransferRequest.getAmount()).isEqualTo(new BigDecimal("20.00"));
		
		
	}

}
