<template>
  <v-navigation-drawer app>
    <div class="text-center pa-10">
      <v-avatar class="mb-4" color="grey darken-1" size="64">
        <v-img
          aspect-ratio="30"
          :src="require(`@/assets/image/user/${profileImg}`)"
        ></v-img>
      </v-avatar>
      <h4 style="color: #2b2b2b; font-weight: bolder;">{{ memberId }}</h4>
    </div>

    <v-divider></v-divider>

    <v-list>
      <v-list-group
        v-for="link in links"
        :key="link.index"
        :prepend-icon="link.icon"
        no-action
        color="#2255b1"
      >
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title v-text="link.text"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list shaped subheader>
          <v-list-item-group color="#2255b1">
            <template v-for="subheader in link.subheaders">
              <v-subheader :key="subheader.text" v-if="subheader.text != ''">{{
                subheader.text
              }}</v-subheader>
              <v-list-item
                v-for="child in subheader.items"
                :key="child.id"
                @click="
                  $router.push({ path: child.route + child.catg }).catch(() => {
                    $router.go(0);
                  })
                "
                link
              >
                <v-list-item-icon>
                  <v-icon></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title v-text="child.text"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list-item-group>
        </v-list>
      </v-list-group>
    </v-list>
  </v-navigation-drawer>
</template>
<script>
export default {
  data() {
    return {
      memberId: "",
      profileImg: "comfuck.jpg",
      active_list: 0,
      links: [
        {
          icon: "mdi-format-list-bulleted",
          text: "클래스 둘러보기",
          subheaders: [
            {
              text: "",
              items: [
                { id: 0, text: "전체", route: "/class/list/", catg: "all" },
                { id: 1, text: "취미", route: "/class/list/", catg: 1000 },
                {
                  id: 2,
                  text: "스포츠/레저",
                  route: "/class/list/",
                  catg: 1001,
                },
                {
                  id: 3,
                  text: "어학/외국어",
                  route: "/class/list/",
                  catg: 1002,
                },
                { id: 4, text: "문화/예술", route: "/class/list/", catg: 1003 },
                { id: 5, text: "여행/캠핑", route: "/class/list/", catg: 1004 },
                { id: 6, text: "음악", route: "/class/list/", catg: 1005 },
                {
                  id: 7,
                  text: "건강/다이어트",
                  route: "/class/list/",
                  catg: 1006,
                },
                { id: 8, text: "친목/모임", route: "/class/list/", catg: 1007 },
                { id: 9, text: "맛집/요리", route: "/class/list/", catg: 1008 },
                {
                  id: 10,
                  text: "인문/과학",
                  route: "/class/list/",
                  catg: 1009,
                },
                { id: 11, text: "팬클럽", route: "/class/list/", catg: 1010 },
                { id: 12, text: "게임", route: "/class/list/", catg: 1011 },
                {
                  id: 13,
                  text: "만화/애니메이션",
                  route: "/class/list/",
                  catg: 1012,
                },
                {
                  id: 14,
                  text: "반려동물/동물",
                  route: "/class/list/",
                  catg: 1013,
                },
                {
                  id: 15,
                  text: "교육/공부",
                  route: "/class/list/",
                  catg: 1014,
                },
                {
                  id: 16,
                  text: "IT/컴퓨터",
                  route: "/class/list/",
                  catg: 1015,
                },
                {
                  id: 17,
                  text: "경제/재테크",
                  route: "/class/list/",
                  catg: 1016,
                },
                {
                  id: 18,
                  text: "종교/봉사",
                  route: "/class/list/",
                  catg: 1017,
                },
                {
                  id: 19,
                  text: "자연/귀농",
                  route: "/class/list/",
                  catg: 1018,
                },
              ],
            },
          ],
        },
        // {
        //   icon: "mdi-ab-testing",
        //   text: "테스트",
        //   subheaders: [
        //     {
        //       text: "",
        //       items: [
        //         {
        //           id: 32,
        //           text: "오픈뱅킹테스트",
        //           route: "/class/test/OpenBankingTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 33,
        //           text: "에디터테스트",
        //           route: "/class/test/QuillEditorTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 34,
        //           text: "iamport테스트",
        //           route: "/class/test/iamportTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 35,
        //           text: "계좌 실명 조회 테스트",
        //           route: "/class/test/AccountRealNameTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 36,
        //           text: "QR코드 생성 테스트",
        //           route: "/class/test/AttdQRTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 37,
        //           text: "카카오 맵 테스트",
        //           route: "/class/test/KakaoMapTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 38,
        //           text: "비디오 테스트",
        //           route: "/class/test/VideoTest",
        //           catg: "/",
        //         },
        //         {
        //           id: 39,
        //           text: "프린트 테스트",
        //           route: "/class/test/printTest",
        //           catg: "/",
        //         },
        //       ],
        //     },
        //   ],
        // },
      ],
    };
  },
  watch: {},
  created() {
    this.setMemberInfo();

    if(this.$store.state.id) {
      this.links.push({
          icon: "mdi-account",
          text: "내 클래스",
          subheaders: [
            {
              text: "학습 관리",
              items: [
                { id: 20, text: "내 학습", route: "/class/my/instudy", catg: "/" },
                { id: 21, text: "출석관리", route: "/class/attd", catg: "/" },
                { id: 22, text: "강의노트", route: "/class/mynote/home", catg: "/" },
                { id: 23, text: "작성한 글 목록", route: "/class/myboard", catg: "/" },
              ],
            },
            {
              text: "수강신청 관리",
              items: [
                { id: 24, text: "찜바구니", route: "", catg: "" },
                { id: 25, text: "주문내역", route: "", catg: "" },
              ],
            },
          ],
        },
        {
          icon: "mdi-crown",
          text: "만능재주꾼",
          subheaders: [
            {
              text: "클래스 개설",
              items: [
                { id: 26, text: "개설신청", route: "/class/open", catg: "/" },
                { id: 27, text: "심사확인", route: "", catg: "" },
              ],
            },
            {
              text: "클래스 관리",
              items: [
                { id: 28, text: "클래스 수정", route: "", catg: "" },
                { id: 29, text: "질문과 답변", route: "", catg: "" },
                { id: 30, text: "출석체크", route: "", catg: "" },
                { id: 31, text: "수익 관리", route: "", catg: "" },
              ],
            },
          ],
        });
    }
  },
  methods: {
    clickList(idx) {
      this.active_list = idx;
    },
    setMemberInfo() {
      this.memberId = this.$store.state.id;
      if (!this.memberId) {
        this.memberId = "비회원";
        this.profileImg = "comfuck.jpg";
        return;
      } else if (this.memberId == "admin") {
        this.profileImg = "logo-color.png";
      } else if (this.memberId != "" && this.memberId != "admin") {
        this.getMemberInfo();
      }
    },
    // 로그인 회원 정보 가져와서 셋팅
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
  },
};
</script>
<style scoped>
.text-center {
  background-color: #e9e9ec;
}
</style>
