<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';
export default defineComponent({
  name: 'app',
  components: {
    TheHeader,
    TheFooter
  },
});
</script>

<style>
#components-layout-demo-top-side-2 {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 使容器至少占据整个视口高度 */
}

/* 确保router-view不与footer重叠 */
.router-view {
  flex: 1; /* 让router-view占据剩余空间 */
  overflow-y: auto; /* 如果内容溢出，则显示滚动条 */
}

.the-footer {
  /* 根据需要调整样式，例如设置背景色、文本颜色等 */
}
</style>