<template>
  <div>
    <v-row>
      <v-col cols="12" md="6" class="mx-auto">
        <v-sheet height="64">
          <v-toolbar
          flat
        >
        <!-- prev month -->
        <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="prev()"
          >
            <v-icon small>
              mdi-chevron-left
            </v-icon>
          </v-btn>

          <!-- next month -->
          <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="next()"
          >
            <v-icon small>
              mdi-chevron-right
            </v-icon>
          </v-btn>
          
          <v-toolbar-title v-if="calendar">
            {{today}}
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-menu
            bottom
            right
          >
          </v-menu>
        </v-toolbar>
      </v-sheet>

         <!-- 달력 부분 -->
         <v-sheet height="500" max-width="800">
          <v-calendar   v-model="value" ref="calendar" @click:date="open"></v-calendar>
        </v-sheet>
      </v-col> 
    </v-row>

   <!-- 다이어로그 띄우기 -->
   <div class="example">
      <MakeSchedule  :items.sync="items" :dialog="dialog" :calendar="calendar" @dialogClose="dialogClose"></MakeSchedule>
    </div>

    <!-- 일정 조회 -->
    <template>
      <v-card
      class="mx-auto mb-8 mt-5"
      max-width="700"
      outlined
      v-for="(item, idx) in items"
      :key="item.writeDate"
     
      >
      <v-list-item three-line>
        <v-list-item-content>
          <div class="text-overline mt-6">
            {{ item.title }} 
            <hr />
            {{ item.startDate }} {{item.startTime}} ~ {{item.endDate}} {{item.endTime}}
          </div>
          <v-list-item-subtitle class="content mt-5 mb-5">
            {{ item.info }}</v-list-item-subtitle
          >
        </v-list-item-content>
        <v-btn x-small outlined color="error"  id="delete"  @click="deleteSchedule(item.skedId)">
              삭제</v-btn>
      </v-list-item>
      
    </v-card> 
  </template>
  </div>
</template>
  
<script>
import MakeSchedule from "./MakeSchedule.vue"
export default {
  data() {
    return {
      Id: this.$route.params.moimId,
      items: [], //소모임 일정 전체조회
      month:'',
      year:'',
      value: '',
      dialog: false,
      today:'',
      //받아오는 값 저장할 변수 지정
      calendar: {
        startDate: '',
        startTime: '',
        info: '',
        endDate: '',
        endTime: '',
        title: '',
        dateOpen: false,
        memberId: this.$store.state.id,
      }
    }},

  created(){
    this.todate()
    this.getSchedule()
  },
  components: { MakeSchedule },
  methods: {
    deleteSchedule(skedId){
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
      this.axios.delete("/deleteSchedule",{
        params:{
          skedId : skedId,
        }
      }).then((resp) => {
        console.log("일정 삭제 결과" + resp);
        this.$swal(
            '삭제 완료!',
            '작성한 일정을 삭제하였습니다.',
            'success'
          )
        vm.getSchedule()
      }).catch((err) => {
        console.log(err)
      })
        }
      })
    },
      getSchedule() {
      this.axios
        .get("/selectSchedule", {
          params: {
            moimId: this.Id,
          },
        })
        .then((resp) => {
          console.log(resp);
          console.log(this.items);
          this.items = resp.data;
   
        })
        .catch((err) => {
          console.log(this.items);
          console.log(err);
        });
    },
    todate() {
      var day = new Date();
      this.year = day.getFullYear();
      this.month = ("0" + (day.getMonth() + 1)).slice(-2);
      this.today=this.year+"-"+this.month;
    },
    open(date) {
      console.log(date)
      this.calendar.startDate = date.date;
      console.log(this.calendar);
      // this.$store.commit('OPEN_CALENDAR_DIALOG', date)
      //다이어로그 실행
      this.dialog = true;
    },
    dialogClose(dialog) {
      this.getSchedule();
      this.dialog = false;
    },
    prev () {
      this.month--
      if(this.month<1){
        this.month=12;
        this.year--;
      }
      this.today=this.year+"-"+this.month;
      this.$refs.calendar.prev()
    },
    next () {
      this.month++
      if(this.month>12){
        this.month=1;
        this.year++;
      }
      this.today=this.year+"-"+this.month;
      this.$refs.calendar.next()
    },
  },
  mounted: {

  },
  create(){
this.calendar.title = new Date()
  }
}
</script>
<style>
.smallCal{
  width: 200px;
}

#delete{
  float: right;
  margin-right: 3%;
  margin-top: 5%;
}
</style>