package cn.com.qws.service.base;

import cn.com.qws.common.DataGridOptions;
import cn.com.qws.common.PagerModel;
import cn.com.qws.utils.StringUtils;
import org.beetl.sql.core.engine.PageQuery;

public class ServiceUtil {

    /**
     * 将分页查询转为dataTable结构
     *
     * @param query
     * @return
     */
    public static PagerModel queryToTable(PageQuery query) {

        PagerModel tb = new PagerModel();
        tb.setRows(query.getList());
        tb.setTotal((int) query.getTotalRow());
        return tb;
    }

    /**
     * 将dataTable传过来的参数转为分页查询
     *
     * @param opts
     * @return
     */
    public static PageQuery pageToQuery(DataGridOptions opts) {
        PageQuery query = new PageQuery();
        query.setPageSize(opts.getLength());
        query.setPageNumber(opts.getPageNumber());
        query.setParas(opts.getParams());
        if (!StringUtils.isEmpty(opts.getOrder())) {
            query.setOrderBy(opts.getOrder());
        }
        return query;
    }
}
