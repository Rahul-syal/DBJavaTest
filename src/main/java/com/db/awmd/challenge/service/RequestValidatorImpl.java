package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.InsufficentFundsException;
import com.db.awmd.challenge.exception.SameAccountTransferException;

@Component
public class RequestValidatorImpl implements RequestValidator{

	@Override
	public void validateRequest(Account debitAccount, Account creditAccount,TransferRequest transferRequest)
			throws InsufficentFundsException, SameAccountTransferException, AccountIdNotFoundException {
		
		if(debitAccount == null){
			throw new AccountIdNotFoundException("Debit Account" + transferRequest.getFromAccountId() + " not found");
		} if(creditAccount == null){
			throw new AccountIdNotFoundException("Credit Account" + transferRequest.getToAccountId() + " not found");
		} if(transferRequest.getFromAccountId().equals(transferRequest.getToAccountId())){
			throw new SameAccountTransferException("Transfer between same accounts not allowed");
		} if(!inSufficentFunds(debitAccount, transferRequest)){
			throw new InsufficentFundsException("Insufficent Funds !! Available Balance: " + debitAccount.getBalance());
		}
			
		
	}
	public boolean inSufficentFunds(Account debitAccount,TransferRequest transferRequest ){
		return debitAccount.getBalance().subtract(transferRequest.getAmount()).compareTo(BigDecimal.ZERO) >=0;
		
	}

}
