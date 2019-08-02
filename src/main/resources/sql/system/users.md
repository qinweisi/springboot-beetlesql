
findByCondition
===
@ /*
    根据条件查询
@ */

	select #use("baseCols")# FROM t_users a WHERE  #use("condition")#

baseCols
===
@ /*
    基础字段
@ */

	a.id,a.login_name,a.name,a.company_name,a.company_code,a.create_time,a.create_user,a.update_time,a.update_user

condition
===
@ /*
    动态条件
@ */

	1 = 1
	@if(!isEmpty(id)){
	 AND a.id = #id#
	@}
	@if(!isEmpty(loginName)){
	 AND a.login_name = #loginName#
	@}
	@if(!isEmpty(password)){
	 AND a.password = #password#
	@}
	@if(!isEmpty(name)){
	 AND a.name = #name#
	@}
	@if(!isEmpty(companyName)){
	 AND a.company_name = #companyName#
	@}
	@if(!isEmpty(companyCode)){
	 AND a.company_code = #companyCode#
	@}
	@if(!isEmpty(createTime)){
	 AND a.create_time = #createTime#
	@}
	@if(!isEmpty(createUser)){
	 AND a.create_user = #createUser#
	@}
	@if(!isEmpty(updateTime)){
	 AND a.update_time = #updateTime#
	@}
	@if(!isEmpty(updateUser)){
	 AND a.update_user = #updateUser#
	@}


updateSample
===
@ /*
    动态更新
@ */

	@if(!isEmpty(id)){
	 AND a.id = #id#
	@}
	@if(!isEmpty(loginName)){
	 AND a.login_name = #loginName#
	@}
	@if(!isEmpty(password)){
	 AND a.password = #password#
	@}
	@if(!isEmpty(name)){
	 AND a.name = #name#
	@}
	@if(!isEmpty(companyName)){
	 AND a.company_name = #companyName#
	@}
	@if(!isEmpty(companyCode)){
	 AND a.company_code = #companyCode#
	@}
	@if(!isEmpty(createTime)){
	 AND a.create_time = #createTime#
	@}
	@if(!isEmpty(createUser)){
	 AND a.create_user = #createUser#
	@}
	@if(!isEmpty(updateTime)){
	 AND a.update_time = #updateTime#
	@}
	@if(!isEmpty(updateUser)){
	 AND a.update_user = #updateUser#
	@}

updateByCondition
===
@ /*
    根据动态条件动态更新
@ */

    UPDATE t_users a SET #use("updateSample")# WHERE #use("condition")#
    
updateById
===
@ /*
    根据id更新
@ */

    UPDATE t_users a SET #use("updateSample")# WHERE a.id = #id#

selectByLoginName
===
@ /*
    根据用户名查询用户信息
@ */

    select a.* from t_users a where a.login_name = #loginName#

queryPage
===
@ /*
    分页
@ */

    SELECT 
        @pageTag() {  
            #use("baseCols")# 
        @}  
    FROM t_users a WHERE #use("condition")#
   