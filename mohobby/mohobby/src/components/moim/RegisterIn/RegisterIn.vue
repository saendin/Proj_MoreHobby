<template>
  <v-card class="mx-auto" max-width="1000" outlined>
    <div class="box1">
      <!-- 이미지 미리보기 -->
      <!-- <v-container fluid> -->
      <div style="display: inline-flex; margin-left: 20px">
        <v-img :src="uploadimageurl" aspect-ratio="4/3" height="180px" width="270px" lazy-src error
          style="margin-right: 10px" />
      </div>
      <!-- <v-container fluid>
        <div v-for="(list, i) in fileList" :key="i">
          {{ list.name }}
        </div>
      </v-container> -->

      <!-- 이미지 등록하기 -->
      <v-row>
        <v-col cols="12" sm="6" md="4">
        <form id="imgForm" v-on:submit.prevent>
          <v-container fluid>
            <v-file-input name="files" class="mx-auto" max-width="50" label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)" type="file" filled
              prepend-icon="mdi-camera" counter show-size dense accept="image/*" @change="onImageChange" />
          </v-container>
        </form>
        </v-col>

          <v-col cols="12" sm="6" md="6">
            <v-text-field label="모임명" required v-model="moimName"></v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="2">
            <v-btn @click="idCheck">중복검사</v-btn>
          </v-col>
      </v-row>
    </div>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-subtitle class="text-h5 mb-1">
          <v-container fluid>
            <v-textarea name="input-7-1" filled auto-grow placeholder="함께하고 싶은 소모임 활동을 자세히 소개해주세요. (50자 이상)"
              v-model="moimInfo"></v-textarea>
          </v-container>
        </v-list-item-subtitle>
        <hr>
        <v-list-item-content>

          <!--주제선택-->
          <v-col cols="12" sm="5">
            <v-dialog v-model="check" max-width="600px">
              <template v-slot:activator="{ on, attrs }">
                <div class="buttonSelect">
                  <span class="font-weight-bold">소모임 주제</span>
                </div>
                <v-btn color="green" dark rounded v-bind="attrs" v-on="on">
                  {{ selected || '주제 선택' }}
                </v-btn>
              </template>
              <v-sheet elevation="10" rounded="xl">

                <!-- 취미 hobb -->
                <div class="pa-12">
                  <v-chip-group active-class="light-green--text" column v-model="selected">
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">취미</span>
                      </div>
                      <div class="pa-1">
                        <v-chip v-for="item in hobb" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>

                    <!-- 스포츠/레저 sports -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">스포츠/레저</span>
                      </div>
                      <div class="pa-1">
                        <v-chip v-for="item in sports" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 어학/외국어 lang -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">어학/외국어</span>
                      </div>
                      <div class="pa-1">
                        <v-chip v-for="item in lang" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 문화/예술 arts -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">문화/예술</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in arts" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 여행/캠핑 trip -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">여행/캠핑</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in trip" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 음악 music -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">음악</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in music" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 건강/다이어트 diet -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">건강/다이어트</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in diet" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 친목/모임 amity -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">친목/모임</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in amity" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 맛집/요리 matzip <=맛집ㅋㅋㅋㅋㅋㅋㅋㅋㅋ -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">맛집/요리</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in matzip" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 인문/과학 humenities -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">인문/과학</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in humenities" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!--  팬클럽 fan -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">팬클럽</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in fan" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!--  게임 game -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">게임</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in game" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!--  만화/애니메이션 ani -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">만화/애니메이션</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in ani" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 반려동물/동물 animal -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">반려동물/동물</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in animal" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 교육/공부 edu -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">교육/공부</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in edu" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- IT/컴퓨터 it -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">IT/컴퓨터</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in it" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 경제/재테크 financial -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">경제/재테크</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in financial" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 종교/봉사 charity -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">종교/봉사</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in charity" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                    <!-- 자연/귀농 nature -->
                    <div>
                      <div class="mt-3">
                        <span class="font-weight-bold">자연/귀농</span>
                      </div>
                      <div class="pa-4">
                        <v-chip v-for="item in nature" :key="item" :value="item" class="mr-3 mb-3">
                          {{ item }}
                        </v-chip>
                      </div>
                    </div>
                  </v-chip-group>
                  <v-card-actions class="justify-center mr-5">
                    <v-btn depressed elevation="2" @click="[(check = false), cancel()]">
                      취소
                    </v-btn>
                    <v-btn color="success" depressed elevation="2" @click="[check = false, save()]">
                      저장
                    </v-btn>
                  </v-card-actions>
                </div>
              </v-sheet>
            </v-dialog>
          </v-col>
        </v-list-item-content>
        <hr>

        <!-- 지역 입력 -->
        <v-col cols="5" sm="5" class="pt-6 pb-3">
          <span class="font-weight-bold">지역 설정</span>
          <v-card-actions></v-card-actions>
          <v-select v-if="regionList" :items="regionList" :item-text="'keywordName'" :item-value="'keywordName'"
            v-model="moimRegion" label="지역" dense>
          </v-select>
        </v-col>

        <hr>
        <!-- 최대인원 -->
        <v-list-item-content>
          <div class="flexcon">
            <v-col cols="5" sm="5">
              <div>
                <span class="font-weight-bold">최대 인원 설정</span>
              </div>
              <v-select :items="[30, 50, 100]" label="최대 인원을 선택해주세요" required v-model="maxPeople"></v-select>
            </v-col>
          </div>
        </v-list-item-content>
        <small class="text-lg-left">* 양식을 다 채워서 가입해주세요!</small>

        <v-card-actions class="lastbutton">
          <v-spacer></v-spacer>
          <v-btn outlined rounded text @click="$router.go(-1);">
            취소하기
          </v-btn>
          <v-btn outlined rounded color="success" @click="[insertMoim(),uploadImage()]">
            생성하기
          </v-btn>
        </v-card-actions>
      </v-list-item-content>
    </v-list-item>
  </v-card>

