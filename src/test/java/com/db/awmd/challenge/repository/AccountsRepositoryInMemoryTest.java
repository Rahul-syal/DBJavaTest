package com.db.awmd.challenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;

public class AccountsRepositoryInMemoryTest {

	private AccountsRepository accountsRepository;

	@Before
	public void setUp() {
		accountsRepository = new AccountsRepositoryInMemory();
	}

	@Test
	public void doTransactionTest() throws Exception {

		accountsRepository.createAccount(new Account("Debit-123", new BigDecimal("150")));
		accountsRepository.createAccount(new Account("Credit-123", BigDecimal.ZERO));

		Account debitAccount = accountsRepository.getAccount("Debit-123");
		Account creditAccount = accountsRepository.getAccount("Credit-123");

		TransferRequest transferRequest = new TransferRequest("Debit-123", "Credit-123", new BigDecimal("20"));

		accountsRepository.doTransaction(debitAccount, creditAccount, transferRequest);

		assertBalance("Debit-123", new BigDecimal("130"));
		assertBalance("Credit-123", new BigDecimal("20"));
	}

	private void assertBalance(String accountId, BigDecimal balance) {
		assertThat(accountsRepository.getAccount(accountId).getBalance()).isEqualTo(balance);
	}

}