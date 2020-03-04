package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficentFundsException;
import com.db.awmd.challenge.exception.SameAccountTransferException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This Service class is responsible to execute service layer functionalities
 * like create Accounts, Get Accounts and Accounts transactions
 * 
 * @author Rahul
 *
 */
@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	private RequestValidator requestValidator;
	@Getter
	private NotificationService notificationService;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
		this.accountsRepository = accountsRepository;
		this.notificationService = notificationService;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	/**
	 * This method is responsible for transactions between account funds
	 * deduction from Debit account and funds credited in credit account.
	 * For certain Business conditions it may throw below mentioned business exceptions
	 * based on the inputs receive from request or while validating the accounts funds during transactions
	 * After successful transactions it is sending notifications to end user.
	 * 
	 * @param transferRequest
	 * @throws InsufficentFundsException
	 * @throws SameAccountTransferException
	 * @throws AccountIdNotFoundException
	 */
	public void postTransaction(TransferRequest transferRequest)
			throws InsufficentFundsException, SameAccountTransferException, AccountIdNotFoundException {

		synchronized (this) {
			final Account debitAccount = accountsRepository.getAccount(transferRequest.getFromAccountId());
			final Account creditAccount = accountsRepository.getAccount(transferRequest.getToAccountId());
		
			requestValidator.validateRequest(debitAccount, creditAccount, transferRequest);

			boolean response = accountsRepository.doTransaction(debitAccount, creditAccount, transferRequest);
			if (response) {
				notificationService.notifyAboutTransfer(debitAccount,
						"The transfer to the account with ID " + creditAccount.getAccountId()
								+ " is now complete for the amount of " + transferRequest.getAmount() );
				notificationService.notifyAboutTransfer(creditAccount,
						"The account with ID + " + debitAccount.getAccountId() + " has transferred "
								+ transferRequest.getAmount() + " into your account.");

			}
		}

	}
}
