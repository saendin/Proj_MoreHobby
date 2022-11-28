<template>
  <form name="imgForm" id="imgForm" v-on:submit.prevent>
    <div class="container">
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="6" md="4">
                  <!-- 이미지 미리보기 -->
                  <!-- <v-container fluid> -->
                    <div style="display: inline-flex; margin-left: 20px; margin-top: 10px">
                    <v-img
                      :src="uploadimageurl"
                      aspect-ratio="4/3"
                      height="180px" width="270px"
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
  
                <!-- 이미지 등록하기 -->
                <v-container fluid>
                  <v-file-input
                    class="mx-auto"
                    label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)"
                    type="file"
                    name="files"
                    filled
                    prepend-icon="mdi-camera"
                    counter
                    show-size
                    dense
                    accept="image/*"
                    @change="onImageChange"
                  />
                </v-container>
            </v-col>
            <v-col cols="12" sm="6" md="8">
              <span>소개글</span><br><br>
              <v-textarea name="moimInfo" no-resize filled height="245"
                placeholder="함께하고 싶은 소모임 활동을 자세히 소개해주세요." v-model="intro"></v-textarea>
            </v-col>
          </v-row>
          <v-row class="mt-12">
            <v-col col="12" sm="3" md="6">
              <div class="title">
                <v-icon color="green">mdi-map-marker</v-icon> 주로 어느지역에서 활동 하나요?
              </div><br><br>
              <somoimregion @Region="Region"></somoimregion>
               <input name="moimRegion" type="hidden" v-model="moimRegion" value="">
               <input name="moimName" type="hidden" v-model="moimName" value="">
               <input name="moimId" type="hidden" v-model="moimId" value="">
            </v-col>
            <v-divider vertical></v-divider>
            <v-col col="12" sm="3" md="6">
              <div class="title">
                <v-icon color="green">mdi-wifi</v-icon> 주로 어떤 주제로 활동 하나요?
              </div><br><br>
              <somoimtopic @Catg="Catg"></somoimtopic>
              <input name="moimCatg" type="hidden" v-model="moimCatg" value="">
            </v-col>
          </v-row>
          <v-row class="mt-16">
            <v-col col="12" sm="12" md="12">
              <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn class="mr-3">취소</v-btn>
              <v-btn color="success"  @click="uploadImage()">저장</v-btn>
            </v-card-actions>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
    </div>
  </form>
  </template>
  <script>
  import somoimtopic from "@/components/moim/setting/somoimtopic.vue"
  import somoimregion from "@/components/moim/setting/somoimregion.vue"
  export default {
    name: "",
    data() {
      return {
        dialog: false,
        intro: '',
        sampleData: '',
        uploadimageurl: '', //이미지업로드2
        fileList: [],
        file: {},
        formData: {},
        moimCatg: '',
        moimRegion: '',
        moimInfo : '',
        moimName : this.$route.params.moimName,
        moimId : this.$route.params.moimId
      };
    },
    components: { somoimtopic, somoimregion },
    methods: {
      Region(moimRegion) {
        this.moimRegion = moimRegion
        console.log(moimRegion)
        console.log('자식에게 받은 값 ' + this.moimRegion)
      },
      Catg(moimCatg) {
        this.moimCatg = moimCatg
        console.log(this.moimCatg)
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
      //정보 수정
      uploadImage() {
        const formData = new FormData(imgForm)
        // formData.append("moimName", this.moimName)
        const vm = this;
        this.axios({
          data : formData, // 게시글 저장
          url: "/moimProfileUpdate", // 이미지 저장을 위해 back서버와 통신
          method: "post"
          //headers: { "Content-Type": "multipart/form-data" }, // 이걸 써줘야 formdata 형식 전송가능
        })
          .then((res) => {
            console.log(res)
            this.$swal("수정이 완료되었습니다.")
          })
          .catch((err) => {
            console.log(err);
          });
      },
    }
  }
  
  </script>
  <style scoped>
  .container {
    width: 90%;
  }
  
  .title {
    font-weight: bold;
    font-size: 1.25rem !important
  }
  
  .text {
    text-align: center;
    margin-top: 30px;
    font-size: 20px;
    font-weight: bold;
  }
  
  .thumbnail-wrappper {
    width: 75px;
  }
  
  
  v-img {
    max-width: 100%;
    height: auto;
  }
  </style>