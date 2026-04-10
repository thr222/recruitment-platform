<script setup lang="ts">
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'
import type { CompanyCardItem } from '@/types/company'

const companies: CompanyCardItem[] = [
  { id: 1, name: '星河科技', city: '上海', stage: 'B轮', industry: '企业服务', openPositions: 18 },
  { id: 2, name: '云图人才', city: '深圳', stage: 'A轮', industry: 'HR SaaS', openPositions: 9 }
]
</script>

<template>
  <div>
    <AppSectionTitle title="企业列表" description="企业品牌页突出行业、阶段、城市和在招职位数量，方便求职者判断机会质量" />
    <div class="board-list">
      <div v-for="company in companies" :key="company.id" class="panel-card" style="padding: 24px; display: flex; justify-content: space-between; align-items: center;">
        <div>
          <div style="font-size: 22px; font-weight: 700;">{{ company.name }}</div>
          <div style="margin-top: 10px; color: rgba(22, 50, 79, 0.72);">{{ company.industry }} · {{ company.stage }} · {{ company.city }}</div>
        </div>
        <el-tag type="success" round>{{ company.openPositions }} 个在招职位</el-tag>
      </div>
    </div>
  </div>
</template>
