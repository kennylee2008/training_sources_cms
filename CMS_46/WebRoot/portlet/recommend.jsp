<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/backend/common/taglib.jsp" %>

	<div class="right" style="height:200px">
		<div class="right_topic_1">
			推荐阅读
		</div>
		<div class="right_topic_2">
			<a href="recommend_all.jsp"><img src="images/more_red.gif" style="float:right;margin-top:10px;border:0px"></a>
		</div>
		<div class="right_topic_3">
		<c:forEach items="${recommend}" var="c">
			· <a href="article.jsp?articleId=${c.id }">${c.title }</a> <br/>
		</c:forEach>
		</div>
	</div>
	