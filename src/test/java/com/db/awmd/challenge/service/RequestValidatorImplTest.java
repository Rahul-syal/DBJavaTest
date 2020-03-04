package com.db.awmd.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.InsufficentFundsException;
import com.db.awmd.challenge.exception.SameAccountTransferException;


public class RequestValidatorImplTest {

    private RequestValidator requestValidator;

    @Before
    public void setUp() {
    	requestValidator = new RequestValidatorImpl();
    }

    @Test
    public void validate_should_throwException_when_accountFromNotFound() throws Exception {
        final Account accountTo = new Account("Id-2");
        final TransferRequest transfer = new TransferRequest("Id-1", accountTo.getAccountId(), new BigDecimal("2.00"));

        try {
        	requestValidator.validateRequest(null, new Account("Id-2"), transfer);
        } catch (AccountIdNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Debit AccountId-1 not found");
        }
    }

    @Test
    public void validate_should_throwException_when_accountToNotFound() throws Exception {
        final Account accountFrom = new Account("Id-1");
        final TransferRequest transfer = new TransferRequest("Id-1", "Id-5342", new BigDecimal("2.00"));

        try {
        	requestValidator.validateRequest(accountFrom, null, transfer);
        } catch (AccountIdNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Credit AccountId-5342 not found");
        }
    }

    @Test
    public void validate_should_throwException_when_NotEnoughFunds() throws Exception {
        final Account accountFrom = new Account("Id-1");
        final Account accountTo = new Account("Id-2");
        final TransferRequest transfer = new TransferRequest("Id-1", "Id-2", new BigDecimal("2.00"));

        try {
        	requestValidator.validateRequest(accountFrom, accountTo, transfer);
        } catch (InsufficentFundsException nbe) {
            assertThat(nbe.getMessage()).isEqualTo("Insufficent Funds !! Available Balance: 0");
        }
    }

    @Test
    public void validate_should_throwException_when_transferBetweenSameAccount() throws Exception {
        final Account accountFrom = new Account("Id-1", new BigDecimal("20.00"));
        final Account accountTo = new Account("Id-1");
        final TransferRequest transfer = new TransferRequest("Id-1", "Id-1", new BigDecimal("2.00"));

        try {
        	requestValidator.validateRequest(accountFrom, accountTo, transfer);
        } catch (SameAccountTransferException sae) {
            assertThat(sae.getMessage()).isEqualTo("Transfer between same accounts not allowed");
        }
    }

    @Test
    public void validate_should_allowValidTransferBetweenAccounts() throws Exception {
        final Account accountFrom = new Account("Id-1", new BigDecimal("20.00"));
        final Account accountTo = new Account("Id-2");
        final TransferRequest transfer = new TransferRequest("Id-1", "Id-2", new BigDecimal("2.00"));

        requestValidator.validateRequest(accountFrom, accountTo, transfer);
    }

}