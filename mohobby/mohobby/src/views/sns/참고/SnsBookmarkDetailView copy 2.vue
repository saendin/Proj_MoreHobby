<!-- 댓글입력창 -->
<template>
  <div>
    <div>


      <!-- 본 댓글입력창 : 세션값 있으면 입력 가능. 없으면 입결 불가능-->
      <v-card-actions>
        <v-col cols="10" v-if="memberId">
          <v-text-field
            class="m1-11"
            placeholder="댓글을 입력해주세요!"
            counter
            filled
            rounded
            solo
            dense
            hide-details
            v-model="inputCmt"
            name="content"
            @keydown.enter="regCmt()"
          />
        </v-col>
        <v-col cols="10" v-else>
          <v-text-field
            class="m1-11"
            placeholder="댓글은 로그인부터 부탁드립니다🙏"
            disabled
            counter
            filled
            rounded
            solo
            dense
            hide-details
          />
        </v-col>
        <v-spacer></v-spacer>


        <!-- 댓글 등록버튼 -->
        <div style="margin-right: 80px">
          <v-btn @click="regCmt()" class="ma-2 white--text" color="#2ac187" dense rounded>등록</v-btn>
        </div>
      </v-card-actions>
    </div>
    <!-- 본 댓글 입력창 끝 -->

  
    <!-- 댓글리스트 -->
    <div>
      <div class="profile" v-for="cmt in comments" :key="cmt.commId">
        <div v-if="cmt.parentCommId != ''" style="width: 35px"></div>
        <v-avatar class="ml-10 my-5 mr-4" color="grey darken-1" size="30">
          <v-img aspect-ratio="30" alt="profile_img" style="cursor: pointer"
            :src="require(`@/assets/image/user/${cmt.profileImg}`)"
          @click="$router.push({ path: '/snsUserFeed?userId=' + cmt.memberId }).catch(() => {$router.go(0);})" />
        </v-avatar>
        <input type="hidden" class="cmtId" value="cmt.commId" />
        <div class="user text-overline">
          {{ cmt.memberId }}
          <small class="date">
            <span style="font-size: 1em">
              {{writeDate(cmt.writeDate)}}
            </span>
          </small>


          <!-- 버튼: 저장, 답장, 수정, 삭제 
            댓글 쓴 사람과 로그인세션 아이디가 같을 떄 -> 수정, 삭제, 답장 버튼
           -->
          <div class="btn">
            <v-btn
              v-if="replyBtn == true"
              @click="showReCmtForm(cmt.commId, cmt.memberId)"
              x-small
              outlined
              color="dark-grey"
              class="mr-3"
              >답장</v-btn>



            <v-btn
              v-if="cmt.memberId == memberId && editBtn == true"
              @click="showEditForm(cmt.commId)"
              x-small
              outlined
              color="success"
              class="mr-3"
              >수정</v-btn>

              <v-btn
                v-if="cmt.memberId == memberId && deleteBtn == true"
                @click="deleteCmt(cmt.commId, cmt.targetId)"
                x-small
                outlined
                color="error"
                >삭제</v-btn>

              <v-btn
                v-if="cmt.memberId == memberId && saveBtn == true"
                @click="editCmt(cmt.commId)"
                x-small
                outlined
                color="success"
                class="mr-3"
                >저장</v-btn>

                <v-btn
                v-if="cmt.memberId == memberId && cancelBtn == true"
                @click="cancleEdit(cmt.commId)"
                x-small
                outlined
                color="success"
                class="mr-3"
                >취소</v-btn>
          </div>
          <!-- 댓글 버튼 끝 -->


          <!-- 대댓폼 (유저소환) -->
          <v-card-actions>
            <div class="content">
              <!-- 상위댓글이 있으면 -->
              <div v-if="cmt.parentCommId != '' && originContentFrm == true">
                <span class="member_id" 
                      @click="$router.push({path: '/snsUserFeed?userId=' + cmt.parentMemberId,}).catch(() => {$router.go(0);})">
                <strong>@{{ cmt.parentMemberId }}</strong></span>
                {{ cmt.content }}
              </div>
              <!-- 상위댓글이 없으면 -->
              <!-- <div v-if="cmt.parentCommId == '' && cmt.commId != editForm && !formValue"> -->
              <div v-if="originContentFrm == true">
                {{ cmt.content }}
              </div>
              <!-- 유저소환 끝 -->


              <!-- 댓글 수정폼 -->
              <!-- <div v-if="cmt.commId == editForm && cmt.commId != reCmt && formValue"> -->
              <div v-if="cmt.commId == editCmtId && editForm == true">
                <v-textarea
                  name="editContent"
                  auto-grow
                  outlined
                  :value="originContent"
                  @keydown.enter="editCmt(cmt.commId)"
                  v-model="editedContent"
                ></v-textarea>
              </div>
            </div>
          </v-card-actions>


          <!-- showReCmtForm(cmt.commId) 대댓글 입력창 -->
          <div v-if="cmt.commId == reCmt">
            <v-card-actions>
              <v-col cols="10">
                <v-text-field
                  class="m1-11"
                  filled
                  rounded
                  solo
                  dense
                  hide-details
                  :prefix="cmtMemberId"
                  v-model="inputReCmt"
                  name="content"
                  @keydown.enter="regReCmt(cmt.commId, cmt.memberId)"
                />
              </v-col>
              <v-spacer></v-spacer>


              <!-- 대댓글 등록버튼 -->
              <div style="margin-right: 80px">
                <v-btn class="ma-2 white--text" color="#2ac187" rounded @click="regReCmt(cmt.commId, cmt.memberId)">등록</v-btn>
              </div>
            </v-card-actions>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Cmt",
  props: {
    postid: String,
    targetId: String,
 
  },


  data() {
    return {
      cmtCount:"",
      inputCmt: "", //댓글v-model
      inputReCmt: "", //대댓글v-model
      editedContent: "", //수정댓글v-model
      originContent: "", //원댓글
      memberId: this.$store.state.id,
      comments: [], //cmt리스트
      editCmtId: "",
      reCmt: "", //대댓등록창 show여부
      cmtMemberId: "", //소환된 회원
      // formValue: false, //form 노출여부
      originContentFrm: false, //원댓폼
      editForm: false, //댓글수정창
      replyBtn: false, //답장버튼
      saveBtn: false, //저장버튼
      editBtn: false, //수정버튼
      deleteBtn: false, //삭제버튼
      cancelBtn: false, //취소버튼
    };
  },


  created() {
    this.getCmtList(); //댓글리스트
    this.editBtn = true;
    this.deleteBtn = true;
    this.replyBtn = true;
  },

  methods: {

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
        confirmButtonColor: "#2ac187",
        cancelButtonColor: "#d33",
        cancelButtonText: "취소",
        confirmButtonText: "이동",
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push({ path: "login" });
        }
      });

    },


    //date filter
    writeDate(writeDate) {
      return this.$moment(writeDate).fromNow();
    },

    
    //댓글리스트
    getCmtList() {
      this.axios("/sns/cmt/" + this.postid)
        .then((res) => {
          this.comments = res.data;
          this.cmtCount=res.data.length
          this.$emit('cmtCount',  this.cmtCount)
        })
        .catch((err) => {
          console.log(err);
        });

    },


    //댓글등록 - 입력창
    regCmt() {
      if(this.confirmMember(this.memberId) == false){ //유효성검사 - 회원 id, 내용값
        this.loginConfirm();

      } else if (this.inputCmt == ""){ 
        this.$swal("내용 입력하셔야 등록 가능하세요🙏")
        
      } else{
        this.axios
        .post("/sns/cmt", {
          memberId: this.memberId,
          targetId: this.postid,
          content: this.inputCmt,
        })
        .then((res) => {
          console.log("댓글등록 성공! " + res);
          this.inputCmt = ""; //댓글입력창 초기화
          this.getCmtList();

          const noticeContent = {
            myId: this.$store.state.id,
            targetId: this.targetId,
            contentType: 1, //0:좋아요 1:댓글
            postId: this.postid,
            noticeType: 0, //0:sns ,1:소모임, 2:강의
          };

          this.stompClient.send(
            "/app/Notice",
            JSON.stringify(noticeContent),
            (res) => {
              console.log("알림성공");
            }
          );
        })
        
        .catch((err) => {
          console.log();
          alert("댓글등록실패: "+err);
        });
      }

    },


    //댓글 삭제
    deleteCmt(commId, targetId) {

      this.axios
        .delete("/sns/cmt/" + commId + "/" + targetId)
        .then((res) => {
          console.log("댓글 삭제 성공! " + res);
          this.inputCmt = ""; //댓글입력창 초기화
          this.getCmtList();
        })

        .catch((err) => {
          alert(err);
        });
        
    },


    //댓글 수정버튼
    showEditForm(cmtId, content) {
      //수정폼 닫기
      if (this.editForm) { //수정폼 열린상태면
        this.editForm = false; //닫고
        this.editCmtId = -1;
        this.originContentFrm = true; //원댓폼
        this.saveBtn = false; //저장버튼 숨김
        this.cancelBtn = false; //취소버튼 숨김
        this.replyBtn = true;//답장버튼 노출
        this.editBtn = true; //수정버튼 노출
        this.deleteBtn = true; //삭제버튼 노출

      //수정폼 열기
      } else { //수정폼 닫힌 상태면
        this.editForm = true; //수정폼 열기
        this.editCmtId = cmtId;
        this.originContentFrm = false; //원댓폼
        this.replyBtn = false; //답장버튼 숨김
        this.editBtn = false; //수정버튼 숨김
        this.deleteBtn = false; //삭제버튼 숨김
        this.saveBtn = true; //저장버튼 노출
        this.cancelBtn = true; //취소버튼 노출
      }

    },
    

    //댓글 수정 저장
    editCmt(commId) {
      
      this.editedContent = this.cmt.content; //변경된 내용으로 교체

      this.axios
        .put("/sns/cmt/" + commId, {
          content: this.editedContent,
        })
        .then((res) => {
          this.editForm = "";
          console.log("댓글수정 성공! " + res);

          this.showEditForm();
          

          this.getCmtList();
        })
        .catch((err) => {
          alert("댓글수정 실패: "+err);
        });
    },

    //댓글 취소
    cancleEdit(cmtId){
      this.showEditForm();
      return;
    },


    //대댓 등록창
    //답장버튼 -> 세션 정보 있으면 대댓글 입력창 열리게
    //v-if 인자 comm_id == reCmt -> 열림 / comm_id != reCmt ->닫힘
    showReCmtForm(cmtId, cmtmemId) {
      if(this.confirmMember(this.memberId) == false){ //유효성검사 - 회원 id, 내용값
        this.loginConfirm();

      } else {
        this.inputReCmt = ""; //대댓 입력창 초기화

        if (cmtId == this.reCmt) {
          this.reCmt = -1; //댓글창닫기

        } else {        
          this.reCmt = cmtId; //댓글창열기
          if (cmtId == this.reCmt) this.cmtMemberId = "@" + cmtmemId; //대댓 달 유저 아이디 호출
        }

      }
    },


    //대댓글등록 - 회원아이디가 있고, 내용 있을 때
    regReCmt(commId, parentMemberId) {
      if (this.inputCmt == ""){ 
        this.$swal("내용 입력하셔야 등록 가능하세요🙏")
        return;
      } else{
      let vm =this
      this.axios
        .post("/sns/recmt", {
          postId: this.postid,
          memberId: this.memberId,
          parentCommId: commId,
          targetId: this.postid,
          content: this.inputReCmt,
          parentMemberId: parentMemberId,
        })
        .then((res) => {
          console.log("대댓글등록 성공! " + res)
          this.inputReCmt = "" //입력창 초기화
          this.reCmt = -1 //대댓글창 닫히게
          this.getCmtList(); //리스트 재호출


          //대댓글 알림
          const noticeContent = {  
            myId: this.$store.state.id,
            targetId: parentMemberId,
            contentType: 2, //0:좋아요 1:댓글
            postId: vm.postid,
            noticeType: 0, //0:sns ,1:소모임, 2:강의
          };
          console.log(noticeContent)
          this.stompClient.send(
            "/app/Notice",
            JSON.stringify(noticeContent),
            (res) => {
              console.log("대댓글 알림 성공");
            }
          );
        })
        .catch((err) => {
          console.log("대댓글 등록 실패"+err);
        });
      }
    },

    //댓글 삭제버튼 추가하기
    // onAdd(){
    //   const input = documnet.querySelector('.input');
    //   const itemAdd = document.querySelector('.itemAdd');
    //   const items = document.querySelector('.items');

    //   const item = document.createElement('li');
    //   item.setAttribute('class', 'item');

    //   const itemText = document.createElement('span');
    //   itemText.setAttribute('class', 'itemText');
    //   itemText.innerHtml = text;

    //   const itemDel = document.createElement('button');
    //   itemDel.setAttribute('class','itemDel');
    //   itemDel.innerHTML = '삭제';

    //   item.appendChild(itemText);
    //   item.appendChild(itemDel);
    //   item.appendChild(item);

    //   itemAdd.addEventListener('click',() => {
    //     onAdd();
    //   })
    // },
  },
};
</script>
<style scoped lang="css" src="@/assets/css/sns/CmtReg.css" />