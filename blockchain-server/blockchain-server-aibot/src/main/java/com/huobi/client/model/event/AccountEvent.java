package com.huobi.client.model.event;

import java.util.LinkedList;
import java.util.List;

import com.huobi.client.model.AccountChange;
import com.huobi.client.model.enums.AccountChangeType;

/**
 * The account change information received by subscription of account.
 */
public class AccountEvent {

  private long timestamp = 0;
  private AccountChangeType changeType = AccountChangeType.INVALID;
  private List<AccountChange> accountChangeList = new LinkedList<>();

  /**
   * Get the UNIX formatted timestamp generated by server in UTC.
   *
   * @return The timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * The event that asset change notification related.
   *
   * @return The change type, see {@link AccountChangeType}
   */
  public AccountChangeType getChangeType() {
    return changeType;
  }

  /**
   * Get the detail of account change.
   *
   * @return The list of account change, see {@link AccountChange}
   */
  public List<AccountChange> getData() {
    return accountChangeList;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setChangeType(AccountChangeType changeType) {
    this.changeType = changeType;
  }

  public void setData(List<AccountChange> accountChangeList) {
    this.accountChangeList = accountChangeList;
  }
}
