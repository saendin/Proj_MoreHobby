<template>
  <div class="mx-auto" style="width:1050px; height:500px;">
    <v-card outlined>

      <!-- 추가버튼 -->
      <div 
        class="plus_btn"
        rounded
        @click="addFileList(i)"
      >
        <v-icon color="#2255b1" size="30">mdi-plus</v-icon>
        <v-icon color="#2255b1" size="30">mdi-camera</v-icon>
      </div>

      <div class="room-file-upload-example">

        <div class="room-file-notice-item room-file-notice-item-red">
            타인에게 불쾌감을 줄 수 있는 게시글은 비공개 처리가 될 수 있으니 유의 부탁드립니다🙏
        </div>
      </div>

      <!-- 
        수정버튼을 눌렀을 때 등록된 이미지 불러올 곳
      <div class="room-file-upload-example">
        <div class="room-file-notice-item room-file-notice-item-red">
          타인에게 불쾌감을 줄 수 있는 게시글은 비공개 처리가 될 수 있으니 유의 부탁드립니다🙏
        </div>
      </div> -->


      <form id="feedInsert" name="feedInsert" v-on:submit.prevent>
        <v-container fluid>
          <input type="hidden" v-model="memberId" name="memberId" />
          <input type="hidden" v-model="hashtag" name="hashtag" />



          <div class="main-container">

            <div class="room-deal-information-container">


                <div class="room-file-upload-example-container">
                  
                  <div v-for="(file,i) in fileList" :key="i">
                  
                  <v-file-input
                      v-model="file.file"
                      @change="onImageChange(i)"
                      style="margin-top:20px;"
                      label="이곳을 눌러 사진을 첨부해주세요🙏"
                      type="file"
                      class="mx-auto"
                      id="file_input"
                      name="fileList"
                      prepend-icon="mdi-plus"
                      dense
                      accept="image/png, image/jpeg, image/jpg"
                    />

                    <!-- 이미지 미리보기 -->
                  <div class="img_wrap">
                    <div class="img_box">
                      <v-img                    
                        :src="file.url"
                        aspect-ratio="4/3"
                        height="200"
                        width="200"
                        lazy-src
                        error
                      />
                    



                    <!-- 삭제버튼 -->
                    <div class="del_btn"
                        @click="delFileList(i)"
                         rounded
                      >
                        
                      <v-icon class="del_icon">mdi-delete</v-icon>
                    </div>
                  </div>
                </div>

              </div> 

            </div>
            <div v-if='!fileList[0].file' class="room-file-notice-item">
              사진은 한 장 이상 등록 부탁드립니다🙏
            </div>
          </div>
          
        </div>


          <!-- 내용 -->
          <v-textarea
            name="content"
            auto-grow
            placeholder="내용을 입력해주세요!"
            value=""
            counter="2000"
            v-model="content"
            @change="checkContent(content)"
          ></v-textarea>
        </v-container>



        <!-- 유저소환 -->

        <!-- 해시태그 -->
        <v-container fluid>
          <v-combobox
            v-model="model"
            :hide-no-data="!search"
            :items="items"
            :search-input.sync="search"
            hide-selected
            label="해시태그를 등록해보세요! 특수문자 및 공백은 입력이 불가능합니다🙏"
            multiple
            small-chips
            solo
          >

            <!-- :filter="filter" -->
            <template v-slot:no-data>
              <v-list-item>
                <span class="subheading">Create</span>
                <v-chip :color="`${colors[nonce - 1]} lighten-3`" label small>
                  {{ search }}
                </v-chip>
              </v-list-item>
            </template>
            <template v-slot:selection="{ attrs, item, parent, selected }">
              <v-chip
                v-if="item === Object(item)"
                v-bind="attrs"
                :color="`${item.color} lighten-3`"
                :input-value="selected"
                label
                small
              >
                <span class="pr-2">
                  {{ item.text }}
                </span>
                <v-icon small @click="parent.selectItem(item)"> $delete </v-icon>
              </v-chip>
            </template>
            <template v-slot:item="{ index, item }">
              <v-text-field
                v-if="editing === item"
                v-model="editing.text"
                autofocus
                flat
                background-color="transparent"
                hide-details
                solo
                @keyup.enter="edit(index, item)"
              ></v-text-field>
              <v-chip v-else :color="`${item.color} lighten-3`" dark label small>
                {{ item.text }}
              </v-chip>
              <v-spacer></v-spacer>
              <v-list-item-action @click.stop>
                <v-btn icon @click.stop.prevent="edit(index, item)">
                  <v-icon>{{
                    editing !== item ? "mdi-pencil" : "mdi-check"
                  }}</v-icon>
                </v-btn>
              </v-list-item-action>
            </template>
          </v-combobox>
        </v-container>
      </form>
    </v-card>
    <v-btn @click="uploadImage" rounded color="#2255b1" class="white--text" style="float:right;">등록</v-btn>
  </div>
