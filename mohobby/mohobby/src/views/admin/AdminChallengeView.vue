<template>
    <main>
        <AdminSidebar></AdminSidebar>
         <v-row align="center" justify="space-around">
            <v-btn depressed>챌린지 등록하러 가기</v-btn>
         </v-row>
         <br>
        <h3>챌린지 목록</h3>
         <v-col class="py-2" v-for="(category, index) in categorys" :key="index">
            <v-btn>{{ category }}</v-btn>
        </v-col>
        <br>
        <br>
            <v-row v-for="item in challlist" :key="item" class="challItem" >
              <v-card
    class="mx-auto"
    max-width="344"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title class="text-h5 mb-1">
          {{ item.title }}
        </v-list-item-title>
        <v-list-item-subtitle>{{ item.content }}</v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        tile
        size="80"
        color="grey"
      ></v-list-item-avatar>
    </v-list-item>

    <v-card-actions>
      <v-btn
        outlined
        rounded
        text
      >
        {{ item.category }}
      </v-btn>
    <v-btn
      depressed
      color="primary"
    >정산대기</v-btn>
    </v-card-actions>
  </v-card>
            </v-row>
        <br>
        <br>
        <h3>승인 대기 중인 인증샷</h3>
         <v-col class="py-2" v-for="(category, index) in categorys" :key="index">
            <v-btn>{{ category }}</v-btn>
        </v-col>
         <v-row> 
        <v-col lg="12">
            <v-data-table
                :headers="headers"
                :items="flags"
                :items-per-page="5"
                class="elevation-1">
            <template v-slot:item.action>
                <v-btn color="success">View</v-btn>
            </template>
            </v-data-table>
        </v-col>
    </v-row>
    </main>
</template>
<script>
import AdminSidebar from "../../components/admin/AdminSidebar.vue";
import challengeInfo from "../../components/admin/challengeInfo.vue";
export default {
    name: '',
    components: { AdminSidebar, challengeInfo },
    data() {
      return {
            categorys: ['공예', '운동', '음악', '미술', '기타' ],
            headers: [
          {
            text: '챌린지번호',
            align: 'start',
            sortable: false,
            value: 'challId',
          },
          { text: '챌린지명', value: 'challName' },
          { text: '유저ID', value: 'memberId' },
          { text: '인증날짜', value: 'certDate' },
          { text: '상세보기', value: 'action' },
          { text: '인증승인여부', value: 'certYn' },
        ],
        flags: [
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
          {
            challId : 1,
            challName: '한달에 독서 3권하기 가능? 불가능?',
            memberId: 'user1',
            certDate: '2022-10-03',
            certYn: '승인됨'
          },
        ],
        challlist : [
          {title : "하루에 운동 10분하기", content : "어떤 운동이든 하루에 10분하고 인증샷 남기기", category : "운동"},
          {title : "하루에 운동 10분하기", content : "어떤 운동이든 하루에 10분하고 인증샷 남기기", category : "운동"},
          {title : "하루에 운동 10분하기", content : "어떤 운동이든 하루에 10분하고 인증샷 남기기", category : "운동"},
          {title : "하루에 운동 10분하기", content : "어떤 운동이든 하루에 10분하고 인증샷 남기기", category : "운동"},
          {title : "하루에 운동 10분하기", content : "어떤 운동이든 하루에 10분하고 인증샷 남기기", category : "운동"}
        ]
      }
    },
    beforeCreate() {},
    created() {},
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeUnmount() {},
    unmounted() {},
    methods: {}
}
</script>
<style scoped>
.py-2 {
    display: inline-block;
    width: 80px;
}

.challItem {
  display : inline-block;
  margin : 10px;
}

.challItem > div {
  width: 450px;
}
</style>