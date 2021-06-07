package com.huobi.client.model.enums;

import com.huobi.client.impl.utils.EnumLookup;

/**
 * SPOT, MARGIN, OTC, POINT, UNKNOWN.
 */
public enum AccountType {
  SPOT("spot"),
  MARGIN("margin"),
  SUPER_MARGIN("super-margin"),
  OTC("otc"),
  POINT("point"),
  MINEPOOL("minepool"),
  UNKNOWN("unknown");

  private final String code;

  AccountType(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<AccountType> lookup = new EnumLookup<>(AccountType.class);

  public static AccountType lookup(String name) {
    return lookup.lookup(name);
  }

}
