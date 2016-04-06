<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<script src="<c:url value='/resource/js/batchUserOps.js'/>"></script>

<div class="dropdown" id="actionList"
 style="float: Left; margin-right: 5px;">
 
 <c:if test="${statusType eq 'ACTIVE'}">
 <a id="dLabel" role="button" data-toggle="dropdown"
  class="btn btn-xs btn-primary" data-target="#" href="#"><spring:message
   code="label.modal.actions" /> <span class="caret"></span> </a>
 <ul class="dropdown-menu multi-level" role="menu"
  aria-labelledby="dropdownMenu">
  <li class="dropdown-submenu"><a href="#"><spring:message
     code="label.modal.setRole" /></a>
   <ul class="dropdown-menu">
    <c:forEach items="${roleTypes}" var="role">
     <li><a class="set-role" href="#${role.type}"
      val="${role.type}"><spring:message
        code="label.admin.userlist.role_${role.type}" /></a></li>
    </c:forEach>

   </ul></li>

  <li><a href="#" class="set-community"><spring:message
     code="label.modal.setCommunity" /></a></li>
  <li><a href="#" class="reset-password"><spring:message
     code="label.modal.resetPassword" /></a></li>
 </ul>
 </c:if>
 
  <c:if test="${statusType eq 'NOTCOMFIRMED'}">
 <a id="dLabel" role="button" data-toggle="dropdown"
  class="btn btn-xs btn-primary" data-target="#" href="#"><spring:message
   code="label.modal.actions" /> <span class="caret"></span> </a>
 <ul class="dropdown-menu multi-level" role="menu"
  aria-labelledby="dropdownMenu">

  <li><a href="#" class="notcomfirmrd-user" id = "DELETE">Видалити</a></li>
  <li><a href="#" class="notcomfirmrd-user" id = "SENDEMAILAGAIN">Надіслати email щераз</a></li>
 </ul>
 </c:if>
 
 <c:if test="${statusType eq 'BLOCK'}">
     <a id="dLabel" role="button" data-toggle="dropdown"
  class="btn btn-xs btn-primary" data-target="#" href="#"><spring:message
   code="label.modal.actions" /> <span class="caret"></span> </a>
 <ul class="dropdown-menu multi-level" role="menu"
  aria-labelledby="dropdownMenu">
    <li><a href="#">Розблокувати</a></li>
 </ul>
 </c:if>
 </div>
 
 <div id="modalWindow" class="form-horizontal">
 <div id="userCommunitySelectModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal">&times;</button>
     <h4 class="modal-title">
      <spring:message code="label.registrator.enterData" />
     </h4>
     <label class="control-label"><spring:message
       code="label.community.title" />: </label> <input id="tc_search"
      class="form-control input-md" type="text">
    </div>
    <div class="control-group error"></div>
    <div class="modal-footer">
     <button class="btn btn-info" data-dismiss="modal">
      <spring:message code="label.modal.cancel" />
     </button>
     <button class="submit btn btn-success">
      <spring:message code="label.modal.confirm" />
     </button>
    </div>
   </div>
  </div>
 </div>
</div>
