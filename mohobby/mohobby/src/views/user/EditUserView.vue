<template>
  <div>
    <UserSidebar></UserSidebar>
    <h1>회원정보 수정하기</h1>
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
                    <h1 style="text-align: center" class="mb-10">
                      회원 정보 수정
                    </h1>
                    <h6 style="text-align: center">
                      모든 요소는 필수적으로 입력해 주셔야합니다.
                    </h6>
                    <!--프로필 이미지-->
                    <div class="profile-image">
                      <v-avatar
                        class="ml-10 my-10 mr-4"
                        color="grey darken-1"
                        size="150"
                      >
                        <v-img
                          aspect-ratio="30"
                          :src="require(`@/assets/image/user/` + profileImg)"
                          alt="profile_img"
                        />
                      </v-avatar>
                    </div>
                    <div>
                      <!-- 이미지 등록하기 -->
                      <v-container fluid>
                        <v-file-input
                          class="mx-auto"
                          label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)"
                          type="file"
                          filled
                          prepend-icon="mdi-camera"
                          counter
                          show-size
                          dense
                          accept="image/*"
                          @change="onImageChange"
                        />
                      </v-container>
                      <!-- 이미지 미리보기 -->
                      <!-- <v-container fluid> -->
                      <div style="display: inline-flex; margin-left: 10px">
                        <v-img
                          :src="uploadimageurl"
                          aspect-ratio="4/3"
                          height="150px"
                          width="200px"
                          lazy-src
                          error
                          style="margin-right: 10px"
                        />
                      </div>
                      <v-container fluid>
                        <div v-for="(list, i) in fileList" :key="i">
                          {{ list.name }}
                        </div>
                      </v-container>
                      <v-btn @click="uploadImage">uploadImage</v-btn>
                    </div>
                    <br />
                    <br />
                    <!-- 아이디입력 -->
                    <v-text-field
                      label="아이디"
                      prepend-inner-icon="mdi-account"
                      color="#2ac187"
                      v-model="memberId"
                      @input="checkId()"
                      :messages="idCheckMessage"
                      oninput="javascript: this.value= this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '');"
                    ></v-text-field>
                    <!-- 비밀번호입력 -->
                    <v-text-field
                      color="#2ac187"
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
                      color="#2ac187"
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
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="이름"
                      v-model="memberName"
                    >
                    </v-text-field>
                    <!-- 닉네임입력 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="닉네임"
                      v-model="nickName"
                    >
                    </v-text-field>
                    <!-- 생년월일입력 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-account-outline"
                      label="생일"
                      v-model="calcBirth"
                      readonly
                    >
                    </v-text-field>

                    <!-- 성별입력 -->
                    <v-col>
                      <v-radio-group label="성별" row v-model="gender">
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
                      color="#2ac187"
                      prepend-inner-icon="mdi-email"
                      type="text"
                      label="이메일"
                      v-model="email"
                      @input="checkEmail()"
                      :messages="emailCheckMessage"
                    ></v-text-field>
                    <!-- 휴대전화번호 입력 -->
                    <v-text-field
                      color="#2ac187"
                      prepend-inner-icon="mdi-cellphone"
                      type="text"
                      label="휴대전화"
                      v-model="phoneNum"
                      oninput="javascript: if (this.value.length > 13) this.value = this.value.slice(0, 13);"
                      @input="inputPhoneNumber()"
                      :messages="'-를 제외하고 입력해주세요'"
                    ></v-text-field>
                    <!-- 회원 소개하기 정보-->
                    <br />
                    <v-textarea
                      name="input-7-1"
                      label="소개글"
                      v-model="intro"
                      hint="Hint text"
                    ></v-textarea>
                    <!-- 관심사 정보 -->

                    <v-row justify="space-around">
                      <v-col cols="12" sm="30" md="30">
                        <v-sheet class="py-4 px-1">
                          <v-slide-group class="pa-2">
                            <v-slide-item
                              v-for="catg in catg"
                              :key="catg.keywordId"
                            >
                              <div class="displayflex">
                                <v-chip-group
                                  multiple
                                  active-class="primary--text"
                                  v-model="newInputKeyword"
                                >
                                  <v-chip :value="catg.keywordId">
                                    {{ catg.keywordName }}
                                  </v-chip>
                                </v-chip-group>
                              </div>
                            </v-slide-item>
                          </v-slide-group>
                        </v-sheet>
                      </v-col>
                    </v-row>
                    <!-- 회원정보 수정버튼 -->
                    <v-btn
                      type="submit"
                      color="#2ac187"
                      depressed
                      large
                      block
                      dark
                      class="mb-3"
                      @click="editUser()"
                    >
                      회원정보수정
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

