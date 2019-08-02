package cn.com.qws.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qinweisi
 * @Description 登录/注册信息
 **/
@ApiModel("登录信息")
public class LoginDto {

    // 登录账号
    @ApiModelProperty(value = "登录账号", required = true)
    private String loginName;
    // 密码
    @ApiModelProperty(value = "密码", required = true)
    private String password;


    /**
     * 登录账号
     *
     * @return
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 登录账号
     *
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 密码（密文）
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码（密文）
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
