<template>
  <div v-if = "items.secPost == 0 || items.memberId == memberId" id="container">
    <SnsSidebar />
    <h1>피드디테일</h1>
    <br>
    <!-- 뒤로가기 -->
    <v-btn  @click="goback()"
                      color="grey" icon class="mx-auto black--text font-weight-bold">
            <v-icon xx-large>mdi-keyboard-backspace</v-icon>
    </v-btn>
    <div id="wrap">
    <v-container fluid style="margin-top: 0px">
      <v-card class="mx-auto" min-width="600">
        <!-- 프로필 -->
        <div>
          <div class="flex">
            <v-avatar class="ml-10 my-10 mr-4" color="grey darken-1" size="64">
              <v-img
                aspect-ratio="30"
                :src="require(`@/assets/image/user/${items.profileImg}`)"
                @click="goMyProfile(items.memberId)"
              />
            </v-avatar>
            <div class="user text-overline">
              {{ items.memberId }}<br />{{
                this.$moment(items.writeDate).format("YYYY.MM.DD")
              }}
            </div>
          </div>
        </div>

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





        <!-- 좋아요, 댓글, 메세지 -->
        <v-row>
          <v-col cols="4">
            <div class="d-flex justify-start ma-2">

              <v-btn v-if="items.likeStatus === 1" 
                      icon 
                      text 
                      @click="like(memberId, items.postId)">
                <v-icon color="red lighten-2">mdi-heart</v-icon>
              </v-btn>

              <v-btn v-else-if="items.likeStatus === 0" 
                      icon 
                      text 
                      @click="like(memberId, items.postId)">
                <v-icon>mdi-heart-outline</v-icon>
              </v-btn>

              <span class="mr-2 mt-1">{{ items.likes }}</span>
              <v-icon class="mr-2">mdi-chat-outline</v-icon>
              <span class="mr-2 mt-1">{{ items.cmts }}</span>
              <v-icon v-if="this.$store.state.id != items.memberId" @click="send" color="#2255b1">mdi-send</v-icon>
            </div>
          </v-col>
          <v-col cols="8">



            <!-- 카카오톡 공유 - 따로 권한 X-->
            <div class="d-flex justify-end ma-2">
              <v-btn @click="sendLink()" icon>
                <v-icon color="#2255b1">mdi-share-variant</v-icon>
              </v-btn>
              <!-- 카카오톡 공유 끝 -->

              
              <!-- 북마크아이콘 -->
              <v-btn v-if="mark === 1" @click="bookmarkDel(items.postId, memberId)" icon>
                <v-icon size="25" color="#2255b1">mdi-bookmark</v-icon>
              </v-btn>
              <v-btn v-else @click="markLogin(memberId,1)" icon>
                <v-icon color="#2255b1">mdi-bookmark-outline</v-icon>
              </v-btn>
              <!-- 북마크아이콘 끝 -->


              <!-- 
                
                dot 메뉴 - 게시글 작성자만 확인 가능. 수정, 삭제, 비밀글로 전환
              
              -->
              <v-menu v-if = "memberId && memberId == items.memberId ">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn dark icon v-bind="attrs" v-on="on">
                    <v-icon color="grey">mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item v-for="(list, i) in lists" :key="i">
                    <v-list-item-title style="cursor: pointer" @click="listBtn(i)">
                      {{ list.title }}
                    </v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
            <!-- 메뉴 끝 -->
          </v-col>
        </v-row>
        <!-- 좋아요, 댓글, 메세지 끝 -->


        <!-- 

                북마크dialog
                
        -->
        <v-dialog v-model="dialog2" max-width="500px">
          <v-card>
            <v-card-title class="justify-space-around">
              저장할 컬렉션 선택
            </v-card-title>
            <v-card-text>


              <!-- 컬렉션 선택 select_box -->
              <v-select
                @click="getCollectionList(memberId)"
                :items="isCollections" item-text="catgName" item-value="catgId" label="저장할 컬렉션을 선택하세요" v-model="selectedCollection" />
            </v-card-text>
            <v-row class="ma-4 justify-space-around">
              <v-btn color="white" dense rounded dark @click="dialog3 = !dialog3">
                <v-icon color="#2255b1">mdi-plus</v-icon>
              </v-btn>
            </v-row>
            <v-card-actions>


              <!-- 북마크 컬렉션 지정 저장, 취소버튼 -->
              <v-row class="ma-4 justify-space-around">
                <v-btn
                  text
                  @click="bookmark(selectedCollection, items.postId)"
                >
                  저장
                </v-btn>
                <v-btn text @click="dialog2 = false"> 취소 </v-btn>
                <!-- 북마크 컬렉션 지정 저장,취소버튼 끝 -->


              </v-row>
            </v-card-actions>
          </v-card>
        </v-dialog>


        <!-- 컬렉션추가 dialog in dialog -->
        <v-dialog v-model="dialog3" max-width="500px">
          <v-card>
            <v-card-title class="justify-space-around">
              <span>새 컬렉션 생성</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field v-model="catgName" 
                                  label="*컬렉션이름을 입력해주세요!" 
                                  required
                                  :rules="rules" />

                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="mx-auto">
              <v-row class="ma-4 justify-space-around">
                <v-btn v-if="catgName.length >=1 && catgName.length<=10 && catgNames.indexOf(catgName) == -1 && checkSpace(catgName) == false && checkSpecial(catgName) == false"
                      color="blue darken-1"
                      @click="createCollection(memberId)"
                      text >
                  저장
                </v-btn>
                <v-btn color="primary" text @click="dialog3 = false">
                  취소
                </v-btn>
              </v-row>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!-- 북마크 끝 -->


        <!-- 

          내용

         -->
        <div class="contents">
          <v-card-text class="text--primary">
            <p id="content_box">{{ items.content }}</p>
          </v-card-text>
        </div>
        <!-- 내용 끝 -->


        <!-- 

          해시태그(키워드검색)

         -->
        <v-chip-group id="hashtagGroup" class="ml-8">
          <v-chip
            v-for="hashtag in hashtags" :key="hashtag" :color="`${colors[nonce - 1]} lighten-3`"
            @click="search($event)"  label small>
            <span style="color:#2255b1;">#{{ hashtag }}</span>
          </v-chip>
        </v-chip-group>
        <br />


        <!-- 

          댓글

         --> 
        <v-col cols="12">
          <CmtReg @cmtCount="cmtAllCount" :postid="postId" :targetId="items.memberId" />
        </v-col>
      </v-card>
    </v-container>
  </div>
  </div>
