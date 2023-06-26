package com.itheima.travel.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回结果
 */
@Data
public class Result implements Serializable {

    private Boolean success;  // 是否操作成功  true表示操作成功, false表示操作失败
    private String message;   // 操作信息
    private Object data;      // 后端返回给前端的数据

    // 一个参数构造
    public Result(Boolean success) {
        this.success = success;
    }

    // 二个参数构造
    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // 二个参数构造方法
    public Result(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    // 三个参数构造
    public Result(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
