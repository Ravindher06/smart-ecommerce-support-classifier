package com.example.supportclassifier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TicketRequest {
    @NotBlank(message = "customerEmail is required")
    @Email(message = "customerEmail must be a valid email address")
    private String customerEmail;

    @NotBlank(message = "messageBody is required")
    @Size(max = 10000, message = "messageBody must not exceed 10000 characters")
    private String messageBody;

    public TicketRequest() {
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
