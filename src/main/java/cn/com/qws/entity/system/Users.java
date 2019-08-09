package cn.com.qws.entity.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


/**
 * @Author qinweisi
 * @Description 用户信息表
 **/
@Table(name = "environment_test.t_users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {

    // pk
    private Long id;
    // 创建人id
    private Long createUser;
    // 最近修改人id
    private Long updateUser;
    // 企业信用代码（唯一）
    private String companyCode;
    // 企业名称
    private String companyName;
    // 登录账号
    private String loginName;
    // 名称
    private String name;
    // 密码（密文）
    private String password;
    // 创建时间（新增时无需set）
    private Date createTime;
    // 最近修改时间
    private Date updateTime;

    /**
     * pk
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * pk
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建人id
     *
     * @return
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人id
     *
     * @param createUser
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 最近修改人id
     *
     * @return
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 最近修改人id
     *
     * @param updateUser
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 企业信用代码（唯一）
     *
     * @return
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 企业信用代码（唯一）
     *
     * @param companyCode
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 企业名称
     *
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 企业名称
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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
     * 名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 创建时间（新增时无需set）
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间（新增时无需set）
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 最近修改时间
     *
     * @return
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最近修改时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
