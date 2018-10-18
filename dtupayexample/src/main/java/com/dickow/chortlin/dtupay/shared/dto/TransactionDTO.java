package com.dickow.chortlin.dtupay.shared.dto;

public class TransactionDTO {
    private String merchant;
    private String customer;
    private Integer amount;

    public TransactionDTO(String merchant, String customer, Integer amount) {
        this.merchant = merchant;
        this.customer = customer;
        this.amount = amount;
    }

    public TransactionDTO() {
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
