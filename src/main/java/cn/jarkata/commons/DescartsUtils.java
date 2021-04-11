package cn.jarkata.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescartsUtils {

    private final List<Map<String, String>> dataMapList = new ArrayList<>();


    public DescartsUtils descarts(List<String> dataList, String key) {
        List<Map<String, String>> dataMapListTmp = new ArrayList<>(dataMapList);
        dataMapList.clear();
        if (dataMapListTmp.isEmpty()) {
            //集合为空
            for (String datVal : dataList) {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put(key, datVal);
                dataMapList.add(dataMap);
            }
            return this;
        }

        for (Map<String, String> map : dataMapListTmp) {
            for (String val : dataList) {
                Map<String, String> dataMap = new HashMap<>(map);
                dataMap.put(key, val);
                dataMapList.add(dataMap);
            }
        }
        return this;
    }

    public List<Map<String, String>> getDataMapList() {
        return dataMapList;
    }
}
