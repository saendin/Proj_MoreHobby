<template>
  <div class="container">
    <v-card-actions class="mb-16">
      <v-spacer></v-spacer>
      <div class="margin">
        <v-btn @click="voteMake()">
          <v-icon>mdi-plus-circle-outline</v-icon>투표 생성하기
        </v-btn>
      </div>
    </v-card-actions>
    <div v-if="items.length != 0">
    <v-card class="mx-auto mb-8" max-width="800" outlined v-for="(item,idx) in items" :key="item.date">
      <v-list-item three-line>
        <v-list-item-avatar tile size="60" class="rounded-pill">
          <v-img :src="profile[idx].avatar"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <div class="text-overline mt-6">
            {{ item.memberId }}
            <hr />
            {{ item.startDate | moment('YYYY-MM-DD HH:mm')}} ~ {{item.endDate | moment('YYYY-MM-DD HH:mm')}} 까지 투표 진행
          </div>
          <v-list-item-subtitle class="content mt-5">
            {{ item.content }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <div class="vote">
        <div class="voteContent">
          <v-card v-if="result == false && vote != item.voteId">
            <v-card-actions class="pl-6 pt-5">{{item.topic}}</v-card-actions>
            <v-row>
              <v-col class="pl-10" cols="12" sm="4" md="4">
                <v-radio-group v-model="itemSelectList[idx]['itemSelect']">
                  <v-radio 
                  v-for="vote in itemsList" 
                  v-if="item.voteId === vote.voteId" 
                  :key="vote.itemId" 
                  :label="vote.content" 
                  :value="vote.itemId"
                  hide-details/>
                </v-radio-group>
              </v-col>
            </v-row>
          </v-card>
          <v-card-text v-else class="text--primary" v-for="item in content" :key="item.itemID">
            <p>{{item.content}}</p>
            <v-progress-linear height="25" color="success" :value="calcProgress(item.cnt)">
              <strong>{{ item.cnt == 0 ? 0 : Math.ceil(calcProgress(item.cnt)) }}%</strong>
            </v-progress-linear>
          </v-card-text>
        </div>
      </div>
      <div v-if="item.endDate >= nowDate">
      <div v-if="itemSelectList[idx]['memberId'] === null">
      <v-card-actions class="mr-5">
        <v-spacer></v-spacer>
        <v-btn color="success" @click="insertSelect(item.voteId, itemSelectList[idx]['itemSelect'],idx)">투표하기</v-btn>
      </v-card-actions>     
      </div>
      
      <div v-else-if="itemSelectList[idx]['memberId'] === memberId">
        <v-card-actions class="mr-5">
        <v-spacer></v-spacer>
        <v-btn depressed color="success" v-if="item.voteId != vote" @click="selectCheck(item.voteId, itemSelectList[idx]['itemSelect'], selectItemList[idx]['itemSelect'])">투표하기</v-btn>
        <v-btn depressed color="deep-orange" class="white--text" v-if="item.voteId != vote" @click="voteResult(item.voteId)">결과확인</v-btn>
        <v-btn depressed color="deep-orange" class="white--text" v-if="item.voteId == vote" @click="voteResult(item.voteId)">목록으로</v-btn>
      </v-card-actions>
      </div>
      </div>
      <div v-else>
        <v-card-actions class="mr-5">
        <v-spacer></v-spacer>
        <v-btn v-if="item.voteId != vote" color="error" depressed disabled>종료가된 투표입니다.</v-btn>
        <v-btn depressed color="deep-orange" class="white--text" v-if="item.voteId != vote" @click="voteResult(item.voteId)">결과확인</v-btn>
        <v-btn depressed color="deep-orange" class="white--text" v-if="item.voteId == vote" @click="voteResult(item.voteId)">목록으로</v-btn>
      </v-card-actions>     
      </div>
    </v-card>
  </div>
    <div v-else class="nodata">
        🙏<br>
        게시글이 없습니다<br>
      첫 게시글의 주인공이 되어보세요!
      </div>
  </div>
</template>
<script>

export default {
  created() {
    this.getvoteList()
    this.getvoteItemList()
    this.voteItemSelect()
    this.selectCheckItem()
    this.setNowTimes()
  },
  data() {
    return {
      moimId : this.$route.params.moimId,
      result : false,
      vote : '',
      items: [],
      itemsList : [],
      itemSelectList : [],
      content: [],
      selectItemList : [],
      profile : [],
      memberId : this.$store.state.id,
      totalCnt: '',
      nowDate : ''
    };
  },
  methods: {
    setNowTimes () {
    let myDate = new Date()  
    this.nowDate = myDate
    },
    voteMake: function () {
      this.$router.push({ path: "makeVote" });
    },
    goDetail: function () {
      this.$router.push({ path: "voteDetail" });
    },
    getvoteList() {
      let vm = this
      this.axios.get("/voteList", {
        params : {
          moimId : this.moimId,
        }
      })
      .then ((resp) => {
        console.log(resp.data);
        this.items = resp.data
      })
      .catch((err) =>  {
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
    },
    getvoteItemList() {
      this.axios.get("/voteItemList", {
        params : {
          moimId : this.moimId,
        }
      })
      .then ((resp) => {
        console.log(resp.data);
        this.itemsList = resp.data
      })
      .catch((err) =>  {
        console.log(err)
      })
    },
    voteItemSelect() {
      this.axios.get("/voteItemSelect", {
        params : {
          memberId : this.$store.state.id,
          moimId : this.moimId
        }
      })
      .then ((resp) => {
        console.log(resp.data)
        this.itemSelectList = resp.data
      })
      .catch((err) => {
        console.log(err)
      })
    },
    updateSelect(voteId, select, preSelect) {
      console.log("select : " + select)
      console.log("preSelect : " + preSelect)
      this.axios.put("/selectVote", {
          voteId : voteId,
          memberId : this.$store.state.id,
          itemSelect : select,
          moimId : this.moimId,  
          preSelect : preSelect      
      })
      .then((resp) => {
        console.log(resp)
        this.voteResult(voteId)
        this.getvoteList()
        this.getvoteItemList()
        this.voteItemSelect()
      })
      .catch((err) => {
        console.log(err)
      })
    },
    insertSelect(voteId, select) {
      if(select != 0) {
      this.axios.get("/vote",{
        params : {
          voteId : voteId,
          memberId : this.$store.state.id,
          itemSelect : select,
          moimId : this.moimId
        }
      })
      .then((resp)=> {
        console.log(resp)
        this.voteResult(voteId)
        this.getvoteList()
        this.getvoteItemList()
        this.voteItemSelect()
      })
      .catch((err)=> {
        console.log(err)
      })
    } else {
      this.$swal('투표항목을 선택해주세요')
    }
      
    },
    voteResult(voteId) {
      this.axios.get("/voteResult", {
       params : {
        voteId : voteId
       } 
      })
      .then((resp)=> {
        console.log(resp)
        this.content = resp.data
        this.calcTotalCnt();
        if(voteId === this.vote) {
        this.vote = -1
        this.selectCheckItem()
         } else {
        this.vote = voteId
         }
      })
      .catch((err)=> {
        console.log(err)
      })
    },
    selectCheck(voteId, select, preSelect) {
      this.axios.post("/selectCheck",  {
          voteId : voteId,
          memberId : this.$store.state.id,
          itemSelect : select,
          moimId : this.moimId
      })
      .then((resp) => {
        console.log(resp)
        if (resp.data === 'YES') {
          this.updateSelect(voteId, select, preSelect)
          this.getvoteList()
          this.getvoteItemList()
          this.voteItemSelect()
        } else {
          this.$swal('중복투표를 할 수 없습니다.')
        }
      })
      .catch((err)=> {
        console.log(err)
      })
    },
    selectCheckItem() {
      this.axios.get("selectCheckItem", {
        params : {
          memberId : this.$store.state.id,
          moimId : this.moimId
        }
      })
      .then((resp)=> {
        console.log(resp)
        this.selectItemList = resp.data
      })
      .catch((err)=> {
        console.log(err)
      })
    },
    calcProgress(cnt) {
      //todo-퍼센트 구하기
      return cnt / this.totalCnt * 100;
    },
    calcTotalCnt() {
      //todo-총투표수구하기
      let sum = 0;
      
      if(this.content.length != 0 || this.content == null) {
        
        for(let item of this.content) {
          sum += item.cnt;
        }
      }
      this.totalCnt = sum;
    },
  },
}
</script>

<style scoped>
.container {
  width : 85%;
}
.voteContent {
  max-width: 90%;
  margin-bottom: 3%;

}
.nodata {
  width : 85%;
  height: 300px;
  margin-top : 250px;
  margin-left: 75px;
  font-weight: bold;
  text-align: center;
}
.vote {
  margin-left: 14%;
}
.margin {
  margin-right: 10.5%;
}
</style>