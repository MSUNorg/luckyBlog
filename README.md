# luckyBlog
响应式个人博客网站

### 技术栈
* 后端：`SpringBoot`, `Mybatis`, `Mysql`, `velocity`, `ehcache`
* 前端：`html`, `css`, `javascript`, `jQuery`
* 构建：`Maven`


## install & depoly
### 安裝本地依赖包
```
mvn clean
#
mvn -B -DskipTests clean dependency:list install
```

### 下载依赖 & 测试
```
mvn clean install
```

### 打包
```
mvn clean package -DskipTests
```

### 运行
```
jps -l|awk '{print $1}'|xargs kill
#
java -Dserver.port=8080 -cp "/usr/local/java/bin/lib:/usr/local/java/bin/jre/lib:conf/:lib/*:" com.msun.luckyBlog.Launcher
#
nohup java -Dserver.port=8080 -cp "/usr/local/java/bin/lib:/usr/local/java/bin/jre/lib:conf/:lib/*:" com.msun.luckyBlog.Launcher > /dev/null  2>&1 &
#
nohup java -Dserver.port=80 -cp "/data/jdk1.7.0_55/lib:/data/jdk1.7.0_55/jre/lib:/data/lucky-blog-1.0.0-SNAPSHOT/conf/:/data/lucky-blog-1.0.0-SNAPSHOT/lib/*:" com.msun.luckyBlog.Launcher > /dev/null  2>&1 &