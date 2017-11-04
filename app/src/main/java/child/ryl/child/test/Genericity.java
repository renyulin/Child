package child.ryl.child.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 */
public class Genericity<T> {
    public void setData(List<T>... list) {
        Log.e("Genericity-->T-list", list + "-length-"+list.length);
        List<Map<String, Object>> mapd = (List<Map<String, Object>>) list[1];
        List<String> strings = (List<String>) list[0];
        for (String s : strings) {
            Log.e("Genericity-->s-map", s);
        }
        for (Map<String, Object> map : mapd) {
            Log.e("Genericity-->map-map", map.toString());
        }
    }

    private void addData() {
        List<T> strings = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        strings.add((T) map);
        strings.add((T) "ss");
        setData(strings);
    }
}

