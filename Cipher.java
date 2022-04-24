import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Cipher {
    final private String fileInputName;
    final private String fileOutputName;
    static int encryptKey;

    static final public char[] alphabetRus = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У'
            , 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л'
            , 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ' ', '"', ':', '-', '!', ',', '?'};


    public Cipher(String fileInputName, int encryptKey) {
        this.fileInputName = fileInputName;
        this.encryptKey = encryptKey;

        this.fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".")) +
                "_CRYPT" + fileInputName.substring(fileInputName.lastIndexOf("."));
    }

    public String getTextFromFile() throws IOException {
        try (FileInputStream fis = new FileInputStream(fileInputName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int b;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            return baos.toString();
        }
    }

    public void pushTextToFile(String text) throws IOException {
        try (FileWriter fw = new FileWriter(fileOutputName)) {
            fw.write(text);
            System.out.println("Файл сохранен по адресу: " + fileOutputName);
        }
    }

    public String encrypt(String inputText) throws IOException {

        char[] text = inputText.toCharArray();
        char[] result = new char[text.length];

        boolean isAdded;

        for (int i = 0; i < text.length; i++) {
            isAdded = false;
            for (int j = 0; j < alphabetRus.length; j++) {
                if (text[i] == alphabetRus[j]) {
                    int index = (j + encryptKey) % alphabetRus.length;
                    if (index < 0) {
                        index += alphabetRus.length;
                    }
                    result[i] = alphabetRus[index];
                    isAdded = true;
                }
            }
            if (!isAdded) {
                result[i] = text[i];
            }
        }
        return new String(result);
    }

    public String bruteForce(String inputText, String popularLetter) throws IOException {

        int decryptKey = 0;
        int marker = 0;
        String text;

        while (encryptKey < alphabetRus.length) {

            encryptKey++;

            text = encrypt(inputText);

            int countMarker = count(text, popularLetter);

            if (countMarker > marker) {
                marker = countMarker;
                decryptKey = encryptKey;
            }
        }
        encryptKey = decryptKey;
        return encrypt(inputText);
    }

    public String statAnal(String inputText) throws IOException {
        String[] popLetter = " оеаин".split("");

        int[] keys = new int[popLetter.length];

        for (int i = 0; i < popLetter.length; i++) {
            bruteForce(inputText.toLowerCase(), popLetter[i]);
            keys[i] = encryptKey;
            encryptKey = 0;
        }

        int key = 0;
        int maxRepeatKey = 0;
        for (int i = 0; i < keys.length; i++) {
            int countRepeatKey = 0;
            for (int j = 0; j < keys.length; j++) {
                if (keys[i] == keys[j]) {
                    countRepeatKey++;
                }
            }
            if (countRepeatKey > maxRepeatKey) {
                maxRepeatKey = countRepeatKey;
                key = keys[i];
            }
        }
        encryptKey = key;
        return encrypt(inputText);
    }


    public static int count(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }

    public String getFileInputName() {
        return fileInputName;
    }

    public String getFileOutputName() {
        return fileOutputName;
    }
}
