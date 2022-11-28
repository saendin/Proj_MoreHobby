<template>
  <div>
    <UserSidebar></UserSidebar>
    <h1>회원정보</h1>
    <!-- Demo content-->
    <div class="container">
      <v-app id="inspire">
        <v-main class="lighten-4">
          <!-- login component -->
          <v-container style="max-width: 600px" fill-height>
            <v-layout align-center row wrap>
              <v-flex xs12>
                <v-card>
                  <div class="pa-10">
                    <!--프로필 이미지-->
                    <div class="profile-image">
                      <v-avatar
                        class="ml-10 my-10 mr-4"
                        color="grey darken-1"
                        size="150"
                      >
                        <v-img
                          v-if="profileImg"
                          aspect-ratio="30"
                          :src="require(`@/assets/image/user/` + profileImg)"
                          alt="profile_img"
                        />
                      </v-avatar>
                    </div>
                    <!-- 아이디 -->
                    <v-text-field
                      label="아이디"
                      prepend-inner-icon="mdi-account"
                      color="#2ac187"
                      v-model="memberId"
                      readonly
                    ></v-text-field>
                    <!-- 비밀번호 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-lock"
                      type="password"
                      label="비밀번호"
                      v-model="password"
                      readonly
                    ></v-text-field>
                    <!-- 이름 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="이름"
                      v-model="memberName"
                      readonly
                    >
                    </v-text-field>
                    <!-- 닉네임 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="닉네임"
                      v-model="nickName"
                      readonly
                    >
                    </v-text-field>
                    <!-- 생년월일 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="생년월일"
                      v-model="birth"
                      readonly
                    >
                    </v-text-field>
                    <!-- 성별 -->
                    <v-col>
                      <v-radio-group label="성별" v-model="gender" row readonly>
                        <v-radio
                          label="여성"
                          value="2"
                          color="#2ac187"
                        ></v-radio>
                        <v-radio
                          label="남성"
                          value="1"
                          color="#2ac187"
                        ></v-radio>
                        <v-radio
                          label="선택안함"
                          value="0"
                          color="#2ac187"
                        ></v-radio>
                      </v-radio-group>
                    </v-col>

                    <!-- 지역 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="지역"
                      v-model="regionName"
                      readonly
                    >
                    </v-text-field>

                    <!-- 이메일 -->
                    <v-text-field
                      id="email"
                      color="#2ac187"
                      prepend-inner-icon="mdi-email"
                      type="text"
                      label="이메일"
                      v-model="email"
                      readonly
                    ></v-text-field>
                    <!-- 휴대전화번호 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-cellphone"
                      type="text"
                      label="휴대전화"
                      v-model="phoneNum"
                      readonly
                    ></v-text-field>
                    <!-- 회원 소개하기 정보-->
                    <br />
                    <v-textarea
                      name="input-7-1"
                      label="소개글"
                      v-model="intro"
                      hint="Hint text"
                      readonly
                    ></v-textarea>
                    <!-- 관심사 정보 -->
                    <v-textarea
                      name="input-7-1"
                      label="관심사"
                      v-model="keyword"
                      hint="Hint text"
                      readonly
                    ></v-textarea>

                    <!-- 회원정보 수정버튼 -->
                    <v-btn
                      type="submit"
                      color="#2ac187"
                      depressed
                      large
                      block
                      dark
                      class="mb-3"
                      @click="checkMember()"
                    >
                      회원정보수정
                    </v-btn>

                    <!-- 회원탈퇴 버튼 -->
                    <!-- 회원정보 수정버튼 -->
                    <v-btn
                      type="submit"
                      color="#2ac187"
                      depressed
                      large
                      block
                      dark
                      class="mb-3"
                      @click="deleteMember()"
                    >
                      회원탈퇴
                    </v-btn>
                  </div>
                </v-card>
              </v-flex>
            </v-layout>
          </v-container>
        </v-main>
      </v-app>
    </div>
    <!-- End -->
  </div>
</template>

<script>
import UserSidebar from "../../components/user/UserSidebar.vue";
const { IMP } = window;
IMP.init("imp46541776");