</template>

<script>

import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";
import CmtReg from "@/components/sns/FeedDetail/CmtReg.vue";
//Kakao.init('0e317fda8cca7ac1d7e440fc807131bd'); // 사용하려는 앱의 JavaScript 키 입력
export default {
  name: "FeedDetail",
  components: { SnsSidebar, CmtReg },
  data() {
    return {
      //carousel
      model: 0,


      move: [],
      drag: false,
      touch: false,


      imgs: [], //이미지 저장
      imgsLength: Number,
      width: 800,
      

      //게시글관련
      items: [], //게시글 정보 저장
      hashtags: [], //해시태그 배열 split 후 저장
      feeds: [], //해시태그 검색 정보 저장
      targetType: 2,
      memberId: this.$store.state.id, //세션아이디
      writer: this.$route.query.writer, //글쓴이 아이디
      postId: this.$route.query.postId,
      show: true,
      targetId: "",
      colors: ["grey", "green", "purple", "indigo", "cyan"], //tag color
      nonce: 1,
      lists:[], //dot list 목록

      listTitle: "",

      roomId: 0, //채팅

      secret: Number, // 비밀글 여부

      //북마크
      dialog2: false, //컬렉션 선택 dialog
      dialog3: false, //컬렉션 추가 dialog
      // catgName: "", //카테고리이름
      thumbnail: "", //썸네일
      mark: 0, //북마크 아이콘
      isCollections: [], //유저의 기존 컬렉션,
      catgNames:[], //이미 존재하는 컬렉션 이름들
      selectedCollection: "", //북마크를 저장할 컬렉션
      catgName: "", //새로 생성할 컬렉션 이름
      rules: [
                value => !!value || '이름은 입력 부탁드립니다🙏', //이름 없으면
                value => (value && value.length <= 10) || '10글자 이내로 부탁드립니다🙏', //10글자 이내이면
                value => (this.catgNames.indexOf(value) == -1) || '이미 존재하는 이름입니다🙏', //이미 존재하는 이름이면
                value => (this.checkSpace(value) == false) || '공백은 자제 부탁드립니다🙏', //공백이 있으면
                value => (this.checkSpecial(value) == false ) || '특수문자는 자제 부탁드립니다🙏', //특수문자가 있으면
              ],

      notifications: false,
      sound: true,
      widgets: false,

      cmtCount:"",
      userOneInfo : [],
    };
  },
  setup() {},

  created() {


    // Kakao.init('0e317fda8cca7ac1d7e440fc807131bd'); //js키 초기화(페이지 로딩시 처음한번만)
    this.writer = this.$route.query.writer;
    this.postId = this.$route.query.postId;
    this.showDetail(this.postId, this.memberId); //게시글 상세 로드
    this.detailImg(this.postId); //게시글 이미지 로드

  },

  watch: {
    catgName(catgName){
      console.log("catgName: " + catgName);
    //   if(this.checkSpace(catgName) == true){
    //     this.
    //   }
    //   else if(this.checkSpecial(catgName) == true){
      
    // }
    }
  },


  methods: {
      goback() {
        this.$router.go(-1);
      },

      //카카오톡 공유하기
      cmtAllCount(cmtCount){
        this.items.cmts = cmtCount

      },
      sendLink() {
        
        Kakao.Link.sendDefault({
          objectType: 'feed',
          content: {
            title: this.items.memberId + ' 님 모하비 피드',
            description: this.items.content,
            imageUrl: 'https://ifh.cc/g/H0FFVT.jpg',
            link: {
              webUrl: this.$url + 'sns-feed-detail?writer=' + this.writer +'&postId=' + this.postId,
            },
          },
          // social: {
          //   likeCount: this.items.likes,  //좋아요 수
          //   commentCount: this.items.cmts,  //댓글 수
          // },
          buttons: [
            {
              title: '모하비에서 확인하기',  //첫 번째 버튼 
              link: {
                mobileWebUrl: this.$url + 'sns-feed-detail?writer=' + this.writer +'&postId=' + this.postId,  //버튼 클릭 시 이동 링크
                webUrl: this.$url + 'sns-feed-detail?writer=' + this.writer +'&postId=' + this.postId,
              },
            },
          ],
        })
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
        

      //세션유무 검증
      confirmMember(memberId){  
        if(memberId){
          console.log("true");
          return true;
        }
        else{
          console.log("false");
          return false;
        }
      },


      //로그인 검증 모달
      loginConfirm(){
        this.$swal({
          title: "로그인하셔야 가능하세요🙏",
          text: "🙏로그인화면으로 이동부탁드립니다🙏",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#2255b1",
          cancelButtonColor: "#F36A3E",
          cancelButtonText: "취소",
          confirmButtonText: "이동",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "login" });
          }
        });
      },


    /*
        
        게시글

    */
    //게시글 상세 로드
    showDetail(postId, memberId) {
      this.axios("/sns/user/feed_detail/" + postId, {
        params: {
          memberId: memberId,
        },
      })
        .then((res) => {
          console.log();
          this.items = res.data;

          //비공개 게시물일 경우
          if(this.memberId != this.writer && this.items.secPost === 1 ){
            this.$swal({
              // title: "정말 삭제할까요?",
              text: "비공개 처리된 게시물입니다!",
              icon: "warning",
              showCancelButton: false,
              confirmButtonColor: "#2255b1",
              confirmButtonText: "확인",
            }).then((result) => {
              if (result.isConfirmed) {
                this.$router.go(-1);
              }
          });

          } else{
            if(this.confirmMember(this.memberId) == true ){
              this.getBookmarkStatus(postId);
              this.getCollectionList(this.memberId);
              console.log(this.memberId + "의 좋아요 상태: " + this.items.likeStatus);

            }

            //dot list 세팅
            this.lists.push({title: "수정"});
            this.lists.push({title: "삭제"});
            if(this.items.secPost == 0){
              this.lists.push({title: "비밀글로"});
            } else {
              this.lists.push({title: "비밀글 해제"});
            }


            if (this.items.hashtag != null) {
              let str = this.items.hashtag; //%%,%%,%% 형태
              let hashtag = str.split(","); //해시태그 자르기
              this.hashtags = hashtag; //자른 해시태그들 hashtags에 담기
            }
        }
          console.log("상세페이지 접근 성공!");
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
          this.$router.push({ path: 'sns-feed-register', params: { postId: this.postId } });
        },



    //게시글 삭제 검증
    feedSwal(postId,n) {
      if(n == 1){
        this.$swal({
          title: "정말 삭제할까요?",
          text: "삭제된 게시글은 복구가 불가합니다🙏",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#2255b1",
          cancelButtonColor: "#F36A3E",
          cancelButtonText: "취소",
          confirmButtonText: "네, 삭제할게요!",
        }).then((result) => {
          if (result.isConfirmed) {
            this.deletePost(postId);
            this.$swal("삭제 완료!", "게시글이 삭제되었습니다.", "success");
          }
        });
      }
      if(n == 2) {
        this.$swal({
          title: "비밀글로 전환할까요?",
          text: "비밀글은 본인만 조회가 가능합니다🙏",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#2255b1",
          cancelButtonColor: "#F36A3E",
          cancelButtonText: "취소",
          confirmButtonText: "네, 전환할래요!",
        }).then((result) => {
          if (result.isConfirmed) {
            this.changeSecPost(postId);
            this.$swal("전환 완료!", "비밀 게시글로 전환되었습니다!", "success");
          }
        });
      } 
    },


    //게시글 삭제
    deletePost(postId) {
      this.axios
        .delete("/sns/myfeed/" + postId)
        .then((res) => {
          console.log("댓글 삭제 성공! " + res);
          this.goMyProfile(this.items.memberId);
        })
        .catch((err) => {
          alert(err);
        });
    },

    //비밀글로 전환 or 해제
    changeSecPost(postId) {
      console.log(postId);
      if(this.items.secPost == 0){
        this.secret  = 1;
      }else {
        this.secret = 0;
      }
        this.axios.put("/sns/myfeed/secret", {
          postId : postId,
          secPost: this.secret,
        })
        .then((res) => {
          if(this.items.secPost == 0){
            this.items.secPost  = 1;

            this.lists[2].title = "비밀글 해제"
          }else {
            this.items.secPost = 0;
            this.lists[2].title = "비밀글로"
            this.$swal("비밀글이 해제되었습니다!");
          }
          console.log(this.items.secPost);
        })
        .catch((err) => {
          alert("비밀글전환 실패" + err);
        });
    },


    //DOT LIST
    listBtn(i) {
      if (i == 0) {
        //게시글 수정
        console.log("수정하기");
        this.editPost();
      }
      if (i == 1) {
        //게시글 삭제
        console.log("삭제하기");
        this.feedSwal(this.items.postId,1);
      }
      if (i == 2 && this.items.secPost == 0) {
        //비밀글 전환
        console.log("비밀글로")
        this.feedSwal(this.postId,2);
      }
      if (i == 2 && this.items.secPost == 1) {
        //비밀글 해제
        console.log("비밀글 해제")
        this.changeSecPost(this.postId);
      }
    },

    
    //해시태그 키워드 검색
    search(e) {
      let getHashtag = e.target.innerText; //선택한 해시태그
      this.$router.push({ name: "snsmain", params: { detailHashtag : getHashtag },
      });
    },


    //채팅방 이동
    send() {
      let vm = this;
      this.axios
        .get("/getSnsChatRoomNo", {
          params: { myId : this.$store.state.id, targetId : this.items.memberId },
        })
        .then(function (res) {
          console.log("res.data.vroomNo : " + res.data.vroomNo);
          vm.$router.push({
            name: "chat",
            query: { getRoomId: res.data.vroomNo },
          });
        })
        .catch(function (err) {
          console.log(err);
        });
    },

    
    //좋아요
    like(memberId, postId) {


      //좋아요 알림
      const noticeContent = {
        myId: this.$store.state.id,
        targetId: this.items.memberId,
        contentType: 0,
        postId: postId,
        likeStatus: this.items.likeStatus,
        noticeType: 0,
      };
      this.stompClient.send(
     
        "/app/Notice",
        JSON.stringify(noticeContent),
        (res) => {
          console.log("좋아요 알림 전송"),
          console.log(res);
        }
      );


      //좋아요 유저검증
      if(this.confirmMember(memberId) == false){
        this.loginConfirm();
      }else{
        //상태값에따라 좋아요 or 취소처리
        this.axios
          .post("/sns/like", {
            targetId: postId,
            memberId: memberId,
          })
          .then((res) => {
            //좋아요 상태가 아닐 때
            if (this.items.likeStatus === 0) {
              ++this.items.likes;
              this.items.likeStatus = 1;
              console.log("좋아요 완료");

              //좋아요 상태일때
            } else if (this.items.likes > 0) {
              --this.items.likes;
              this.items.likeStatus = 0;
              console.log("좋아요 취소");
            }
          })
          .catch((err) => {
            console.log(err);
          });
       }
    },


    /*

      북마크
    
      */


    //북마크 전 로그인검증
    markLogin(memberId,e){
      if(this.confirmMember(memberId) == true && e==1){
        this.dialog2 = true;
      }else if(this.confirmMember(memberId) == true && e==2){
        Kakao.Share.sendDefault();
      }else{
        this.loginConfirm();
      }     
    },


    //북마크상태조회
    getBookmarkStatus(postId) {
      this.axios("sns/collection/bookmark/isBookmark/" + postId, {
        params: {
          memberId: this.memberId,
        },
      })
        .then((res) => {
          console.log("북마크상태 조회 성공!");
          console.log("북마크상태: "+res.data);
          this.mark = res.data; //변수에 0(북마크X), 1(북마크O)이 담김
          console.log("북마크 상태"+this.mark);
          
        })
        .catch((err) => {
          alert("게시글호출 실패" + err);
        });
    },


    //북마크
    bookmark(selectedCollection, postId) {
        this.axios
          .post("/sns/collection/bookmark", {
            catgId: selectedCollection,
            postId: postId,
            thumbnail: this.items.thumbnail,
          })
          .then((res) => {
            this.getBookmarkStatus(postId);
            this.dialog2 = false;
            console.log("북마크 성공!");
          })
          .catch((err) => {
            console.log(err);
          });
    },


    //컬렉션 리스트 호출
    getCollectionList(memberId) {

      this.axios("/sns/collection/" + memberId)
        .then((res) => {

          if(res.data.length == 0){ // 만들어놓은 컬렉션이 존재하지 않으면 디폴트 컬렉션 생성
                  this.createDefaultCollection(memberId);

          }else{ // 만든 컬렉션이 존재하면 이름만 뽑아서 바인딩

              for(let i=0; i<res.data.length; i++){

                let isCatgName = res.data[i].catgName; 
                this.catgNames.push(isCatgName);

              }
                this.isCollections = res.data;
          }
        })
        .catch((err) => {
          alert("컬렉션호출 실패" + err);
        });
    },


    //북마크 삭제
    bookmarkDel(postId, memberId) {
      this.axios
        .delete("/sns/collection/bookmark/" + postId + '/' + memberId)
        .then((res) => {
          this.getBookmarkStatus(postId);
          console.log("북마크 삭제 성공! " + res);
        })
        .catch((err) => {
          alert(err);
        });
    },


    //컬렉션생성
    createCollection(memberId) {
      if (this.catgName == "" || this.catgName == undefined) {
        this.$swal("컬렉션 이름을 입력해주세요🙏");
        return;
      }
      if (this.catgName.length > 11) {
        this.$swal("이름은 10글자 이내로 입력해주세요🙏");
        return;
      }
      this.dialog = !this.dialog;
      this.axios
        .post("/sns/collection", {
          memberId: memberId,
          catgName: this.catgName,
          thumbnail: "",
        })
        .then((res) => {
          // console.log(thumbnailImg);
          console.log(res);
          this.catgName = "";
          this.dialog3 = !this.dialog3;
          this.getCollectionList(memberId);

        })
        .catch((err) => {
          alert(err);
        });
    },


    //디폴트 컬렉션 생성
    createDefaultCollection(memberId){
        const thumbnail = 'bookmark_default.png'

        this.axios.post('/sns/collection', {
            memberId : memberId,
            catgName : 'default',
            thumbnail : thumbnail,

        }).then(res => {
            this.getCollectionList(memberId);
        }).catch(err => {
            alert(err);
        });
      },


    //사진 넘기기
    logic(e) {
      let currentMove = this.touch ? e.touches[0].clientX : e.clientX;
      if (this.move.length == 0) {
        this.move.push(currentMove);
      }
      if (this.move[this.move.length - 1] - currentMove < -100) {
        this.$refs.myCarousel.$el
          .querySelector(".v-window__prev")
          .querySelector(".v-btn")
          .click();
        this.drag = false;
        this.touch = false;
      }
      if (this.move[this.move.length - 1] - currentMove > 100) {
        this.$refs.myCarousel.$el
          .querySelector(".v-window__next")
          .querySelector(".v-btn")
          .click();
        this.drag = false;
        this.touch = false;
      }
    },


    //유저 프로필로 이동
    async goMyProfile(userId) {
      await this.getOneUser(userId);
      if (this.userOneInfo.role == 4) {
        this.$swal.fire("관리자에 의해 접근 금지된 유저 프로필입니다.");
      } else {
         this.$router.push({ path: "/sns-user-feed", query: { userId: userId } });
      }
    },
     async getOneUser(userId) {
      const vm = this;
      await this.axios({
            url: "/member/" + userId,
            method: "get",
          })
          .then(function (response) {
            vm.userOneInfo = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
    },
  },
};

</script>
<style scoped lang="css" src="@/assets/css/sns/FeedDetail.css" />