package com.hdu.orange_recruitment.constants;

import java.util.HashMap;
import java.util.Map;

public class ProgressStatusConstants {

    private static final Map<String, String> status2Id = new HashMap<>();

    private static final Map<String, String> Id2status = new HashMap<>();

    static {
//        type2Id.put("收藏", 0);
        status2Id.put("收藏", "1");
        status2Id.put("沟通过", "2");
        status2Id.put("简历已投递", "3");
        status2Id.put("待面试", "4");
        status2Id.put("待评价", "5");
        status2Id.put("已发offer", "6");
        status2Id.put("确认入职", "7");

//        Id2Type.put(0, "结合生产实际或经济建设");
        Id2status.put("1", "收藏");
        Id2status.put("2", "沟通过");
        Id2status.put("3", "简历已投递");
        Id2status.put("4", "待面试");
        Id2status.put("5", "待评价");
        Id2status.put("6", "已发offer");
        Id2status.put("7", "确认入职");
    }

    public static Map<String, String> getStatusList(){
        return status2Id;
    }

    public static String getStatusById(int id){
        return Id2status.get(id);
    }

    public static String getIdByStatus(String status){ return status2Id.get(status);}
}
