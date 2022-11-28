<template>
  <div id = "container">
    <SnsSearchbar></SnsSearchbar>
    <div>
      <v-btn @click="goUserFeed()">유저피드테스트</v-btn>
    </div>
    <!-- 인기 피드리스트 -->
      <h3>추천 만능 재주꾼들 피드</h3>
      <HotLecturerSpace name="items"/>
      <h3>재주 견습생들 피드</h3>
      <!-- 랜덤피드 무한스크롤링 -->
        <!-- <ul v-if="noneUser===9">    -->
      <NoneUser />
    <!-- </ul> -->
  </div>
</template>
<script>

  import HotLecturerSpace from "../../components/sns/Main/HotLecturerList";
  import NoneUser from "../../components/sns/Main/Noneuser";

  export default {
    name: "snsMain",
    components: { SnsSearchbar, HotLecturerSpace, NoneUser },
    data() {
      return {
        items: [],
      };
    },
    setup() {
      
    },
    created() {
      this.getHotLecturerList();
    },
    mounted() {
      
    },
    unmounted() {
  
    },
    methods: {
      getFeedDetail(postId) {
        this.$router.push({ name: 'snsFeedDetail',
                            query: {id : postId} 
                          });
      },
      getHotLecturerList() {
        //hotLectureList조회
        this.axios('/sns/main/top20LecturerFeeds').then(res => {
            console.log(res);
            this.items = res.data;
            
            console.log(this.items);
            console.log(this.items.data[0].cmts);
          }).catch(err =>{
            console.log(err);
          });
      },
      // feedDetail(postId){
      //   this.$router.push( '/snsFeedDetail?postId='+ postId)
      feedDetail(){
      this.$router.push( '/snsFeedDetail');
      }
    }};
</script>

<style scoped>
  * {
    list-style:none;
  }
  #container {
    width : 80%;
    list-style:none;
    margin: 0 auto;
  }
</style>
