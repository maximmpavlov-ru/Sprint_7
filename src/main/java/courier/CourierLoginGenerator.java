package courier;

import java.util.concurrent.ThreadLocalRandom;

public class CourierLoginGenerator {

    public static String generateRandomLogin(){
        int min = 1, max = 1000;
        return ("SuperFastCourier" + ThreadLocalRandom.current().nextInt(min, max + 1));
    }
}
