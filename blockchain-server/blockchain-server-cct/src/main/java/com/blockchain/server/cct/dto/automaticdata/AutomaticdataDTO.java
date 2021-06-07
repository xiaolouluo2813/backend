package com.blockchain.server.cct.dto.automaticdata;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class AutomaticdataDTO {
    private String coinName;
    private String unitName;
    private BigDecimal price;
    private BigDecimal amount;
    private String orderType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomaticdataDTO that = (AutomaticdataDTO) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
