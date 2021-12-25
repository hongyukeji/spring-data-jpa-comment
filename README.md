# spring-data-jpa-comment

> Spring Data Jpa 支持 @TableComment("表名注释") @ColumnComment("字段注释")

> JPA创建或修改数据库的表注释和字段注释

## 说明：

JPA 比较方便，让开发免于手动创建表操作，但有一个问题表中字段无注释，虽然JPA有提供方法，但无法适应所有主流数据库。
JPA 自身提供方法如下：

```java
public class MyEntity {

 @Column(nullable = false,columnDefinition = "int(2) comment '我是年龄注释...'")
 private Integer age;

}
```

其中 **columnDefinition** 其实就是写 Native Sql，这样违背了JPA的初衷“屏蔽底层数据库差异”。

spring-data-jpa-comment 目前适配了三种数据库 Mysql Sqlserver oracle，后期可以添加其他数据库。

spring-data-jpa-comment 的方法很简单将 java属性上的注解注释内容 修改到表字段里面。

用法如下：

1、在yaml文件中添加（默认为true）

```yaml
spring:
  jpa:
    comment:
      enable: true
```

2、在您的项目 @Configuration 注解类上面添加 @EnableJpaCommentAutoConfiguration

```java
package org.example.config;

import ltd.hongyu.spring.data.jpa.comment.config.EnableJpaCommentAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJpaCommentAutoConfiguration
public class AppBootstrapConfig {
}
```

3、Entity 实体类里面添加注解 **@TableComment** 和  **@ColumnComment**

```java

package org.example.entity;

import ltd.hongyu.spring.data.jpa.comment.annotation.ColumnComment;
import ltd.hongyu.spring.data.jpa.comment.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author <a href="mailto:1527200768@qq.com">hongyu</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "T_SYS_ORG")
@TableComment("组织信息表")
public class SysOrgEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ColumnComment("编号")
    @Column(unique = true)
    private String orgCode;

    @ColumnComment("组织id")
    private String orgId;

    @ColumnComment("组织名称")
    private String orgName;


}
```

调用 service 方法 更新全库或 单表字段注释，这里没有采用启动自动更新字段注释，而采用手动方式调用，
主要考虑表注释一般不会频繁更新。

```java
@RestController
@RequestMapping("api/sys")
public class SetCommentController {

    @Resource
    JpaCommentService jpaCommentService;

   /**
    * 更新全库字段注释
    */
    @GetMapping("alterAllTableAndColumn")
    public String alterAllTableAndColumn() {
        jpaCommentService.alterAllTableAndColumn();
        return "success";
    }

}
```

开启日志打印 application.yaml 中添加

```yaml
logging:
  level:
    root: INFO
    ltd.hongyu.spring.data.jpa.comment: DEBUG  # spring-data-jpa-comment日志打印
```

控制台可以打印如下信息：

```
......
2021-12-26 18:30:58.227 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 orgId 的注释为 '性别 1 男 2 女' 
2021-12-26 18:30:58.230 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 orgName 的注释为 '性别 1 男 2 女'
2021-12-26 18:30:58.232 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 RVERSION 的注释为 '乐观锁版本号'
2021-12-26 18:30:58.235 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 CREATE_DATE 的注释为 '创建时间'
2021-12-26 18:30:58.237 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 CREATE_USER 的注释为 '创建人员信息'
2021-12-26 18:30:58.240 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 UPADATE_DATE 的注释为 '更新时间'
2021-12-26 18:30:58.242 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 UPDATE_USER 的注释为 '更新人员信息'
2021-12-26 18:30:58.245 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_ORG 字段 REMARK 的注释为 '备注'
2021-12-26 18:30:58.248 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 的注释为 '用户信息表'
2021-12-26 18:30:58.250 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 SEX 的注释为 '性别 1 男 2 女'
2021-12-26 18:30:58.253 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 ADDRESS 的注释为 '地址'
2021-12-26 18:30:58.256 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 RVERSION 的注释为 '乐观锁版本号'
2021-12-26 18:30:58.258 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 CREATE_DATE 的注释为 '创建时间'
2021-12-26 18:30:58.261 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 CREATE_USER 的注释为 '创建人员信息'
2021-12-26 18:30:58.264 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 UPADATE_DATE 的注释为 '更新时间'
2021-12-26 18:30:58.266 DEBUG 10476 --- [ant-8080-exec-8] c.a.e.j.c.service.JpaCommentService       : 修改表 T_SYS_USER 字段 UPDATE_USER 的注释为 '更新人员信息'
......
```