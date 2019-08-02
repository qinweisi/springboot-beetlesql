package cn.com.qws.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author qinweisi
 * @Description 分页查询工具
 **/
public class DataGridOptions {

    private int start ;
    private int length;
    private int pageNumber;
    private String sort = "create_time";
    private String order = "desc";

    private Map<String,Object> params = new HashMap<String,Object>();


    public DataGridOptions() {
    }

    public DataGridOptions(int length, int start, int pageNumber) {

        this.length = length;
        this.start = start;
        this.pageNumber = pageNumber;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
