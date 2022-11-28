<template>
<div>
    <v-row>
    <h3>일주일에 운동 3번 가능? 불가능?</h3>
    <v-btn>운동</v-btn>
    </v-row>
        <v-col lg="12">
            <v-data-table
                :headers="headers"
                :items="flags"
                :items-per-page="5"
                class="elevation-1">
            <template v-slot:item.action>
                <v-btn color="success">정산하기</v-btn>
            </template>
            </v-data-table>
        </v-col>
</div>
</template>
<script>

export default{ 
    name:'',
    components:{},
    data(){
      return {
            headers: [
          {
            text: '유저 아이디',
            align: 'start',
            sortable: false,
            value: 'challId',
          },
          { text: '챌린지명', value: 'challName' },
          { text: '유저id', value: 'memberId' },
          { text: '챌린지 달성률', value: 'challRatio' },
          { text: '등록금액', value: 'challMoney' },
          { text: '총 정산금액', value: 'payback' },
          { text: '계좌번호', value: 'account' },
          { text: '정산하기', value: 'action' },
        ],
        flags: [
          {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
             {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
             {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
             {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
             {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
             {
            challName : '일주일에 운동 3번하기 가능? 불가능?',
            memberId: 'user1',
            challRatio: '80%',
            challMoney : '20000',
            payback : '18000',
            account: '국민01012345678',
          },
        ]
      }
    },
    setup(){},
    created(){},
    mounted(){},
    unmounted(){},
    methods:{}
}
</script>