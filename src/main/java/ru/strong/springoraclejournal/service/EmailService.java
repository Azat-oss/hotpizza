package ru.strong.springoraclejournal.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendRegistrationEmail(String toEmail, String userName);
    void sendPaymentReceipt(String toEmail, String userName, Integer orderId, String amount, String orderDetails);
    void sendVerificationCode(String toEmail, String userName, String code);
}
