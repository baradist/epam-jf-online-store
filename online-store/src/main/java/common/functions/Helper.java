package common.functions;

import java.util.Collection;

/**
 * Created by 1 on 23.04.2016.
 */
public interface Helper {

    public static String ArrayToString(Collection<Integer> array) {
        StringBuilder buffer = new StringBuilder();
        for (Integer integer : array) {
            buffer.append(integer);
            buffer.append(",");
        }
        return buffer.substring(0, buffer.length() - 1);
    }
}
