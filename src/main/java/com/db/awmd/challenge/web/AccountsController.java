package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficentFundsException;
import com.db.awmd.challenge.exception.SameAccountTransferException;
import com.db.awmd.challenge.service.AccountsService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is Account controller use to create account,verify account and fund transfers
 * @author Rahul
 *
 */

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
	  
	  
    log.info("Creating account {}", account);
    

    try {
    this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }
  
  /**
   * This method is responsible for account transactions 
   * @param transferRequest
   * @return {@link ResponseEntity}
   */
  @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> makeTransfer(@RequestBody @Valid TransferRequest transferRequest) {
      log.info("Transaction initiated {}");

      try {
          this.accountsService.postTransaction(transferRequest);
      }  catch (SameAccountTransferException | InsufficentFundsException ex) {
    	  log.error("Error Occured while transaction {}  " +ex.getMessage());
          return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
      }catch (AccountIdNotFoundException anfe) {
    	  log.error("Error Occured while transaction {}  " +anfe.getMessage());
          return new ResponseEntity<>(anfe.getMessage(), HttpStatus.NOT_FOUND);
      } 
      log.info("Transaction completed with status {}" + HttpStatus.CREATED);
      return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
