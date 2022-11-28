<template>
  <v-app id="inspire">
    <Header></Header>
    <v-main> 
      <v-container fluid style="padding: 0px;">
        <router-view></router-view>
      </v-container> 
     </v-main>
  </v-app>
</template>
<script>
import { HashLoader } from '@saeris/vue-spinners'
import Header from "./components/common/Header";
export default {

  components: { 
    Header,
    HashLoader,
  },
  data: () => ({
    cards: ["Today", "Yesterday"],
    menu5: "asd",
    color: "#2ac187",
    size: "150",
    sizeUnit: "px",
  }),
  mounted() {
  },
  methods: {
    //채팅방 inout 감지
    CheckOut() {
      console.log("roomNO:" + this.$store.state.stayRoomNo)
      console.log("memberId:" + this.$store.state.id)
      this.axios
        .get("/updateCheckOut", {
          params: {
            roomId: this.$store.state.stayRoomNo,
            memberId: this.$store.state.id
          },
        }).then(function (res) { console.log("res : " + res) })
      this.axios
        .post("/updateCheckTime", {
          memberId: this.$store.state.id,
          roomNo: this.$store.state.stayRoomNo,
        })
        .then(function (res) {
          console.log("성공");
        });
    },

  },
  created() {
    this.stompClient.disconnect(    this.stompClient.connect(
      {},
      frame => {
        // 소켓 연결 성공
        this.connected = true;
        console.log('소켓 연결 성공', frame);
      },
      error => {
        // 소켓 연결 실패
        console.log('소켓 연결 실패', error);
        this.connected = false;
      }
    ),)


  },
  watch: {
    '$route'(to, from) {
      if (from.name == 'chat') {
        this.CheckOut();
        this.stompClient.unsubscribe(this.$store.state.isRoomNo);
        console.log("this.$store.state.isUser : " + this.$store.state.isUser)
        console.log("this.$store.state.stayRoomNo : " + this.$store.state.stayRoomNo,)
        this.stompClient.unsubscribe(this.$store.state.isUser);
      }
    }
  },
};
</script>
<style>
  .loader{
    display: inline-block;
    position: absolute;
    width: 64px;
    height: 64px;
    top: 40%;
    left: 47%;
  }
</style>