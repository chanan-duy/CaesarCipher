import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CipherGUI extends JFrame {

    public static String nameTest = "ddadhjaldlhka";

    private JComboBox<String> languageCombo;
    private JTextField inputFileField, outputFileField, keyField;
    private JButton encryptButton, decryptButton;
    private JTextArea statusArea;

    private CaesarCipher cipher;

    public CipherGUI() {
        setTitle("Шифр Цезаря");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(30, 60, 120);
                Color color2 = new Color(90, 30, 150);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        gradientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel languageLabel = new JLabel("Выберите язык:");
        languageLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        gradientPanel.add(languageLabel, gbc);

        languageCombo = new JComboBox<>(new String[]{"Русский", "English"});
        gbc.gridx = 1;
        gradientPanel.add(languageCombo, gbc);

        JLabel inputFileLabel = new JLabel("Имя входного файла:");
        inputFileLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        gradientPanel.add(inputFileLabel, gbc);

        inputFileField = new JTextField(30);
        gbc.gridx = 1;
        gradientPanel.add(inputFileField, gbc);

        JLabel outputFileLabel = new JLabel("Имя выходного файла:");
        outputFileLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        gradientPanel.add(outputFileLabel, gbc);

        outputFileField = new JTextField(30);
        gbc.gridx = 1;
        gradientPanel.add(outputFileField, gbc);

        JLabel keyLabel = new JLabel("Ключ:");
        keyLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        gradientPanel.add(keyLabel, gbc);

        keyField = new JTextField(5);
        gbc.gridx = 1;
        gradientPanel.add(keyField, gbc);

        encryptButton = new JButton("Зашифровать");
        gbc.gridx = 0; gbc.gridy = 4;
        gradientPanel.add(encryptButton, gbc);

        decryptButton = new JButton("Расшифровать");
        gbc.gridx = 1;
        gradientPanel.add(decryptButton, gbc);

        statusArea = new JTextArea(5, 50);
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gradientPanel.add(scrollPane, gbc);

        add(gradientPanel);

        initCipher();

        languageCombo.addActionListener(e -> initCipher());

        encryptButton.addActionListener(e -> processFile(true));
        decryptButton.addActionListener(e -> processFile(false));
    }

    private void initCipher() {
        String lang = (String) languageCombo.getSelectedItem();
        Alphabet alphabet = "English".equalsIgnoreCase(lang) ? new Alphabet("EN") : new Alphabet("RU");
        cipher = new CaesarCipher(alphabet);
    }

    private void processFile(boolean encrypt) {
        String inputFile = inputFileField.getText().trim();
        String outputFile = outputFileField.getText().trim();
        String keyText = keyField.getText().trim();

        if (inputFile.isEmpty() || outputFile.isEmpty() || keyText.isEmpty()) {
            statusArea.setText("Пожалуйста, заполните все поля.");
            return;
        }

        int key;
        try {
            key = Integer.parseInt(keyText);
        } catch (NumberFormatException ex) {
            statusArea.setText("Ключ должен быть числом.");
            return;
        }

        if (!cipher.validateKey(key)) {
            statusArea.setText("Ключ вне допустимого диапазона для выбранного языка.");
            return;
        }

        try {
            if (!java.nio.file.Files.exists(java.nio.file.Paths.get(inputFile))) {
                statusArea.setText("Входной файл не найден: " + inputFile);
                return;
            }

            String text = FileUtil.readFile(inputFile);
            String processedText = cipher.processText(text, key, encrypt);
            FileUtil.writeFile(outputFile, processedText);
            statusArea.setText(encrypt ? "Файл успешно зашифрован." : "Файл успешно расшифрован.");
        } catch (IOException e) {
            statusArea.setText("Ошибка при работе с файлами: " + e.getMessage());
        }
    }
}
