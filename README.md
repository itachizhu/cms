# cms
java实现的cms

## 添加Docker支持

> 在这里我们直接使用Docker-Toolbox来进行配置支持，这样使得大家即使是在本地开发环境(Mac和Windows)中依然能够得到很好支持。

### 启动Docker-Toolbox Terminal
记录对应的IP如：
```
docker is configured to use the default machine with IP 192.168.99.100
```

### 在Intellij中建立Maven任务
在命令行参数中设置:
```
docker:build
```

### 执行之前的build和新建的docker:build

### 使用Kitematic工具进行容器状态和日志查看

### 使用对应映射的ip和端口链接测试对应的接口

### 添加mysql支持
```
# 第一次创建(需要联网)
docker run -d --name demo-mysql -e MYSQL_ROOT_PASSWORD=000000 -e MYSQL_DATABASE=cmsadmin -e MYSQL_USER=cmsadmin -e MYSQL_PASSWORD=cmsadmin mysql:5.7
# 第一次根据repository创建
docker run -it --name cms-app --link demo-mysql:mysql -p 9091:9091 itachisoft/cms-boot
```
