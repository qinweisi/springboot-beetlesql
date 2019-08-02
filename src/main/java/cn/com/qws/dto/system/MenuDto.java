package cn.com.qws.dto.system;

import io.swagger.annotations.ApiModel;

/**
 * @Author qinweisi
 * @Description 菜单
 **/
@ApiModel("菜单信息")
public class MenuDto {

	// 排序
	private Integer orderNo ;
	// 父级菜单
	private Integer parentId ;
	// 菜单名称
	private String name ;
	// 状态（1：显示  0；隐藏）
	private String state ;
	// 菜单地址
	private String url ;
	
	/**
	* 排序
	*@return 
	*/
	public Integer getOrderNo(){
		return  orderNo;
	}
	/**
	* 排序
	*@param  orderNo
	*/
	public void setOrderNo(Integer orderNo ){
		this.orderNo = orderNo;
	}
	
	/**
	* 父级菜单
	*@return 
	*/
	public Integer getParentId(){
		return  parentId;
	}
	/**
	* 父级菜单
	*@param  parentId
	*/
	public void setParentId(Integer parentId ){
		this.parentId = parentId;
	}
	
	/**
	* 菜单名称
	*@return 
	*/
	public String getName(){
		return  name;
	}
	/**
	* 菜单名称
	*@param  name
	*/
	public void setName(String name ){
		this.name = name;
	}
	
	/**
	* 状态（1：显示  0；隐藏）
	*@return 
	*/
	public String getState(){
		return  state;
	}
	/**
	* 状态（1：显示  0；隐藏）
	*@param  state
	*/
	public void setState(String state ){
		this.state = state;
	}
	
	/**
	* 菜单地址
	*@return 
	*/
	public String getUrl(){
		return  url;
	}
	/**
	* 菜单地址
	*@param  url
	*/
	public void setUrl(String url ){
		this.url = url;
	}
	

}
