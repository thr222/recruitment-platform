<script setup lang="ts">
import { computed } from 'vue'

export interface ModernJobCardData {
  id: string | number
  title: string
  companyName: string
  salary: string
  city: string
  experience?: string
  education?: string
  workType?: string
  companyLogo?: string
  companyStage?: string
  companySize?: string
  tags?: string[]
  benefits?: string[]
  publishedAt?: string
  isUrgent?: boolean
  isFavorited?: boolean
}

const props = withDefaults(
  defineProps<{
    job: ModernJobCardData
    compact?: boolean
  }>(),
  {
    compact: false
  }
)

const emit = defineEmits<{
  viewDetail: [job: ModernJobCardData]
  toggleFavorite: [job: ModernJobCardData]
}>()

const logoText = computed(() => props.job.companyName.trim().slice(0, 1).toUpperCase())
const visibleTags = computed(() => (props.job.tags ?? []).slice(0, props.compact ? 3 : 5))
const visibleBenefits = computed(() => (props.job.benefits ?? []).slice(0, props.compact ? 1 : 2))

function handleViewDetail() {
  emit('viewDetail', props.job)
}

function handleToggleFavorite(event: MouseEvent) {
  event.stopPropagation()
  emit('toggleFavorite', props.job)
}
</script>

<template>
  <article class="modern-job-card" tabindex="0" @click="handleViewDetail" @keydown.enter="handleViewDetail">
    <header class="modern-job-card__header">
      <div class="modern-job-card__company">
        <img v-if="job.companyLogo" class="modern-job-card__logo" :src="job.companyLogo" :alt="`${job.companyName} logo`" />
        <span v-else class="modern-job-card__logo modern-job-card__logo--text">{{ logoText }}</span>

        <div class="modern-job-card__company-copy">
          <span class="modern-job-card__company-name">{{ job.companyName }}</span>
          <span class="modern-job-card__company-meta">
            {{ job.companyStage || '成长型公司' }}
            <template v-if="job.companySize"> · {{ job.companySize }}</template>
          </span>
        </div>
      </div>

      <button
        class="modern-job-card__favorite"
        :class="{ 'modern-job-card__favorite--active': job.isFavorited }"
        type="button"
        :aria-label="job.isFavorited ? '取消收藏职位' : '收藏职位'"
        @click="handleToggleFavorite"
      >
        {{ job.isFavorited ? '已收藏' : '收藏' }}
      </button>
    </header>

    <main class="modern-job-card__body">
      <div class="modern-job-card__title-row">
        <h3 class="modern-job-card__title">{{ job.title }}</h3>
        <strong class="modern-job-card__salary">{{ job.salary }}</strong>
      </div>

      <div class="modern-job-card__meta">
        <span>{{ job.city }}</span>
        <span v-if="job.experience">{{ job.experience }}</span>
        <span v-if="job.education">{{ job.education }}</span>
        <span v-if="job.workType">{{ job.workType }}</span>
      </div>

      <div class="modern-job-card__tags">
        <span v-for="tag in visibleTags" :key="tag" class="modern-job-card__chip">{{ tag }}</span>
        <span v-for="benefit in visibleBenefits" :key="benefit" class="modern-job-card__chip modern-job-card__chip--benefit">
          {{ benefit }}
        </span>
      </div>
    </main>

    <footer class="modern-job-card__footer">
      <span class="modern-job-card__time">{{ job.publishedAt || '刚刚更新' }}</span>
      <span v-if="job.isUrgent" class="modern-job-card__urgent">急招</span>
      <button class="modern-job-card__detail" type="button" @click.stop="handleViewDetail">查看详情</button>
    </footer>
  </article>
</template>

<style scoped>
.modern-job-card {
  position: relative;
  display: grid;
  gap: 18px;
  padding: 22px;
  border: 1px solid rgba(15, 23, 42, 0.08);
  border-radius: 20px;
  background:
    linear-gradient(135deg, rgba(255, 255, 255, 0.98), rgba(248, 250, 252, 0.96)),
    #ffffff;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
  cursor: pointer;
  outline: none;
  transition:
    transform 0.22s ease,
    box-shadow 0.22s ease,
    border-color 0.22s ease;
}