export default {
  name: "",
  props: {
    email: {
      type: String,
      default: "",
    },
  },
  components: { UserSidebar },
  data() {
    return {
      catg: [],
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
      birth: "",
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
      profileImg: "",
      intro: "",
      oldInputKeyword: [],
      newInputKeyword: [],
      clicked: false,
      uploadimageurl: "", //이미지업로드2
      fileList: [],
      file: {},
      formData: {},
      calcBirth: "",
    };
  },
  beforeCreate() {},
  created() {
    this.memberId = this.$route.query.memberId;
    this.getAllRegion();
    this.getAllCatg();
  },
  beforeMount() {},
  mounted() {
    this.setMemberInfo();
    this.getMemberPickKeyword();
    this.getAllMember();
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    // 로그인 회원 정보 가져와서 셋팅
    setMemberInfo() {
      const vm = this;
      this.memberId = this.$store.state.id;
      console.log(this.memberId);
      console.log(this.memberId);
      console.log(this.memberId);
      console.log(this.memberId);
      console.log(this.memberId);
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
            vm.birth = response.data.birth;
            vm.calcBirth = vm.calDate(response.data.birth);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 날짜계산
    calDate(milliSecond) {
        const days = ['일', '월', '화', '수', '목', '금', '토'];
        const data = new Date(milliSecond);  //Date객체 생성

        const year = data.getFullYear();    //0000년 가져오기
        const month = data.getMonth() + 1;  //월은 0부터 시작하니 +1하기
        const date = data.getDate();        //일자 가져오기
        const day = days[data.getDay()];    //요일 가져오기

        return `${year}-${month}-${date}`;
    },
    // 관심사 전체 목록 가져오기
    getAllCatg() {
      const vm = this;
      this.axios({
        url: "/allCatg",
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          if (response.data != "") {
            vm.catg = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
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
          if (response.data != "") {
            for (let i = 0; i < response.data.length; i++) {
              vm.oldInputKeyword[i] = response.data[i].keywordId;
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 유효성 검사 위해 회원 전체 정보 가져오기
    getAllMember() {
      const vm = this;
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
    // 지역 정보 전체 가져오기
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
    editUser() {
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
      this.confirmMemKeyword();
      this.axios({
        url: "/memberupdate",
        method: "put",
        data: {
          memberId: this.memberId,
          regionId: this.regionId,
          nickName: this.nickName,
          memberName: this.memberName,
          password: this.password,
          email: this.email,
          phoneNum: this.phoneNum,
          intro: this.intro,
          gender: this.gender,
          birth: this.birth,
        },
      })
        .then(function (response) {
          console.log("회원정보 수정 성공");
        })
        .catch(function (error) {
          console.log(error);
          console.log("회원정보 수정 실패");
        });
      this.axios({
        url: "/memberupdateprofilename",
        method: "put",
        data: {
          memberId: this.memberId,
        },
      })
        .then(function (response) {
          console.log("이미지 이름 수정 성공");
          vm.$router.push("/");
        })
        .catch(function (error) {
          console.log("이미지 이름 수정 실패");
        });
    },
    // 선택한 키워드와 기존에 멥버가 선택한 키워드 비교 -> insert, delete
    confirmMemKeyword() {
      let insertMemKeywordList = this.newInputKeyword.filter(
        (x) => !this.oldInputKeyword.includes(x)
      );
      let deleteMemKeywordList = this.oldInputKeyword.filter(
        (x) => !this.newInputKeyword.includes(x)
      );

      insertMemKeywordList.filter((x) => this.insertMemKeyword(x));
      deleteMemKeywordList.filter((x) => this.deleteMemKeyword(x));
    },
    insertMemKeyword(keywordId) {
      this.axios({
        url: "/memPickKeyword",
        method: "post",
        data: {
          memberId: this.memberId,
          keywordId: keywordId,
        },
      })
        .then(function (response) {
          console.log("키워드 등록 성공");
        })
        .catch(function (error) {
          console.log("키워드 등록 실패");
        });
    },
    deleteMemKeyword(keywordId) {
      this.axios({
        url: "/memPickKeyword/" + this.memberId + "/" + keywordId,
        method: "delete",
      })
        .then(function (response) {
          console.log("키워드 삭제 성공");
        })
        .catch(function (error) {
          console.log("키워드 삭제 실패");
        });
    },
    // 이미지 미리보기
    onImageChange(file) {
      // v-file-input 변경시
      if (!file) {
        console.log("file" + file);
        return;
      }
      const formData = new FormData(); // 파일을 전송할때는 FormData 형식으로 전송
      //console.log("file.name" + file.name); //name:파일명, size:바이트(인듯),type:image/png
      formData.append("file", file); // formData의 key: 'filelist', value: 이미지
      this.formData = formData;
      // 미리보기
      const reader = new FileReader();
      reader.onload = (e) => {
        this.uploadimageurl = e.target.result;
        // e.target.result를 통해 이미지 url을 가져와서 uploadimageurl에 저장
      };
      reader.readAsDataURL(file);
    },
    //이미지 업로드
    uploadImage() {
      const vm = this;
      this.axios({
        url: "/memberProfileUpdate", // 이미지 저장을 위해 back서버와 통신
        method: "POST",
        headers: { "Content-Type": "multipart/form-data" }, // 이걸 써줘야 formdata 형식 전송가능
        data: vm.formData,
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    onlyAlphabet(ele) {
      ele.value = ele.value.replace(/[^\\!-z]/gi, "");
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
  width: (33.333% - 1rem);
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
