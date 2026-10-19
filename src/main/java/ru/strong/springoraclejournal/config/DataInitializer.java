package ru.strong.springoraclejournal.config;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.strong.springoraclejournal.model.Category;
import ru.strong.springoraclejournal.model.Product;
import ru.strong.springoraclejournal.model.Role;
import ru.strong.springoraclejournal.model.User;
import ru.strong.springoraclejournal.repository.ProductRepository;
import ru.strong.springoraclejournal.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component

public class DataInitializer  implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker = new Faker(new Random(42));
    private final Random random = new Random(42);

    public DataInitializer(UserRepository userRepository,
                           ProductRepository productRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedUsers();
        }
        if (productRepository.count() == 0) {
            seedProducts();
        }
    }

    private void seedUsers() {
        User admin = User.builder()
                .name("Администратор")
                .email("admin@shop.ru")
                .password(passwordEncoder.encode("admin123"))
                .phone("+7-999-000-00-00")
                .address("Москва, ул. Админская, 1")
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);

        User demo = User.builder()
                .name("Иван Пользователь")
                .email("user@shop.ru")
                .password(passwordEncoder.encode("user123"))
                .phone("+7-999-111-22-33")
                .address("Москва, ул. Пушкина, д. 10, кв. 5")
                .role(Role.USER)
                .build();
        userRepository.save(demo);

        for (int i = 0; i < 5; i++) {
            User u = User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress().toLowerCase())
                    .password(passwordEncoder.encode("password123"))
                    .phone("+7-" + faker.numerify("###-###-##-##"))
                    .address(faker.address().streetAddress())
                    .role(Role.USER)
                    .build();
            userRepository.save(u);
        }
    }

    private void seedProducts() {
        // Пиццы (реальные фото пиццы)
        List<String> pizzaNames = List.of(
                "Маргарита", "Пепперони", "Четыре сыра", "Гавайская",
                "Барбекю", "Мексиканская", "Морская", "Цезарь"
        );
        String[] pizzaImages = {
                "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1571066811602-716837d681de?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400&h=300&fit=crop"
        };
        for (int i = 0; i < pizzaNames.size(); i++) {
            String name = pizzaNames.get(i);
            productRepository.save(Product.builder()
                    .name("Пицца " + name)
                    .description(buildPizzaDescription(name))
                    .price(BigDecimal.valueOf(450 + random.nextInt(500)))
                    .category(Category.PIZZA)
                    .imageUrl(pizzaImages[i])
                    .available(true)
                    .build());
        }

        // Суши (реальные фото суши)
        List<String> sushiNames = List.of(
                "Филадельфия", "Калифорния", "Сяке-маки", "Унаги-маки",
                "Эби-маки", "Дракон", "Запечённый ролл", "Сет Ассорти"
        );
        String[] sushiImages = {
                "https://images.unsplash.com/photo-1579871494447-981b9027843b?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1553621042-f6e147245754?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1611143669185-af224c5e3252?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1583623025817-d180a2221d0a?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1579871494447-981b9027843b?w=400&h=300&fit=crop"
        };
        for (int i = 0; i < sushiNames.size(); i++) {
            String name = sushiNames.get(i);
            productRepository.save(Product.builder()
                    .name("Ролл " + name)
                    .description(buildSushiDescription(name))
                    .price(BigDecimal.valueOf(300 + random.nextInt(600)))
                    .category(Category.SUSHI)
                    .imageUrl(sushiImages[i])
                    .available(true)
                    .build());
        }

        // Напитки (реальные фото напитков)
        List<String> drinkNames = List.of(
                "Кока-кола", "Спрайт", "Апельсиновый сок", "Минеральная вода",
                "Зелёный чай", "Молочный коктейль", "Лимонад домашний", "Кофе латте"
        );
        String[] drinkImages = {
                "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1625772299848-391b6a87d7b3?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1548839140-29c7ba4429c6?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1627435601181-ec50ee6d746e?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1523677011781-c91d1bbe2f9e?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400&h=300&fit=crop"
        };
        for (int i = 0; i < drinkNames.size(); i++) {
            String name = drinkNames.get(i);
            productRepository.save(Product.builder()
                    .name(name)
                    .description(buildDrinkDescription(name))
                    .price(BigDecimal.valueOf(80 + random.nextInt(220)))
                    .category(Category.DRINKS)
                    .imageUrl(drinkImages[i])
                    .available(true)
                    .build());
        }

        // Десерты (реальные фото десертов)
        List<String> dessertNames = List.of(
                "Тирамису", "Чизкейк", "Шоколадный фондан", "Морковный торт",
                "Панна-котта", "Эклеры", "Мороженое", "Яблочный пирог"
        );
        String[] dessertImages = {
                "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1622372738917-8d1d7f5f1e9e?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1622896778536-1a196f1295ed?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1567206563064-6f60f40a2b57?w=400&h=300&fit=crop",
                "https://images.unsplash.com/photo-1568571780765-9276ac8b75a4?w=400&h=300&fit=crop"
        };
        for (int i = 0; i < dessertNames.size(); i++) {
            String name = dessertNames.get(i);
            productRepository.save(Product.builder()
                    .name(name)
                    .description(buildDessertDescription(name))
                    .price(BigDecimal.valueOf(180 + random.nextInt(320)))
                    .category(Category.DESSERTS)
                    .imageUrl(dessertImages[i])
                    .available(true)
                    .build());
        }
    }

    private String buildPizzaDescription(String name) {
        return switch (name) {
            case "Маргарита" -> "Классическая итальянская пицца: томатный соус, моцарелла, базилик, оливковое масло.";
            case "Пепперони" -> "Острая пицца с пепперони, моцареллой и томатным соусом.";
            case "Четыре сыра" -> "Моцарелла, пармезан, горгонзола, чеддер на тонкой основе.";
            case "Гавайская" -> "Ветчина, ананасы, моцарелла, томатный соус.";
            case "Барбекю" -> "Курица в соусе барбекю, лук, моцарелла, томатный соус.";
            case "Мексиканская" -> "Острая пицца с халапеньо, говядиной, перцем чили.";
            case "Морская" -> "Креветки, мидии, кальмары, моцарелла, соус песто.";
            case "Цезарь" -> "Курица, листья салата, пармезан, соус Цезарь.";
            default -> "Авторская пицца от шеф-повара.";
        };
    }

    private String buildSushiDescription(String name) {
        return switch (name) {
            case "Филадельфия" -> "Классический ролл с лососем, сливочным сыром и огурцом.";
            case "Калифорния" -> "Краб, авокадо, огурец, икра тобико.";
            case "Сяке-маки" -> "Макки-маки с нежным лососем.";
            case "Унаги-маки" -> "Угорь, унаги-соус, кунжут.";
            case "Эби-маки" -> "Тигровые креветки, авокадо, рис.";
            case "Дракон" -> "Угорь, авокадо, огурец, икра tobiko, унаги-соус.";
            case "Запечённый ролл" -> "Горячий ролл под сырным соусом с морепродуктами.";
            case "Сет Ассорти" -> "Большой сет из 16 кусочков разных роллов.";
            default -> "Свежий ролл, приготовленный из качественных ингредиентов.";
        };
    }

    private String buildDrinkDescription(String name) {
        return switch (name) {
            case "Кока-кола" -> "Освежающий газированный напиток, 0.5 л.";
            case "Спрайт" -> "Лимонно-лаймовый газированный напиток, 0.5 л.";
            case "Апельсиновый сок" -> "Свежевыжатый апельсиновый сок, 0.3 л.";
            case "Минеральная вода" -> "Газированная минеральная вода, 0.5 л.";
            case "Зелёный чай" -> "Тёплый зелёный чай с жасмином.";
            case "Молочный коктейль" -> "Сливочный молочный коктейль с мороженым.";
            case "Лимонад домашний" -> "Домашний лимонад с мятой и лимоном.";
            case "Кофе латте" -> "Ароматный латте на эспрессо и молочной пене.";
            default -> "Освежающий напиток.";
        };
    }

    private String buildDessertDescription(String name) {
        return switch (name) {
            case "Тирамису" -> "Итальянский десерт с маскарпоне, кофе и какао.";
            case "Чизкейк" -> "Нежный чизкейк с ягодным соусом.";
            case "Шоколадный фондан" -> "Горячий шоколадный кекс с жидкой начинкой.";
            case "Морковный торт" -> "Влажный морковный торт со сливочным кремом.";
            case "Панна-котта" -> "Сливочный десерт с ванилью и ягодами.";
            case "Эклеры" -> "Французские пирожные с заварным кремом.";
            case "Мороженое" -> "Мороженое с топингом на выбор.";
            case "Яблочный пирог" -> "Домашний яблочный пирог с корицей.";
            default -> "Сладкий десерт к чаю.";
        };
    }
}
