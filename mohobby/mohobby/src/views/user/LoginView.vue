<template>
  <div style="background-color: #E9E8EA">
      <v-img
        :src="require('@/assets/image/web/login-1.jpg')"
      />
    <!-- login component -->
    <v-container style="max-width: 450px;" fill-height>
      <v-layout align-center row wrap style="padding: 20px 0px 60px 0px;">
        <v-flex xs12>
          <v-card elevation="0">
            <div class="pa-10">
              <h1 style="text-align: center" class="mb-10">
                Login
              </h1>
              <form v-on:submit.prevent="checkMember">
                <v-text-field
                  label="아이디"
                  prepend-inner-icon="mdi-account"
                  color="#2255b1"
                  v-model="memberId"
                ></v-text-field>
                <v-text-field
                  color="#2255b1"
                  prepend-inner-icon="mdi-lock"
                  type="password"
                  label="비밀번호"
                  v-model="password"
                >
                </v-text-field>
                <v-btn
                  type="submit"
                  color="#F9E000"
                  depressed
                  large
                  block
                  class="mb-3"
                >
                  Login
                </v-btn>
                <v-btn
                  color="#2255b1"
                  depressed
                  large
                  block
                  dark
                  @click="$router.push('/register')"
                >
                  Sign Up
                </v-btn>
                <div class="d-flex justify-center">
                  <v-btn
                    style="font-weight: bold"
                    @click="findMemberId()"
                    text
                  >
                    아이디찾기</v-btn
                  >
                  <v-btn
                    style="font-weight: bold"
                    @click="findMemberPassword()"
                    text
                  >
                    비밀번호 찾기</v-btn
                  >
                </div>
                <br />
                <!-- <p style="text-align: center;"> -->
                <div class="d-flex justify-center">
                    <a id="custom-login-btn" @click="kakaoLogin()">
                      <img
                        src="https://www.gb.go.kr/Main/Images/ko/member/certi_kakao_login.png"
                        style="
                          height: 40px;
                          width: 170px;
                          margin-right: 30px;
                        "
                      />
                    </a>
                    <!-- </p> -->
                    <!-- <p style="text-align: center;"> -->
                    <div id="naverIdLogin"></div>
                </div>
              </form>
            </div>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <v-img
      :src="require('@/assets/image/web/footer.jpg')"
    />
  </div>
</template>

<style>
.login,
.image {
  min-height: 100vh;
}

.bg-image {
  background-image: url("@/assets/image/user/logo.jpeg");
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center center;
}

.btn-primary {
  background-color: #2ac187;
  border: #2ac187;
}
</style>
<script>

