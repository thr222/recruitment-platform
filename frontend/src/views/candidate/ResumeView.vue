<template>
  <div class="panel-card" style="padding: 28px;">
    <div style="font-size: 24px; font-weight: 700; margin-bottom: 18px;">在线简历编辑</div>
    <el-form label-position="top">
      <el-row :gutter="20">
        <el-col :span="12"><el-form-item label="姓名"><el-input model-value="张同学" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="手机号"><el-input model-value="13800138000" /></el-form-item></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12"><el-form-item label="最高学历"><el-select placeholder="请选择学历" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="目标城市"><el-select placeholder="请选择城市" /></el-form-item></el-col>
      </el-row>
      <el-form-item label="个人优势"><el-input type="textarea" :rows="4" model-value="3 年 Java 后端经验，熟悉招聘平台中的职位、投递与权限体系。" /></el-form-item>
      <el-form-item label="工作经历"><el-input type="textarea" :rows="5" model-value="2022-至今 星河科技 Java 后端工程师" /></el-form-item>
      <el-form-item label="项目经历"><el-input type="textarea" :rows="5" model-value="负责招聘平台中台重构，建设统一认证、职位检索与投递流程。" /></el-form-item>
      <el-button type="primary">保存简历</el-button>
    </el-form>
  </div>
</template>
