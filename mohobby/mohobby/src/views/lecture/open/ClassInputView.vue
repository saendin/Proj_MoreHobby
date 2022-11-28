<template>
  <v-container fluid style="padding: 30px 40px;">
    <div>
      <h1 class="mb-10">강의 개설 신청</h1>

    </div>
    <form id="classInput" name="classInput" v-on:submit.prevent>
    <div class="fill-height">
        <div id="step1" class="contents-box mb-5">
          <div class="contents">
            <br />
            <!-- 1/7 -->
            <span class="no">1/7</span>
            <h2>클래스에 대한 간략한 정보를 남겨 주세요</h2>
            <br />
            <!-- className -->
            <span class="label">클래스 명</span>
            <input
              type="text"
              class="input"
              placeholder="예) 수학 강의, 고양이 교실, 편집 강의, 텃밭 가꾸기"
              name="className"
              v-model="className"
            />
            <!-- classType -->
            <span class="label">클래스 방식</span>
            <v-radio-group v-model="classType" row name="classType">
              <v-radio
                style="margin-right: 50px"
                label="ONLINE"
                :value="0"
              ></v-radio>
              <v-radio label="OFFLINE" :value="1"></v-radio>
            </v-radio-group>
            <!-- region -->
            <div v-if="classType == 1" class="mb-5">
              <span class="label">강의 장소</span>
              <input type="text" id="address" placeholder="주소를 검색하세요." readonly v-model="address" name="address" />
              <button class="button" @click="searchAddress">주소 찾기</button>
              <input type="text" id="addressDetail" placeholder="상세 주소를 입력하세요" v-model="addressDetail" v-if="address != ''" name="addressDetail" />
              <input type="text" readonly v-show="false" v-model="postcode" name="postcode" />
              <div v-show="mapShow" class="kmap" style="width:500px;height:400px;" ref="map"></div>
            </div>
            <!-- 강의일자 -->
            <div v-if="classType == 1" class="mb-5">
              <span class="label">강의 시작 일자</span>
              <input type="date" :min="$moment().add(60, 'd').format('YYYY-MM-DD')" v-model="startDate" name="startDate" />
            </div>
            <!-- 수업시간 -->
            <div v-if="classType == 1" class="mb-5">
              <span class="label">수업 시간</span>
              <input type="number" min="8" max="16" id="startTime" v-model="startTime" class="mr-1" name="startTime" /> 시 ~
              <input type="number" min="12" max="22" id="endTime" v-model="endTime" class="ml-5 mr-1" name="endTime" />시   
            </div>
            <!-- jobName -->
            <span class="label">직업</span>
            <input type="text" class="input" name="jobName" v-model="jobName" placeholder="강사님의 직업을 알려주세요."/>
            <!-- keywordName(카테고리이름)  -->
            <span class="label">재주 분야</span>
            <select
              class="input mb-5"
              style="width: 400px; margin: 0 10px 0 10px"
              name="keywordId"
              v-model="keywordId"
            >
              <option value="" selected>--- 키워드를 선택하세요 ---</option>
              <option v-for="(item, i) in catg" :key="i" :value="item.keywordId">{{ item.keywordName }}</option>
            </select>
            <!-- 금액-->
            <span class="label">금액(VAT포함)</span>
            <input
              type="text"
              class="input"
              placeholder="숫자만입력"
              style="width: 1150px; margin-right: 10px"
              name="classPrice"
              v-model="classPrice"
            />
            <span style="font-size: 20px; margin-right: 10px">원</span>
          </div>
        </div>
        
        <div id="step2" v-show="step2" class="contents-box mb-5">
          <div class="contents">
            <br />
            <span class="no">2/7</span>
            <h2>전문가 인증을 해주세요</h2>
            <br />
            <span 
              class="label" 
              style="display: inline-block; margin-right: 85px"
            >
              실명
            </span>
            <input
              type="text"
              class="input"
              readonly
              style="width: 400px; margin: 0 10px 0 10px"
              v-model="certName"
              name="account_holder"
            />
            <input type="text" readonly v-show="false" v-model="memberId" name="memberId" />
            <span 
              class="label" 
              style="display: inline-block; margin-right: 25px"
            >
              생년월일
            </span>
            <input
              type="text"
              class="input"
              readonly
              style="width: 400px; margin: 0 10px 0 10px"
              v-model="certBirth"
            />
            <button @click.stop="CertBtn">인증</button>
            <br />
            <br />
            <span class="label" style="display: inline-block"
              >수익금 출금 은행</span
            >
            <select
              class="input"
              style="width: 400px; margin: 0 10px 0 10px"
              name="bankCode"
              v-model="bankCode"
            >
              <option value="" selected>--- 은행을 선택하세요 ---</option>
              <option v-for="(item, i) in bankList" :key="i" :value="item.code">{{ item.name }}</option>
            </select>
            <span class="label" style="display: inline-block"
              >수익금 출금 계좌</span
            >
            <input
              type="text"
              class="input"
              placeholder="숫자만 입력"
              style="width: 400px; margin: 0 10px 0 10px"
              name="account"
              v-model="account"
            />
            <button @click.stop="accountCheck">인증</button>
            <br />
            <br />
          </div>
        </div>
      
        <div id="step3" v-show="step3" class="contents-box mb-5">
          <div class="contents">
            <br />
            <span class="no">3/7</span>
            <h2>클래스를 설명해 주세요.</h2>
            <br />
            <!-- 에디터 -->
            <div class="example" style="width: 1300px" name="classInfo">
              <quill-editor
                class="editor"
                v-model="content"
                :style="editorStyle"
              />
            </div>
          </div>
        </div>
        <div id="step4" v-show="step4" class="contents-box mb-5">
          <div class="contents">
            <br />
            <span class="no">4/7</span>
            <h2>클래스를 잘 나타낼 수 있도록 이미지를 등록하세요.</h2>
            <br />
            <span class="label">메인이미지등록(필수)</span>

            <!-- 파일등록부 -->
            <v-file-input
              class="mx-auto"
              label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)"
              type="file"
              filled
              prepend-icon="mdi-camera"
              counter
              show-size
              dense
              v-model="thumbnailImage"
              id="mainImage"
              accept="image/*"
              name="mainImage"
            />
            <span class="label">추가이미지등록(선택)</span>
            <!-- 파일등록부 -->
            <v-file-input
              class="mx-auto"
              label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)"
              type="file"
              filled
              prepend-icon="mdi-camera"
              counter
              show-size
              dense
              multiple
              id="subImageList"
              accept="image/*"
              v-model="subImageList"
              name="subImageList"
            />
          </div>
        </div>
      
        <div id="step5" v-show="step5" class="contents-box mb-5">
          <div class="contents">
            <br />
            <span class="no">5/7</span>
            <h2>커리큘럼을 등록하세요</h2>
            <br />
            <div v-if="classType==1">
              <span class="label">수업일수를 등록하세요</span>
              <span class="mr-5">주</span>
              <input class="input" type="number" v-model="times" min="1" max="7" id="times" style="width: 70px" name="weekTimes" />
              <span class="ml-1">회</span>
              <span class="mx-5">X</span>
              <input class="input" type="number" v-model="weeks" min="3" id="weeks" style="width: 70px" name="weeks" />
              <span class="ml-1">주</span>
            </div>
            <span class="label">챕터를 등록하세요</span>
            <div v-for="(item, i) in chapList" :key="i">
              <span
                style="padding: 10px 15px 10px 15px; display: inline-block; border: 1px solid black; margin-right: 20px;"
              >
                {{ i+1 }}
              </span>
              <input
                class="input"
                placeholder="챕터 제목을 입력하세요"
                style="width: 1100px"
                type="text"
                v-model="item.chapName"
                @change="checkStep5"
              />
              <button 
                style="margin: 0px 20px 0px; border: 1px solid red; color: red"
                @click.stop="delChapList(i)"
              >
                -
              </button>
              <button
                style="border: 1px solid green; color: green"
                @click.stop="addChapList"
              >
                +
              </button>
            </div>
            <v-row>
              <v-col class="d-flex justify-start align-center">
                <div>회차를 등록하세요</div>
              </v-col>
              <v-col v-if="classType==1" class="d-flex justify-end align-center mr-10">
                <div>수업일자를 등록하세요</div>
              </v-col>
            </v-row>
            <div 
              v-for="(curr, i) in currList"
              :key="i"
            >
              <span
                style="padding: 10px 15px 10px 15px; display: inline-block; border: 1px solid black; margin-right: 20px;"
              >
                {{ i+1 }}
              </span>
              <select
                class="input"
                style="width: 450px; margin: 0 20px 0 10px"
                name="color2"
                v-model="curr.chap"
                @change="checkStep5"
              >
                <option value="" selected>--- 챕터를 선택하세요 ---</option>
                <option 
                  v-for="(chap, i) in chapNames" 
                  :key="i" 
                  :value="chap.chapName" 
                >
                  {{ chap.chapName }}
                </option>
              </select>
              <input
                class="input"
                type="text"
                placeholder="강의 제목을 입력하세요"
                style="width: 620px"
                v-model="curr.partName"
                @change="checkStep5"
              />
              <button
                v-if="classType != 1"
                style="margin: 0px 20px 0px; border: 1px solid red; color: red"
                @click="delCurrList(i)"
              >
                -
              </button>
              <button 
                v-if="classType != 1"
                style="border: 1px solid green; color: green"
                @click="addCurrList(i)"
              >
                +
              </button>
              <input v-if="classType == 1" type="date" :min="curr.minDate" v-model="curr.classDate" @change="currDateChange(i)" />
            </div>
          </div>
        </div>


        <div id="step6" v-show="step6" class="contents-box mb-5">
          <div class="contents">
            <br />
            <span class="no">6/7</span>
            <h2>수업에 필요한 자료를 등록하세요.</h2>
            <br />
            <span class="label d-inline-block">회차</span>
            <span 
              class="label ml-30 d-inline-block"
            >
              영상 등록
            </span>
            <div class="d-flex justify-center" v-for="(curr, i) in currList" :key="i">
              <span
                class="py-5 px-5 d-inline-block" style="border: 1px solid black;"
              >
                {{ i+1 }}
              </span>
              <v-file-input
                type="file"
                class="input currVideo"
                style="width: 530px;"
                accept="video/*"
                v-model="curr.video"
                name="videoList"
                @change="checkStep6(i)"
              />
            </div>
          </div>
        </div>




        <div id="step7" v-show="step7" class="contents-box mb-5">
        <!-- <div id="step7" class="contents-box mb-5"> -->
          <div class="contents">
            <br />
            <span class="no">7/7</span>
            <h2>수업을 듣기 전 필요한 준비물을 등록하세요</h2>
            <span class="label"
              >필요한 물품만 등록해주면 판매 연계는 모하비가 도와드립니다!</span
            >
            <br />
            <br />
            <span class="label">준비물을 등록하세요.</span>
            <input
              type="text"
              class="input"
              placeholder="준비물을 입력하세요"
              style="width: 1150px; margin-right: 10px"
              name="needs"
            />
          </div>
        </div>
        <v-btn outlined @click="submitBtn" class="my-10">
          신청
        </v-btn>
      </div>
    </form>
  </v-container>
