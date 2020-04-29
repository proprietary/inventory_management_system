package GUI;

public class MinMaxMismatchException extends Exception {
    public MinMaxMismatchException(int min, int max) {
        super(String.format("Min is supposed to be less than max. Min is %d and max is %d. Change Min to something less than Max, or equivalently, change Max to something greater than Min.", min, max));
    }
}
