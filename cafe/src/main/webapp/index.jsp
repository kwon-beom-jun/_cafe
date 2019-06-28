<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kitri.cafe.member.model.MemberDto"%>
<%
response.sendRedirect(request.getContextPath() + "/boardadmin/boardmenu");

MemberDto memberDto = new MemberDto();
memberDto.setId("hihihidf");
memberDto.setName("권범준입");
memberDto.setEmail("qjawns0617@naver.com");
session.setAttribute("userInfo",memberDto);
%>

