<template>
  <div style="background-color: #E9E8EA">
    <v-img
      :src="require('@/assets/image/web/login-1.jpg')"
    />
    <!-- login component -->
    <v-container style="max-width: 450px" fill-height>
      <v-layout align-center row wrap style="padding: 20px 0px 50px 0px;">
        <v-flex xs12>
          <v-card elevation="0">
            <div class="pa-10">
              <h1 style="text-align: center" class="mb-10">
                회원가입
              </h1>
              <h6 style="text-align: center">
                모든 요소는 필수적으로 입력해 주셔야합니다.
              </h6>
              <!-- 아이디입력 -->
              <v-text-field
                label="아이디"
                prepend-inner-icon="mdi-account"
                color="#2255b1"
                v-model="memberId"
                @input="checkId()"
                :messages="idCheckMessage"
                oninput="javascript: this.value= this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '');"
              ></v-text-field>
              <!-- 비밀번호입력 -->
              <v-text-field
                color="#2255b1"
                prepend-inner-icon="mdi-lock"
                type="password"
                label="비밀번호"
                v-model="password"
                @input="checkPassword()"
                :messages="passwordCheckMessage"
                oninput="javascript: if (this.value.length > 12) this.value = this.value.slice(0, 12);"
              ></v-text-field>
              <!-- 비밀번호확인입력 -->
              <v-text-field
                color="#2255b1"
                prepend-inner-icon="mdi-lock"
                type="password"
                label="비밀번호 확인"
                v-model="password2"
                @input="checkPassword2()"
                :messages="password2CheckMessage"
                oninput="javascript: if (this.value.length > 12) this.value = this.value.slice(0, 12);"
              >
              </v-text-field>
              <!-- 이름입력 -->
              <v-text-field
                color="#2255b1"
                prepend-inner-icon="mdi-account-outline"
                label="이름"
                v-model="memberName"
              >
              </v-text-field>
              <!-- 닉네임입력 -->
              <v-text-field
                color="#2255b1"
                prepend-inner-icon="mdi-account-outline"
                label="닉네임"
                v-model="nickName"
              >
              </v-text-field>
              <!-- 생년월일입력 -->
              <v-col cols="24" sm="20" md="20">
                <v-dialog
                  ref="dialog"
                  color="#2255b1"
                  v-model="modal"
                  :return-value.sync="birth"
                  persistent
                  width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      color="#2255b1"
                      v-model="birth"
                      label="Picker in dialog"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    color="#2255b1"
                    v-model="birth"
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn
                      text
                      color="#2255b1"
                      @click="modal = false"
                    >
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="#2255b1"
                      @click="$refs.dialog.save(birth)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-dialog>
              </v-col>
              <!-- 성별입력 -->
              <v-col>
                <v-radio-group label="성별" row v-model="gender">
                  <v-radio
                    label="여성"
                    value="2"
                    color="#2255b1"
                  ></v-radio>
                  <v-radio
                    label="남성"
                    value="1"
                    color="#2255b1"
                  ></v-radio>
                  <v-radio
                    label="선택안함"
                    value="0"
                    color="#2255b1"
                  ></v-radio>
                </v-radio-group>
              </v-col>

              <!-- 지역 입력 -->
              <v-col>
                <v-select
                  v-if="regionList"
                  :items="regionList"
                  :item-text="'keywordName'"
                  :item-value="'keywordId'"
                  v-model="regionId"
                  label="지역"
                  dense
                >
                </v-select>
              </v-col>

              <!-- 이메일입력 -->
              <v-text-field
                id="email"
                color="#2255b1"
                prepend-inner-icon="mdi-email"
                type="text"
                label="이메일"
                v-model="email"
                @input="checkEmail()"
                :messages="emailCheckMessage"
              ></v-text-field>
              <!-- 휴대전화번호 입력 -->
              <v-text-field
                color="#2255b1"
                prepend-inner-icon="mdi-cellphone"
                type="text"
                label="휴대전화"
                v-model="phoneNum"
                @input="inputPhoneNumber()"
                oninput="javascript: if (this.value.length > 13) this.value = this.value.slice(0, 13);"
                :messages="'-를 제외하고 입력해주세요'"
              ></v-text-field>
              <!-- 본인인증버튼 -->
              <br />
              <v-btn
                type="submit"
                color="#2255b1"
                depressed
                large
                block
                dark
                class="mb-3"
                @click="CertBtn()"
              >
                본인인증
              </v-btn>
              <!-- 회원가입버튼 -->
              <v-btn
                type="submit"
                color="#eacf3f"
                depressed
                large
                block
                class="mb-3"
                @click="register()"
              >
                회원가입
              </v-btn>
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
<style scoped>
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
  background-color: #2255b1;
  border: #2255b1;
}
</style>

<script>
const { IMP } = window;
IMP.init("imp46541776");

