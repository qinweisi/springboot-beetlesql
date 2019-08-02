package cn.com.qws.controller.system;

import cn.com.qws.common.ResultJson;
import cn.com.qws.dto.system.UsersDto;
import cn.com.qws.entity.system.Users;
import cn.com.qws.service.system.UsersService;
import cn.com.qws.utils.ConvertUtils;
import cn.com.qws.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户信息控制器
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@RestController
@RequestMapping("/users")
@Api(tags = "用户接口")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * @Author qinweisi
     * @Description 分页
     **/
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "loginName", value = "登录名", paramType = "query", required = false, dataType = "String")
    })
    public ResultJson page(Integer page, Integer limit, String loginName) throws Exception {
        Users entity = new Users();
        if(!StringUtils.isEmpty(loginName)){
            entity.setLoginName(loginName);
        }
        ResultJson resultJson = usersService.page(entity, page, limit);
        return resultJson;
    }

    @GetMapping("query-{id}")
    @ApiOperation("通过id查询单个用户")
    @ApiImplicitParam(name = "id", value = "用户Id", paramType = "query", dataType = "long", required = true)
    public ResultJson findById(@PathVariable("id") Long id) throws Exception {
        return usersService.queryById(id);
    }

    @PutMapping("update-{id}")
    @ApiOperation("通过id修改单个用户")
    @ApiImplicitParam(name = "id", value = "用户Id", paramType = "query", dataType = "long")
    public ResultJson updateById(@PathVariable("id") Long id) throws Exception {
        // ResultJson resultJson = usersService.page(entity, pageIndex, pageSize);
        return null;
    }

    @DeleteMapping("delete-{id}")
    @ApiOperation("通过id删除单个用户")
    @ApiImplicitParam(name = "id", value = "用户Id", paramType = "query", dataType = "long")
    public ResultJson deleteById(@PathVariable("id") Long id) throws Exception {
        return null;
    }

    @PostMapping("save")
    @ApiOperation("保存")
    public ResultJson save(@RequestBody UsersDto dto) throws Exception {
        Users users = ConvertUtils.sourceToTarget(dto,Users.class);
        return usersService.save(users);
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public ResultJson update(@RequestBody UsersDto dto) throws Exception {
        Users users = ConvertUtils.sourceToTarget(dto,Users.class);
        return usersService.update(users);
    }
}

