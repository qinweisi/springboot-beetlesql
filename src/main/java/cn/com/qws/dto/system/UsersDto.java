package cn.com.qws.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qinweisi
 * @Description 用户基本信息
 **/
@ApiModel("用户基本信息")
public class UsersDto {

    // 企业信用代码（唯一）
    @ApiModelProperty("企业信用代码")
    private String companyCode;
    // 企业名称
    @ApiModelProperty("企业名称")
    private String companyName;
    // 名称
    @ApiModelProperty("真实姓名")
    private String name;

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

}
