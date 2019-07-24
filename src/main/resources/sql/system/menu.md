
baseCols
===
	a.id,a.name,a.parent_id,a.url,a.order_no,a.state
	
condition
===
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

findAll
===
    SELECT #use("baseCols")# FROM t_menu a

updateByCondition
===
    UPDATE t_menu SET #use("updateSample")# WHERE #use("condition")#

findByState
===
    SELECT #use("baseCols")# FROM t_menu a WHERE a.state = #state#
    
findByUserId
===
    SELECT #use("baseCols")# FROM t_menu a,t_user_role b,t_role_menu c WHERE a.id = c.menu_id AND b.role_id = c.role_id AND a.state = 1 AND b.user_id = #userId#