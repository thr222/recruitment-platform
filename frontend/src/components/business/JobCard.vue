<script setup lang="ts">
import { computed } from 'vue'
import type { JobCardItem } from '@/types/job'

const props = withDefaults(
  defineProps<{
    job: JobCardItem
    compact?: boolean
  }>(),
  {
    compact: false
  }
)

const companyLogo = computed(() => props.job.companyName.trim().slice(0, 1).toUpperCase())
const skillTags = computed(() => props.job.tags.slice(0, props.compact ? 2 : 3))
</script>

<template>
  <article class="job-card">
    <div class="job-card__company">
      <span class="job-card__logo" aria-hidden="true">{{ companyLogo }}</span>
      <span class="job-card__company-name">{{ job.companyName }}</span>
    </div>

    <h3 class="job-card__title">{{ job.title }}</h3>

    <div class="job-card__tags">
      <span class="tag-chip tag-chip--salary">{{ job.salary }}</span>
      <span v-for="tag in skillTags" :key="tag" class="tag-chip tag-chip--skill">{{ tag }}</span>
      <span v-if="job.workMode" class="tag-chip tag-chip--mode">{{ job.workMode }}</span>
    </div>

    <div class="job-card__meta">
      <span>{{ job.city }}</span>
      <span>{{ job.experience }}</span>
    </div>
  </article>
</template>

<style scoped>
.job-card {
  display: grid;
  gap: 16px;
  padding: 24px;
  border: 1px solid var(--brand-line);
  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.job-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.08);
}

.job-card__company {
  display: flex;
  align-items: center;
  gap: 10px;
}

.job-card__logo {
  display: inline-grid;
  place-items: center;
  width: 40px;
  height: 40px;
  border-radius: 12px;
  background: #f3f4f6;
  color: var(--brand-title);
  font-size: 15px;
  font-weight: 800;
}

.job-card__company-name {
  color: var(--brand-ink-soft);
  font-size: 14px;
  font-weight: 600;
}

.job-card__title {
  margin: 0;
  color: var(--brand-title);
  font-size: 28px;
  line-height: 1.12;
  letter-spacing: -0.03em;
}

.job-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  padding: 7px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
}

.tag-chip--salary {
  background: #ecfdf5;
  color: #15803d;
}

.tag-chip--skill {
  background: #eff6ff;
  color: #2563eb;
}

.tag-chip--mode {
  background: #fff7ed;
  color: #ea580c;
}

.job-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.job-card__meta span + span::before {
  content: "•";
  margin-right: 12px;
  color: #d1d5db;
}

@media (max-width: 760px) {
  .job-card {
    padding: 20px;
  }

  .job-card__title {
    font-size: 24px;
  }
}
</style>
