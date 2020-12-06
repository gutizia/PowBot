package gutizia.util;

public class Bank {
    private static boolean needToBank = false;

    public static void setNeedToBank(boolean needToBank) {
        Bank.needToBank = needToBank;
    }

    public static boolean needToBank() {
        return needToBank;
    }
}
