<template>
  <!-- 프로필 -->
  <div>
    <div class="profile" v-for="(item,idx) in items" :key="item.commId">
      <v-avatar class="ml-10 my-5 mr-4" color="grey darken-1" size="30">
        <v-img :src="profile[idx].avatar"></v-img>
      </v-avatar>
      <div class="user text-overline" style="width:600px">{{item.commentWriter}}
        <small class="date">{{item.commentDate | moment('YYYY-MM-DD HH:mm')}}</small>
        <div v-if="item.commentWriter == memberId">
        <div class="btn" v-if="item.commId != editForm">
          <v-btn x-small outlined color="success" class="mr-3" @click="updateComment(item.commId)">수정</v-btn>
          <v-btn x-small outlined color="error" @click="alert(idx)">삭제</v-btn>
        </div>
        <div class="btn" v-if="item.commId == editForm">
          <v-btn x-small outlined color="success" class="mr-3" @click="updateComplete(idx)">수정완료</v-btn>
          <v-btn x-small outlined color="error" @click="editForm = -1">취소</v-btn>
        </div>
      </div>
        <v-card-actions class="mt-2">
          <v-col>
            <div id="comment" class="content" v-if="item.commId != editForm"> {{item.content}} </div>
            <div id="text-field" v-if="item.commId == editForm">
              <v-text-field hide-details v-model="contents" @keydown.enter="updateComplete(idx)">
              </v-text-field>
              <v-spacer />
            </div>
          </v-col>
        </v-card-actions>
      </div>

    </div>
    <!-- 댓글 -->
    <div>
      <v-card-actions>
        <v-col cols="10">
          <v-text-field class="ml-11" placeholder="댓글을 남겨보세요!" filled rounded dense hide-details v-model="content"
            @keyup.enter="insertComment()"></v-text-field>
      </v-col>
        <v-spacer></v-spacer>
        <div style="margin-right: 80px">
          <v-btn rounded color="orange" text @click="insertComment()">
            <v-icon>mdi-send</v-icon>
          </v-btn>
        </div>
      </v-card-actions>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    onerId: String,
    boardType: String
  },
  data() {

    return {
      dialog: false,
      boardId: this.$route.query.boardId,
      moimId: this.$route.query.moimId,
      boardType: this.$route.query.boardType,
      profile : [],
      items: [],
      profile: [],
      memberId: this.$store.state.id,
      targetId: '',
      content: '',
      contents: '',
      editForm: "",
    }
  },
  methods: {
    alert(idx) {
      this.$swal({
        title: '정말 삭제할까요?',
        text: "삭제를 원하지 않으면 취소버튼을 눌러주세요!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#2ac187',
        cancelButtonColor: '#d33',
        cancelButtonText: '취소',
        confirmButtonText: '네, 삭제할게요!'
      }).then((result) => {
        if (result.isConfirmed) {
          let vm = this;
      this.axios.delete("/boardDeleteComm",{
        params:{
          commId : this.items[idx].commId,
        }
      }).then((resp) => {
        console.log("댓글 삭제 결과" + resp);
        this.$swal(
            '삭제 완료!',
            '작성한 댓글을 삭제하였습니다.',
            'success'
          )
        vm.getBoard()
      }).catch((err)=> {
        console.log(err)
      })
        }
      })
    },
    getBoard() {
      let vm = this;
      this.axios.get("/detailComment", {
        params : {
          moimId : this.moimId,
          boardType : this.boardType,
          boardId : this.boardId
        }
      })
      .then((resp)=> {
        console.log(resp)
        console.log(this.items)
        this.items = resp.data;
      })
      .catch((err) => {
        console.log(this.items)
        console.log(err)
      }).finally((response)=>{
          console.log(response)
          for(let i=0; i<vm.items.length; i++){
            vm.axios.get("/getImg",{
              params:{
                memberId : vm.items[i].commentWriter
              }
            }).then((response)=>{
              this.profile.push({avatar: require(`@/assets/image/user/${response.data}`)})
            }).catch((err)=>{
              console.log(err)
            })
          }
        })
    },
      updateComment(commId, contents) {
        if(commId == this.editForm){ //수정창닫기
          this.editForm = -1;
        } else{ //댓글창열기
          this.editForm = commId;
          this.contents = contents
        }
      },
      updateComplete(idx){  
        let vm = this

        this.axios.put("/updateComment",{
          content : this.contents,
          commId : this.items[idx].commId,
      })
        .then((resp)=> {
          console.log("댓글 수정 결과" + resp);
          this.$swal("댓글 수정 완료");
          this.editForm = -1;
          vm.getBoard()
        })
        .catch((err) => {
          console.log(this.items)
          console.log(err)
        })
    },
    insertComment() {
      let vm = this;
      this.axios.post("/insertMoimBoardComment", {
        memberId: this.memberId,
        targetId: this.boardId,
        content: this.content
      }).then((resp) => {
        console.log(resp.data);
        this.$swal("댓글등록 완료");
        this.content = '';
        vm.getBoard()
        const noticeContent = {
          myId: this.$store.state.id, //글작성 아이디
          targetId: this.onerId, //알림 받을 아이디
          contentType: 1, //댓글:1 게시글:2
          postId: this.$route.query.boardId,
          moimId: this.$route.query.moimId,
          boardType : this.boardType,
          noticeType:1 //sns:1 moim:2 class:3
        }
        vm.stompClient.send("/app/Notice", JSON.stringify(noticeContent), res => {
          console.log(res)
        });
      })
        .catch((error) => {
          console.log(error)
        })
    },
    updateComment(commId, contents) {
      if (commId == this.editForm) { //수정창닫기
        this.editForm = -1;
      } else { //댓글창열기
        this.editForm = commId;
        this.contents = contents
      }
    },
    updateComplete(idx) {
      let vm = this

      this.axios.put("/updateComment", {
        content: this.contents,
        commId: this.items[idx].commId,
      })
        .then((resp) => {
          console.log("댓글 수정 결과" + resp);
          this.$swal("댓글 수정 완료");
          this.editForm = -1;
          vm.getBoard()
        })
        .catch((err) => {
          console.log(this.items)
          console.log(err)
        })
    },
    deleteComment(idx) {
      let vm = this;
      this.axios.delete("/boardDeleteComm", {
        params: {
          commId: this.items[idx].commId,
        }
      }).then((resp) => {
        console.log("댓글 삭제 결과" + resp);
        this.$swal("댓글 삭제 완료")
        vm.getBoard()
      }).catch((err) => {
        console.log(err)
      })
    }
  },
  created() {
    this.getBoard()
  },
}
</script>
<style scoped>
.profile {
  position: relative;
  display: flex;
  margin-left: 30px;
  width: 90%;
}

.profile::after {
  content: "";
  position: absolute;
  left: 30px;
  height: 1px;
  width: 100%;
  border-bottom: 1px solid rgba(184, 189, 190, 0.8);
}

.user {
  margin-top: 20px;
}

.date {
  position: absolute;
  right: 0;
}

 .btn{
  position: absolute;
  right: 0;
 }
</style>