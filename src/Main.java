import javax.swing.SwingUtilities;
// запуск программы
// 12
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CipherGUI gui = new CipherGUI();
            gui.setVisible(true);
        });
    }
}
