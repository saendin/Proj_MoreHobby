<template>
  <div class="container">
    <div class="flex">
      <v-chip class="ma-2 mt-4" text-color="white" color="black" label>목록</v-chip>
      <v-radio-group v-model="boardType" row>
        <v-radio label="메인글" class="ml-7"  value=1></v-radio>
        <v-radio v-if="memberId == adminId" label="공지" value=0></v-radio>
      </v-radio-group>
    </div>
    <div class="flex">
      <v-chip class="ma-2 mt-4" text-color="white" color="black" label>제목</v-chip>
      <v-col cols="12" sm="6" md="11">
        <v-text-field class="ml-4" label="제목을 입력해주세요!" v-model="title" solo dense></v-text-field>
      </v-col>
    </div>
    <div class="example">
      <quill-editor
        class="editor"
        v-model="content"
        :style="editorStyle"
      />
    </div>
    <div class="center">
      <v-btn>취소</v-btn>
      <v-btn color="success" class="ml-5" @click="testEditor()">작성완료</v-btn>
    </div>
  </div>
</template>

<script>

export default {
  name: "MoimBoardEditor",
  components: {
  },
  data() {
    return {
      editorStyle: {
        'height': '300px',
      },
      content: "",
      boardType: null,
      title: '',
      moimId: this.$route.query.moimId,
      boardId: '',
      memberId : this.$store.state.id,
      adminId : ''
    };
  },
  methods: {
    getContent(editorContent) {
      this.content = editorContent;
    }, 
    async clickSave(boardId) {
      document.querySelector(".ql-editor").style.display = 'none';
      let step = 0;
      let len = document.querySelector(".ql-editor").querySelectorAll("img").length;
      if (len != 0) {
        for (let i = 0; i < len; i++) {
          let src = document.querySelector(".ql-editor").getElementsByTagName("img")[i].src;
          step = await this.uploadImage(boardId, i, src, step);
        }
      }
      this.saveEditor(boardId);
    },
    //작성완료 버튼 클릭시
    testEditor() {
      this.axios.post("/insertBoard", {
        title: this.title,
        boardType: this.boardType,
        memberId: this.$store.state.id,
        moimId: this.moimId,
      })
        .then((resp) => {
          console.log(resp)
          console.log(resp.data)
          this.boardId = resp.data
          this.clickSave(this.boardId)
        })

    },
    saveEditor(boardId) {
      this.axios({
        method: "POST",
        url: "/saveMoimInfo",
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        timeout: 5000,
        data: JSON.stringify({
          content: this.content,
          filename: boardId
        })
      }).then((res) => {
        if (res.status == 200) {
          this.content = '';
          document.querySelector(".ql-editor").style.display = 'block';
        }
        if(this.boardType == 1) { 
        this.$router.push({ name: "moimBoard" });
        } else {
          this.$router.push({ name: "moimNotice" })
        }
      }).catch((error) => {
        console.log(`error: ${error}`);
      })
    },
    async uploadImage(folder, file, img, step) {   //사진이름은 pk, 사진순서는 index로 
      let result = step;

      let res = await this.axios.post('/uploadMoimImage', {
        foldername: folder,
        filename: folder + "-" + file,
        src: img
      })
    },
    getmoimadmin() {
      this.axios.get("/moimadmin",{
        params : {
          moimId : this.moimId
        }
      })
      .then((resp)=> {
        console.log(resp)
        this.adminId = resp.data[0].memberId
      })
      .catch((err)=> {
        console.log(err)
      })
    }
  },
  computed: {
  },
  mounted() {
    this.getmoimadmin()
  },
};
</script>

<style scoped>
.container {
  width: 85%;
}

.center {
  margin-top: 100px;
  display: flex;
  justify-content: center;
}

.flex {
  display: flex;
}
</style>