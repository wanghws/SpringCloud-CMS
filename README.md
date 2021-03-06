# Spring Cloud 后台管理框架

使用微服务，请求走gateway，登录验证用oauth2，服务调用用feign，account用户中心集成了5张表的权限系统

数据用Mybatis-plus，集成了动态数据dynamic-datasource

缓存用redis，通过spring-cache调用

集成了Swagger接口文档生成

## 集成项目

- [Eureka](https://github.com/spring-cloud/spring-cloud-netflix)
- [Gateway](https://github.com/spring-cloud/spring-cloud-gateway)
- [Hystrix](https://github.com/spring-cloud/spring-cloud-netflix)
- [Oauth2](https://github.com/spring-cloud/spring-cloud-security)
- [OpenFeign](https://github.com/spring-cloud/spring-cloud-openfeign)
- [Druid](https://github.com/alibaba/druid)
- [Mybatis-Plus](https://github.com/baomidou/mybatis-plus)
- [Dynamic-Datasource](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)
- [Knife4j](https://github.com/xiaoymin/swagger-bootstrap-ui)
- [Modelmapper](https://github.com/modelmapper/modelmapper)
- [Vue-Element-Admin](https://github.com/PanJiaChen/vue-element-admin)


## 数据库初始化

```sql

CREATE DATABASE platform_account DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'work'@'%' identified by '123456';
GRANT ALL PRIVILEGES ON platform_account.* to 'work'@'%';

source SpringCloud-CMS/account/account-service/sql/account.sql
source SpringCloud-CMS/account/account-service/sql/init.sql

```

## 密码初始化

curl -X GET "http://localhost:8430/test/password"

admin 123456

## 端口

- Eureka 8400
- Gateway 8410
- Passport 8420
- Account 8430
- CMS-VUE 9527

## 其他组件

1.	spring-aop
2.	spring-cache
3.  spring-data-redis
4.  spring-cloud-commons
5.  guava
6.  logback

## 步骤

1.  启动Eureka
2.  启动Gateway
3.  启动Passport OAuth2验证服务
4.  启动用户账户服务 集成了基本权限5张表
5.  启动前台VUE页面 npm run dev
6.  接口文档访问 http://localhost:8410/doc.html

## 截图

![avatar](screenshot/00.png)

![avatar](screenshot/11.png)

![avatar](screenshot/22.png)

![avatar](screenshot/33.png)

![avatar](screenshot/44.png)

![avatar](screenshot/55.png)