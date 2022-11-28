<template>
  <div class="container">
    <BoardButton></BoardButton>
    <BoardNoAuthority v-if="moim===0 && moimRight===0"></BoardNoAuthority>
    <MoimBoard v-else></MoimBoard>
  </div>
</template>

<script>
    import MoimBoard from "@/components/moim/moimDetail/board/MoimBoard.vue";
    import BoardNoAuthority from "@/components/moim/moimDetail/board/BoardNoAuthority.vue"
import BoardButton from "@/components/moim/moimDetail/board/BoardButton.vue";

    export default {
  components: { MoimBoard, BoardNoAuthority, BoardButton },
  data: () => ({

    fclick: true,
    // 비공개 : 0, 공개 : 1
    moim: 1,
    moimRight : 1, 
    }),
  }
</script>

<style scoped>
  .container {
  width : 80%;
}
</style>