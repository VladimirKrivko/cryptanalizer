import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Cipher {
    final private String fileInputName;
    final private String fileOutputName;
    final int encryptionKey;

    private int biasKey;

    final private char[] alphabetRus = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У'
            , 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л'
            , 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '"', ':', '-', '!', '?', ' '};

    public Cipher (String fileInputName, int encryptionKey) {
        this.fileInputName = fileInputName;
        this.encryptionKey = encryptionKey;

        this.fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".")) +
                "_ENCRYPT" + fileInputName.substring(fileInputName.lastIndexOf("."));
    }

    public void encrypt() throws IOException {
        try (FileInputStream fis = new FileInputStream(fileInputName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileWriter fw = new FileWriter(fileOutputName)) {


            int b;
            while((b = fis.read()) != -1) {
                baos.write(b);
            }
            String s = baos.toString();

            char[] text = s.toCharArray();
            char[] result = new char[text.length];

            int key = encryptionKey;

            boolean isAdded;

            for (int i = 0; i < text.length; i++) {
                isAdded = false;
                for (int j = 0; j < alphabetRus.length; j++) {
                    if (text[i] == alphabetRus[j]) {
                        int index = (j + key) % 74;
                        if (index < 0) {
                            index += 74;
                        }
                        result[i] = alphabetRus[index];
                        isAdded = true;
                    }
                }
                if (!isAdded) {
                    result[i] = text[i];
                }
            }
            fw.write(result);
        }
        System.out.println("Файл сохранен по адресу: " + fileOutputName);
    }

    public String getFileOutputName() {
        return fileOutputName;
    }
}
