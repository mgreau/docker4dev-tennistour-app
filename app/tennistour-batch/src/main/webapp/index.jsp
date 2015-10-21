<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Batch - Simple Chunk</title>
    </head>
    <body>
        <h1>Java EE 7 - Batch Application - Simple Chunk</h1>
        <h2>View all players and tournaments : </h2>
        <ul>
        	<li><a href="${pageContext.request.contextPath}/list">All players and tournaments</a></li>
        </ul>
         <hr/>
        <h2>Load tournaments files : </h2>
        <ul>
        	<li>Load the <a href="${pageContext.request.contextPath}/executeJob?tt=frenchopen_ATP_2013.csv">French ATP 2013</a> file.</li>
        	<li>Load the <a href="${pageContext.request.contextPath}/executeJob?tt=frenchopen_WTA_2013.csv">French WTA 2013</a> file.</li>
			<li>Load the <a href="${pageContext.request.contextPath}/executeJob?tt=usopen_ATP_2011.csv">US Open ATP 2011</a> file.</li>
			<li>Load the <a href="${pageContext.request.contextPath}/executeJob?tt=usopen_ATP_2012.csv">US Open ATP 2012</a> file.</li>
        </ul>
    </body>
</html>
