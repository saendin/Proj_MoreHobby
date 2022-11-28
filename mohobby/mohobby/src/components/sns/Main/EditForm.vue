<template>
  <div class="mx-auto" style="width:1050px; height:500px;">
    <v-card outlined>

        <!-- 이미지 -->
        <v-row>
          <v-col cols="12" id="image_box" style="position:relative">
            <v-icon v-show="imgsLength == 2" class="image_icon">mdi-numeric-2-box-multiple</v-icon>
            <v-icon v-show="imgsLength == 3" class="image_icon">mdi-numeric-3-box-multiple</v-icon>
            <v-icon v-show="imgsLength == 4" class="image_icon">mdi-numeric-4-box-multiple</v-icon>
            <v-icon v-show="imgsLength == 5" class="image_icon">mdi-numeric-5-box-multiple</v-icon>
            <v-carousel v-model="model" ref="myCarousel" :touchless="true">
              <v-carousel-item
                v-for="(img, i) in imgs"
                :key="i"
                :aspect-ratio="4 / 3"
                :width="width"
                :src="require(`@/assets/image/sns/${img.postId}/${img.fileName}`)"/>
            </v-carousel>
          </v-col>
        </v-row>
      
        <form id="feedInsert" name="feedInsert" v-on:submit.prevent>
          <v-container fluid>
            <input type="hidden" v-model="memberId" name="memberId" />
            <input type="hidden" v-model="hashtag" name="hashtag" />



            <div class="main-container">

              <div class="room-deal-information-container">


                  <div class="room-file-upload-example-container">
                    

                    

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



        <!-- 해시태그 -->
        <v-container fluid>
          <v-combobox
            v-model="model"
            :hide-no-data="!search"
            :items="items"
            :search-input.sync="search"
            hide-selected
            label="해시태그를 등록해보세요!"
            multiple
            small-chips
            solo
          >

            <!-- :filter="filter" -->
            <template v-slot:no-data>
              <v-list-item>
                <span class="subheading">Create</span>
                <v-chip :color="`${ colors[nonce - 1] } lighten-3`" label small>
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
        <!-- 해시태그 끝 -->

        <!-- 수정 해시태그 -->
        <v-container fluid>
          <v-combobox
            v-model="model"
            :hide-no-data="!search"
            :items="items"
            :search-input.sync="search"
            hide-selected
            label="해시태그를 등록해보세요!"
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
      <!-- 해시태그 끝 -->


      </form>
    </v-card>
    <v-btn @click="uploadImage" rounded color="#2255b1" class="white--text" style="float:right;">등록</v-btn>
  </div>
</template>

<script>
export default {
props:{
  postId : Number,
}, 
data() {
  return {
    //수정모드

    //carousel
    model: 0,
    move: [],
    drag: false,
    touch: false,

    imgs: [], //이미지 저장






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
    {
      text: '',
      color: '',
    },
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


  memberId: this.$store.state.id, //세션아이디
  };
},

created() {
  this.getHobbies();


     this.showDetail(this.postId, this.memberId);
     this.detailImg(this.postId); //게시글 이미지 로드
      
     
     //게시글 해시태그 로드
      //model에 푸시해주기

    //  model: [ //추가된 해시태그
    // {
    //   text: '취미',
    //   color: 'blue',
    // },

 
},

watch: {

  //해시태그 색상변경
  model (val, prev) {
    if (val.length === prev.length) return

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


  

      //게시글 상세 로드
      showDetail(postId, writer) {
      this.axios("/sns/user/feed_detail/" + postId, {
        params: {
          memberId: writer,
        },
      })
        .then((res) => {

          this.content = res.data.content; //내용 바인딩

          let userHashtags = res.data.hashtag
          if (userHashtags.length > 0) { //해시태그 있으면 가공
            // let soloHashtag = userHashtags; //%%,%%,%% 형태
            // let userHashtag = soloHashtag.split(","); //해시태그 자르기

            for(let i=0; i<userHashtags.length; i++){
                 v = { text: userHashtags[i],
                  color: this.colors[this.nonce - 1] } 
            }
                this.model.push(v);

                this.nonce++;
              }
        })
        .catch((err) => {
          alert("게시글호출 실패" + err);
        });
    },


    //게시글 이미지 로드
    detailImg(postId) {
      this.axios("/sns/user/feed_detail_img/" + postId)
        .then((res) => {
          this.imgs = res.data;
          this.imgsLength = this.imgs.length;
          console.log("이미지 로딩 성공!");
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //게시글 수정
    editPost() {
      this.$router.push({ path: 'snsFeedRegister', params: { postId: this.postId } });
      // if (this.editedContent == "" || this.editedContent == undefined){
      //   this.$swal('내용 입력부터 부탁드립니다🙏')
      //   return;
      // }
      // this.axios.put('/sns/myfeed/' + this.postId, {
      //       content : this.editedContent,
      //   }).then(res => {
      //     console.log("게시글수정 성공! "+res);
      //   }).catch(err => {
      //     console.log(err)
      //   });
  },


  //해시태그 카테고리 키워드
  //취미조회
    getUserHobby(userId) {
      this.axios('/sns/user/hobbies/' + userId)
      .then(res => {
          this.hobbies = res.data;

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

        }).catch(err => {
          console.log(err);
    })
  },


  // getHobbies() {
      
  //     let hobbies = ["스포츠/레저",
  //                   "어학/외국어",
  //                   "문화/예술",
  //                   "여행/캠핑",
  //                   "음악",
  //                   "건강/다이어트",
  //                   "친목/모임",
  //                   "맛집/요리",
  //                   "인문/과학",
  //                   "팬클럽",
  //                   "게임",
  //                   "만화/애니메이션",
  //                   "반려동물/동물",
  //                   "교육/공부",
  //                   "IT/컴퓨터",
  //                   "경제/재테크",
  //                   "종교/봉사",
  //                   "자연/귀농"
  //   ]

  //     for(let i = 0; i<hobbies.length; i++){
  //       let hashtag;
  //       let isColor
  //       let h = this.colors.length-1; //컬러의 인덱스 = h
  //       if(i > h) {
  //         isColor = this.colors[i%h]; //해시태그 인덱스에서 컬러 인덱스 나눠서 나머지값 넣기
  //       } else {
  //         isColor = this.colors[i]; //i인덱스가 h보다 작으면 인덱스값으로
  //       }
  //       hashtag = hobbies[i];
  //       this.items.push({
  //         text: hashtag,
  //         color: isColor
  //       })
  //     }
  // },


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
            self.$router.push({ path: '/snsUserFeed', query: {userId : self.memberId} });
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  }
};
</script>
<style scoped lang="css" src="@/assets/css/sns/RegisterForm.css" />

