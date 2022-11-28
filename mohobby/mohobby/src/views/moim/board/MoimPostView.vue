<template>
  <div>
    <BoardButton/>
    <MoimPost></MoimPost>
  </div>
</template>
<script>
  import MoimSidebar from "@/components/moim/moimDetail/MoimSidebar.vue";
  import MoimPost from "@/components/moim/moimDetail/board/MoimPost.vue";
import BoardButton from "@/components/moim/moimDetail/board/BoardButton.vue";
  export default {
  components: { MoimSidebar, MoimPost, BoardButton },
  data: () => ({
    }),
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
  },
};
</script>
<style scoped>

</style>