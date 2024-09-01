package com.hdu.orange_recruitment.constants;

import java.util.HashMap;
import java.util.Map;

public class ProjectPassConstants {

    private static final Map<String, Integer> pass2Id = new HashMap<>();

    private static final Map<Integer, String> Id2Pass = new HashMap<>();

    static {
        pass2Id.put("未通过", 0);
        pass2Id.put("通过", 1);
        pass2Id.put("未审核", 2);

        Id2Pass.put(0, "未通过");
        Id2Pass.put(1, "通过");
        Id2Pass.put(2, "未审核");
    }

    public static Map<String, Integer> getProjectPassList(){
        return pass2Id;
    }
    public static String getPassResById(int id){
        return Id2Pass.get(id);
    }

    public static int getIdByPassRes(String passRes){
        return pass2Id.get(passRes);
    }
}
