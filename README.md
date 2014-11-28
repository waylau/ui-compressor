ui-compressor
=============

UI-Compressor ,a tool for compress js and css files.

用于压缩 js, css 文件

##Usage 用法

Checkout This repository:

	git clone https://github.com/waylau/ui-compressor.git

Package the source code to JAR:

	mvn package

Run:

	java -cp target/ui-compressor-1.0.0.jar com.waylau.uicompressor.App

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

##Download ui-compressor-x.x.x.jar

[https://github.com/waylau/ui-compressor/releases](https://github.com/waylau/ui-compressor/releases)


