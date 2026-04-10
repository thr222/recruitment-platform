# recruit-resume

## 模块职责
简历管理，负责在线简历、教育经历、工作经历、项目经历与求职意向。

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
被 application 依赖，提供简历完整度与简历可投递校验