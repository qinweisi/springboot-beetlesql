package cn.com.qws.dto.system;

import io.swagger.annotations.ApiModel;

/**
 * @Author qinweisi
 * @Description 角色信息
 **/
@ApiModel("角色信息")
public class RoleDto {

	private String remark ;
	private String roleCode ;
	private String roleDesc ;
	private String roleName ;

	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public String getRoleCode(){
		return  roleCode;
	}
	public void setRoleCode(String roleCode ){
		this.roleCode = roleCode;
	}
	
	public String getRoleDesc(){
		return  roleDesc;
	}
	public void setRoleDesc(String roleDesc ){
		this.roleDesc = roleDesc;
	}
	
	public String getRoleName(){
		return  roleName;
	}
	public void setRoleName(String roleName ){
		this.roleName = roleName;
	}

}
