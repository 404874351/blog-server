# blog-server

### 介绍

个人博客-服务端

本项目实现了基础博客系统，逻辑简单，没有太多花里胡哨的功能，适合初学者快速上手。

本项目使用了SSM框架，并使用SpringSecurity进行身份验证和权限控制。个人体验下来，SpringSecurity集成度过高，在配置多方式登录和动态权限控制时有点麻烦。但掌握好套路之后，难度也不是很大。

有一说一，Spring系列框架虽然开箱即用，注解配置起来很爽，还是太重了，对于轻量级的项目而言有些没必要。为此，我做了一份golang重构版本，感兴趣的朋友可以到我个人空间查看。

### 主要功能

- 用户管理：系统管理员可分配用户角色
- 角色管理：基于角色为用户分配菜单和资源
- 菜单管理：基于角色动态展示后台管理页面
- 权限控制：基于SpringBoot，JWT实现的RBAC权限管理
- 多方式登录：支持传统密码登录和短信验证码登录
- 博客业务：提供博客、分类、标签、评论、留言等核心功能

### 技术选型

- Java版本：Java 8
- Web框架：SSM
- 持久层框架：MyBatis-plus
- 数据库：Mysql 8
- 缓存：Redis 6
- 安全控制：JWT身份验证，基于SpringSecurity的RBAC权限控制
- 文件存储：七牛云对象存储
- 短信服务：阿里云SMS
- IDE推荐：JetBrains IDEA

### 目录结构

```bash
blog-server/src/main

/java/com.zjc.blog
├── config                      # 配置类
├── controller                  # 接口控制器
├── dto                         # 响应数据类
├── entity                      # 数据模型
├── enums                       # 项目常量
├── exception                   # 自定义异常类型
├── handler                     # 相关全局处理器
├── mapper                      # mysql 数据映射接口
├── security                    # 系统安全控制相关
    ├── detail                  # 自定义用户登录详细信息
    ├── filter                  # 认证与授权过滤器
    ├── handler                 # 认证与授权结果处理器
    ├── provider                # 自定义认证业务提供者
    ├── service                 # 自定义用户认证业务
    └── token                   # 自定义用户登录令牌
├── service                     # 业务类
├── utils                       # 常用工具包
├── vo                          # 接收参数类
└── BlogServiceApplication      # 项目入口文件

/resources
├── mapper                      # mybatis sql映射文件            
├── application.yml             # 项目配置文件
└── model.sql                   # sql初始化文件
```

### 安装使用

1. 克隆项目到本地

2. 初始化IDEA项目

3. 修改application.yml，将配置改成自己的

4. 新建mysql数据库，执行model.sql文件

5. 打开IDEA，点击运行


### 部署上线

1. 使用IDEA自带Maven打包

2. 将target目录下jar文件复制到服务器的工作目录

```bash
cd <your-working-dir>
cp -r xxx/target/blog-server-1.0.0.jar  ./server.jar
```

3. 前台启动

```bash
java -jar server.jar
```

4. 后台启动，日志输出到文件

```bash
nohup java -jar server.jar >log.out 2>&1 &
```

### 学习文档

1. 尚硅谷Java系列视频 https://www.bilibili.com/video/BV1PY411e7J6/
2. JWT https://jwt.io/
3. RBAC权限控制 https://casbin.org/

### 后续改进

1. 项目使用了Mybatis-plus，某些操作反而不如直接写SQL语句简单，如有必要可以直接使用Mybatis。
3. 如依然存在问题，可联系作者。 QQ：404874351
