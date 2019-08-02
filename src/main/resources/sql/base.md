delete
===
@ /*
    删除
@ */

    DELETE FROM #text(table)# WHERE id = #id#


count
===
@ /*
    查询记录数
@ */

    SELECT COUNT(1) FROM #text(table)#  WHERE #text(field)# = #value# 
    @if(!isEmpty(id)){
    AND id != #id#
    @}







