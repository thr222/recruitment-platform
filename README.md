# 企业招聘与求职平台

一个用于面试展示的企业招聘与求职平台，采用前后端分离 + 模块化单体架构。

## 技术栈

- 前端：Vue 3 + TypeScript + Vite + Pinia + Element Plus
- 后端：Spring Boot + Spring Security + JWT + MyBatis-Plus + MySQL + Redis
- 架构：前后端分离、模块化单体

## 目录说明

- `backend`：多模块 Spring Boot 后端工程
- `frontend`：Vue 3 前端工程

## 当前交付内容

- 后端多模块父子工程与领域分层骨架
- 认证、用户、企业、职位、简历、投递、消息、运营、统计等领域模块
- 前端四套角色布局与典型页面壳子
- SQL 建表脚本、初始化数据脚本、权限矩阵与接口设计文档

## 启动建议

### 前端

```bash
cd frontend
npm install
npm run dev
```

### 后端

```bash
cd backend
mvn clean spring-boot:run -pl recruit-boot -am
```

> 当前工作区未检测到 Maven，可先安装 Maven 或补充 Maven Wrapper 后运行后端。
