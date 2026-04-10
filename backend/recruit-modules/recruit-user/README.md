# recruit-user

## 模块职责
用户中心，负责账号基础信息、求职者档案、招聘者档案与账号安全设置。

## 是否核心模块
true

## 推荐包结构
- controller：REST 接口层
- service：业务服务层
- manager：复杂业务编排层
- mapper：持久化访问层
- entity：数据库实体层
- dto：请求对象
- vo：响应对象
- query：查询条件对象
- enums：业务枚举
- constant：模块常量
- convert：对象转换
- repository：仓储封装

## 模块交互
被 auth、company 依赖，提供用户基础资料查询与初始化能力