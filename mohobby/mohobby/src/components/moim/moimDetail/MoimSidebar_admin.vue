<template>
  <v-navigation-drawer app>
    <div class="text-center pa-10">
      <v-avatar class="mb-4" color="grey darken-1" size="64">
        <v-img
          aspect-ratio="30"
          :src="require(`@/assets/image/user/${profileImg}`)"
        ></v-img>
      </v-avatar>
      <h4 class="white--text">{{ memberId }}</h4>
      <div class="right">
      </div>
    </div>

    <v-divider></v-divider>

    <v-list>
      <v-list-group
        v-for="link in links"
        :key="link.text"
        :prepend-icon="link.icon"
        no-action
      >
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title v-text="link.text"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list
          v-for="subheader in link.subheaders"
          :key="subheader.text"
          shaped
          subheader
        >
          <v-subheader v-if="subheader.text != ''">{{
            subheader.text
          }}</v-subheader>
          <v-list-item-group color="primary">
            <v-list-item
              v-for="child in subheader.items"
              :key="child.text"
              @click="$router.push({ path: child.route })"
              link
            >
              <v-list-item-content style="padding-left: 55px">
                <v-list-item-title v-text="child.text"></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-dialog v-model="dialog" max-width="500">
              <template v-slot:activator="{ on, attrs }">
                <v-list-item
                  link
                  style="padding-left: 71px"
                  v-bind="attrs"
                  v-on="on"
                >
                  삭제하기
                </v-list-item>
              </template>
              <v-card>
                <v-card-title
                  class="text-h5 red darken-2 font-weight-bold white--text"
                >
                  소모임 삭제하기
                </v-card-title>

                <v-card-text class="pa-5">
                  소모임을 삭제하시면 해당 소모임관 관련된 모든 게시물, 사진,
                  일정, 투표, N빵 등의 모든 내용이 사라집니다. 그래도 정말
                  삭제하시겠습니까?
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn @click="dialog = false"> 취소 </v-btn>
                  <v-btn color="error" @click="[dialog = false, removeMoim()]"> 삭제 </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-list-item-group>
        </v-list>
      </v-list-group>
      <v-list-item
        v-for="link in items"
        :key="link.text"
        @click="$router.push({ name: link.route })"
        link
      >
        <v-list-item-icon>
          <v-icon>{{ link.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ link.text }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>
<script>
export default {
  data() {
    return {
      memberId: '',
      moimId: this.$route.params.moimId,
      profileImg: "comfuck.jpg",
      dialog: false,
      links: [
        {
          icon: "mdi-plus-box",
          text: "관리하기",
          subheaders: [
            {
              text: "",
              items: [
                { text: "소모임 기본설정", route: "moimSetting" },
                { text: "소모임 가입설정", route: "registerSetting" },
                { text: "소모임 가입신청자", route: "moimJoin" },
              ],
            },
          ],
        },
      ],
      items: [
        { icon: "mdi-note-text", text: "게시글", route: "moimBoard" },
        // { icon: "mdi-image", text: "사진첩", route: "moimPhoto" },
        { icon: "mdi-clipboard-check", text: "공지사항", route: "moimNotice" },
        { icon: "mdi-calendar-text", text: "일정", route: "moimSchedule" },
        { icon: "mdi-chart-gantt", text: "투표", route: "moimVote" },
        { icon: "mdi-chart-pie", text: "N빵", route: "moimNbbang" },
      ],
    };
  },
  created() {
    this.setMemberInfo();
  },
  methods: {
    setMemberInfo() {
      this.memberId = this.$store.state.id;
      if (!this.memberId) {
        this.memberId = "비회원";
        this.profileImg = "comfuck.jpg";
        return;
      } else if (this.memberId == "admin") {
        this.profileImg = "female.png";
      } else if (this.memberId != "" && this.memberId != "admin") {
        this.getMemberInfo();
      }
    }, // 로그인 회원 정보 가져와서 셋팅
    getMemberInfo() {
      const vm = this;
      this.axios({
        url: "/member/" + this.memberId,
        method: "get",
      })
        .then(function (response) {
          if (response.data != "") {
            console.log(response.data);
            vm.memberId = response.data.memberId;
            vm.profileImg = response.data.profileImg;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //소모임 삭제
    removeMoim(){
      console.log("=========================")
      this.axios.delete("/removemoim",{
        params: {
          moimId : this.moimId
        }
      }).then((resp)=>{
        console.log(resp)
        this.$swal('소모임 삭제가 완료되었습니다.')
      }).catch((err)=>{
        console.log(err)
      })
    }
  }
}   
</script>
<style scoped>
.text-center {
  background-color: #2ac187;
}

.right {
  float: right;
  margin-right: -30px;
}
</style>
