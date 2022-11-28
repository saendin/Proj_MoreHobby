<template>
  <div class="container">
    <BoardButton></BoardButton>
    <MoimNotice></MoimNotice>
  </div>
</template>
<script>
  import MoimNotice from "@/components/moim/moimDetail/notice/MoimNotice.vue";
import BoardButton from "@/components/moim/moimDetail/board/BoardButton.vue";
  export default {
  components: {MoimNotice, BoardButton },
  data: () => ({
    }),
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
  .container {
  width : 80%;
}
</style>