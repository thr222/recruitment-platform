<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const navLinks = [
  { label: 'Discover', href: '/jobs' },
  { label: 'For job seekers', href: '/login' },
  { label: 'For companies', href: '/companies' }
]

const tagPool = [
  'Frontend', 'Backend', 'AI', 'iOS Developers', 'Remote', 'Full Stack Developers', 'Data Science', 
  'DevOps', 'Product', 'Design', 'Marketing', 'Sales', 'Finance', 'Legal',
  'San Francisco', 'New York', 'London', 'Seattle', 'Austin', 'Berlin', 'Denver',
  'Web3', 'Blockchain', 'Robotics', 'Cyber Security', 'E-commerce', 'Hardware',
  'Mobile', 'Android Developers', 'Machine Learning', 'Cloud', 'SaaS', 'Aerospace',
  'Artificial Intelligence', 'Mental Health', 'React Developers', 'Node JS Developers', 'Flutter Developers'
]

interface FloatingTag {
  label: string
  top: string
  left: string
  scale: number
  duration: string
  delay: string
  depth: number // Added for individual parallax depth
}

const floatingTags = ref<FloatingTag[]>([])
const mouseX = ref(0)
const mouseY = ref(0)

const handleMouseMove = (e: MouseEvent) => {
  const x = (e.clientX / window.innerWidth) - 0.5
  const y = (e.clientY / window.innerHeight) - 0.5
  mouseX.value = x * 60
  mouseY.value = y * 60
}

const handleTagClick = (label: string) => {
  console.log(`[Wellfound Hero] Filtering by tag: ${label}`)
}

onMounted(() => {
  window.addEventListener('mousemove', handleMouseMove)
  
  const count = Math.floor(Math.random() * 10) + 20 // 20-30 tags
  const newTags: FloatingTag[] = []
  
  for (let i = 0; i < count; i++) {
    const label = tagPool[Math.floor(Math.random() * tagPool.length)]
    
    // Random position avoiding the dead center (where the title is)
    let top = Math.random() * 90 + 5 // 5% to 95%
    let left = Math.random() * 90 + 5 // 5% to 95%
    
    // Basic avoidance of the center area (40-60% range)
    if (top > 35 && top < 65 && left > 30 && left < 70) {
      top = top < 50 ? top - 25 : top + 25
      left = left < 50 ? left - 25 : left + 25
    }

    newTags.push({
      label,
      top: `${top}%`,
      left: `${left}%`,
      scale: 0.8 + Math.random() * 0.4, // 0.8 to 1.2
      duration: `${4 + Math.random() * 4}s`,
      delay: `${-Math.random() * 8}s`,
      depth: 0.5 + Math.random() // Random depth factor for parallax
    })
  }
  floatingTags.value = newTags
})

onUnmounted(() => {
  window.removeEventListener('mousemove', handleMouseMove)
})
</script>

<template>
  <div class="relative min-h-screen bg-white text-black font-wellfound overflow-hidden">
    <!-- Floating Background Layer with Parallax -->
    <div 
      class="absolute inset-0 z-0 pointer-events-none overflow-hidden transition-transform duration-700 ease-out"
      :style="{ transform: `translate(${mouseX}px, ${mouseY}px)` }"
    >
      <div 
        v-for="(tag, index) in floatingTags" 
        :key="index"
        class="absolute pointer-events-auto cursor-pointer group"
        :style="{ 
          top: tag.top, 
          left: tag.left,
          transform: `translate(${mouseX * tag.depth * 0.2}px, ${mouseY * tag.depth * 0.2}px)`
        }"
        @click="handleTagClick(tag.label)"
      >
        <div 
          class="px-4 py-2 bg-white/60 backdrop-blur-md border border-white/20 rounded-full shadow-[0_4px_20px_-4px_rgba(0,0,0,0.1)] text-xs md:text-sm font-bold text-gray-500/80 animate-float transition-all duration-300 group-hover:scale-105 group-hover:shadow-xl group-hover:text-black group-hover:bg-white group-hover:opacity-100"
          :style="{ 
            transform: `scale(${tag.scale})`,
            animationDuration: tag.duration,
            animationDelay: tag.delay
          }"
        >
          {{ tag.label }}
        </div>
      </div>
    </div>

    <!-- Navigation Bar -->
    <nav class="relative z-20 flex items-center justify-between px-6 py-6 md:px-16 animate-slide-down">
      <div class="flex items-center gap-10">
        <!-- Logo -->
        <a href="/" class="flex items-center text-3xl font-bold tracking-tighter">
          wellfound<span class="text-wellfound-red">:</span>
        </a>
        
        <!-- Desktop Nav -->
        <div class="hidden md:flex items-center gap-8">
          <router-link
            v-for="link in navLinks" 
            :key="link.label" 
            :to="link.href"
            class="text-base font-semibold text-gray-600 hover:text-black transition-colors"
          >
            {{ link.label }}
          </router-link>
        </div>
      </div>

      <div class="flex items-center gap-6">
        <button class="px-6 py-2.5 text-base font-bold hover:bg-gray-100 rounded-xl transition-colors">
          Log In
        </button>
        <button class="px-8 py-2.5 text-base font-bold bg-black text-white rounded-xl hover:bg-zinc-800 transition-colors">
          Sign Up
        </button>
      </div>
    </nav>

    <!-- Hero Content -->
    <main class="relative z-10 flex flex-col items-center justify-center pt-[10vh] pb-10 text-center px-6 animate-zoom-in">
      <div class="max-w-5xl mx-auto">
        <h1 class="text-6xl md:text-[110px] font-black tracking-tighter leading-[0.85] flex items-center justify-center flex-wrap gap-x-8 gap-y-4">
          <span class="inline-flex items-center text-black">
            W<span class="text-wellfound-red">:</span>
          </span>
          <span>Find what's next</span>
        </h1>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* Custom animations or refinements if needed */
</style>
