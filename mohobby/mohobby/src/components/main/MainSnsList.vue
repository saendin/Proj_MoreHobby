<template>
   <v-container fluid>
    <v-sheet style="background-color: #e9e9ec; border-radius: 5px" max-width="2200" height="650">
      <v-slide-group class="pt-12">
        <v-slide-item v-for="(info,i) in snsHighLikesList.data" :key="i">
          <div style="width: 350px">
            <!-- FIRST CARD -->
            <div class="card card-first" style="width: 350px">
              <div class="card-header-wrapper" @click="goMyFeed(info.memberId)">
                <h2 class="card-title"></h2>
                <h4 class="card-subtitle">{{info.memberId}}</h4>
                <div class="card-avatar-wrapper" style="width:200px;">
                  <img class="card-avatar" :src="require(`@/assets/image/user/${info.profileImg}`)" alt="프로필사진">
                </div>
              </div>
              <div class="card-photo-wrapper" @click="getFeedDetail(info.memberId, info.postId)">
                <img class="card-photo" :src="require(`@/assets/image/sns/${info.postId}/${info.thumbnail}`)" alt="썸네일">
              </div>
              <p class="card-text">{{ info.content }}</p>
              <div class="card-like" height="32" width="32">
                <div style="width:130px;"><v-icon color="#e9e9ec">mdi-heart</v-icon> {{ info.likes }} likes</div>       
              </div>
              <button class="card-button">More</button>
            </div>
          </div>
        </v-slide-item>
      </v-slide-group>
    </v-sheet>
  </v-container>
</template>
<script>
export default {
  props:{
     snsHighLikesList: {
      type: [],
      required: true,
    },
  },
  data() {
    return {
      listInfo: [],
    };
  },
  setup() {
    
  },
  created() {
  
  },
  mounted() {

  },
  unmounted() {

  },
  methods: {
    //피드상세로 이동
    getFeedDetail(memberId, postId) {
        this.$router.push({ path: '/sns-feed-detail', query: {writer : memberId, postId : postId} });
      },
    //유저피드로 이동
    goMyFeed(userId) {
      this.$router.push({ path: "/sns-user-feed", query: { userId: userId } });
    },
  },
};
</script>

<style scoped lang="css" src="@/assets/css/sns/HotLecturer.css" />
