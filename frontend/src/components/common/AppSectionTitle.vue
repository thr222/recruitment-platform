<script setup lang="ts">
defineProps<{
  title: string
  description?: string
  eyebrow?: string
}>()
</script>

<template>
  <div class="section-title">
    <div v-if="eyebrow" class="section-chip">{{ eyebrow }}</div>
    <h2>{{ title }}</h2>
    <p v-if="description">{{ description }}</p>
  </div>
</template>

<style scoped>
.section-title {
  display: grid;
  gap: 10px;
  margin-bottom: 18px;
}

h2 {
  margin: 0;
  color: var(--brand-title);
  font-size: clamp(24px, 3vw, 34px);
  font-weight: 800;
  letter-spacing: -0.02em;
}

p {
  max-width: 780px;
  margin: 0;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}
</style>
