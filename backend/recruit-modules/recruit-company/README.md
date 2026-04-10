# recruit-company

## 模块职责
企业管理，负责企业入驻、认证资料、企业信息维护与企业成员管理。

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
company -> user: 查询招聘者信息；被 job、operation 依赖