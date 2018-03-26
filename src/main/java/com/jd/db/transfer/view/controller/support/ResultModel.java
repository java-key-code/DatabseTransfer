package com.jd.db.transfer.view.controller.support;

/**
 * 功能：
 *
 * @author miaojundong
 */
public class ResultModel {
    /**
     * 响应结果：true(成功)/false(失败)
     */
    private boolean success;
    /**
     * 响应消息提示
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public ResultModel() {}

    public ResultModel(boolean success) {
        this(success, null, null);
    }

    public ResultModel(boolean success, String msg) {
        this(success, msg, null);
    }

    public ResultModel(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
