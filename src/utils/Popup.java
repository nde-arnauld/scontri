package utils;

public class Popup {

    public static void toPrint(boolean result, String if_ok, String if_bad) {
        if (result) {
            System.out.println("\n" + if_ok);
        } else {
            System.out.println("\n" + if_bad);
        }
    }
}
