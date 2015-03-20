ui-compressor
=============

UI-Compressor ,a tool for compressing js and css files.

用于压缩 js, css 文件

##Features 特性

* 灵活配置
* 支持对 js, css 文件的压缩
* 忽略不想压缩的文件（或者目录）
* 自定义编码
* 代码混淆
* 代码优化
* 异常处理
* 生成任务报告

##Usage 用法

Checkout This repository:

	git clone https://github.com/waylau/ui-compressor.git

Package the source code to JAR:

	mvn package

Run:

	java -cp target/ui-compressor-1.2.0.jar com.waylau.uicompressor.App

or 

	java -jar target/ui-compressor-1.2.0.jar com.waylau.uicompressor.App

##Configuration 配置

 Configuration in `src/main/resources/config.properties`:

	#读取文件的目录
	infile  = d:/UIC      
	#输出文件的目录
	outfile = d:/UIC-temp    
	#忽略扫描的文件目录类型(英文;号隔开，不要留空)
	ignore  = .svn;.git
	
	#文件使用的字符集编码
	charset = utf8
	#如果没有指定输入文件，默认为标准输入。在这种情况下，'类型' 选项是必须的。除非文件名不是 js 或者 css。(英文;号隔开，不要留空)
	type = js;css
	#在一个指定的列数之后插入一个换行（一般是不需要的）
	linebreak = -1
	# 仅压缩，不混淆 ,false 为不混淆，true 为混淆
	nomunge = true
	#显示详细信息和警告信息
	verbose = false
	#保留所有的分号
	preserveSemi = false
	#禁用自带的所有优化措施
	disableOptimizations = false

##Screenshot 快照

![](http://99btgc01.info/uploads/2014/12/uic.jpg)

##Download ui-compressor-x.x.x.jar

[https://github.com/waylau/ui-compressor/releases](https://github.com/waylau/ui-compressor/releases)

##Version 版本说明

###v1.2.0 (2015-03-20)

更新：

1.修复了压缩js文件时的 bug 
 

###v1.1.0 (2014-12-11)

更新：

1.修复了读空文件时的 bug 

2.处理压缩命名不规范代码时的异常。对该文件保留，继续执行下个文件的压缩

3.设置了执行任务开始、结束的标识

4.增加了统计执行任务话费时间的报告功能

###v1.0.0 (2014-11-17)

功能：

1.独立配置

2.忽略不想压缩的文件（或者目录）

3.自定义编码

4.代码混淆

5.代码优化


##Who use it

* [www.waylau.com](http:www.waylau.com)




