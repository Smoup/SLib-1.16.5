package splug.slib.utils.strings;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public class StringsFormater {


    /**
    * @param charTo символ который будет использован для выравнивания
     * @param borders отступ по краям строки
    */
    public static List<String> center(List<String> strings, String charTo, int borders) {
        final List<String> out = new ArrayList<>();
        final int maxLength = getMaxLength(strings) + (borders * 2);
        for (final String s : strings) {
            final int compensate = maxLength - s.length();
            if (compensate == 0) {
                out.add(s);
                continue;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(charTo.repeat(compensate / 2));
            sb.append(s);
            sb.append(charTo.repeat(compensate / 2));
            if (compensate % 2 != 0) {
                sb.append(charTo);
            }
            out.add(sb.toString());
        }
        return out;
    }

    private static int getMaxLength(List<String> strings) {
        int max = 0;
        for (final String string : strings) {
            if (string.length() > max) max = string.length();
        }
        return max;
    }
}
