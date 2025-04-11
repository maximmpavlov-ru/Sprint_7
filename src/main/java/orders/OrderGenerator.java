package orders;

public class OrderGenerator {

    public static Order generateOrder(String[] colors) {
        return new Order(
                "Василий",
                "Кузнецов",
                "Москва, Красная площадь",
                "Спортивная",
                "+79111234567",
                10,
                "2025-04-16",
                "Содержательный комментарий с какими-то данными",
                colors);
    }
}
