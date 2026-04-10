<script setup lang="ts">
import type { JobCardItem } from '@/types/job'

defineProps<{ job: JobCardItem }>()
</script>

<template>
  <article class="job-card panel-card">
    <div class="job-card__head">
      <div class="job-card__main">
        <div class="job-card__title-row">
          <h3>{{ job.title }}</h3>
          <span class="status-pill">
            <i class="status-dot" />
            急招
          </span>
        </div>
        <div class="job-card__meta">
          <span>{{ job.companyName }}</span>
          <span>{{ job.city }}</span>
          <span>{{ job.experience }}</span>
        </div>
      </div>
      <div class="job-card__salary">{{ job.salary }}</div>
    </div>

    <div class="job-card__tags">
      <span v-for="tag in job.tags" :key="tag" class="tag-item">{{ tag }}</span>
    </div>
  </article>
</template>

<style scoped>
.job-card {
  padding: 22px 24px;
  transition: transform 0.22s ease, box-shadow 0.22s ease, border-color 0.22s ease;
}

.job-card:hover {
  transform: translateY(-4px);
  border-color: rgba(0, 166, 166, 0.24);
  box-shadow: 0 22px 70px rgba(17, 72, 106, 0.14);
}

.job-card__head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;
}

.job-card__main {
  min-width: 0;
}

.job-card__title-row {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 22px;
  line-height: 1.3;
}

.job-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.job-card__salary {
  flex-shrink: 0;
  color: var(--brand-accent);
  font-size: 24px;
  font-weight: 800;
  letter-spacing: -0.02em;
}

.status-pill {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 7px 12px;
  border-radius: 999px;
  background: rgba(25, 180, 91, 0.12);
  color: #148d48;
  font-size: 12px;
  font-weight: 700;
}

.job-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 18px;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(0, 166, 166, 0.08);
  color: var(--brand-primary-deep);
  font-size: 13px;
  font-weight: 600;
}

@media (max-width: 760px) {
  .job-card__head {
    flex-direction: column;
  }

  .job-card__salary {
    font-size: 22px;
  }
}
</style>
