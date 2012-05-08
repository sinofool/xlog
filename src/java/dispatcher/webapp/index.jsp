<%@page language="java" import="com.renren.dp.xlog.dispatcher.SystemManager" contentType="text/html; charset=utf-8" %>
<%@page import="com.renren.dp.xlog.storage.NamespaceInfo" %>
<%@page import="java.util.List" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/commons.css" type="text/css" />
<title>xlog dispatcher后台管理</title>
<script>
	function operate(operator){
		window.location.href="/control?operator="+operator;
	}
</script>
</head>
<body style="text-align: center">
<%
	SystemManager sm=new SystemManager();
	int status=sm.getDispatcherStatus();
	String displayStatus="停止";
	String displayStartDate="";
	if(status==1){
		displayStatus="启动";
		displayStartDate=sm.getStartDate();
	}
	String displayVersion=sm.getVersion();
	List<NamespaceInfo> nsInfoList=sm.getNamespaceInfo();
%>
<p align="center"><font size="4">Xlog Dispatcher后台管理</font></p>
<p align="left"><font size="3"><b>&nbsp;基本信息</b></font></p>
<table width="20%" class="tabinfo1" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td nowrap="nowrap">系统状态:</td>
		<td nowrap="nowrap"><%=displayStatus %></td>
		<%
			if(status==0){
		%>
		<td nowrap="nowrap"><input type="button" id="start" name="start" onClick="operate('start');" value="启动"/></td>	
		<td nowrap="nowrap"><input type="button" id="stop" name="stop" value="停止" disabled="true"/></td>
		<%
			}else{
		%>
		<td nowrap="nowrap"><input type="button" id="start" name="start" value="启动"  disabled="true"/></td>	
		<td nowrap="nowrap"><input type="button" id="stop" name="stop" onClick="operate('stop');" value="停止"/></td>
		<%
			}
		%>
	</tr>
	<tr>
		<td nowrap="nowrap">启动时间:</td>
		<td nowrap="nowrap"><%=displayStartDate %></td>
		<td nowrap="nowrap" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td nowrap="nowrap">系统版本:</td>
		<td nowrap="nowrap"><%=displayVersion %></td>
		<td nowrap="nowrap" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td nowrap="nowrap">本地缓存文件大小:</td>
		<td nowrap="nowrap"><%=sm.getCacheFilesSize() %></td>
		<td nowrap="nowrap" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td nowrap="nowrap">发送失败文件大小:</td>
		<td nowrap="nowrap"><%=sm.getSendFailureFileSize() %></td>
		<td nowrap="nowrap" colspan="2">&nbsp;</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td><div class="title1" /></td></tr>
</table>
<p align="left"><font size="3"><b>&nbsp;系统参数</b></font></p>
<table width="50%" class="tabinfo1" border="0" cellspacing="3" cellpadding="0">
	<tr>
		<td align="left" width="20%">参数名称</td>
		<td align="left" width="30%">参数值</td>
		<td align="left" width="50%">备注</td>
	</tr>
	<tr>
		<td align="left">zookeeper url</td>
		<td align="left"><%=sm.getParam("zookeeper.connstr") %></td>
		<td align="left">连接zookeeper的url,格式:host:port/xlog</td>
	</tr>
	<tr>
		<td align="left">缓存数据路径</td>
		<td align="left"><%=sm.getParam("oplog.store.path") %></td>
		<td align="left">存储缓存数据的本地路径</td>
	</tr>
	<tr>
		<td align="left">缓存数据保留时间</td>
		<td align="left"><%=sm.getParam("oplog.cache.time") %></td>
		<td align="left">超过该时间即删除，单位:分钟(min)</td>
	</tr>
	<tr>
		<td align="left">数据同步周期</td>
		<td align="left"><%=sm.getParam("oplog.sync.interval") %></td>
		<td align="left">单位:秒(s)</td>
	</tr>
	<tr>
		<td align="left">数据清除周期</td>
		<td align="left"><%=sm.getParam("oplog.clear.interval") %></td>
		<td align="left">单位:秒(s)</td>
	</tr>
	<tr>
		<td align="left">存储库类型</td>
		<td align="left"><%=sm.getParam("storage.type") %></td>
		<td align="left">持久层存储数据的类型</td>
	</tr>
	<tr>
		<td align="left">存储库缓存队列容量</td>
		<td align="left"><%=sm.getParam("storage.repository.queue.capacity") %></td>
		<td align="left">每个namespace对应缓存数据的队列容量</td>
	</tr>
	<tr>
		<td align="left">存储系统地址</td>
		<td align="left"><%=sm.getParam("storage.uri") %></td>
		<td align="left">第三方存储系统的uri</td>
	</tr>
	<tr>
		<td align="left">数据存储份数</td>
		<td align="left"><%=sm.getParam("storage.replication") %></td>
		<td align="left">日志数据向存储系统中写入的份数</td>
	</tr>
	<tr>
		<td align="left">HDFS缓存大小</td>
		<td align="left"><%=sm.getParam("hdfs.buffer.size") %></td>
		<td align="left">HDFS客户端向HDFS写数据的缓存大小</td>
	</tr>
	<tr>
		<td align="left">slot最小dispatcher个数</td>
		<td align="left"><%=sm.getParam("slot.replication") %></td>
		<td align="left">zookeeper中每个slot中最少注册dispatcher数量</td>
	</tr>
	<tr>
		<td align="left">master dispatcher开始balance的延时</td>
		<td align="left"><%=sm.getParam("master.start.delay") %></td>
		<td align="left">单位:秒(s)</td>
	</tr>
	<tr>
		<td align="left">zookeeper session过期时间</td>
		<td align="left"><%=sm.getParam("zk.session.timeout") %></td>
		<td align="left">单位:秒(s)</td>
	</tr>
	<tr>
		<td align="left">请求消息大小</td>
		<td align="left"><%=sm.getParam("ice.message.size.max") %></td>
		<td align="left">单位:兆(M)</td>
	</tr>
	<tr>
		<td align="left">dispatcher uuid</td>
		<td align="left"><%=sm.getParam("xlog.uuid") %></td>
		<td align="left">dispacher唯一编号</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td><div class="title1" /></td></tr>
</table>
<p align="left"><font size="3"><b>&nbsp;队列信息</b></font></p>
<table width="40%" class="tabinfo1" border="0" cellspacing="3" cellpadding="0">
	<tr>
		<td align="left" width="40%">Namespace</td>
		<td align="left" width="20%">当前队列大小</td>
		<td align="left" width="20%">发送成功数量</td>
		<td align="left" width="20%">发送失败数量</td>
	</tr>
	<% for(NamespaceInfo nsi:nsInfoList) { %>
		<tr>
			<td align="left"><%=nsi.getNamespace() %></td>
			<td align="left"><%=nsi.getQueueSize() %></td>
			<td align="left"><%=nsi.getSuccessCount() %></td>
			<td align="left"><%=nsi.getFailureCount() %></td>
		</tr>
	<% } %>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td><div class="title1" /></td></tr>
</table>
</body>
</html>