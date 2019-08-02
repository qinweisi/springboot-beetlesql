
findByCondition
===
@ /*
    根据条件查询
@ */

	select #use("baseCols")# FROM t_role a WHERE  #use("condition")#

baseCols
===
@ /*
    基础字段
@ */

	a.id,a.remark,a.role_code,a.role_desc,a.role_name

condition
===
@ /*
    动态条件
@ */

	1 = 1
	@if(!isEmpty(id)){
	 AND a.id = #id#
	@}
	@if(!isEmpty(remark)){
	 AND a.remark = #remark#
	@}
	@if(!isEmpty(roleCode)){
	 AND a.role_code = #roleCode#
	@}
	@if(!isEmpty(roleDesc)){
	 AND a.role_desc = #roleDesc#
	@}
	@if(!isEmpty(roleName)){
	 AND a.role_name = #roleName#
	@}


updateSample
===
@ /*
    动态更新
@ */

	@if(!isEmpty(id)){
	 AND a.id = #id#
	@}
	@if(!isEmpty(remark)){
	 AND a.remark = #remark#
	@}
	@if(!isEmpty(roleCode)){
	 AND a.role_code = #roleCode#
	@}
	@if(!isEmpty(roleDesc)){
	 AND a.role_desc = #roleDesc#
	@}
	@if(!isEmpty(roleName)){
	 AND a.role_name = #roleName#
	@}

updateByCondition
===
@ /*
    根据动态条件动态更新
@ */

    UPDATE t_role a SET #use("updateSample")# WHERE #use("condition")#