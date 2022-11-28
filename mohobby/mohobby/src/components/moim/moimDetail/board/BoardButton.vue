<template>
  <!-- 버튼 -->
  <div class="container">
  <v-card-actions>
    <v-spacer></v-spacer>
    <v-card-actions v-if="(moim===0 && moimRight===1) || (moim===1 && moimRight===1)">
      <v-btn class="mr-3" @click="write(moimId)"><v-icon>mdi-lead-pencil</v-icon></v-btn>
    </v-card-actions>
    <v-card-actions v-if="(moim===0 && moimRight===0) || (moim===1 && moimRight===0)">
      <v-btn disabled class="mr-3"><v-icon>mdi-lead-pencil</v-icon></v-btn>
    </v-card-actions>
  </v-card-actions>
  </div>
</template>
<script>
  import MoimSidebar from "@/components/moim/moimDetail/MoimSidebar.vue";

  export default {
  components: { MoimSidebar },
  data() {
  return {
    moimId: this.$route.params.moimId,
    moim: 1,
    moimRight : 1,
  }
  },
  setup() {},
  created() {
  },
  mounted() {},
  unmounted() {},
  methods : {
    write : function(moimId) {
      console.log(moimId)
      this.$router.push({ name : 'moimboardwrite', query : {moimId : moimId}})
    }
  }
}
</script>

<style scoped>

.container {
  margin-right : 50px;
}

</style>