export default {
  name: "",
  props: {
    email: {
      type: String,
      default: "",
    },
  },
  components: {},
  data() {
    return {
      memberId: "",
      idCheckMessage: "",
      allMember: null,
      password: "",
      password2: "",
      passwordCheckMessage: "",
      password2CheckMessage: "",
      canRegister: "",
      memberName: "",
      nickName: "",
      birth: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      emailCheckMessage: "",
      email: "",
      phoneNum: "",
      regionList: [],
      regionId: "",
      gender: "",
      checkCert: false,
    };
  },
  beforeCreate() {},
  created() {
    this.getAllRegion();
  },
  beforeMount() {},
  mounted() {
    // 카카오, 네이버 로그인 클릭 시 넘어오는 이메일 셋팅
    const vm = this;
    this.email = this.$route.params.email;
    if (this.email !== undefined) {
      document.getElementById("email").setAttribute("readonly", true);
    }
    // 유효성 검사시 사용할 회원 전체 정보 가져 오기
    this.axios({
      url: "/memberAll",
      method: "get",
    })
      .then(function (response) {
        console.log(response);
        if (response.data != "") {
          //this.$store.commit("setId", this.memberId);
          console.log(response.data);
          vm.allMember = response.data;
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    getAllRegion() {
      const vm = this;
      this.axios({
        url: "/regionAll",
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          if (response.data != "") {
            console.log(response.data);
            vm.regionList = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    checkId() {
      for (let i = 0; i < this.allMember.length; i++) {
        console.log(
          "this.allMember[i].memberId : " + this.allMember[i].memberId
        );
        console.log(this.memberId);
        if (this.memberId === "") {
          this.idCheckMessage = "";
          return;
        } else if (this.memberId !== this.allMember[i].memberId) {
          this.idCheckMessage = "사용 가능한 아이디입니다.";
          this.canRegister = "true";
        } else if (this.memberId === this.allMember[i].memberId) {
          this.idCheckMessage = "사용 불가능한 아이디입니다.";
          this.canRegister = "false";
          return;
        }
      }
    },
    checkPassword() {
      let special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

      if (this.password.search(/\s/) != -1) {
        this.passwordCheckMessage = "공백은 포함할 수 없습니다";
        this.canRegister = "false";
      } else if (special_pattern.test(this.password) == false) {
        this.passwordCheckMessage = "한글자 이상의 특수문자를 포함해 주세요";
        this.canRegister = "false";
      } else if (this.password == this.password.toLowerCase()) {
        this.passwordCheckMessage = "한 글자 이상의 대문자를 포함해 주세요";
        this.canRegister = "false";
      } else if (this.password.length < 8) {
        this.passwordCheckMessage = "8자리 이상을 입력하세요";
        this.canRegister = "false";
      } else {
        this.passwordCheckMessage = "사용 가능한 비밀번호 입니다.";
        this.canRegister = "true";
      }
    },

    checkPassword2() {
      if (this.password !== this.password2) {
        this.password2CheckMessage = "비밀번호가 일치하지 않습니다.";
        this.canRegister = "false";
      } else {
        this.password2CheckMessage = "비밀번호가 일치합니다.";
        this.canRegister = "true";
      }
    },

    checkEmail() {
      let reg_email =
        /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
      for (let i = 0; i < this.allMember.length; i++) {
        if (this.allMember[i].email === this.email) {
          this.emailCheckMessage = "이미 사용중인 이메일입니다.";
          this.canRegister = false;
          return;
        }
      }
      if (!reg_email.test(this.email)) {
        this.emailCheckMessage = "이메일 형식이 아닙니다.";
        this.canRegister = false;
      } else {
        this.emailCheckMessage = "사용 가능한 이메일 입니다.";
        this.canRegister = true;
      }
    },

    inputPhoneNumber() {
      var number = this.phoneNum.replace(/[^0-9]/g, "");
      var phone = "";
      if (number.length < 4) {
        return number;
      } else if (number.length < 7) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3);
      } else if (number.length < 11) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 3);
        phone += "-";
        phone += number.substr(6);
      } else {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 4);
        phone += "-";
        phone += number.substr(7);
      }
      this.phoneNum = phone;
    },
    register() {
      const vm = this;

      if (
        this.memberId === "" ||
        this.password === "" ||
        this.regionId === "" ||
        this.memberName === "" ||
        this.nickName === "" ||
        this.gender === "" ||
        this.birth === "" ||
        this.email === "" ||
        this.phoneNum === ""
      ) {
        alert("필수항목이 입력되지 않았습니다.");
        return;
      }

      if (this.checkCert === false) {
        alert("본인인증을 먼저 진행해 주세요");
        return;
      }
      this.axios({
        url: "/member",
        method: "post",
        data: {
          memberId: this.memberId,
          password: this.password,
          regionId: this.regionId,
          memberName: this.memberName,
          nickName: this.nickName,
          gender: this.gender,
          birth: this.birth,
          email: this.email,
          phoneNum: this.phoneNum,
        },
      })
        .then(function (response) {
          console.log("회원등록 성공");
          vm.$router.push("/login");
        })
        .catch(function (error) {
          console.log("회원등록 실패");
        });
    },
    CertBtn() {
      IMP.certification(
        {
          //merchant_uid: "ORD20180131-0010013" // 주문 번호
        },
        (rsp) => {
          console.log(rsp.imp_uid);
          const imp_uid = rsp.imp_uid;
          if (rsp.success) {
            // 인증 정보 받기
            this.axios.get("/iamport/cert/" + imp_uid).then((result) => {
              this.checkCert = true;
              console.log("인증성공");
            });
          } else {
            this.checkCert = false;
          }
        }
      );
    },
    onlyAlphabet(ele) {
      ele.value = ele.value.replace(/[^\\!-z]/gi, "");
    },
  },
};
</script>
