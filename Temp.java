import java.io.*;

public class Temp {
    public static void main(String[] args) throws IOException {
        char[] alphabetRus = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У'
                , 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л'
                , 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '"', ':', '-', '!', '?', ' '};

        String fileInputName = "d:\\file1.txt";
        String fileOutputName = "d:\\file2.txt";


        try (FileInputStream fis = new FileInputStream(fileInputName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileWriter fw = new FileWriter(fileOutputName)) {


            int b;
            while((b = fis.read()) != -1) {
                baos.write(b);
            }
            String s = baos.toString();
            System.out.println(s);

            char[] text = s.toCharArray();
            char[] result = new char[text.length];

            int key = -99;

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
    }
}
