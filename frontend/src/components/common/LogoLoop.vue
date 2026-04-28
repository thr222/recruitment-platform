<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, useSlots, watch } from 'vue';
import './LogoLoop.css';

interface LogoItem {
  src?: string;
  alt?: string;
  href?: string;
  title?: string;
  node?: any;
  ariaLabel?: string;
}

const props = withDefaults(defineProps<{
  logos: LogoItem[];
  speed?: number;
  direction?: 'left' | 'right' | 'up' | 'down';
  width?: number | string;
  logoHeight?: number;
  gap?: number;
  pauseOnHover?: boolean;
  hoverSpeed?: number;
  fadeOut?: boolean;
  fadeOutColor?: string;
  scaleOnHover?: boolean;
  ariaLabel?: string;
  className?: string;
}>(), {
  speed: 120,
  direction: 'left',
  width: '100%',
  logoHeight: 28,
  gap: 32,
  pauseOnHover: true,
  hoverSpeed: 0,
  fadeOut: false,
  ariaLabel: 'Partner logos',
  scaleOnHover: false
});

const ANIMATION_CONFIG = { SMOOTH_TAU: 0.25, MIN_COPIES: 2, COPY_HEADROOM: 2 };

const containerRef = ref<HTMLElement | null>(null);
const trackRef = ref<HTMLElement | null>(null);
const seqRef = ref<HTMLElement | null>(null);

const seqWidth = ref(0);
const seqHeight = ref(0);
const copyCount = ref(ANIMATION_CONFIG.MIN_COPIES);
const isHovered = ref(false);

const effectiveHoverSpeed = computed(() => {
  if (props.hoverSpeed !== undefined && isHovered.value) return props.hoverSpeed;
  if (props.pauseOnHover && isHovered.value) return 0;
  return undefined;
});

const isVertical = computed(() => props.direction === 'up' || props.direction === 'down');

const targetVelocity = computed(() => {
  const magnitude = Math.abs(props.speed);
  let directionMultiplier;
  if (isVertical.value) {
    directionMultiplier = props.direction === 'up' ? 1 : -1;
  } else {
    directionMultiplier = props.direction === 'left' ? 1 : -1;
  }
  const speedMultiplier = props.speed < 0 ? -1 : 1;
  return magnitude * directionMultiplier * speedMultiplier;
});

const updateDimensions = () => {
  if (!containerRef.value || !seqRef.value) return;
  
  const containerWidth = containerRef.value.clientWidth;
  const sequenceRect = seqRef.value.getBoundingClientRect();
  const sequenceWidth = sequenceRect.width;
  const sequenceHeight = sequenceRect.height;

  if (isVertical.value) {
    const parentHeight = containerRef.value.parentElement?.clientHeight ?? 0;
    if (parentHeight > 0) {
      containerRef.value.style.height = `${parentHeight}px`;
    }
    if (sequenceHeight > 0) {
      seqHeight.value = Math.ceil(sequenceHeight);
      const viewport = containerRef.value.clientHeight || parentHeight || sequenceHeight;
      const copiesNeeded = Math.ceil(viewport / sequenceHeight) + ANIMATION_CONFIG.COPY_HEADROOM;
      copyCount.value = Math.max(ANIMATION_CONFIG.MIN_COPIES, copiesNeeded);
    }
  } else if (sequenceWidth > 0) {
    seqWidth.value = Math.ceil(sequenceWidth);
    const copiesNeeded = Math.ceil(containerWidth / sequenceWidth) + ANIMATION_CONFIG.COPY_HEADROOM;
    copyCount.value = Math.max(ANIMATION_CONFIG.MIN_COPIES, copiesNeeded);
  }
};

let rafId: number | null = null;
let lastTimestamp: number | null = null;
let offset = 0;
let currentVelocity = 0;

