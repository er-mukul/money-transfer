package com.mashreq.money.transfer.commons.enums;

public enum MoneyTransferStatus {
    CREDITED, DEBITED, FAILED;

    public static MoneyTransferStatus getByName(String name) {
        try{
            return Enum.valueOf(MoneyTransferStatus.class, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
