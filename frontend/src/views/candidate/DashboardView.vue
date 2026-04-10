<template>
  <div>
    <div class="hero-banner page-section">
      <h1>求职者工作台</h1>
      <p>围绕简历完整度、投递进度和推荐职位，展示求职者每天最关心的三类信息。</p>
    </div>
    <div class="metric-grid page-section">
      <div class="metric-card"><div class="metric-value">85%</div><div class="metric-label">简历完整度</div></div>
      <div class="metric-card"><div class="metric-value">12</div><div class="metric-label">累计投递</div></div>
      <div class="metric-card"><div class="metric-value">3</div><div class="metric-label">进行中流程</div></div>
      <div class="metric-card"><div class="metric-value">18</div><div class="metric-label">今日推荐职位</div></div>
    </div>
    <div class="board-split">
      <div class="panel-card" style="padding: 24px;">
        <div style="font-size: 22px; font-weight: 700; margin-bottom: 18px;">最新投递进度</div>
        <el-timeline>
          <el-timeline-item timestamp="2026-04-08 14:30" type="warning">星河科技：状态更新为面试中</el-timeline-item>
          <el-timeline-item timestamp="2026-04-07 19:10">远帆智能：简历已被查看</el-timeline-item>
          <el-timeline-item timestamp="2026-04-07 09:20">云图人才：投递成功</el-timeline-item>
        </el-timeline>
      </div>
      <div class="panel-card" style="padding: 24px;">
        <div style="font-size: 22px; font-weight: 700; margin-bottom: 18px;">求职提醒</div>
        <el-alert title="建议补充项目经历与期望城市，提升投递命中率" type="info" :closable="false" />
        <el-divider />
        <el-alert title="有 3 条未读消息待处理" type="warning" :closable="false" />
      </div>
    </div>
  </div>
</template>
