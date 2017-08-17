# threecss-box

threecss-box是基于ThreeCSS分布式框架开发的一款网盘项目。

该项目由ThreeCSSBox(服务器)与ThreeCSSBoxClient(客户端)两个子项目组成。


体验地址：
https://box.threecss.com


依赖身份系统：
threecss-identity


打版本：在项目根目录下，执行

	ant


配置：

	dist/ThreeCSSBoxClient/js/app/Url.js-----访问网盘服务器与身份系统服务器

	dist/ThreeCSSBoxConfigData/configext.json----访问身份系统及其他配置

	dist/ThreeCSSBoxConfigData/mybatis-config.xml---访问网盘数据库

	dist/ThreeCSSBox.properties----ThreeCSSBoxConfigData在服务器路径


推荐环境：

	jdk-8u121

	apache-tomcat-8.5.12

	MariaDB-10.1.22

	CentOS-7-1611

	支持Html5浏览器


发布项目：

1、该项目依赖threecss-identity，需要先部署身份系统，具体详见：

https://github.com/dianbaer/threecss-identity

2、安装数据库
	
	create database threecssbox
	
	source ****/threecssbox.sql

3、将ThreeCSSBoxConfigData放入服务器某个路径，例如
	
	/home/ThreeCSSBoxConfigData

4、将ThreeCSSBox.properties放入tomcat根目录下，例如
	
	/home/tomcat/ThreeCSSBox.properties
	
	并修改config_dir对应的ThreeCSSBoxConfigData路径

5、将ThreeCSSBoxClient与ThreeCSSBox.war放入tomcat/webapps，例如
	
	/home/tomcat/webapps/ThreeCSSBox.war
	
	/home/tomcat/webapps/ThreeCSSBoxClient


threecss-box功能：

1、登录：
	
	支持账号密码登录，支持第三方用户平台登录。

2、个人信息：
	
	支持查看个人基本信息、云盘容量信息。

3、个人文件：
	
	文件多视图展示（详细信息视图、大图标视图）。
	文件夹目录层级操作。
	新建文件夹、文件重命名。
	文件下载、批量下载、下载速率控制。
	文件删除、批量删除。
	文件移动、批量移动、移动树型文件夹视图展示。
	文件排序（名称、大小、修改日期）。

4、文件上传：
	
	文件上传视图展示（可交互视图、缩略视图）。
	MD5校验急速上传。
	暂停、开始、取消、全部取消。
	分块上传、上传速率控制。

5、回收站：
	
	回收站视图展示（详细信息视图）。
	文件删除、批量删除、清空回收站。
	文件还原、批量还原。

6、图片预览
	
	图片预览视图展示。
	前后图片切换。
	图片旋转、下载、删除。



