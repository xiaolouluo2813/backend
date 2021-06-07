package com.huobi.client.model;

import java.util.List;

import com.huobi.client.model.enums.AccountType;

/**
 *  sub-account completed info
 */
public class CompleteSubAccountInfo {
  private long id;
  private AccountType type;
  private List<Balance> balances;

  /**
   * get sub-id
   *
   * @return sud-id
   */
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /**
   * get sub type
   *
   * @return sub type
   */
  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  /**
   * get balance list
   *
   * @return The balance list.
   */
  public List<Balance> getBalanceList() {
    return balances;
  }

  public void setBalances(List<Balance> balances) {
    this.balances = balances;
  }


}
