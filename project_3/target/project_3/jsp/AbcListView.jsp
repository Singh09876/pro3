<%@page import="in.co.rays.project_3.controller.AbcListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.dto.AbcDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.model.RoleModelInt"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/129.jpg');
	background-size: 100%;
		  background-attachment: fixed;
	
}

.p1 {
	padding: 4px;
	width: 200px;
	font-size: bold;
}

.text {
	text-align: center;
}

</style>
</head>
<nav class="fixed-top">
<%@include file="Header.jsp"%></nav>
<br>
<br>
<br>
<body class="hm">





		<%@include file="calendar.jsp" %>


	<div>
		<form class="pb-5" action="<%=ORSView.ABC_LIST_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AbcDTO"
				scope="request"></jsp:useBean>
			


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<AbcDTO> it = list.iterator();
				if (list.size() != 0) {
			%>
			<center>
				<h1 class="text-dark font-weight-bold pt-3">
					<u>Member List</u>
				</h1>
			</center>
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<div class="row">

				<div class="col-sm-2"></div>
				<div class="col-sm-2">
					<input type="text" name="name" placeholder="Enter Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("name", request)%>">
				</div>
				&emsp;
				<div class="col-sm-2">
					<input type="text" name="login" placeholder="Enter Login Id"
						class="form-control"
						value="<%=ServletUtility.getParameter("login", request)%>">
				</div>
				&emsp;
				<%-- <div class="col-sm-2"><%=HTMLUtility.getList("Role", String.valueOf(dto.getRoleId()), list1)%></div>
 --%>
                &emsp;
               
				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 15px" name="operation"
						value="<%=AbcListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
						name="operation" value="<%=AbcListCtl.OP_RESET%>">
				</div>
				<div class="col-sm-2"></div>
			</div>

			</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-bordered table-dark table-hover">
					<thead>
						<tr style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th width="5%" class="text">S.NO</th>
							<th width="15%" class="text">Name</th>
							<th width="15%" class="text">Address</th>
							<th width="20%" class="text">LoginId</th>
							<th width="10%" class="text">Duretion</th>
							<th width="10%" class="text">Contact</th>
							<th width="10%" class="text">Pan</th>
							<th width="10%" class="text">M-Fees</th>
							<th width="5%" class="text">Edit</th>
						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();

					%>
					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"
								></td>
							<td class="text"><%=index++%></td>
							<td class="text"><%=dto.getName()%></td>
							<td class="text"><%=dto.getAddress()%></td>
							<td class="text"><%=dto.getLogin()%></td>
							<td class="text"><%=dto.getDuretion()%></td>
							<td class="text"><%=dto.getMobileNo()%></td>
							<td class="text"><%=dto.getPan()%></td>
							<td class="text"><%=dto.getmFees()%></td>
							<td class="text"><a href="AbcCtl?id=<%=dto.getId()%>"
								>Edit</a></td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=AbcListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=AbcListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=AbcListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						style="padding: 5px;" value="<%=AbcListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr></tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<center>
				<h1 style="font-size: 40px; color: #162390;">User List</h1>
			</center>
			</br>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>




				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div style="padding-left: 48%;">
					<input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=AbcListCtl.OP_BACK%>">
				</div>

				<div class="col-md-4"></div>
			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>


	</div>










































<%@include file="FooterView.jsp"%>



</body>
</html>