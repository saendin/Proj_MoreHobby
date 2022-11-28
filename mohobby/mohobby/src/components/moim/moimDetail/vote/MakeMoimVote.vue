<template>
  <div class="container">
    <v-card class="pa-5">
      <div class="head">
      <div id="main">투표 하기</div>
      </div>
      <hr>
      <v-text-field class="mt-3" label="투표 제목" v-model="title" hide-details />
      <div v-for="(item,idx) in items" :key=idx>     
        <v-text-field class="mt-5" :label="item.list" :value:content="item.content" v-model:content="item.content" hide-details />
    </div>
   
      <v-btn class="mt-8 mb-8" @click="listplus()" small>투표 항목 추가하기</v-btn>

      <v-expansion-panels>
        <v-expansion-panel>
          <v-expansion-panel-header v-slot="{ open }">
            <v-row no-gutters>
              <v-col cols="4">
                투표 종료일
              </v-col>
              <v-col cols="8" class="text--secondary">
                <v-fade-transition leave-absolute>
                  <span v-if="open"></span>
                  <v-row v-else no-gutters style="width: 100%">
                    <v-col cols="6">
                      {{ trip.end || '날짜를 정해주세요' }}
                    </v-col>
                  </v-row>
                </v-fade-transition>
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row justify="space-around" no-gutters>

              <v-col cols="3">
                <v-menu ref="endMenu" :close-on-content-click="false" :return-value.sync="trip.end" offset-y
                  min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="trip.end" label="End date" prepend-icon="mdi-calendar" readonly v-bind="attrs"
                      v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="date" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="$refs.endMenu.isActive = false">
                      Cancel
                    </v-btn>
                    <v-btn text color="primary" @click="$refs.endMenu.save(date)">
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
       </v-expansion-panels>

      <div class="attach">
      <v-btn class="mt-5" @click="attach()">첨부하기</v-btn>
      </div>
    </v-card>
  </div>
</template>
<script>
  export default {
    data(){
      return{
      items : [{
        list: "투표항목을 입력해주세요...!",
        content : ''
      },
      {
        list: "투표항목을 입력해주세요...!",
        content : ''
      }
    ],
      moimId : this.$route.params.moimId,
      memberId : this.$store.state.id,
      title : '',
      date: '',
      calendarFlag: false,
      picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      trip: {
        name: '',
        location: null,
        start: null,
        end: null,
      },
    }
    },
    methods: {
      goDetail: function () {
      this.$router.push({ path: "voteDetail" });
      },
      showCalendar(date) {
        this.calendarFlag = true;
      },
      onClickOutside() {
      this.calendarFlag = false
      },
      pickDate(){
        this.date = this.picker;
      },
      listplus() {
        this.items.push({
                list: "투표항목을 입력해주세요...!",
                content : ''
      })
    },
    attach() {
      console.log(this.items)
      if(this.title == '' ) {
        this.$swal("항목을 모두 기입해주세요")
      } else if (this.items[0].content == '' || this.items[1].content == '') {
        this.$swal("투표항목을 두가지 이상 기입해주세요")
      } else if (this.trip.end == '' || this.trip.end == null) {
        this.$swal("종료일을 선택해주세요")
      } else if(this.items.content != '') { 
      console.log(this.title)
      console.log(this.items)
      console.log(this.trip.end)
      this.axios.post("/voteList",{
        topic : this.title,
        votelist : this.items,
        endDate : this.trip.end,
        moimId : this.moimId,
        memberId : this.memberId
       })
      .then((result) => {
      console.log(result)
      this.$swal("등록완료")
      this.$router.push({ name : "moimVote" });
      
      }).catch((err) => {
      console.log(err)
      this.$swal("등록완료")
      this.$router.push({ name : "moimVote" });
        
      });
    }
    }
  }
}
</script>
<style scoped>
.container{
  max-width: 60%;
}
.head{
  text-align: center;
  margin-bottom: 2%;
}
.item{
  margin-top: 3%;
}
.attach{
  text-align: center;
  margin-top: 2%;
}
</style>