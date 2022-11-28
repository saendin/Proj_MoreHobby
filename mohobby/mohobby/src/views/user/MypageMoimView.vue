<template>
  <div>
    <UserSidebar></UserSidebar>
    <h3>내가 참여중인 소모임</h3>
      <Category @sendSelectedCatg="getselectedCatg"></Category>
      <div class="box" @click="goToMoim(idx)" v-for="(takeMoim,idx) in takeMoimList" :key="idx">
  <v-card
    class="mx-3"
    max-width="550"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="text-overline mb-4">
          {{takeMoim.moimName}}
        </div>
        <v-list-item-subtitle>{{takeMoim.moimInfo}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-list-item-avatar
        tile
        size="80">
        <img :src="require(`@/assets/image/moim/profile` + takeMoim.profileImg)" />
      </v-list-item-avatar>
    </v-list-item>
    <v-card-actions>
       <span class="people">
          {{manageMoim.regCnt}}명 참여중
    </span>
    <v-spacer></v-spacer>
      <v-chip 
        outlined
        rounded
        text
      >
      {{takeMoim.moimRegion}}
      </v-chip>
      <v-chip
        outlined
        rounded
        text
      >
      {{takeMoim.moimCatg}}
      </v-chip>
    </v-card-actions>
  </v-card>  
</div>
    <br>
    <h3>내가 운영중인 소모임</h3>
    <Category @sendSelectedCatg="getselectedCatg"></Category>
      <div class="box" @click="goToMoim(idx)" v-for="(manageMoim,idx) in manageMoimList" :key="manageMoim.title">
  <v-card
    class="mx-3"
    max-width="550"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="text-overline mb-4">
          {{manageMoim.moimName}}
        </div>
        <v-list-item-subtitle>{{manageMoim.moimInfo}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-list-item-avatar
        tile
        size="80">
        <img :src="require(`@/assets/image/moim/profile` + manageMoim.profileImg)" />
      </v-list-item-avatar>
    </v-list-item>
    <v-card-actions>
      <span class="people">
          {{manageMoim.regCnt}}명 참여중
    </span>
    <v-spacer></v-spacer>
      <v-chip 
        outlined
        rounded
        text
      >
      {{manageMoim.moimRegion}}
      </v-chip>
      <v-chip
        outlined
        rounded
        text
      >
      {{manageMoim.moimCatg}}
      </v-chip>
    </v-card-actions>
  </v-card>  
</div>
  </div>
</template>
<script>
import Category from '@/components/user/Category.vue';
import UserSidebar from "../../components/user/UserSidebar.vue";

export default {
  name: "",
  components: { UserSidebar, Category },
  data() {
    return {
        items: [],
        memberId : this.$store.state.id,
        moimCatg : "",
        takeMoimList : [],
        manageMoimList : [],
        catg : [],
        selectedCatg : "",
    };
  },
  setup() {},
  created() {
  },
  mounted() {
  },
  unmounted() {},
  methods: {
    getselectedCatg(data) {
      console.log("부모컴포넌트" + data);
      this.selectedCatg = data;
      this.getTakeMoim();
    },
    // 참여 중 소모임 목록 가져오기
  getTakeMoim() {
const vm = this;
      this.axios({
        url: "http://localhost:8088/java/mypagemoim",
        method: "post",
        data: {
          memberId : this.memberId,
          memberRole : 0,
          keywordName : this.selectedCatg,
        }
      })
        .then(function (response) {
          console.log("참여 중 소모임 " + response.data);
          vm.MoimList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 운영 중 소모임 목록 가져오기
    getManageMoim() {
      const vm = this;
      this.axios({
        url: "http://localhost:8088/java/mypagemoim",
        method: "post",
        data: {
          memberId : this.memberId,
          memberRole : 1,
          keywordName : this.selectedCatg,
        }
      })
        .then(function (response) {
          console.log("운영 중 소모임 " + response.data);
          vm.manageMoimList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    goToTakeMoim(idx) {
      this.$router.push({ name : 'moimBoard' , params : { moimId : this.takeMoim[idx].moimId, boardType: 1}})
    },

    goToManageMoim(idx) {
      this.$router.push({ name : 'moimBoard' , params : { moimId : this.manageMoim[idx].moimId, boardType: 1}})
    }
  },
};
</script>

<style scoped>
  .container {
      margin: 0 auto;
      width : 1000px;
  }
  .box {
      float: left; 
      width: 50%;
      margin-bottom: 15px;
      cursor : pointer;    
  }
</style>