import router from "@/router";
export default {
  data: () => ({
    step: 1,
    naverLogin: null,
    memberId: "",
    password: "",
    loginInfo: null,
    email: "",
    token: "",
  }),
  props: {
    source: String,
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {
    // login페이지 띄울때 무조건 로그아웃
    this.$store.commit("setIsLoginFalse");
    this.$store.commit("logout");
    this.$store.commit("setUserData", null);

    this.naverLogin = new window.naver.LoginWithNaverId({
      clientId: "qtK4gDKw7gcdHhTwYZpV",
      callbackUrl: "http://localhost:8080/login",
      isPopup: false, // 팝업을 통한 연동 처리여부
      loginButton: {
        color: "green",
        type: 3,
        height: 40,
        width: 150,
      }, // 로그인 버튼의 타입을 지정
    });

    // 설정 정보를 초기화하고 연동을 준비
    this.naverLogin.init();

    this.naverLogin.getLoginStatus((status) => {
      const vm = this;
      if (status) {
        console.log(status);
        console.log(this.naverLogin.user);

        // 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크
        var email = this.naverLogin.user.getEmail();
        if (email == "undifined" || email == null) {
          alert("이메일은 필수 정보입니다. 정보 제공을 동의해주세요.");
          // 사용자 정보 재동의를 위해 다시 네이버 동의 페이지로 이동함
          this.naverLogin.reprompt();
          return;
        }
        vm.email = email;
        vm.checkMemberByEmail();
      } else {
        console.log("callback 처리에 실패하였습니다.");
      }
    });
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    kakaoLogin() {
      const vm = this;
      window.Kakao.Auth.loginForm({
        scope: "profile_nickname, account_email, gender, birthday", //동의항목 페이지에 있는 개인정보 보호 테이블의 활성화된 ID값을 넣습니다.
        success: function (response) {
          console.log(response); // 로그인 성공하면 받아오는 데이터
          window.Kakao.API.request({
            // 사용자 정보 가져오기
            url: "/v2/user/me",
            success: (res) => {
              const kakao_account = res.kakao_account;
              vm.token = kakao_account.access_token;
              vm.email = kakao_account.email;

              vm.checkMemberByEmail();
            },
          });
          // window.location.href='/' //리다이렉트 되는 코드
        },
        fail: function (error) {
          console.log(error);
        },
      });
    },
    kakaoLogout() {
      if (!Kakao.Auth.getAccessToken()) {
        console.log("Not logged in.");
        return;
      }
      Kakao.Auth.logout(function (response) {
        alert(response + " logout");
        // window.location.href='/'
      });
    },

    // 멤버 check해서 일반로그인
    checkMember() {
      console.log(this.memberId, this.password);

      const vm = this;
      this.axios({
        url: "/membercheck",
        method: "post",
        data: {
          memberId: this.memberId,
          password: this.password,
        },
      })
        .then(function (response) {
          if (response.data != "" && response.data.memberId != "admin") {
            console.log("if문 안에" + response.data);
            if (response.data.delDate != null) {
              vm.$swal.fire("탈퇴한 회원입니다.");
              return;
            }
            // 유저 id, 유저 정보 넣기
            vm.$store.state.id = vm.memberId;
            vm.$store.commit("setUserData", response.data);
            // 유효성 검사
            // 메인으로 이동(로그인성공)
            vm.$router.push("/");
          } else if (response.data != "" && response.data.memberId == "admin") {
            console.log("else if문 안에" + response.data);
            vm.$store.state.id = vm.memberId;
            vm.$store.commit("setUserData", response.data);
            // 메인으로 이동(로그인성공)
            vm.$router.push("/adminuser");
          } else {
            vm.$swal.fire({
              icon: "error",
              title: "아이디, 비밀번호가 일치하지 않습니다.",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 네이버, 카카오 로그인 시 이메일 있는지 검사
    checkMemberByEmail() {
      console.log(this.email);
      const vm = this;
      this.axios({
        url: "/memberEmail/" + this.email,
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          if (response.data !== "" && response.data.constructor === Object) {
            console.log(response.data);
            if (response.data.delDate != null) {
              vm.$swal.fire("탈퇴한 회원입니다.");
              return;
            }
            if (response.data.role == 4) {
              vm.$swal.fire("관리자에 의해 접근 금지된 유저입니다.");
              return;
            }
            // vuex를 이용해 로그인 정보 저장
            vm.$store.state.id = vm.memberId;
            vm.$store.commit("setIsLoginTrue");
            vm.$store.commit("setUserData", response.data);
            // 로그인 성공시 메인으로 이동
            this.$router.push("/");
          } else {
            vm.$router.push({
              name: "register",
              params: { email: vm.email },
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 아이디 찾기
    findMemberId() {
      const vm = this;
      this.$swal
        .fire({
          title: "가입 시 입력한 이메일을 입력하세요",
          html: `<input type="text" id="email" class="swal2-input" placeholder="email">`,
          confirmButtonText: "제출",
          cancelButtonText: "취소",
          focusConfirm: false,
          preConfirm: () => {
            const email = this.$swal.getPopup().querySelector("#email").value;
            if (!email) {
              this.$swal.showValidationMessage(`이메일을 입력해 주세요`);
            }
            return { email: email };
          },
        })
        .then((result) => {
          vm.email = result.value.email;
          this.axios({
            url: "/memberEmail/" + vm.email,
            method: "get",
          })
            .then(function (response) {
              vm.$swal.fire(
                "가입하신 아이디는" + response.data.memberId + "입니다."
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        });
    },
    // 비밀번호 찾기
    findMemberPassword() {
      const vm = this;
      this.$swal
        .fire({
          title: "아이디를 입력하세요",
          html: `<input type="text" id="memberId" class="swal2-input" placeholder="memberId">`,
          confirmButtonText: "제출",
          cancelButtonText: "취소",
          focusConfirm: false,
          preConfirm: () => {
            const memberId = this.$swal
              .getPopup()
              .querySelector("#memberId").value;
            if (!memberId) {
              this.$swal.showValidationMessage(`아이디를 입력해 주세요`);
            }
            return { memberId: memberId };
          },
        })
        .then((result) => {
          vm.memberId = result.value.memberId;
          this.axios({
            url: "/member/" + vm.memberId,
            method: "get",
          })
            .then(function (response) {
              vm.$swal.fire(
                "찾으시는 비밀번호는" + response.data.password + "입니다."
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        });
    },
  },
};
</script>
