package com.company.dto;

public class PaymentRequest {
    private Double amount;
    private String paymentMethod;

    // Getter/Setter
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