export default {
  name: "",
  props: {
    email: {
      type: String,
      default: "1",
    },
  },
  components: { UserSidebar },
  data() {
    return {
      catg: [],
      memberId: this.$store.state.id,
      allMember: null,
      password: "",
      password2: "",
      canRegister: "",
      memberName: "",
      nickName: "",
      birth: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      phoneNum: "",
      regionList: [],
      regionId: "",
      gender: "",
      checkCert: false,
      profileImg: "comfuck.jpg",
      intro: "",
      keyword: "",
      regionName: "",
    };
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {
    this.profileImg = "comfuck.jpg";
    this.setMemberInfo();
    this.getMemberPickKeyword();
    this.getAllRegion();
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    // 로그인 회원 정보 가져와서 셋팅
    setMemberInfo() {
      const vm = this;
      this.axios({
        url: "/member/" + this.memberId,
        method: "get",
      })
        .then(function (response) {
          if (response.data != "") {

            vm.memberId = response.data.memberId;
            vm.password = response.data.password;
            vm.password2 = response.data.password;
            vm.nickName = response.data.nickName;
            vm.email = response.data.email;
            vm.regionId = response.data.regionId;
            vm.phoneNum = response.data.phoneNum;
            vm.intro = response.data.intro;
            vm.gender = response.data.gender;
            vm.memberName = response.data.memberName;
            vm.profileImg = response.data.profileImg;
            vm.birth = vm.calDate(response.data.birth);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    calDate(milliSecond) {
        const days = ['일', '월', '화', '수', '목', '금', '토'];
        const data = new Date(milliSecond);  //Date객체 생성

        const year = data.getFullYear();    //0000년 가져오기
        const month = data.getMonth() + 1;  //월은 0부터 시작하니 +1하기
        const date = data.getDate();        //일자 가져오기
        const day = days[data.getDay()];    //요일 가져오기

        return `${year}-${month}-${date}`;
    },
    // 멤버확인
    checkMember() {
      const vm = this;
      this.$swal
        .fire({
          title: "아이디, 비밀번호 확인",
          html: `<input type="text" id="inputId" class="swal2-input" placeholder="Username">
  <input type="password" id="inputPassword" class="swal2-input" placeholder="Password">`,
          confirmButtonText: "Sign in",
          focusConfirm: false,
          preConfirm: () => {
            const inputId = this.$swal
              .getPopup()
              .querySelector("#inputId").value;
            const inputPassword = this.$swal
              .getPopup()
              .querySelector("#inputPassword").value;
            if (!inputId || !inputPassword) {
              this.$swal.showValidationMessage(
                `아이디와 비밀번호를 입력하세요`
              );
            }
            return { inputId: inputId, inputPassword: inputPassword };
          },
        })
        .then((result) => {
          console.log(result.value.inputId);
          console.log(result.value.inputPassword);

          vm.inputId = result.value.inputId;
          vm.inputPassword = result.value.inputPassword;

          console.log("vm.inputId" + vm.inputId);
          console.log("vm.memberId" + vm.memberId);
          if (vm.inputId !== vm.memberId) {
            this.$swal.fire({
              icon: "error",
              title: "아이디가 일치하지 않습니다.",
            });
          } else {
            this.axios({
              url: "/membercheck",
              method: "post",
              data: {
                memberId: vm.memberId,
                password: vm.inputPassword,
              },
            })
              .then(function (response) {
                console.log(response);
                if (response.data !== "") {
                  vm.$router.push("/edituser");
                } else {
                  vm.$swal.fire({
                    icon: "error",
                    title: "비밀번호가 일치하지 않습니다.",
                  });
                }
              })
              .catch(function (error) {
                console.log(error);
              });
          }
        });
    },

    // 회원이 선택한 관심사 가져오기
    getMemberPickKeyword() {
      const vm = this;
      this.axios({
        url: "/memPickKeyword/" + this.memberId,
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          if (response.data != "") {
            for (let i = 0; i < response.data.length; i++) {
              vm.keyword += response.data[i].keywordName + ",  ";
            }
            console.log("vm.keyword" + vm.keyword);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 지역 정보 전체 가져와서 회원의 지역 셋팅하기
    getAllRegion() {
      const vm = this;
      this.axios({
        url: "/regionAll",
        method: "get",
      })
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            console.log(response.data);
            console.log(vm.regionId);
            if (response.data[i].keywordId == vm.regionId) {
              vm.regionName = response.data[i].keywordName;
              return;
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 회원탈퇴
    deleteMember() {
      const vm = this;
      this.$swal
        .fire({
          title: "정말 탈퇴하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "탈퇴",
          cancelButtonText: "취소",
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.axios({
              url: "/memberdelete",
              method: "put",
              data: {
                memberId: vm.memberId,
              },
            })
              .then(function (response) {
                console.log(response);
                vm.$swal.fire("탈퇴되었습니다.");
              })
              .catch(function (error) {
                console.log(error);
              });
          }
        });
    },
  },
};
</script>

<style scoped>
.image {
  min-height: 100vh;
}

.profile-image {
  float: left;
  width: calc(33.333% - 1rem);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 3rem;
}

.profile-image img {
  border-radius: 50%;
}

.btn-primary {
  background-color: #2ac187;
  border: #2ac187;
}
</style>
