package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

	void createAccount(Account account) throws DuplicateAccountIdException;

	Account getAccount(String accountId);

	void clearAccounts();

	/**
	 * This Method doing actual business transaction and update balance in
	 * debit and credit accounts
	 * 
	 * @param debitAccount
	 * @param creditAccount
	 * @param transferRequest
	 * @return
	 */
	boolean doTransaction(Account debitAccount, Account creditAccount, TransferRequest transferRequest);
}
