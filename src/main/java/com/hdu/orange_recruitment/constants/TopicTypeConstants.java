package com.hdu.orange_recruitment.constants;

import java.util.HashMap;
import java.util.Map;

public class TopicTypeConstants {

    private static final Map<String, Integer> type2Id = new HashMap<>();

    private static final Map<Integer, String> Id2Type = new HashMap<>();

    static {
        type2Id.put("结合生产实际或经济建设", 0);
        type2Id.put("结合科研", 1);
        type2Id.put("结合实验室建设", 2);
        type2Id.put("教师自拟", 3);
        type2Id.put("其他", 4);

        Id2Type.put(0, "结合生产实际或经济建设");
        Id2Type.put(1, "结合科研");
        Id2Type.put(2, "结合实验室建设");
        Id2Type.put(3, "教师自拟");
        Id2Type.put(4, "其他");
    }

    public static Map<String, Integer> getTypeList(){
        return type2Id;
    }

    public static String getTypeById(int id){
        return Id2Type.get(id);
    }
}