.modern-job-card::before {
  content: "";
  position: absolute;
  inset: 0;
  border-radius: inherit;
  background: radial-gradient(circle at 20% 0%, rgba(37, 99, 235, 0.08), transparent 34%);
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.22s ease;
}

.modern-job-card:hover,
.modern-job-card:focus-visible {
  transform: translateY(-4px);
  border-color: rgba(37, 99, 235, 0.22);
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.12);
}

.modern-job-card:hover::before,
.modern-job-card:focus-visible::before {
  opacity: 1;
}

.modern-job-card__header,
.modern-job-card__title-row,
.modern-job-card__footer {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}

.modern-job-card__company {
  display: flex;
  min-width: 0;
  align-items: center;
  gap: 12px;
}

.modern-job-card__logo {
  width: 46px;
  height: 46px;
  flex: 0 0 46px;
  border-radius: 14px;
  object-fit: cover;
  box-shadow: inset 0 0 0 1px rgba(15, 23, 42, 0.08);
}

.modern-job-card__logo--text {
  display: inline-grid;
  place-items: center;
  background: #0f172a;
  color: #ffffff;
  font-size: 18px;
  font-weight: 800;
}

.modern-job-card__company-copy {
  display: grid;
  min-width: 0;
  gap: 4px;
}

.modern-job-card__company-name {
  overflow: hidden;
  color: #0f172a;
  font-size: 15px;
  font-weight: 750;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.modern-job-card__company-meta,
.modern-job-card__meta,
.modern-job-card__time {
  color: #64748b;
  font-size: 13px;
}

.modern-job-card__favorite {
  border: 1px solid rgba(15, 23, 42, 0.1);
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.8);
  color: #334155;
  font-size: 13px;
  font-weight: 700;
  padding: 8px 12px;
  cursor: pointer;
  transition:
    background 0.2s ease,
    border-color 0.2s ease,
    color 0.2s ease;
}

.modern-job-card__favorite:hover,
.modern-job-card__favorite--active {
  border-color: rgba(37, 99, 235, 0.25);
  background: #eff6ff;
  color: #2563eb;
}

.modern-job-card__body {
  position: relative;
  z-index: 1;
  display: grid;
  gap: 12px;
}

.modern-job-card__title {
  margin: 0;
  color: #0f172a;
  font-size: 24px;
  font-weight: 800;
  letter-spacing: -0.03em;
  line-height: 1.18;
}

.modern-job-card__salary {
  flex: 0 0 auto;
  color: #0f766e;
  font-size: 22px;
  font-weight: 850;
  white-space: nowrap;
}

.modern-job-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  line-height: 1.5;
}

.modern-job-card__meta span + span::before {
  content: "";
  display: inline-block;
  width: 4px;
  height: 4px;
  margin-right: 10px;
  border-radius: 50%;
  background: #cbd5e1;
  vertical-align: middle;
}

.modern-job-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.modern-job-card__chip {
  display: inline-flex;
  align-items: center;
  min-height: 28px;
  border-radius: 999px;
  background: #f1f5f9;
  color: #334155;
  font-size: 12px;
  font-weight: 700;
  padding: 6px 10px;
}

.modern-job-card__chip--benefit {
  background: #ecfdf5;
  color: #047857;
}

.modern-job-card__footer {
  align-items: center;
  padding-top: 2px;
}

.modern-job-card__urgent {
  margin-right: auto;
  border-radius: 999px;
  background: #fff7ed;
  color: #ea580c;
  font-size: 12px;
  font-weight: 800;
  padding: 6px 10px;
}

.modern-job-card__detail {
  border: none;
  border-radius: 999px;
  background: #0f172a;
  color: #ffffff;
  font-size: 13px;
  font-weight: 800;
  padding: 10px 14px;
  cursor: pointer;
  transition:
    transform 0.2s ease,
    background 0.2s ease;
}

.modern-job-card__detail:hover {
  transform: translateY(-1px);
  background: #2563eb;
}

@media (max-width: 720px) {
  .modern-job-card {
    padding: 18px;
  }

  .modern-job-card__title-row,
  .modern-job-card__footer {
    flex-direction: column;
    align-items: flex-start;
  }

  .modern-job-card__title {
    font-size: 21px;
  }

  .modern-job-card__salary {
    font-size: 20px;
  }
}
</style>
