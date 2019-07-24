package cn.com.qws.service.system;

import cn.com.qws.common.Constants;
import cn.com.qws.common.ResultJson;
import cn.com.qws.dao.system.MenuDao;
import cn.com.qws.entity.system.Menu;
import cn.com.qws.common.Constants;
import cn.com.qws.common.ResultJson;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 菜单接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Service
public class MenuService {

    @Autowired
    private SQLManager sqlManager;
    @Autowired
    private MenuDao menuDao;

    private static final String SQL_ID = "system/menu.";

    public ResultJson findAll() {
        ResultJson resultJson = new ResultJson();
        try {
            List<Menu> list = sqlManager.select(SQL_ID + "findAll", Menu.class);
            resultJson.setCode(Constants.ResultCode.SUCCESS);
            resultJson.setData(list);
            resultJson.setMsg(Constants.MsgCode.SUCCESS);
        }catch (Exception e){
            resultJson.setCode(Constants.ResultCode.FAIL);
            resultJson.setMsg(e.getMessage());
        }
        return resultJson;
    }

    /**
     * @Author qinweisi
     * @Description 获取状态为显示（state = 1）的菜单
     **/
    public List<Menu> getList(){
        return menuDao.findAll();
    }

    /**
     * @Author qinweisi
     * @Description 根据用户id，查询菜单
     **/
    public List<Menu> getMenusByUserId(Long userId){
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        return sqlManager.select(SQL_ID + "findAll",Menu.class,param);
    }


}
