import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Temp {
    public static void main(String[] args) throws IOException {

        Map<Character, Integer> unsortedMap = new HashMap<>();

        unsortedMap.put('А', 155);
        unsortedMap.put('О', 180);
        unsortedMap.put('Т', 120);
        unsortedMap.put('Н', 145);
        unsortedMap.put('Е', 160);
        unsortedMap.put('С', 130);
        unsortedMap.put('И', 140);
        unsortedMap.put(' ', 220);
        unsortedMap.put('Л', 135);

        unsortedMap.entrySet().forEach(System.out::println);

        System.out.println("___________________________");
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

        sortedMap.entrySet().forEach(System.out::println);

    }
}