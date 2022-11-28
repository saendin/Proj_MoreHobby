<template>
  <div class="container">
  <!-- <MoimSidebar></MoimSidebar> -->
  <!--비회원 소모임 모집 -->
  <v-card-actions  v-if="moimRight==='0'" >
  <v-spacer></v-spacer>
  <div class="text-center">
    <v-dialog
      v-model="noneuser"
      width="500"
    >
  <template v-slot:activator="{ on, attrs }">
  <v-btn text>
  <v-chip
    color="#D66B43"
    outlined
    v-bind="attrs"
    v-on="on"
  >
  <v-icon left>
    mdi-plus
  </v-icon>
  새로운 소모임 모집하기
</v-chip>
</v-btn>
</template>
<v-card>
  <br><br>
        <v-card-text class="font-weight-bold">
          소모임을 만들기 위해서는 먼저 로그인을 해주세요!.
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            depressed
            @click="noneuser=false"
          >
            닫기
          </v-btn>
          <v-btn
            color="success"
            depressed
            @click="join"
          >
            로그인하러 가기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</v-card-actions>
<!-- 회원 소모임 버튼 -->
<v-card-actions v-else>
  <v-spacer></v-spacer>
  <v-btn text>
  <v-chip
    color="#D66B43"
    outlined
    @click="select"
  >
  <v-icon left>
    mdi-plus
  </v-icon>
  새로운 소모임 모집하기
</v-chip>
</v-btn>
</v-card-actions>
    <h1>Mo#obby 소모임</h1> 
    <div>
      <!-- moimRight : {{moimRight}} newRight : {{newRight}} moimLength : {{moimLength}} -->
    <!-- 소모임 유저별 모임 리스트 -->
    <MyMoim_leader v-if="(moimRight == 2 || moimRight == 3) && (newRight == 1 || moimLength == 1)"></MyMoim_leader>
    <MyMoim_user v-else-if="(moimRight == 1 || moimRight == 0) && (newRight == 1)"></MyMoim_user>
    <MyMoim_none v-else-if="moimRight == '' || newRight == 0 || moimLength == 0"></MyMoim_none>
  </div>
    <!-- 소모임 검색창 -->
    <br>
    <div>
      <v-text-field outlined label="Search" append-icon="mdi-magnify" hide-details @keyup.enter="submit" v-model="search">
      </v-text-field>
    </div>
    <!-- 소모임 키워드 -->
    <div>
      <v-row justify="space-around">
    <v-col cols="12" sm="30" md="30">
      <v-sheet class="py-4 px-1">
        <v-chip-group active-class="primary--text">
          <v-chip v-for="catg in catgs" :key="catg" :value="catg" @click="chipclick" color="rgb(34, 85, 177)" text-color="white">
            {{ catg }}
          </v-chip>
        </v-chip-group>
      </v-sheet>
    </v-col>
  </v-row>
    </div>
  </div>
</template>
<script>
  import MyMoim_none from "./mymoim/MyMoim_none.vue";
  import MyMoim_user from "./mymoim/MyMoim_user.vue";
  import MyMoim_leader from "./mymoim/MyMoim_leader.vue";
  export default {
    components :{ MyMoim_user, MyMoim_leader, MyMoim_none },
    mounted() {
    this.$store.watch(
      () => this.$store.getters.getId,
      () => {
        console.log(this.moimRight);
      }
    );
  },
    data() {
      return {
        catgs: [
          '전체',
          '취미',
          '스포츠/레저',
          '어학/외국어',
          '문화/예술',
          '여행/캠핑',
          '음악',
          '건강/다이어트',
          '친목/모임',
          '맛집/요리',
          '인문/과학',
          '팬클럽',
          '게임',
          '만화/애니메이션',
          '반려동물/동물',
          '교육/공부',
          'IT/컴퓨터',
          '경제/재테크',
          '종교/봉사',
          '자연/귀농'
        ],
        search : '',
        moimRight : '',
        id : '',
        newRight : null,
        moimLength : null,
        noneuser : false,
      }
    },
    created() {
      this.moimRight = this.$store.state.user.role;
      this.id = this.$store.state.id;
      this.moimR();
      this.moimnewRight();
    },
    methods : {
  select : function() {
    if (this.moimRight !== '0') {
      this.$router.push({ path: 'moimRegisterIn' })
      }
    },
    join() {
     this.$router.push({ path: 'login' })
    },
    submit() {
      console.log(this.search)
      this.$emit('search', this.search)
    },
    chipclick(e) {
      console.log(e.target.innerText)
      if(e.target.innerText === '전체') {
        this.$emit('category', '')
      } else {
      this.$emit('category', e.target.innerText)
    }
    },
    moimR() {
      this.axios.get("/moimRight", {
        params : {
          memberId : this.$store.state.id,
        }
      })
      .then((resp)=> {
        console.log("moimRight");
        console.log(resp.data.length)
        if(resp.data.length == 0) {
          this.moimLength = 0
        } else {
          this.moimLength = 1
        }
        this.moimRight = this.$store.state.user.role;
      })
      .catch((err)=> {
        console.log(err)

      }) 
    },
    moimnewRight() {
      this.axios.get("/moimnewRight", {
        params : {
          memberId : this.$store.state.id,
        }
      })
      .then((resp)=> {
        console.log("NEWRIGHT")
        console.log(resp.data.length)
        if(resp.data.length == 0) {
          this.newRight  = 0
        } else {
          this.newRight = 1
        }
        this.moimRight = this.$store.state.user.role;
      })
    }
  }
 }

</script>
<style scoped>
  .container {
    width : 1000px;
  }

  .h1 {
    display: inline-block;
  }
</style>