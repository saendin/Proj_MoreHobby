<template>
  <div>
    <div style="background-color: #F5F5F5; ">
    <div class="container">
      <v-container>
       <v-text-field label="글 내용 검색" v-model="boardSearch" @keyup.enter="Search" single-line solo></v-text-field>
      </v-container>
      <div v-if="items.length != 0">
    <v-card
      class="mx-auto mb-8"
      max-width="800"
      outlined
      v-for="(item, idx) in items"
      :key="idx"
      @click="goPost(idx)"
    >
      <v-list-item three-line>
        <v-list-item-avatar tile size="60" class="rounded-pill">
          <v-img :src="profile[idx].avatar"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <div class="text-overline mt-6">
            {{ item.memberId }}
            <hr />
            {{ item.writeDate | moment('YYYY-MM-DD HH:mm') }}
          </div>
          <v-list-item-subtitle class="content mt-5">
            {{ item.title }}
          </v-list-item-subtitle >
        </v-list-item-content>
      </v-list-item>
      <v-card-actions>
        <v-spacer></v-spacer>
        <div class="text-overline mb-1 mr-2">댓글 {{ item.cnt }}</div>
      </v-card-actions>
    </v-card>
    </div>
    <div v-else class="nodata">
        🙏<br>
        공지사항이 없습니다<br>
        관리자에게 문의해주세요
      </div>
    </div>
  </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      items: [],
      //moim : 비공개 0 공개 1
      //moimRight : 비회원 0 회원 1
      moimRight: 1,
      moimId : this.$route.params.moimId,
      boardType : 0,
      boardSearch : "",
      profile: []
    };
  },
  methods: {
    goPost(idx) {
      this.$router.push({ name: "moimPost", query : { moimId : this.moimId, boardId: this.items[idx].boardId, boardType: 0}});
    },
    getNotice() {
      let vm = this
      this.axios.get("/noticeList", {
        params : {
          moimId : this.moimId,
          boardType : 0
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
                memberId : vm.items[i].memberId
              }
            }).then((response)=>{
              this.profile.push({avatar: require(`@/assets/image/user/${response.data}`)})
            }).catch((err)=>{
              console.log(err)
            })
          }
        })
      }
    },
    Search() {
      console.log('검색테스트');
      console.log(this.boardSearch);
      this.axios.get("/boardSearch", {
       params : {
        moimId : this.moimId,
        title : this.boardSearch,
        boardType : this.boardType
       } 
      })
      .then((resp)=> {
        console.log(resp);
        this.items = resp.data
      })
      .catch((err)=> {
        console.log(err);
      })
    },
  created() {
    this.getNotice()
  },
};
</script>
<style scoped>
.container {
  width: 91%;
}
.nodata {
  width : 85%;
  height: 300px;
  margin-top : 250px;
  margin-left: 75px;
  font-weight: bold;
  text-align: center;
}
</style>