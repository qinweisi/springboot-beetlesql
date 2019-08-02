package cn.com.qws.entity.system;
import org.beetl.sql.core.annotatoin.Table;


/**
 * @Author qinweisi
 * @Description 角色信息表
 **/
@Table(name="environment_test.t_role")
public class Role {

	private Long id ;
	private String remark ;
	private String roleCode ;
	private String roleDesc ;
	private String roleName ;

	public Long getId(){
		return  id;
	}
	public void setId(Long id ){
		this.id = id;
	}
	
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
