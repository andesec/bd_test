package com.smallworld.data;

import java.util.Objects;

public class Transaction {
  // Represent your transaction data here.

  // Unique identifier of the transaction
  private Integer mtn;

  // Amount of the transaction
  private Double amount;

  //Sender information
  private String senderFullName;
  private Integer senderAge;

  //Beneficiary information
  private String beneficiaryFullName;
  private Integer beneficiaryAge;

  //Issue information
  //If there are no issues in the transaction, issueId = null, issueMessage = null and issueSolved = true
  private Integer issueId;
  private Boolean issueSolved;
  private String issueMessage;

  public Transaction() {
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.mtn);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Transaction other = (Transaction) obj;

    if (mtn.intValue() != other.mtn.intValue())
      return false;

    return true;
  }

  public Integer getMtn() {
    return mtn;
  }

  public Double getAmount() {
    return amount;
  }

  public String getSenderFullName() {
    return senderFullName;
  }

  public Integer getSenderAge() {
    return senderAge;
  }

  public String getBeneficiaryFullName() {
    return beneficiaryFullName;
  }

  public Integer getBeneficiaryAge() {
    return beneficiaryAge;
  }

  public Integer getIssueId() {
    return issueId;
  }

  public Boolean getIssueSolved() {
    return issueSolved;
  }

  public String getIssueMessage() {
    return issueMessage;
  }
}
