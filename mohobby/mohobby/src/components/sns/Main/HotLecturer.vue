<template>
  
  <!-- <div class="cards-container" > -->
    <v-container fluid>
    <!-- FIRST CARD -->
    <v-slide-group class="pa-2">
        <v-slide-item  v-for="(info,i) in infoList" :key="i" style="width:280px;">
          <div class="card card-first">
            <div class="card-header-wrapper" @click="goUserFeed(info.memberId)">
                <h2 class="card-title"></h2>
                <h4 class="card-subtitle"><span class="subtitle_userId">{{info.memberId}}</span></h4>
                <div class="card-avatar-wrapper" style="width:200px;">
                    <img class="card-avatar" :src="require(`@/assets/image/user/${info.profileImg}`)" alt="프로필사진" style="cursor:pointer;">
                </div>
            </div>
            <div class="card-photo-wrapper" style="cursor:pointer;" @click="getFeedDetail(info.memberId, info.postId)">
              <v-icon v-show="info.imgs > 1" class="image_icon">mdi-checkbox-multiple-blank</v-icon>
              <img class="card-photo" :src="require(`@/assets/image/sns/${info.postId}/${info.thumbnail}`)" alt="썸네일">
            </div>
            <p class="card-text">{{ info.content }}</p>
            <div class="card-like" height="32" width="32">
                <div style="width:130px;">
                  <v-icon color="#bdbdbd">mdi-heart</v-icon> {{ info.likes }} 좋아요
                  <v-icon color="#bdbdbd" class="ml-1 mr-1">mdi-chat-outline</v-icon>
                  <span class="mr-2 mt-1">{{ info.cmts }} 개</span>
                </div>
            </div>
            <button class="card-button" @click="getFeedDetail(info.memberId, info.postId)">More</button>
          </div>
      </v-slide-item>
    </v-slide-group>
  </v-container>
  <!-- </div> -->
</template>
<script>
export default {
  name: "HotLecturer",
  props:{
      items : []
  },
  data() {
    return {
      infoList: [],
      memberId: this.$store.state.id, //세션아이디
    };
  },
  setup() {
    
  },
  created() {
    this.getHotLecturerList();
    console.log(this.memberId);
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

    //hot강의 피드
    getHotLecturerList() {
        //hotLectureList조회
        this.axios('/sns/main/top20LecturerFeeds')
        .then(res => {
            this.infoList = res.data;
           
          }).catch(err =>{
            console.log(err);
          });
      },


    //유저피드로 이동
    goUserFeed(userId) {
      this.$router.push({ path: "/sns-user-feed", query: { userId: userId } });
    },
  },
};
</script>

<style scoped lang="css" src="@/assets/css/sns/HotLecturer.css" />