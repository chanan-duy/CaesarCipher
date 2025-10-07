public class Alphabet {
    public static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String currentAlphabet;
    private int alphabetLength;

    public Alphabet(String language) {
        if ("EN".equalsIgnoreCase(language)) {
            currentAlphabet = ENGLISH_ALPHABET;
        } else {
            currentAlphabet = RUSSIAN_ALPHABET;
        }
        alphabetLength = currentAlphabet.length();
    }

    public char shiftChar(char ch, int key, boolean encrypt) {
        char upperChar = Character.toUpperCase(ch);
        int index = currentAlphabet.indexOf(upperChar);
        if (index == -1) {
            return ch;
        }
        int shift = encrypt ? key : -key;
        int shiftedIndex = (index + shift) % alphabetLength;
        if (shiftedIndex < 0) shiftedIndex += alphabetLength;

        char shiftedChar = currentAlphabet.charAt(shiftedIndex);
        return Character.isUpperCase(ch) ? shiftedChar : Character.toLowerCase(shiftedChar);
    }

    public boolean validateKey(int key) {
        return key >= 1 && key < alphabetLength;
    }
}
