<#include "common/header.ftl">
<div>this content</div>
<form id="addUser" action="${BASE_PATH}/web/user/getUserList">
    <input id="name" class="name" placeholder="姓名"/>
    <input id="mobile" class="mobile" placeholder="电话">
    <input id="submit" class="submit" type="submit" value="提交">
</form>
<#include "common/footer.ftl">