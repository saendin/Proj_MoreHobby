<template>
  <div class="mx-auto">
    <SnsSidebar />
    <div style="margin-top: 3rem">
      <v-tabs color="#2255b1" fixed-tabs>
        <v-tab @click="show=true">팔로워 목록</v-tab>
        <v-tab @click="show=false">팔로잉 목록</v-tab>
      </v-tabs>
      <!-- 팔로워목록 -->
      <Follow v-if="show == true" 
              :userResult="followerLists" 
              :keyword="follower" 
              :followerCheck="followerCheck" 
              :followType="followerType" 
              @updateFollow="updateFollow" />
      
      <!-- 팔로잉목록 -->
      <Follow v-else 
              :userResult="followingLists" 
              :keyword="following"
              :followerCheck="followerCheck" 
              :followType="followingType" 
              @updateFollow="updateFollow" />

      <NoFollow v-show="noShow == true" :keyword="keyword" />
    </div>
  </div>
</template>
<script>
    import SnsSidebar from "@/components/sns/Common/SnsSidebar";
    import Follow from "@/components/sns/Management/User";
    import NoFollow from "@/components/sns/Management/NoFollow";
import router from "@/router";
    // import ShowMgmnt from "@/components/sns/Management/ShowMngmt";

  export default {
    name: "Follower",
    components: { SnsSidebar, Follow, NoFollow },
    data() {
      return {
        followingLists: [], //내가 구독하는 사람들
        followerLists: [], //나를 구독하는 사람들
        follower:"",
        following:"",
        followerType:1,
        followingType:2,
        
        show: Boolean,

        followers: [],
        followings: [],

        followerCheck: [],

        keyword:"",

        noShow: false,
        check :false,
        memberId : this.$store.state.id,
      };
    },
    setup() {
      
    },
    created() {
      // this.getFollowingList();
      // this.getFollowerList();
      this.updateFollow();
      this. show = true;
    },
    mounted() {
  
    },
    unmounted() {
  
    },
    
    watch: {

    },


    methods: {
      //팔로우상태 변경
      updateFollow(){
          this.getFollowingList();
          this.getFollowerList();
      },

    //팔로우 상태 조회
/*
    checkFollow(memberId, userId) {
      this.axios("/sns/follow/check", {
        params: {
          myId: memberId,
          targetId: userId,
        },
      })
        .then((res) => {
          this.followStatus = res.data;
          console.log("팔로우상태: " + this.followStatus);
        })
        .catch((err) => {
          alert(err);
        });
    },
*/

      //팔로우 체크
      checkFollow(){
        this.followerCheck = [];
        for(let i=0; i<this.followerLists.length; i++){          
          for(let j=0; j<this.followingLists.length; j++){
            if(this.followerLists[i].followerId==this.followingLists[j].followingId){
              this.followerCheck.push(1); // 맞팔이면 1
              this.check=true; // 위의 조건을 만족하면 check값을 true로    
              console.log(i + " : " + this.followerCheck);
              break;
            }
          }
          if(this.check == false){ // 맞팔이 아니면 0 push
            this.followerCheck.push(0);
          }
          this.check=false //check값 초기화
          // console.log(i + " : " + this.followerCheck);
        }
        console.log("followCheck : " + this.followerCheck);
      },


      //팔로워 목록조회
      getFollowerList() {
        let vm = this;
        this.axios('/sns/follow/search/follower/'+ this.memberId)
        
        .then(res => {

            this.followerLists = res.data;

            this.followers = this.followerLists.followerId;
            this.follower= this.memberId + " 님의 팔로워 목록입니다"

            //팔로워 값이 없을 때 노쇼페이지 노출
            if(this.followerLists.length === 0){
              this.noShow = true;
              this.show = false;
              this.keyword="팔로워";
            }
            vm.checkFollow();
            console.log("팔로워목록 호출 성공");
        }).catch(err =>{
            console.log(err);
        });

      },
      

      //팔로잉 목록조회
      getFollowingList() {
        let vm = this;
        this.followerCheck = [];
        this.axios('/sns/follow/search/following/' + this.memberId)

        .then(res => {

            this.followingLists = res.data;
            this.followings = this.followingLists.followingId;
            this.following= this.memberId + " 님의 팔로잉 목록입니다"

            //팔로잉 값이 없을 때 노쇼페이지 노출
            if(this.followingLists.length === 0){
              this.noShow = true;
              this.show = false;
              this.keyword="팔로잉";
            }
            console.log("팔로잉목록 호출 성공");
            vm.checkFollow();
        }).catch(err =>{
            console.log(err);
        });

    },
  

  },
};
</script>