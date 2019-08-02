
findByCondition
===
@ /*
    根据条件查询
@ */

	select #use("baseCols")# FROM t_menu a WHERE  #use("condition")#
	
baseCols
===
@ /*
    基础字段
@ */

	a.id,a.name,a.parent_id,a.url,a.order_no,a.state
	
condition
===
@ /*
    动态条件
@ */

	1 = 1  
	@if(!isEmpty(id)){
	 AND id=#id#
	@}
	@if(!isEmpty(name)){
	 AND name = #name#
	@}
	@if(!isEmpty(parentId)){
	 AND parent_id = #parentId#
	@}
	@if(!isEmpty(url)){
	 AND url = #url#
	@}
	@if(!isEmpty(orderNo)){
	 AND order_no = #orderNo#
	@}
	@if(!isEmpty(state)){
	 AND state = #state#
	@}
	
updateSample
===
@ /*
    动态更新
@ */

    @if(!isEmpty(name)){
     AND name = #name#
    @}
    @if(!isEmpty(parentId)){
     AND parent_id = #parentId#
    @}
    @if(!isEmpty(url)){
     AND url = #url#
    @}
    @if(!isEmpty(orderNo)){
     AND order_no = #orderNo#
    @}
    @if(!isEmpty(state)){
     AND state = #state#
    @}

updateByCondition
===
@ /*
    根据动态条件动态更新
@ */

    UPDATE t_menu SET #use("updateSample")# WHERE #use("condition")#

findAll
===
* 查询全部(这行注释不能采用beetlsql的注释方法，否则语句报错)

    SELECT #use("baseCols")# FROM t_menu a

findByState
===
@ /*
    根据状态查询列表
@ */

    SELECT #use("baseCols")# FROM t_menu a WHERE a.state = #state#
    
findByUserId
===
@ /*
    根据用户id查询列表
@ */

    SELECT #use("baseCols")# FROM t_menu a,t_user_role b,t_role_menu c WHERE a.id = c.menu_id AND b.role_id = c.role_id AND a.state = 1 AND b.user_id = #userId#