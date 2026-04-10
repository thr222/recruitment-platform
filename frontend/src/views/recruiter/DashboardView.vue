<template>
  <div>
    <div class="hero-banner page-section">
      <h1>招聘工作台</h1>
      <p>聚焦职位投放、候选人推进和面试安排，用一屏展示招聘效率和待处理事项。</p>
    </div>
    <div class="metric-grid page-section">
      <div class="metric-card"><div class="metric-value">18</div><div class="metric-label">在招职位</div></div>
      <div class="metric-card"><div class="metric-value">53</div><div class="metric-label">待处理投递</div></div>
      <div class="metric-card"><div class="metric-value">8</div><div class="metric-label">今日面试</div></div>
      <div class="metric-card"><div class="metric-value">24%</div><div class="metric-label">简历转面率</div></div>
    </div>
    <div class="board-split">
      <div class="panel-card" style="padding: 24px;">
        <div style="font-size: 22px; font-weight: 700; margin-bottom: 18px;">待优先处理的投递</div>
        <el-table :data="[{ name: '王同学', job: 'Java 后端开发工程师', stage: '待初筛' }, { name: '陈同学', job: '高级测试开发', stage: '待约面' }]">
          <el-table-column prop="name" label="候选人" />
          <el-table-column prop="job" label="投递职位" />
          <el-table-column prop="stage" label="当前阶段" />
        </el-table>
      </div>
      <div class="panel-card" style="padding: 24px;">
        <div style="font-size: 22px; font-weight: 700; margin-bottom: 18px;">招聘建议</div>
        <el-alert title="Java 岗位投递量高但处理延迟偏高，建议增加筛选规则。" type="warning" :closable="false" />
      </div>
    </div>
  </div>
</template>
