package com.hdu.orange_recruitment.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ERROR = 1;
    private static final int SUCCESS = 0;
    public R() {
        put("code", SUCCESS);
        put("msg", "success");
    }

    public static R error() {
        return error(ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    //成功结果封装
    public static R success(Object object) {
        return ok().put("data", object);
    }
    public static R success(String msg, Object object) {
        R r = new R();
        r.put("msg", msg);
        r.put("data", object);
        return r;
    }
}
