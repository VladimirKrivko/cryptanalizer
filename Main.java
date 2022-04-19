import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        welcomeScreen();

        String choice;
        while(!"exit".equalsIgnoreCase(choice = reader.readLine())) {
            switch (choice) {
                case "1" -> {
                    System.out.println("Введите путь файла для шифрования.");
                    String pathFile = reader.readLine();
                    Path fileInputName;
                    try {
                        fileInputName = Path.of(pathFile);
                        if (!Files.exists(fileInputName) || pathFile.equals("")) {
                            throw new IOException();
                        }
                        System.out.println("Введите ключ шифрования. Запомните его, он понадобится для расшифровки файла.");
                        int key = Encrypt.getEncryptKey(reader.readLine());
                        Encrypt encrypt = new Encrypt(pathFile, key);
                        encrypt.encrypt();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Указанный файл не существует.\n_________________________");
                    }
                }
                case "2" -> {
                    System.out.println("Введите путь файла для расшифровки.");
                    String pathFile = reader.readLine();
                    Path fileInputName;
                    try {
                        fileInputName = Path.of(pathFile);
                        if (!Files.exists(fileInputName) || pathFile.equals("")) {
                            throw new IOException();
                        }
                        System.out.println("Введите ключ шифрования.");
                        int key = Decrypt.getEncryptKey(reader.readLine());
                        Decrypt decrypt = new Decrypt(pathFile, key);
                        decrypt.decrypt();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Указанный файл не существует.\n_________________________");
                    }
                }
                case "3" -> {
                    System.out.println("Брут-форс.");
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
                "Введи 3 - если надо рачшифровать файл без ключа (brute force).\n" +
                "Введи exit - для выхода из программы.");
    }
}