</template>
<script>
const { IMP } = window;
IMP.init("imp46541776");


export default {
  name: "ClassInputView",
  components: {
  },
  data() {
    return {
      editorStyle: {
        'height': '300px',
        'padding-bottom': '70px',
      },
      content: "",
      thumbnailImage: '',
      step2: false,
      step3: false,
      step4: false,
      step5: false,
      step6: false,
      step7: false,
      startTime: 8,
      endTime: 17,
      startDate: '',
      times: 1,
      weeks: 3,
      mapShow: false,
      posX: 0,
      posY: 0,
      address: '',
      addressDetail: '',
      postcode: '',
      currList: [
        {
          partName: '',
          chap: '',
          video: '',
          file: '',
          minDate: this.startDate,
          classDate: '',
        },
      ],
      chapList: [
        {
          chapName: '',
        },
      ],
      bankCode : '',
      account : '',
      accountCert: false,
      bankList: [
        {name: '농협은행', code: '011'},
        {name: '산업은행', code: '002'},
        {name: '기업은행', code: '003'},
        {name: '국민은행', code: '004'},
        {name: 'KEB하나은행', code: '081'},
        {name: '우리은행', code: '020'},
        {name: 'SC제일은행', code: '023'},
        {name: '시티은행', code: '027'},
        {name: '대구은행', code: '032'},
        {name: '광주은행', code: '034'},
        {name: '제주은행', code: '035'},
        {name: '전북은행', code: '037'},
        {name: '경남은행', code: '039'},
        {name: '새마을금고', code: '045'},
        {name: '신한은행', code: '088'},
        {name: '카카오뱅크', code: '090'},
      ],
      certName: '',
      certBirth: '',
      sampleData: "",
      phoneNum: "",
      catg: [],
      keywordCount: 0,
      regionList: [],
      keywordId: "",
      regionId: "",
      memberId: this.$store.state.id,
      className: "",
      classPrice: 0,
      classInfo: "",
      imgAmount: 0,
      classType: 0,
      certAble: 0,
      certStandard: "",
      startDate: 0,
      accountHolder: "",
      //이미지Data
      mainUploadimageurl: [], //미리보기 이미지url
      subUploadimageurl: [], //미리보기 이미지url
      imagecnt: 0, //업로드한 이미지개수 axious시에 넘겨줌
      subImageList: [],
      mainImage: '',
      file: {},
      formData: {},
      imgList: [],
      files: [],
    };
  },
  created() {
    // let test = 'test';
    // this.$emit('')
  },
  mounted() {
    this.getAllCatg();
    this.getAllRegion();
  },
  computed: {
    chapNames() {
      return this.chapList.filter(({chapName}) => chapName !== '');
    }
  },
  unmounted() {},
  methods: {
    // 지역 전체 가져오기
    getAllRegion() {
      const vm = this;

      this.axios({
        url: "/regionAll",
        method: "get",
      })
        .then(function (response) {
          if (response.data != "") {
            vm.regionList = response.data;
          }

        })
        .catch(function (error) {
          console.log(error);

        });
    },
    // 관심사 전체 목록 가져오기
    getAllCatg() {
      const vm = this;
      this.axios({
        url: "/allCatg",
        method: "get",
      })
        .then(function (response) {
          if (response.data != "") {
            vm.catg = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    CertBtn: function() {
      IMP.certification({
      }, rsp => {
        const imp_uid = rsp.imp_uid;
        if (rsp.success) {
          // 인증 정보 받기
          this.axios.get('/iamport/cert/'+imp_uid)
          .then(result => {
            let name = result.data.name;
            let birth = result.data.birthday;
            let memberName = this.$store.state.user.memberName;
            let memberBirth = this.$moment(this.$store.state.user.birth).format('YYYY-MM-DD');

            if(name == memberName && birth == memberBirth) {
              this.certName = name;
              this.certBirth = birth;
            } else {
              this.$swal('로그인 정보와 일치하지 않습니다!', '', 'error');
            }
          });
        }
        else {
          console.log("인증 실패");
        }
      })
    },
    accountCheck: function() {
      if(this.certName != '' && this.certBirth != '') {
        this.$store.state.loading = true;
        this.axios.get('/bankRealName', {
            params: {
                Bncd: this.bankCode,
                Acno: this.account
            }
        }).then( result => {
            if(result.data.dpnm == this.$store.state.user.memberName) {
              this.$swal('로그인 정보와 일치합니다!', '', 'success');
              this.accountCert = true;
              this.step3=true;
            } else {
                this.$swal('로그인 정보와 일치하지 않습니다!', '', 'error');
                this.bankCode = '';
                this.account = '';
                this.accountCert = false;
            }
            this.$store.state.loading = false;
        })
      } else {
        this.$swal('실명인증을 먼저 진행해주세요!', '', 'info');
        this.$store.state.loading = false;
      }

    },
    addChapList() {
      this.chapList.push({
        chapName: '',
      });
    },
    delChapList(i) {
      if(this.chapList.length == 1) {
        this.chapList[0].chapName = '';
      } else {
        this.chapList.splice(i, 1);
      }
    },
    addCurrList(i) {
      this.currList.push({
        partName: '',
        chap: '',
        video: '',
        file: '',
        minDate: this.$moment(this.currList[i].minDate).add(1, "d").format('YYYY-MM-DD'),
        classDate: '',
      })
    },
    delCurrList(i) {
      if(this.currList.length == 1) {
        this.currList[0].partName = '';
        this.currList[0].chap = '';
        this.currList[0].video = '';
        this.currList[0].file = '';
        this.currList[0].minDate = this.startDate;
        this.currList[0].classDate = '';
      } else {
        this.currList.splice(i, 1);
      }
    },
    searchAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.address = data.roadAddress; // 도로명 주소 변수
          this.postcode = data.zonecode;
          // 지도 띄우기
          this.floatingMap();
        }
      }).open();
      this.mapShow = true;
    },
    floatingMap() {
      let kakao = window.kakao;

      var mapContainer = this.$refs.map, // 지도를 표시할 div 
          mapOption = {
              center: new kakao.maps.LatLng(128.5932793, 35.8690334), // 지도의 중심좌표
              level: 2 // 지도의 확대 레벨
          };  

      // 지도를 생성합니다    
      var map = new kakao.maps.Map(mapContainer, mapOption); 

      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(this.address, function(result, status) {

          // 정상적으로 검색이 완료됐으면 
          if (status === kakao.maps.services.Status.OK) {
              var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

              // 결과값으로 받은 위치를 마커로 표시합니다
              var marker = new kakao.maps.Marker({
                  map: map,
                  position: coords
              });

              // 인포윈도우로 장소에 대한 설명을 표시합니다
              var infowindow = new kakao.maps.InfoWindow({
                  content: '<div style="width:150px;text-align:center;padding:6px 0;">강의 장소</div>'
              });
              infowindow.open(map, marker);

              // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
              map.setCenter(coords);
          } 
      });

    },
    offlineCurrList() {
      let total = (this.times * this.weeks) - this.currList.length;

      for(let i=0; i<total; i++) {
        this.currList.push({
          partName: '',
          chap: '',
          video: '',
          file: '',
          minDate: this.startDate,
          classDate: '',
        })
      }

      this.currDateChange(total-1);
    },
    checkStep1() {
      if(this.classType == 0 && this.className != '' && this.jobName != '' && this.keywordId != '' && this.classPrice != 0) {
        this.step2=true;
        return;
      }
      if(this.classType == 1 && this.className != '' && this.jobName != '' && this.keywordId != '' && this.classPrice != 0 && this.address != '' && this.startDate != null) {
        this.step2=true;
        return;
      }
      this.step2=false;
    },
    checkStep5() {
      let sum = 0;

      for(let chap of this.chapList) {
        if(chap.chapName == '') {
          sum += 1;
        }
      }

      if(sum != 0) {
        this.step6 = false;
        return;
      }

      for(let curr of this.currList) {
        if(curr.chap == '' || curr.partName == '') {
          sum += 1;
        }
      }

      if(sum != 0) {
        this.step6 = false;
        return;
      }

      this.step6 = true;
    },



    checkStep6(i) {
      this.uploadVideo(i);


      
      let sum = 0;

      for(let curr of this.currList) {
        if(this.classType == 0 && (  curr.video == '' || curr.video == null)) {
          sum += 1;
        }
        if(this.classType == 1 && (  curr.video == '' || curr.video == null || curr.classDate == '' || curr.classDate == null)) {
          sum += 1;
        }
      }

      if(sum != 0) {
        this.step7 = false;
      } else {
        this.step7 = true;
      }
    },
    uploadVideo(i) {
      let vm = this;
      let file = this.currList[i].video;

      let video = document.createElement('video');
      video.preload = 'metadata';

      video.onloadedmetadata = function() {

        window.URL.revokeObjectURL(video.src);
        file.duration = video.duration;
        
        if (video.duration < 1) {
          return;
        }

        vm.getDuration(i, video.duration);

      }
      
      video.src = URL.createObjectURL(file);
    },


    getDuration(i, duration) {
      this.currList[i].duration = Math.floor(duration);
    },

    
    submitBtn() {
      if(!this.step2 || !this.step3 || !this.step4 || !this.step5 || !this.step6 || !this.step7) {
        this.$swal('작성 양식을 완료해주세요!', '', 'info');
        return;
      }

      //classes
      const formData = new FormData(classInput);
      let subImageInput = document.getElementById('subImageList');
      let subImages = subImageInput.files;
      formData.append("imgAmount", subImages.length+1);
      if(this.classType == 1) {
        let endDate = this.$moment(this.currList[this.currList.length-1].classDate).format('YYYY/MM/DD');
        formData.append("endDate", endDate);
      }

      //html
      formData.append("content", this.content);

      //chapter
      formData.append("chapListJson", JSON.stringify(this.chapList));

      //curriculum
      let currList = [];
      this.currList.forEach((curr, i) => {
        currList.push({
          partNo: (i+1),
          partName: curr.partName,
          chapName: curr.chap,
          videoLength: curr.duration,
        })
        if(this.classType == 1) {
          currList[i].classDate = curr.classDate;
        }
      });
      formData.append("currListJson", JSON.stringify(currList));
      
      this.$store.state.loading = true;
      this.axios.post('class/open', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      }).then(res => {
        if(res.status == 200) {
          //to-do 신청 성공 페이지
        }
        this.$store.state.loading = false;
      }).catch(err => {
        console.log(err);
        this.$store.state.loading = false;
      })

    },
    currDateChange(idx) {

      for(let i = (idx+1); i<this.currList.length; i++) {
        this.currList[i].minDate = this.$moment(this.currList[idx].classDate).add(1,"d").format('YYYY-MM-DD');
      }
    },
  },
  watch: {
    times() {
      if(this.classType == 1) {
        this.offlineCurrList();
      }
    },
    weeks() {
      if(this.classType == 1) {
        this.offlineCurrList();
      }
    },
    classType() {
      if(this.classType == 1) {
        this.offlineCurrList();
      }
      this.checkStep1();
    },
    className() {
      this.checkStep1();
    },
    jobName() {
      this.checkStep1();
    },
    keywordId() {
      this.checkStep1();
    },
    classPrice() {
      this.checkStep1();
    },
    bankCode() {
      this.step3 = false;
    },
    account() {
      this.step3 = false;
    },
    content() {
      if(this.content != '') {
        this.step4 = true;
      } else {
        this.step4 = false;
      }
    },
    thumbnailImage() {
      if(this.thumbnailImage != '' || this.thumbnailImage != null) {
        this.step5 = true;
      } else {
        this.step5 = false;
      }
    },
    chapList() {
      this.checkStep5();
    },
    currList() {
      this.checkStep5();
    },
    step2() { if(this.step2 == false) this.step3 = false; },
    step3() { if(this.step3 == false) this.step4 = false; },
    step4() { if(this.step4 == false) this.step5 = false; },
    step5() { if(this.step5 == false) this.step6 = false; },
    step6() { if(this.step6 == false) this.step7 = false; },
    startDate() {
      for(let curr of this.currList) {
        curr.minDate = this.startDate;
      }
    },

  },
};
</script>

<style scoped>
.ql-editor {
  height: 300px;
}

.container {
  margin-left: 40px;
}

.contents-box {
  width: 1350px;
  border: 0.5px solid grey;
}

.contents {
  margin-left: 20px;
}

.no {
  color: #2ac187;
  font-size: 20px;
}

.label {
  font-size: 17px;
  display: block;
}

.input {
  padding: 10px;
  width: 1200px;
  border: 1px solid lightgrey;
  margin-top: 10px;
  margin-bottom: 25px;
}

button {
  border: 1px solid black;
  border-radius: 5px;
  padding: 10px 17px 10px 17px;
}

form {
  display: inline-block;
}
.authBox {
  padding: 20px;
  width: 400px;
  text-align: left;
  border: 1px solid gray;
}
.tokenBox {
  padding: 20px;
  width: 400px;
  text-align: left;
  border: 1px solid gray;
}

.v-input {
  margin: 0px;
}

</style>
