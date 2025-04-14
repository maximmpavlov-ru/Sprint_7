package courier;

public class CourierData {

    public static Courier getNormalCourierData(String login) {
        return new Courier(login, "12345", "Vasiliy");

    }

    public static Courier getCourierWithoutLogin() {
        return new Courier(null,"12345", "Vasiliy");
    }
}
