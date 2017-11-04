package child.ryl.child.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A {
    Genericity genericity = new Genericity();

    public void way() {
        List<String> strings = new ArrayList<>();
        strings.add("dddd");
        strings.add("dddd");
        strings.add("dddd");
        strings.add("dddd");
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("i:" + i, "s:" + i);
            maps.add(map1);
        }
        genericity.setData(strings, maps);
    }
}
