package cn.com.qws.common;

import java.util.List;

/**
 * @Author qinweisi
 * @Description 分页模型
 **/
public class PagerModel implements java.io.Serializable{

    private long total = 0;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