</template>
<script>
export default {
  data() {
    return {
      items: [{
        src: "https://cdn.class101.net/images/84d01f2e-ec28-4e30-8ed1-fc6e55954e42/960xauto.webp"
      }],
      hobb: [
        '독서',
        '글쓰기',
        'DIY/공예',
        '가죽/목공',
        '드론/액션캠/RC',
        '바둑/장기',
        '취미',
      ],
      sports: [
        '러닝/걷기',
        '자전거',
        '테니스',
        '등산/산악',
        '야구',
        '축구/풋살',
        '당구',
        '서핑',
        '자동차',
      ],
      lang: [
        '영어',
        '일본어',
        '중국어',
        '외국어',
        '스페인어/포르투갈어'
      ],
      arts: [
        '그림/일러스트',
        '디자인',
        '캘리그라피/서예',
        '사진',
        '댄스/무용',
        '공연/뮤지컬',
        '영화',
        '미술/전시',
        '문화/예술'
      ],
      trip: [
        '국내여행',
        '해외여행',
        '캠핑/백패킹'
      ],
      music: [
        '노래',
        '악기',
        '음악'
      ],
      diet: [
        '피트니스',
        '요가/필라테스'
      ],
      amity: [
        '친목/모임',
        '육아/맘 모임'
      ],
      matzip: [
        '맛집',
        '요리/레시피'
      ],
      humenities: [
        '역사',
        '심리학',
        '인문/철학'
      ],
      fan: [
        '프로야구',
        '팬클럽'
      ],
      game: [
        '게임'
      ],
      ani: [
        '만화/애니메이션'
      ],
      animal: [
        '반려동물/동물'
      ],
      edu: [
        '공부'
      ],
      it: [
        'IT'
      ],
      financial: [
        '경제/재테크'
      ],
      charity: [
        '봉사/자선'
      ],
      nature: [
        '꽃/식물'
      ],
      check: false,
      region: false,
      moimName: '',
      moimImg: '',
      moimInfo: '',
      moimCatg: '',
      moimRegion: "",
      maxPeople: '',
      regionList: [],
      selected: '',
      temp: '',
      sampleData: "",
      uploadimageurl: "", //이미지업로드2
      fileList: [],
      file: {},
      formData: {},
    }
  },
  created() {
    const vm = this;
    this.axios({

      url: "http://localhost:8088/java/regionAll",
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
  methods: {
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
      const formData = new FormData(imgForm)
      formData.append("moimName", this.moimName)
      const vm = this;
      this.axios({
        url: "/moimProfileUpload", // 이미지 저장을 위해 back서버와 통신
        method: "POST",
        headers: { "Content-Type": "multipart/form-data" }, // 이걸 써줘야 formdata 형식 전송가능
        data: formData,
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 소모임 등록
    insertMoim() {
      let vm = this;
      if (
        this.moimName === "" ||
        // this.moimImg === "" ||
        this.moimInfo === "" ||
        this.moimCatg === "" && this.moimCatg === "주제 선택" ||
        this.moimRegion === "" ||
        this.maxPeople === ""
      ) {
        alert("필수항목이 입력되지 않았습니다.");
        return;
      }
      this.axios({
        url: "http://localhost:8088/java/moimInsert",
        method: "post",
        data: {
          memberId: this.$store.state.id,
          moimName: this.moimName,
          moimImg: "모임1.jpg",
          moimInfo: this.moimInfo,
          moimCatg: this.moimCatg,
          moimRegion: this.moimRegion,
          maxPeople: this.maxPeople
        },
      })
        .then(resp => {
          console.log("11111111111111111111111" + resp.data);
          this.$swal("소모임생성완료")
          vm.updaterole()
          this.$router.push({ name: 'moimMain' })

        })
        .catch(error => {
          console.log(error)
        })
    },
    showChip(e) {
      this.moimCatg = e
    },
    save() {
      this.moimCatg = this.selected
      this.temp = this.selected
    },
    cancel() {
      this.selected = this.temp
    },
    idCheck() {
      var pattem_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
      this.axios.post("/idCheck", {
        moimName: this.moimName
      })
        .then((resp) => {
          console.log(resp)
          if (this.moimName === '' || pattem_spc.test(this.moimName)) {
            this.$swal('사용할수 없는 모임명입니다.')
            this.moimName = ""
          }
          else if (resp.data === 'YES') {
            this.$swal('사용가능한 모임명 입니다.')

          }
          else {
            this.$swal("이미 사용중인 모임명 입니다.")
            this.moimName = ""
          }
        })
        .catch((err) => {
          console.log(err)
          alert("데이터이동실패")
        })
    },
    updaterole() {
      this.axios.put("/updaterole", {
        memberId: this.$store.state.id,
      })
        .then((resp) => {
          console.log(resp)
        })
        .catch((err) => {
          console.log(err)
        })
    }

  },
  watch: {
    selected: "showChip"
  }
}
</script>
  
<style scoped>
.searchB {
  margin-left: 10px;
}

.buttonSelect {
  margin-bottom: 30px;
}

.flexcon {
  display: flex;
}

.box1 {
  margin-left: 3%;
  margin-top: 1%;
}
</style>