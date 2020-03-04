package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.InsufficentFundsException;
import com.db.awmd.challenge.exception.SameAccountTransferException;

public interface RequestValidator {
	
	void validateRequest(Account debitAccount, Account creditAccount,TransferRequest transferRequest ) throws InsufficentFundsException,SameAccountTransferException,AccountIdNotFoundException;

}