</template>

<script>
export default {
data() {
  return {
  //해시태그
  activator: null,
  attach: null,
  colors: ['green', 'purple', 'indigo', 'cyan', 'teal', 'orange','darkgrey'],
  editing: null,
  editingIndex: -1,

  // Top 6해시태그 추천 키워드
  items: [ 
    { header: '최근 인기있는 해시태그를 추가해보세요!' },
  ],

  nonce: 1,
  menu: false,
  model: [ //추가된 해시태그
    // {
    //   text: '취미',
    //   color: 'blue',
    // },
  ],

  search: null,
  x: 0,
  y: 0,

  //이미지Data
  uploadimageurl: [], //미리보기 이미지url
  imagecnt: 0,//업로드한 이미지개수 axious시에 넘겨줌
  fileList: [ {
    file: "",
    url:"",
  }],
  file : {},
  postId : "1",
  formData : {},


  fListName:[], //파일이름 중복체크
  addFName: "", //추가되는 파일 이름


  //sns글등록Data
  memberId : this.$store.state.id,
  content: "",
  getHashtag:[],//내가 추가한 해시태그
  hashtag:"",//해시태그 스트링화
  };
},

created() {
  this.getHobbies();
},

watch: {

  //해시태그 색상변경
  model (val, prev) {
    if (val.length === prev.length) return;
    // if(checkSpace(val) == true) return;
    // if(checkSpecial(val) == true) return;

    this.model = val.map(v => {
      if (typeof v === 'string') {
        v = {
          text: v,
          color: this.colors[this.nonce - 1],
        }

        this.items.push(v)

        this.nonce++
      }
      return v
    })
  }
},

methods: {

/*

          
     동적 FILE_INPUT_BOX
        

*/
  //해시태그 카테고리 키워드
  getHobbies() {
      
      let hobbies = ["스포츠/레저",
                    "어학/외국어",
                    "문화/예술",
                    "여행/캠핑",
                    "음악",
                    "건강/다이어트",
                    "친목/모임",
                    "맛집/요리",
                    "인문/과학",
                    "팬클럽",
                    "게임",
                    "만화/애니메이션",
                    "반려동물/동물",
                    "교육/공부",
                    "IT/컴퓨터",
                    "경제/재테크",
                    "종교/봉사",
                    "자연/귀농"
    ]

      for(let i = 0; i<hobbies.length; i++){
        let hashtag;
        let isColor
        let h = this.colors.length-1; //컬러의 인덱스 = h
        if(i > h) {
          isColor = this.colors[i%h]; //해시태그 인덱스에서 컬러 인덱스 나눠서 나머지값 넣기
        } else {
          isColor = this.colors[i]; //i인덱스가 h보다 작으면 인덱스값으로
        }
        hashtag = hobbies[i];
        this.items.push({
          text: hashtag,
          color: isColor
        })
      }
  },


  // 유효성검사 & 미리보기
  onImageChange(i) {
    let file = this.fileList[i].file;
    if (!file)  return;

    console.log("file------>");
    console.log(file);


    for(let j=0; j<this.fileList.length; j++){
      let f = this.fileList[j].file.name;
      this.fListName.push(f);
    }
    console.log(this.fListName);


    //파일 이름 중복 검사
    // if(this.fileList.length > 1 && this.fListName.indexOf(file.name) !== -1){
    //   this.$swal("중복되는 파일은 등록이 불가합니다🙏");
    //   this.file = "";
    //   this.fListName = [];
    //   return;
    // }

    
    //파일 형식 검사
    if(!file.type.match("image/.*")){
      this.$swal("png, jpg, jpeg파일만 부탁드립니다🙏");
    };


    //파일리더 열어서 url값 담기
    const fileReader = new FileReader();
    console.log("file.name: " + file.name);
    
    fileReader.onload = (e) => {

      this.fileList[i].url = e.target.result
    };
    fileReader.readAsDataURL(file);

  },


  //파일추가
  addFileList(i) {
    let file = this.fileList;
    let l = file.length;
    console.log(file);

    if(l > 0 && file[l-1].file == ""){
      this.$swal("사진이 첨부되지 않은 추가칸이 있는지 확인 부탁드립니다🙏");
        return; //file없으면 return
    }
    if(this.fileList.length > 4){
      this.$swal("사진은 5장까지만 추가 부탁드립니다🙏")
      return; //추가한것부터 1개로 취급해서 계산됨. 즉, 파일 5개 이상 추가 못하도록
    }

    //파일 초기화
    this.fileList.push({
      file: '',
      url: '',
    })
  },


  //파일 삭제
  delFileList(i) {
    if(this.fileList.length == 1) { //fileList가 1개면 초기화 
      this.fileList[0].file = ''; 
      this.fileList[0].url = '';
    } else {
      this.fileList.splice(i, 1); //아니면 인덱스번호 잘라내기(삭제)
    }
  },


//해시태그수정
edit (index, item) {
  if (!this.editing) {
    this.editing = item
    this.editingIndex = index
  } else {
    this.editing = null
    this.editingIndex = -1
  }
},

// 공백이 있나 없나 체크
checkSpace(str) {
  if(str.search(/\s/) !== -1) {
    return true; //공백O
  } else {
    return false; //공백X
  }
},

// 특수 문자가 있나 없나 체크
checkSpecial(str) {
  let bannedPattern = /[!?@#$%^&*():;+-=~{}<>\_\[\]\|\\\"\'\,\.\/\`\₩]/gi;

  if(bannedPattern.test(str)) {
    return true; //금지문자O
  } else {
    return false; //금지문자X
  }
},


//게시글 등록


    //사진 업로드
    uploadImage() {
      let file = this.fileList;
      let l = file.length;

      if(this.content.length > 2000) {
        this.$swal("내용은 2000자 이내로 부탁드립니다🙏");
        return;
      }
      if(l = 1 && file[0].file == ""){
        this.$swal("사진은 최소 한 장 이상 등록 부탁드립니다🙏");
          return;
      }
      // if(l > 1 && file[l-1].file == "" || file[l-1].url == ""){
      //   this.$swal("빈 사진추가 칸은 삭제 혹은 사진첨부 부탁드립니다🙏");
      //     return;
      // }

      let self = this;
      console.log(self.fileList);

      //추가된 해시태그 배열에 담기
      this.model.forEach((hashtag) => {
        this.getHashtag.push(hashtag.text);
      });

      //해시태그 배열 스트링화
      const hashtags = this.getHashtag.join();
      feedInsert.hashtag.value = hashtags;

      const formData = new FormData(feedInsert);  // form태그 담아 전송

      this.axios.post('/sns/myfeed', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          },
        })
        .then(function (res) {
            self.$router.push({ path: '/sns-user-feed', query: {userId : self.memberId} });
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  }
};
</script>
<style scoped lang="css" src="@/assets/css/sns/RegisterForm.css" />

