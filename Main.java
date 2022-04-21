import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        welcomeScreen();

        String choice;
        while (!"exit".equalsIgnoreCase(choice = reader.readLine())) {
            switch (choice) {
                case "1" -> {
                    try {
                        Cipher encrypt = new Cipher(getPathFile(), getEncryptKey());
                        String text = encrypt.getTextFromFile();
                        String encryptText = encrypt.encrypt(text);
                        encrypt.pushTextToFile(encryptText);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Указанный файл не существует.\n" +
                                "_______________________________________");
                    }
                }
                case "2" -> {
                    try {
                        Cipher encrypt = new Cipher(getPathFile(), -getEncryptKey());
                        String text = encrypt.getTextFromFile();
                        String encryptText = encrypt.encrypt(text);
                        encrypt.pushTextToFile(encryptText);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Указанный файл не существует.\n" +
                                "_______________________________________");
                    }
                }
                case "3" -> {
                    System.out.println("BruteForce:");

                    try {
                        Cipher encrypt = new Cipher(getPathFile(), 0);
                        String text = encrypt.getTextFromFile();
                        String encryptText = encrypt.bruteForce(text);
                        encrypt.pushTextToFile(encryptText);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Указанный файл не существует.\n" +
                                "_______________________________________");
                    }
                }
                default -> {
                    System.out.println("Некорректный ввод. Попробуй еще раз.");
                }
            }
            welcomeScreen();
        }
        reader.close();
    }

    static void welcomeScreen() {
        System.out.println("Введи 1 - если надо зашифровать файл.\n" +
                           "Введи 2 - если надо расшифровать файл.\n" +
                           "Введи 3 - если надо расшифровать файл без ключа (brute force).\n" +
                           "Введи exit - для выхода из программы.");
    }

    static String getPathFile() throws IOException {
        System.out.println("Введите путь файла.");
        String pathFile = reader.readLine();
        Path fileInputName;
        fileInputName = Path.of(pathFile);
        try {
            if (!Files.exists(fileInputName) || pathFile.equals("")) {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return pathFile;
    }

    public static int getEncryptKey() throws IOException {
        System.out.println("Введите ключ шифрования.");
        char[] chars = reader.readLine().toCharArray();
        int key = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                key += chars[i];
            } else {
                key -= chars[i];
            }
        }
        return key;
    }
}