const animate = (timestamp: number) => {
  if (lastTimestamp === null) lastTimestamp = timestamp;
  const deltaTime = Math.max(0, timestamp - lastTimestamp) / 1000;
  lastTimestamp = timestamp;

  const target = effectiveHoverSpeed.value !== undefined ? effectiveHoverSpeed.value : targetVelocity.value;
  const easingFactor = 1 - Math.exp(-deltaTime / ANIMATION_CONFIG.SMOOTH_TAU);
  currentVelocity += (target - currentVelocity) * easingFactor;

  const seqSize = isVertical.value ? seqHeight.value : seqWidth.value;

  if (seqSize > 0 && trackRef.value) {
    offset += currentVelocity * deltaTime;
    offset = ((offset % seqSize) + seqSize) % seqSize;

    const transformValue = isVertical.value
      ? `translate3d(0, ${-offset}px, 0)`
      : `translate3d(${-offset}px, 0, 0)`;
    trackRef.value.style.transform = transformValue;
  }

  rafId = requestAnimationFrame(animate);
};

let resizeObserver: ResizeObserver | null = null;

onMounted(() => {
  updateDimensions();
  
  if (window.ResizeObserver && containerRef.value) {
    resizeObserver = new ResizeObserver(updateDimensions);
    resizeObserver.observe(containerRef.value);
    if (seqRef.value) resizeObserver.observe(seqRef.value);
  }

  // Handle image loading
  const images = seqRef.value?.querySelectorAll('img') ?? [];
  images.forEach(img => {
    if (img.complete) {
      updateDimensions();
    } else {
      img.addEventListener('load', updateDimensions, { once: true });
      img.addEventListener('error', updateDimensions, { once: true });
    }
  });

  rafId = requestAnimationFrame(animate);
});

onUnmounted(() => {
  if (rafId) cancelAnimationFrame(rafId);
  if (resizeObserver) resizeObserver.disconnect();
});

const cssVariables = computed(() => ({
  '--logoloop-gap': `${props.gap}px`,
  '--logoloop-logoHeight': `${props.logoHeight}px`,
  ...(props.fadeOutColor && { '--logoloop-fadeColor': props.fadeOutColor })
}));

const rootClassName = computed(() => [
  'logoloop',
  isVertical.value ? 'logoloop--vertical' : 'logoloop--horizontal',
  props.fadeOut ? 'logoloop--fade' : '',
  props.scaleOnHover ? 'logoloop--scale-hover' : '',
  props.className || ''
].filter(Boolean).join(' '));

const containerStyle = computed(() => {
  const widthVal = typeof props.width === 'number' ? `${props.width}px` : props.width;
  return {
    width: isVertical.value ? (widthVal === '100%' ? undefined : widthVal) : (widthVal ?? '100%'),
    ...cssVariables.value
  };
});

const handleMouseEnter = () => {
  isHovered.value = true;
};
const handleMouseLeave = () => {
  isHovered.value = false;
};

const slots = useSlots();
</script>

<template>
  <div
    ref="containerRef"
    :class="rootClassName"
    :style="containerStyle"
    role="region"
    :aria-label="ariaLabel"
  >
    <div
      ref="trackRef"
      class="logoloop__track"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
    >
      <ul
        v-for="copyIndex in copyCount"
        :key="`copy-${copyIndex}`"
        class="logoloop__list"
        role="list"
        :aria-hidden="copyIndex > 1"
        :ref="el => { if (copyIndex === 1) seqRef = (el as HTMLElement) }"
      >
        <li
          v-for="(item, itemIndex) in logos"
          :key="`${copyIndex}-${itemIndex}`"
          class="logoloop__item"
          role="listitem"
        >
          <slot v-if="slots.item" name="item" :item="item" :index="itemIndex"></slot>
          <template v-else>
            <a
              v-if="item.href"
              class="logoloop__link"
              :href="item.href"
              :aria-label="item.ariaLabel || item.title || 'logo link'"
              target="_blank"
              rel="noreferrer noopener"
            >
              <span v-if="item.node" class="logoloop__node">{{ item.node }}</span>
              <img
                v-else-if="item.src"
                :src="item.src"
                :alt="item.alt || ''"
                :title="item.title"
                loading="lazy"
                decoding="async"
                draggable="false"
              />
            </a>
            <template v-else>
              <span v-if="item.node" class="logoloop__node">{{ item.node }}</span>
              <img
                v-else-if="item.src"
                :src="item.src"
                :alt="item.alt || ''"
                :title="item.title"
                loading="lazy"
                decoding="async"
                draggable="false"
              />
            </template>
          </template>
        </li>
      </ul>
    </div>
  </div>
</template>
