<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/suggestion.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/cssload.css'/>">
<script
	src="<c:url value='/resource/js/lib/jquery.autocomplete.min.js'/>"></script>
<c:if test="${not empty tableSetting.script}">
	<script src="<c:url value='/resource/js/${tableSetting.script}.js'/>"></script>
	<script src="<c:url value='/resource/js/batchUserOps.js'/>"></script>
</c:if>
<div class="container">

	<c:if test="${not empty tableSetting.tableTitle}">

		<div style="text-align: center;">
			<h4>
				<spring:message code="${tableSetting.tableTitle}" />
			</h4>
		</div>

		<div class="dataTable_wrapper">
			<div class="dropdown" id="actionList"
				style="float: Left; margin-right: 5px;">
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
			</div>

			<table id="example"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<c:forEach items="${tableSetting.columns}" var="entry">
							<th><spring:message code="${entry.value.title}" /></th>
						</c:forEach>
					</tr>
				</thead>
				<tfoot style="display: table-header-group">
					<tr>
						<c:forEach items="${tableSetting.columns}" var="entry"
							varStatus="status">
							<c:if test="${entry.value.type eq 'search'}">
								<th>
									<div class="form-group">
										<div class="col-md-12" style="padding: 0">
											<input type="hidden" id="searchTypeIndex${entry.key}"
												name="category" value="like" /> <input maxlength="50"
												id="inputIndex${entry.key}" class="form-control" type="text"
												placeholder="<spring:message code="${entry.value.title}" />" />
										</div>
									</div>
								</th>
							</c:if>
							<c:if test="${entry.value.type eq 'label'}">
								<th>
									<div class="form-group">
										<div class="col-md-12" style="padding: 0"></div>
									</div>
								</th>
							</c:if>

						</c:forEach>
						<th><input type="submit" id="bth-search"
							class="btn btn-sm btn-primary" value="Пошук" /></th>
					</tr>
				</tfoot>
				<tbody id="user-list">
					<c:if test="${not empty userList}">
						<c:forEach items="${userList}" var="user">
							<tr>
								<td><input class="logindata" type="hidden" name="userLogin"
									value="${user.login}" />${user.lastName}</td>
								<td>${user.firstName}</td>
								<td>${user.middleName}</td>
								<td>${user.email}</td>
								<td class="role"><spring:message
										code="label.admin.userlist.role_${user.role}" /></td>
								<td class="community">${user.territorialCommunity}</td>
								<td><a
									href="<c:url value='/administrator/users/edit-registrated-user/?login=${user.login}'/>"
									class="btn btn-sm btn-primary" role="button">Профіль</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>

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

<%--AJAX Loader for the dark display--%>
<div id="dark_bg">
	<div class="windows8">
		<div class="wBall" id="wBall_1">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_2">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_3">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_4">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_5">
			<div class="wInnerBall"></div>
		</div>
	</div>
</div>

<script>
var table;
var communityModal = $("#userCommunitySelectModal");
var actions = $("#actionList");

jQuery(document).ready(function($) {
    table = $('#example').DataTable({
         "searching": false,
         "bSort" : true,
         "bDestroy": true,
         "order": [[ 2, "asc" ]],
         "oLanguage" : {
             "sEmptyTable" : jQuery.i18n.prop('dataTable.sEmptyTable'),
             "sInfo" : jQuery.i18n.prop('dataTable.sInfo'),
             "sInfoEmpty" : jQuery.i18n.prop('dataTable.sInfoEmpty'),
             "sInfoFiltered" : jQuery.i18n.prop('dataTable.sInfoFiltered'),
             "oPaginate" : {
                 "sFirst" : jQuery.i18n.prop('dataTable.sFirst'),
                 "sLast" : jQuery.i18n.prop('dataTable.sLast'),
                 "sNext" : jQuery.i18n.prop('dataTable.sNext'),
                 "sPrevious" : jQuery.i18n.prop('dataTable.sPrevious')
             },
             "sLengthMenu" : jQuery.i18n.prop('dataTable.sLengthMenu'),
             "sLoadingRecords" : jQuery.i18n.prop('dataTable.sLoadingRecords'),
             "sProcessing" : jQuery.i18n.prop('dataTable.sProcessing'),
             "sSearch" : jQuery.i18n.prop('dataTable.sSearch'),
             "sZeroRecords" : jQuery.i18n.prop('dataTable.sZeroRecords')
         },
         "serverSide": true,
         "aoColumns" : [        
              <c:forEach items="${tableSetting.columns}" var="entry" varStatus="status">
                  <c:if test="${entry.value.type eq 'search'}">
                      {
                       "sTitle" : "<spring:message code="${entry.value.title}" />",
                       "sClass": "${entry.value.data}",
                       "mData" : '${entry.key}'
                      },
                  </c:if>
                  <c:if test="${entry.value.type eq 'button'}">
                      {
                       "sTitle" : "<spring:message code="${entry.value.title}" />",
                       "mData" : "action",
                       "bSortable": false,
                       "defaultContent": '<button  class="btn btn-sm btn-primary" id="${entry.value.buttonId}">Профiль</button>'               
                      },
                  </c:if>
                  <c:if test="${entry.value.type eq 'label'}">
                      {
                        "sTitle" : "<spring:message code="${entry.value.title}" />",
                        "mData" : '${entry.key}',
                        "bSortable": false,
                        "sClass": "${entry.value.data}",
                        "mRender": function ( data, type, full ) {
                            return  jQuery.i18n.prop("msg.role."+data);
                         }
                      },
                 </c:if>
              </c:forEach>
              
             ],
             "ajax": {
                "url":"${tableSetting.url}",
                "type":"POST",
                dataType: "json",
                contentType: 'application/json; charset=utf-8',
                "data": function ( data ) {
                  addSearchValue(data);
                $("#example_wrapper").prepend(actions);
                  return JSON.stringify(data);
                }
            }
     }); 
    
    $("#example_wrapper").prepend(actions);
    
    function addSearchValue(data) {
        data.tableName = '${tableSetting.tableName}';
        console.log("data.tableName = "+data.tableName);
        for (var i = 0; i < data.columns.length; i++) {
            column = data.columns[i];
            column.search.compareSign = $('#searchTypeIndex'+i).val();
            column.search.value = $('#inputIndex'+i).val();
        }
    }
  
    
});
</script>