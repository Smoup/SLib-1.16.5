package splug.slib.utils.number;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass @SuppressWarnings("unused")
public class RomanNumbersUtils {

    private static final String[] thousands = {"", "M", "MM", "MMM"};
    private static final String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    private static final Map<Character, Integer> romanToIntMap = new HashMap<>(
            Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50,
                    'C', 100, 'D', 500, 'M', 1000));

    public static int toInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = romanToIntMap.getOrDefault(s.charAt(i), 0);
            int next = i < s.length() - 1 ? romanToIntMap.getOrDefault(s.charAt(i + 1), 0) : 0;

            if (current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    public static String toRoman(int number) {
        if (number <= 0 || number > 3999)
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");

        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                units[number % 10];
    }
}
