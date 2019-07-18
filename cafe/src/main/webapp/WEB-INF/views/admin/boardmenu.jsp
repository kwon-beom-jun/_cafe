<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/template/top.jsp" %>

<style>
.menulist {width : 300px;}
.category {padding: 5px 10px; cursor : pointer; position: relative; 
			font-weight: bold; text-align: left; background-color: #a2a2a2;}
.menu {text-align: left; display: none;}
.menu a{display: block; background-color: #caf7f5; padding: 10px; text-decoration: none;}
.menu a:hover{color: #000000; text-decoration: underline;}
</style>

<script>
$(document).ready(function(){
	$("#boardmenu p.category").click(function() {
		$(this).next("div.menu").slideDown(500).siblings("div.menu").slideUp("slow");
	});
});
</script>

<div class="menulist" id="boardmenu">
	
	<c:set var="idx" value="0" />
	<c:forEach varStatus="i" var="board" items="${boardmenu}">
		
		<c:if test="${idx != board.ccode }">
			
			<%-- <c:forEach varStatus="i" var="board" items="${boardmenu}"> ${i.index}는 0부터 ${i.count}는 1부터 --%>
			<p class="category">${board.cname}</p>
			<c:set var="idx" value="${board.ccode }" /> 
			<div class="menu">
			
		</c:if>
			<!-- 글쓰기로 바로 가지 않고 글목록 부터 가게 만들어두었음. 시험용으로 write로 함. -->
			<!-- board.control = 1~4 그냥 게시판 나머지 5,6,7은 다른 게시판 key word= 검색 내가 작성한 값-->
			<a href="${root }/${board.control}/write?bcode=${board.bcode}&pg=1&key=&word="> 
				${board.bname}
			</a>
			
		<!-- 마지막이 아닐 때에만 -->
		<c:if test="${i.index < boardmenu.size()- 1  }">
			<!-- 다음번 보드시코드 번호를 가져옴 -->
			<c:if test="${idx != boardmenu.get(i.index + 1).ccode }">
				</div>
			</c:if>
		</c:if>
		
	</c:forEach>
	</div> <!-- 마지막 사이즈에 있는 div를 닫음. -->
</div>

<%@ include file="/WEB-INF/views/commons/template/bottom.jsp" %>
    