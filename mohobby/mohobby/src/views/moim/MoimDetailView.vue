<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" rel="stylesheet"></link>
<template>
  <div>
    <!-- 권한에 따라 컴포넌트 다르게 보여주기 -->
    <MoimSidebar v-if="moimInfo.memberId != this.$store.state.id" :moimId="Id"/>
    <MoimSidebar_admin v-else></MoimSidebar_admin>
    
    <div v-if="moimInfo.moimOpen == 1">
    <InvisibleBoard></InvisibleBoard>
    </div>
    <div v-if="moimInfo.moimOpen == 0">
    <router-view/>
    </div>
  </div>
</template>
<script>
import MoimSidebar from "@/components/moim/moimDetail/MoimSidebar.vue";
import MoimSidebar_admin from "@/components/moim/moimDetail/MoimSidebar_admin.vue";
import InvisibleBoard from "../../components/moim/moimDetail/invisibleBoard.vue";

export default {
  components: { MoimSidebar, MoimSidebar_admin, InvisibleBoard },
  data() {
    return {
     Id : this.$route.params.moimId,
     moimInfo : [],
    }
  },
  setup() { },
  created() {
  },
  mounted() {
    this.getMoimOneInfo();
  },
  unmounted() { },
  methods: {
    getMoimOneInfo() {
      const vm = this;
       this.axios({
        url: "http://localhost:8088/java/moimOneInfo/" + this.Id,
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          vm.moimInfo = response.data;
          console.log(vm.moimInfo);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
      
  },
  props: [],
}
</script>

<style scoped>
</style>