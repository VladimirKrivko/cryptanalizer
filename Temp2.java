import java.nio.charset.StandardCharsets;

public class Temp2 {
    public static void main(String[] args) {
        byte[] alphabetRus = "AБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ".getBytes();
        for (byte rus : alphabetRus) {
            System.out.print((char) rus);
        }
    }
}
