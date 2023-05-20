package ninyo.questionone.model;

import java.util.Map;

public class Digit {

    private char[] bits;
    private boolean illegal;
    private static Map<Integer, Integer> decoding = Map.of(
            126, 0,
            48, 1,
            109, 2,
            121, 3,
            51, 4,
            91, 5,
            95, 6,
            112, 7,
            127, 8,
            123, 9
    );

    public Digit() {
        bits = new char[7];
        for (int i = 0; i < 7; i++) {
            bits[i] = '0';
        }
        illegal = false;
    }

    public void setBit(int index) {
        bits[index] = '1';
    }

    public boolean isIllegal() {
        return "?".equals(toString());
    }

    public void setIllegal(boolean illegal) {
        this.illegal = illegal;
    }

    public String toString() {
        if (illegal) {
            return "?";
        }
        int decimal = Integer.parseInt(String.valueOf(bits), 2);
        int decoded = decode(decimal);
        if (decoded == -1) {
            return "?";
        }
        return Integer.toString(decoded);
    }

    private int decode(int decimal) {
        return decoding.containsKey(decimal) ? decoding.get(decimal) : -1;
    }
}
