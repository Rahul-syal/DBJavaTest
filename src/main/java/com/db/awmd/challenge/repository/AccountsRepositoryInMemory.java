package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * This is repository class making actual transaction between business and
 * backend database
 * 
 * @author Rahul
 *
 */
@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public boolean doTransaction(Account debitAccount, Account creditAccount, TransferRequest transferRequest) {

		accounts.computeIfPresent(debitAccount.getAccountId(), (key, account) -> {
			account.setBalance(account.getBalance().add(transferRequest.getAmount().negate()));
			return account;
		});
		accounts.computeIfPresent(creditAccount.getAccountId(), (key, account) -> {
			account.setBalance(account.getBalance().add(transferRequest.getAmount()));
			return account;
		});

		return true;
	}

}
