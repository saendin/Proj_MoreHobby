<template>
  <div id="wrap_box">
    <SnsSidebar />
    <div class="container">
      <div class="profile">
        <!-- 프로필이미지 -->
        <div class="profile-image">
          <v-avatar class="ml-10 my-10 mr-4" color="grey darken-1" size="150">
            <v-img aspect-ratio="30" :src="require(`@/assets/image/user/${infoes.profileImg}`)" alt="profile_img" />
          </v-avatar>
        </div>
        <!-- 프로필이미지 끝 -->


        <!-- 내 게시물 정보(닉네임, 개시물, 팔로워, 팔로잉 개수) -->
        <div class="profile-user-settings">
          <h1 class="profile-user-name">{{ infoes.memberId }}</h1>
          <span class="profile-real-name">{{ infoes.nickname }}</span>
          <!-- 권한 마크 ---- 일반: 0/ 강사 : 1/ 소모임관리자 : 2/ 둘 다: 3-->
          <div title="강의를 개설한 만능 재주꾼에게만 주어지는 마크입니다" v-if="infoes.role == 1 || infoes.role == 3"
            class="btn profile-settings-btn" aria-label="profile settings">
            <v-icon color="blue">mdi-shield-check</v-icon><i class="fas fa-cog" aria-hidden="true"></i>
          </div>
          <div title="모임을 운영하는 투철한 모험가에게만 주어지는 마크입니다" v-if="infoes.role == 2 || infoes.role == 3"
            class="btn profile-settings-btn" aria-label="profile settings">
            <v-icon color="green">mdi-shield-star</v-icon><i class="fas fa-cog" aria-hidden="true"></i>
          </div>


          <!-- 메뉴팝업(신고)-->
          <v-menu v-if="sessionId && sessionId != infoes.memberId">
            <template v-slot:activator="{ on, attrs }">
              <v-btn dark icon v-bind="attrs" v-on="on">
                <v-icon color="grey">mdi-dots-vertical</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-for="(list, i) in lists" :key="i">
                <v-list-item-title style="cursor: pointer" @click="listBtn(i)">
                  <v-dialog v-model="userFlagModal" scrollable max-width="300px">
                    <template v-slot:activator="{ on, attrs }">
                      <div v-bind="attrs" v-on="on">{{ list.title }}</div>
                    </template>
                    <v-card>
                      <v-card-title>Select Country</v-card-title>
                      <v-divider></v-divider>
                      <v-card-text style="height: 300px;">
                        <v-radio-group v-model="selectedCode" column>
                          <v-radio label="부적절한 게시물 개시" value="us1"></v-radio>
                          <v-radio label="다른 유저에게 욕설, 비방" value="us2"></v-radio>
                          <v-radio label="게시글, 댓글 도배" value="us3"></v-radio>
                          <v-radio label="홍보성 게시물 반복적 개시" value="us4"></v-radio>
                          <v-radio label="기타" value="us5"></v-radio>
                        </v-radio-group>
                      </v-card-text>
                      <v-divider></v-divider>
                      <v-card-actions>
                        <v-btn color="blue darken-1" text @click="userFlagModal = false">
                          Close
                        </v-btn>
                        <v-btn color="blue darken-1" text @click="userFlagging()">
                          Save
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
        <div class="profile-stats">
          <ul>
            <li></li>
          </ul>
          <ul>
            <li><span >{{ infoes.postCnt }}</span> posts</li>
            <li>
              <FollowModal :followCnt="followerCnt" :text="followerText" :dataList="follower" />
            </li>
            <li>
              <FollowModal :followCnt="followingCnt" :text="followingText" :dataList="following"  />
            </li>
          </ul>
          

          <!-- 내 게시물 정보 끝 -->
          <ul>
            <!-- 소개 -->
            <li>
              <p class="profile-bio">{{ infoes.intro }}</p>
            </li>
            <li></li>
            <!-- 소개 끝 -->
          </ul>
        </div>

        <!-- 유저취미 -->
        <v-chip-group id="hobbyGroup" class="ml-8">
          <v-chip 
            v-for="(hobby,i) in hobbies" :key="i" color="#2255b1"
            @click="search($event)" dark >
            {{ hobby.keywordName }}
          </v-chip>
        </v-chip-group>
        <br />
          

        <!-- 버튼 : 유저본인이냐에 따라 버튼 바뀜 -->
        <div class="profile-bio">
          <ul v-if="sessionId && sessionId == infoes.memberId">
            <button class="btn profile-edit-btn" @click="goMypage(sessionId)">
              Edit Profile
            </button>
          </ul>
          <ul v-else>
            <div id="btn_wrap">
              <button v-if="followStatus === 0" @click="followup(sessionId, infoes.memberId)"
                class="btn profile-edit-btn2">
                Follow
              </button>
              <button v-else style="background-color: #F36A3E; color: white" @click="unfollow(sessionId, infoes.memberId)"
                class="btn profile-edit-btn2">
                Unfollow
              </button>
              <button class="btn profile-edit-btn2" @click="send(sessionId)">
                Message
              </button>
            </div>
          </ul>
        </div>
      </div>
    </div>
    <!-- 피드 컴포넌트 -->
    <div>
      <Feeds :userId="userId" />
    </div>

    <!-- 피드 끝 -->
  </div>
