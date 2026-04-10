# recruit-message

## 模块职责
消息中心，负责站内通知、流程提醒、未读数与会话消息。

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
被 application、operation 调用发送通知