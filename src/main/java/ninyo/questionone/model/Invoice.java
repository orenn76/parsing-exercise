package ninyo.questionone.model;

public class Invoice {

    private Digit[] digits;
    private boolean illegal;

    public Invoice() {
        digits = new Digit[9];
        illegal = false;
    }

    public void addDigit(int index, Digit digit) {
        digits[index] = digit;
    }

    public Digit getDigit(int index) {
        return digits[index];
    }

    public boolean isIllegal() {
        if (illegal || hasIllegalDigit()) {
            return true;
        }
        return false;
    }

    public void setIllegal(boolean illegal) {
        this.illegal = illegal;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digits.length; i++) {
            builder.append(digits[i].toString());
        }
        if (isIllegal()) {
            builder.append(" ILLEGAL");
        }
        return builder.toString();
    }

    private boolean hasIllegalDigit() {
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != null && digits[i].isIllegal()){
                return true;
            }
        }
        return false;
    }



}