</template>
<script>
import Feeds from "@/components/sns/Profile/Feeds.vue";
import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";
import FollowModal from "@/components/sns/Profile/FollowModal.vue";
export default {
  name: "UserProfile",
  components: { SnsSidebar, Feeds, FollowModal },
  data() {
    return {

      hobbies: [],

      infoes: [],
      sessionId: this.$store.state.id,
      sessionInfo: this.$store.state.user,
      userId: "",
      // userId: this.$store.state.id, //임시(로그인유저프로필)
      follower: [],
      following: [],
      followerText: "follower",
      followingText:"following",

      followerCnt:"",
      followingCnt:"",

      lists: [  //메뉴 리스트
        { title: "신고하기" },
      ],
      followStatus: Number,

      // 신고
      selectedCode: '',
      userFlagModal: false,
      memberId: this.$store.state.id,
      flagedUser: 'user11',
      flagReason: "",
    }
  },

  created() {
    console.log("유저 프로필로 이동!");
    this.userId = this.$route.query.userId; //넘겨받은 유저아이디 바인딩
    console.log(this.$route.query.userId);
    this.loadUserProfile(this.userId);
    this.getUserHobby(this.userId);
    this.followCheck(this.sessionId, this.userId);
    this.getFollowing(this.userId);
    this.getFollower(this.userId);
  },
  //팔로우를 실행하면 follower모달의 다시실행된 getFollowingList값을 받아와 보내줘야함
  watch: {

    getFollowing() {

    }

  },
  watch: {
    //같은 이름의 data나 computed 속성의 data들이 변경될 때 메소드가 실행됨
    followerCnt(){

    },

    followingCnt(){

    }
  },

  methods: {

      //취미조회
      getUserHobby(userId) {
        this.axios('/sns/user/hobbies/' + userId)
        .then(res => {
            this.hobbies = res.data;
            console.log('infoes ---> ')
            console.log(this.hobbies);
          }).catch(err => {
            console.log(err);
      })
    },



    //프로필 업로드
    loadUserProfile(userId) {
      this.axios('/sns/user/profile/' + userId)
        .then(res => {
          this.infoes = res.data;
          console.log('infoes : ' + this.infoes)
          console.log(this.infoes);
        }).catch(err => {
          console.log(err);
        });
    },


    //세션유무 검증
    confirmMember(memberId) {
      if (memberId) {
        console.log("true");
        return true;
      } else {
        console.log("false");
        return false;
      }
    },


    //로그인 검증 모달
    loginConfirm() {
      this.$swal({
        title: "로그인하셔야 가능하세요🙏",
        text: "🙏로그인화면으로 이동부탁드립니다🙏",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#2255b1",
        cancelButtonColor: "#F36A3E",
        cancelButtonText: "취소",
        confirmButtonText: "이동",
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push({ path: "login" });
        }
      });
    },


    //팔로우 상태 조회
    followCheck(memberId, userId) {
      this.axios("/sns/follow/check", {
        params: {
          myId: memberId,
          targetId: userId,
        },
      })
        .then((res) => {
          this.followStatus = res.data;
        })
        .catch((err) => {
          this.$swal("오류가 발생했어요ㅠ ㅠ\n잠시후에 다시 시도해주세요!" + err);
        });
    },


    //팔로우
    followup(memberId, userId) {
      this.follower= [],
      console.log(memberId);
      if (this.confirmMember(memberId) == false) {
        this.loginConfirm();
      } else {
        this.axios
          .post("/sns/follow", {
            followerId: memberId, //로그인세션 아이디
            followingId: userId, //피드주인 아이디
          })
          .then((res) => {
            this.followStatus = 1;
            this.getFollower(userId);
          })
          .catch((err) => {
            this.$swal("팔로우에 실패했습니다ㅠ ㅠ\n잠시후에 다시 시도해주세요!"+err);
          });
      }
    },


    //언팔로우
    unfollow(memberId, userId) {
      this.follower= []
      if (this.confirmMember(memberId) == false) {
        this.loginConfirm();
      } else {
        this.axios
          .delete("/sns/follow/" + memberId + "/" + userId)
          .then((res) => {
            this.followStatus = 0;
            this.getFollower(userId);
          })
          .catch((err) => {
            this.$swal("언팔로우에 실패했습니다ㅠ ㅠ\n잠시후에 다시 시도해주세요!"+err);
          })
      }
    },

    
    // 팔로워 목록 불러오기
    getFollower(userId) {
      const vm = this;
      vm.follower=[];
      this.axios("sns/follow/search/follower/" + userId)
      
      .then(function(res) {

          for (let i = 0; i < res.data.length; i++) {
            vm.follower.push(res.data[i]);

            if(i < res.data.length-1){
                  vm.follower.push({ divider: true, inset: true });
              }
          }
          vm.followerCnt=res.data.length + " follower";
          console.log("팔로워 : ", vm.follower);
          console.log("몇명: " + vm.follower.length);
        })
        .catch(function (error) {
          this.$swal("잘못된 접근입니다!\n" + error);
        });
    },

    // 팔로잉 목록 불러오기
    getFollowing(userId) {
      const vm = this;
      vm.following=[];
      this.axios("sns/follow/search/following/" + userId)
      
      .then(function(res) {

          for (let i = 0; i < res.data.length; i++) {
            vm.following.push(res.data[i]);

            if(i < res.data.length-1){
                  vm.following.push({ divider: true, inset: true });
              }
        }
          vm.followingCnt=res.data.length + " following";
          console.log("팔로잉: ", vm.following);
        })
        .catch(function (error) {
          this.$swal("잘못된 접근입니다!\n" + error);
        });
    },

    //취미 검색
    search(e){
        //유저 취미 누르면 같은 값 가진 모든 포스트뜸
        let getHobby = e.target.innerText; //선택한 해시태그
        this.$router.push({ name: "mainsearch", query: { searchText : getHobby },
      });
    },


    //마이페이지로 이동
    goMypage(memberId) {
      this.$router.push({
        path: "/mypageprofile",
        query: { memberId: memberId },
      });
    },
    //채팅방 이동
    send(memberId) {
      let vm = this;
      if (this.confirmMember(memberId) == false) {
        this.loginConfirm();
      } else {
        this.axios
          .get("/getSnsChatRoomNo", {
            params: { myId: memberId, targetId: this.userId },
          })
          .then(function (res) {
            console.log(res.data.vroomNo);

            vm.$router.push({
              name: "chat",
              params: { getRoomId: res.data.vroomNo },
            });
          })
          .catch(function (err) {
            this.$swal("오류가 발생했어요ㅠ ㅠ\n잠시후에 다시 시도해주세요!" + err);
          });
      }
    },
    /*
    * Kim ga bin
    * MyPage followingList 확인하기
    */

    // 신고
    userFlagging() {
      const vm = this;
      this.userFlagModal = false;
      if (this.selectedCode == "us5") {
        this.$swal.fire({
          title: '신고 이유를 입력하세요',
          html: `<input type="text" id="flagReason" class="swal2-input" placeholder="신고 이유">`,
          confirmButtonText: '제출하기',
          focusConfirm: false,
          preConfirm: () => {
            const flagReason = this.$swal.getPopup().querySelector('#flagReason').value
            if (!flagReason) {
              this.$swal.showValidationMessage(`신고이유를 입력해 주세요`)
            }
            return { flagReason: flagReason }
          }
        }).then((result) => {
          vm.flagReason = result.value.flagReason;
          console.log(vm.flagReason);
          this.insertFlag();
        })
      } else {
        this.insertFlag();
      }
    },
    insertFlag() {
      const vm = this;
      this.axios({
        url: "/flagging",
        method: "post",
        data: {
          flagFrom: this.memberId,
          flagTo: this.flagedUser,
          flagCode: this.selectedCode,
          flagReason: this.flagReason,
        }
      })
        .then(function (response) {
          console.log(vm.flagReason);
          console.log(response);
          vm.$swal.fire('유저 신고가 완료되었습니다.');
        })
        .catch(function (error) {
          this.$swal("오류가 발생했어요ㅠ ㅠ\n잠시후에 다시 시도해주세요!" + error);
        });
    },
    invite() {

    }
  }
};
</script>
  

<style scoped lang="css" src="@/assets/css/sns/SnsUserFeed.css" />