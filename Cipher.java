import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Cipher {
    final private String fileInputName;
    final private String fileOutputName;
    static int encryptKey;

    static final public char[] alphabetRus = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У'
            , 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л'
            , 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ' ', '"', ':', '-', '!', ',', '?'};

    static final public char[] frequencyAlphabetRus = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
            'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', ' '};

    static final public char[] popLetterRus = " оеаинтср".toCharArray();


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

            int countMarker = countFragmentInText(text, popularLetter);

            if (countMarker > marker) {
                marker = countMarker;
                decryptKey = encryptKey;
            }
        }
        encryptKey = decryptKey;
        return encrypt(inputText);
    }

    public int statAnal(char[] popLettersOpenTextAuthor, char[] popLettersCipherText) throws IOException {

        int[] keys = new int[popLettersOpenTextAuthor.length];

        int indexAlphabet;// = 0;
        for (int i = 0; i < popLettersCipherText.length; i++) {

            for (indexAlphabet = 0; indexAlphabet < alphabetRus.length; indexAlphabet++) {      // получить индекс шифросимвола в массиве алфавита.
                if (alphabetRus[indexAlphabet] == popLettersCipherText[i]) {
                    //indexAlphabet = indexAlphabet;
                    System.out.println("Индекс шифробуквы " + popLettersCipherText[i] + " в алфавите: " + indexAlphabet);
                    break;
                }
            }

            for (int j = indexAlphabet, count = 0; j < alphabetRus.length; j++, count++) {
                if (popLettersOpenTextAuthor[i] != popLettersCipherText[i]) {
                    popLettersCipherText[i] = alphabetRus[j];
                    if (j == alphabetRus.length - 1) {
                        j = -1;
                    }
                } else {
                    keys[i] = count - 1;
                    break;
                }
            }
        }

        System.out.println(Arrays.toString(keys));

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

        System.out.println(key);
        return key;
    }


    public static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }

    public String getFileInputName() {
        return fileInputName;
    }

    public String getFileOutputName() {
        return fileOutputName;
    }

    public static void setEncryptKey(int encryptKey) {
        Cipher.encryptKey = encryptKey;
    }

    public char[] frequencyOfLetters (String inputText) {
        String text = inputText;//.toLowerCase();
        char[] popLetterInText = new char[11];

        Map<Character, Integer> unsortedMap = new HashMap<>();
        for (int i = 0; i < alphabetRus.length; i++) {
            unsortedMap.put(alphabetRus[i], countFragmentInText(text, String.valueOf(alphabetRus[i])));
        }
//        unsortedMap.entrySet().forEach(System.out::println);
//        System.out.println("___________________________");
        Map<Character, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));
//        sortedMap.entrySet().forEach(System.out::println);
        int index = 0;
        for (Character popLetter: sortedMap.keySet()) {
            popLetterInText[index] = popLetter;
            index++;
            if (index >= 11) {
                break;
            }
        }
//        System.out.println(new String(popLetterInText));
        return popLetterInText;
    }
}
