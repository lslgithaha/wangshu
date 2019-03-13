package com.wangshu.common.result;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：接口返回值统一结果格式
 * 版本：1.0.0
 */
public class Result {
    private Integer status; //状态码
    private Integer pageSize = 0; //分页大小
    private Integer pageNumber = 0; //当前页码
    private Integer totalPages = 0; //总页码
    private Long totalElements = 0L; //总记录
    private String message; // 提示信息
    private Object data; // 数据

    public Result(ResultEnum resultEnum) {
        this.status = resultEnum.getCode();
        this.message = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, String msg) {
        this.status = resultEnum.getCode();
        this.message = msg;
    }

    public Result change(ResultEnum resultEnum){
        this.status = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        return this;
    }
    public Result change(ResultEnum resultEnum, String msg){
        this.status = resultEnum.getCode();
        this.message = msg;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
