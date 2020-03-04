package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferRequest {

	@NotNull
	@NotEmpty
	private  String fromAccountId;
	 
	@NotNull
	@NotEmpty
	private  String toAccountId;
	

	@NotNull
	  @Min(value = 0, message = "Amount must be positive.")
	  private BigDecimal amount;
	public TransferRequest(){
		
	}
	
	@JsonCreator
    public TransferRequest(@JsonProperty("fromAccountId") String fromAccountId,
                    @JsonProperty("toAccountId") String toAccountId,
                    @JsonProperty("amount") BigDecimal amount){
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

	@Override
	public String toString() {
		return "TransferRequest [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId + ", amount=" + amount
				+ "]";
	}
	
	
	
}
