<template>
  <div>
    <v-card class="mx-auto" width="800" min-height="520">
      <v-toolbar color="#2255b1" dark> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ keyword }}</v-toolbar>
      <v-list three-line>
        <div v-for="(user, i) in userResult" :key="i">
          <template>
       
            <v-list-item>
              <v-list-item-avatar>
                <!-- 팔로워 목록 클릭시 -->
                <v-img v-if="followType === 1" @click="goUserProfile(user.followerId)"
                  :src="require(`@/assets/image/user/${user.profileImg}`)" />

                <!-- 팔로잉 목록 클릭시 -->
                <v-img v-else-if="followType === 2" @click="goUserProfile(user.followingId)"
                  :src="require(`@/assets/image/user/${user.profileImg}`)" />

                <!-- 유저검색 -->
                <v-img v-else-if="followType === 3" @click="goUserProfile(user.memberId)" :src="require(`@/assets/image/user/${user.profileImg}`)" />
              </v-list-item-avatar>
              <v-list-item-content>


                <div v-if="followType === 1">
                  <!-- 팔로워 목록 : followerId -> 구독 당하는 사람이 나임  -->
                  <v-list-item-title v-html="user.followerId" />
                </div>

                
                <!-- 팔로잉 목록 : followingId -> 구독 하는 사람이 나임 -->
                <div v-else-if="followType === 2">
                  <v-list-item-title v-html="user.followingId" />
                </div>


                <!-- 유저검색 목록: memberId -->
                <div v-else>
                  <v-list-item-title v-show="user.memberId" v-html="user.memberId" />
                  <v-list-item-subtitle v-html="user.nickname" />
                </div>
              </v-list-item-content>


              <!-- 팔로우 = 내가 구독 안한상태면 활성화 -->
              <div v-if="followType == 1">
                <v-btn v-if="followerCheck[i] === 1" @click="unfollow(user.followingId, user.followerId)" rounded
                  color="#F36A3E" class="white--text">언팔로우</v-btn>

                <v-btn v-else-if="followerCheck[i] === 0" @click="followup(user.followingId, user.followerId)" rounded
                  color="#2255b1" class="white--text">팔로우</v-btn>
              </div>
              <div v-else-if="followType == 2">
                <v-btn @click="unfollow(user.followerId,user.followingId)" rounded color="#F36A3E" class="white--text"> 언팔로우
                </v-btn>
              </div>
            </v-list-item>
          </template>
        </div>
      </v-list>
    </v-card>
  </div>
</template>
<script>
import CmtReg from "@/components/sns/FeedDetail/CmtReg.vue";
export default {
  name: "UserResult",
  components: { CmtReg },
  props: {
    followType: Number,
    userResult: [], //유저검색 결과
    keyword: String, //키워드
    followerCheck: [], //팔로우 여부 체크 . 1:맞팔
  },
  data() {
    return {

      users: [], //props 담을 변수
      memberId: this.$store.state.id,

    };
  },
  created() {
    console.log("User page");
    console.log(this.followerCheck);
  },
  // watch: {
  //   userResult(){
  //     this.users=this.userResult
  //   }
  // },


  methods: {
    //유저 프로필로 이동
    goUserProfile(userId) {
      this.$router.replace({ path: "/sns-user-feed", query: { userId: userId } });
    },


    //팔로우
    followup(memberId, userId) {
      this.follower = [],

      this.axios
        .post("/sns/follow", {
          followerId: memberId, //로그인세션 아이디
          followingId: userId, //피드주인 아이디
        })
        .then((res) => {
          console.log("팔로우 성공!");
          this.$emit("updateFollow")
        })
        .catch((err) => {
          console.log(err);
        });
    },


    //언팔로우
    unfollow(memberId, userId) { //내 아이디, userId
      this.axios
        .delete("/sns/follow/" + memberId + "/" + userId)
        .then((res) => {
          console.log("언팔로우 성공!");
          this.$emit("updateFollow")
        })
        .catch((err) => {
          console.log(err);
        });
    },
  }
}
</script>
