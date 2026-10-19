package ru.strong.springoraclejournal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service

public class ConsoleEmailService implements EmailService{
    @Override
    public void sendRegistrationEmail(String toEmail, String userName) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("📧 ПИСЬМО НА: " + toEmail);
        System.out.println("ТЕМА: Добро пожаловать в FoodShop!");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Здравствуйте, " + userName + "!\n");
        System.out.println("Вы успешно зарегистрировались в сервисе FoodShop.");
        System.out.println("Теперь вы можете делать заказы, пополнять баланс и многое другое.\n");
        System.out.println("С уважением, команда FoodShop.");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    @Override
    public void sendPaymentReceipt(String toEmail, String userName, Integer orderId, String amount, String orderDetails) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("📧 ПИСЬМО НА: " + toEmail);
        System.out.println("ТЕМА: Чек по заказу #" + orderId);
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Здравствуйте, " + userName + "!\n");
        System.out.println("Ваш заказ #" + orderId + " успешно оплачен.\n");
        System.out.println("Сумма: " + amount + " руб.\n");
        System.out.println("Состав заказа:");
        System.out.println(orderDetails);
        System.out.println("\nСпасибо за покупку!");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    @Override
    public void sendVerificationCode(String toEmail, String userName, String code) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("📧 ПИСЬМО НА: " + toEmail);
        System.out.println("ТЕМА: Код подтверждения пополнения баланса");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Здравствуйте, " + userName + "!\n");
        System.out.println("Ваш код подтверждения: " + code + "\n");
        System.out.println("Никому не сообщайте этот код!");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }


}
