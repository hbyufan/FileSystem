# FileSystem

[![Build Status](https://travis-ci.org/dianbaer/FileSystem.svg?branch=master)](https://travis-ci.org/dianbaer/FileSystem)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1e755f04dcaf4cb7b521e30950f29684)](https://www.codacy.com/app/232365732/FileSystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dianbaer/FileSystem&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# FileSystem是一个文件存储项目。

该项目由FileSystemServer(服务器)与FileSystemClient(客户端)两个子项目组成。


基于grain

https://github.com/dianbaer/grain

	grain-httpserver
	grain-mariadb
	grain-httpclient


## 依赖身份系统：

Identity


## 打版本：在项目根目录下，执行

	ant


## 配置：

	dist/FileSystemClient/js/app/Url.js-----访问文件存储服务器与身份系统服务器

	dist/FileSystemConfig/mybatis-config.xml---访问文件存储数据库

	dist/FileSystemServer.properties----FileSystemConfig在服务器路径及一些配置


## 推荐环境：

>快捷部署 https://github.com/dianbaer/deployment-server

	jdk-8u121

	apache-tomcat-8.5.12

	MariaDB-10.1.22

	CentOS-7-1611

	支持Html5浏览器


## 发布项目：

>1、该项目依赖Identity，需要先部署身份系统，具体详见：

	https://github.com/dianbaer/Identity

>2、安装数据库
	
	create database filesystem
	
	source ****/filesystem.sql

>3、将FileSystemConfig放入服务器某个路径，例如
	
	/home/FileSystemConfig

>4、将FileSystemServer.properties放入tomcat根目录下，例如
	
	/home/tomcat/FileSystemServer.properties
	
	并修改config_dir对应的FileSystemConfig路径

>5、将FileSystemClient与FileSystemServer.war放入tomcat/webapps，例如
	
	/home/tomcat/webapps/FileSystemServer.war
	
	/home/tomcat/webapps/FileSystemClient


## FileSystem功能：

>1、登录：
	
	支持账号密码登录，支持第三方用户平台登录。

>2、个人信息：
	
	支持查看个人基本信息、存储容量信息。

>3、个人文件：
	
	文件多视图展示（详细信息视图、大图标视图）。
	文件夹目录层级操作。
	新建文件夹、文件重命名。
	文件下载、批量下载、下载速率控制。
	文件删除、批量删除。
	文件移动、批量移动、移动树型文件夹视图展示。
	文件排序（名称、大小、修改日期）。

>4、文件上传：
	
	文件上传视图展示（可交互视图、缩略视图）。
	MD5校验急速上传。
	暂停、开始、取消、全部取消。
	分块上传、上传速率控制。

>5、回收站：
	
	回收站视图展示（详细信息视图）。
	文件删除、批量删除、清空回收站。
	文件还原、批量还原。

>6、图片预览
	
	图片预览视图展示。
	前后图片切换。
	图片旋转、下载、删除。



