public class CaesarCipher {

    private Alphabet alphabet;

    public CaesarCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String processText(String text, int key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            result.append(alphabet.shiftChar(ch, key, encrypt));
        }
        return result.toString();
    }

    public boolean validateKey(int key) {
        return alphabet.validateKey(key);
    }
}
