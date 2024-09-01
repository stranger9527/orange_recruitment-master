package com.hdu.orange_recruitment.constants;

import java.util.HashMap;
import java.util.Map;

public class SpecialityConstants {

    private static final Map<String, Integer> speciality2Id = new HashMap<>();

    private static final Map<Integer, String> id2Speciality = new HashMap<>();

    static {
        speciality2Id.put("计算机科学与技术", 0);
        speciality2Id.put("软件工程", 1);
        id2Speciality.put(0, "计算机科学与技术");
        id2Speciality.put(1, "软件工程");
    }

    public static Map<String, Integer> getSpecialityList(){
        return speciality2Id;
    }

}
