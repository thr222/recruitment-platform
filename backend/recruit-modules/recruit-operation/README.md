# recruit-operation

## 模块职责
运营治理，负责企业审核、职位审核、Banner、字典配置、举报处理。

## 是否核心模块
false

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
operation -> company/job：审核企业和职位；被 admin 后台使用