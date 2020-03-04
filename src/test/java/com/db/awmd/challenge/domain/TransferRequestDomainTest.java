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
		TransferRequest transferRequest = new TransferRequest("Debit-123","Credit-123",new BigDecimal("20.00"));
		
		assertThat(transferRequest.getFromAccountId()).isEqualTo("Debit-123");
		assertThat(transferRequest.getToAccountId()).isEqualTo("Credit-123");
		assertThat(transferRequest.getAmount()).isEqualTo(new BigDecimal("20.00"));
		
		
	}
	@Test
	public void createTransferSetRequest(){
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setAmount(new BigDecimal("20.00"));
		transferRequest.setFromAccountId("Debit-123");
		transferRequest.setToAccountId("Credit-123");
		transferRequest.toString();
		assertThat(transferRequest.getFromAccountId()).isEqualTo("Debit-123");
		assertThat(transferRequest.getToAccountId()).isEqualTo("Credit-123");
		assertThat(transferRequest.getAmount()).isEqualTo(new BigDecimal("20.00"));
		
		
	}
}
