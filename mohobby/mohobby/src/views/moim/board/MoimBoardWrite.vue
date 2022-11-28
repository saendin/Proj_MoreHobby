<template>
  <div class="container">
    <BoardButton></BoardButton>
    <MoimBoardEditor></MoimBoardEditor>
  </div>
</template>

<script>
  import MoimSidebar from "@/components/moim/moimDetail/MoimSidebar.vue"
  import MoimBoardEditor from "@/components/moim/moimDetail/board/write/MoimBoardEditor.vue";
import BoardButton from "@/components/moim/moimDetail/board/BoardButton.vue";
  export default {
    components : { MoimSidebar, MoimBoardEditor, BoardButton }
  }

</script>

<style scoped>
  .container{
    width: 85%;
  }
</style>