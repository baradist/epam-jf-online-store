package common.functions;

import java.util.Collection;

/**
 * Created by 1 on 23.04.2016.
 */
public class Helper {

    public static String ArrayToString(Collection<Integer> array)
    {

        StringBuilder buffer = new StringBuilder();
//        buffer.append(array[0]);

        for (Integer integer : array) {
            buffer.append(integer);
            buffer.append(",");
        }
        return buffer.substring(0, buffer.length() - 1);
//        return buffer.toString();
    }
}
