<template>
  <div id="container" class="scroll" ref="scroll">
    <h1> {{this.$store.state.user.nickName}} 님의 채팅방입니다 </h1>
    <v-card  class="overflow-y-auto" >
      <v-row class="no-gutters elevation-4 overflow-y-auto">
        <v-col cols="12" sm="3" class="flex-grow-1 flex-shrink-0" style="border-right: 1px solid ">
         
            <v-list>
              <v-list-item-group>
                <template v-for="(item, index) in roomList">
                  <v-list-item v-on:click="openRoom(item.roomNo)" style="background-color:white">
                    <v-avatar>
                      <v-img v-if="item.roomNo > 10000" :src="require(`@/assets/image/user/${item.profileImg}`)"
                        height="100px" width="50px" border-radius:10px></v-img>
                      <v-img v-else :src="require(`@/assets/image/moim/${item.profileImg}`)" height="100px" width="50px"
                        border-radius:10px></v-img>
                    </v-avatar>
                    <v-list-item-content>
                      <v-list-item-title v-text="item.nickName" style="white" />
                      <v-list-item-subtitle v-text="item.content" />
                    </v-list-item-content>
                    <v-badge style="cursor: pointer;" v-if="item.nonReadChat != 0" offset-x="10" offset-y="10" color="red"
                      :content="item.nonReadChat">
                    </v-badge>
                  </v-list-item>
                  <v-divider class="my-0" />
                  <hr>
                </template>
              </v-list-item-group>
            </v-list>

        </v-col>
        <v-col cols="auto" class="flex-grow-1 flex-shrink-0  ">
          <v-card flat class="d-flex flex-column fill-height overflow-y-auto">
            <v-card-title>
        
            </v-card-title>
            <v-card-text class="flex-grow-1 overflow-y-auto">
              <v-if roomId="">
                <v-img :src="require(`@/assets/image/moim/mohobby.png`)">
                </v-img>
              </v-if>
              <template v-for="(msg, i) in messages">

                <div :class="{ 'd-flex flex-row-reverse': msg.memberId }">
                  <v-menu offset-y>
                    <template v-slot:activator="{ on }">
                      <v-chip :color="msg.memberId ? 'primary' : ''" dark style="height: auto; white-space: normal"
                        class="pa-4 mb-2" v-on="on">
                        <v-avatar>
                      <v-img :src="require(`@/assets/image/user/${msg.profileImg}`)"
                        height="100px" width="50px" border-radius:10px></v-img>
                        </v-avatar> 
                        {{msg.nickname}} :
                        {{ msg.content }}
                        <sub class="ml-2" style="font-size: 0.5rem">
                          {{ msg.hour }}
                        </sub>
                      </v-chip>
                    </template>
                  </v-menu>
                </div>
              </template>
            </v-card-text>

            <!-- 글 입력폼 -->
            <v-card-text class="flex-shrink-1">
              <div class="messages" ref="messages">
                <v-text-field class="fixed" v-model="message" label="메시지를 입력해주세요" type="text" no-details outlined
                  append-outer-icon="send" @keyup.enter="send()" hide-details />
              </div>
            </v-card-text>

          </v-card>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      subTitle: "", //수정중
      memberId: "", //세션 로그인값
      messages: [], //메세지 내역
      message: "",
      roomId: "", //방번호
      roomList: [], //방목록정보
      hour: "", //메세지시간
      subscribeRoot: "", //구독정보
      targetId: [], //상대방 정보
      createAt: "", //작성시간
      el: document.querySelector(".scroll"), //채팅방 높이
      eh: '', //채팅이후 방높이
      isScroll: ''
    };
  },
  created() {
    this.memberId = this.$store.state.id,
      this.roomId = this.$route.query.getRoomId,
   
    this.connect()
    this.getRoom()
    this.sortRoom()
    this.CheckIn(this.roomId)
  },
  mounted() {
    window.addEventListener('beforeunload', this.CheckOut(this.roomId));
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.CheckOut(this.roomId));
  },

  methods: {
    handler: function handler(event) {
      this.CheckOut(this.roomId);
    },
    //채팅내역 정렬
    sortRoom() {
      this.roomList.sort(function (a, b) {
        return a.msgTime > b.msgTime ? -1 : a.msgTime < b.msgTime ? 1 : 0;
      });
    },
    //날짜변환
    todate() {
      var today = new Date();
      var year = today.getFullYear();
      var month = ("0" + (today.getMonth() + 1)).slice(-2);
      var day = ("0" + today.getDate()).slice(-2);
      var hours = ("0" + today.getHours()).slice(-2);
      var minutes = ("0" + today.getMinutes()).slice(-2);
      var seconds = ("0" + today.getSeconds()).slice(-2);
      this.createAt = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    //소켓서버에 채팅전송
    send() {
      this.todate();
      if (this.message !== "") {
        const msg = {
          roomNo: this.roomId,
          content: this.message,
          memberId: this.memberId,
          memberIds: this.targetId,
          hour: this.createAt,
        };
        this.axios
          .post("/InsertMessage/", {
            memberId: this.memberId,
            roomNo: this.roomId,
            content: this.message,
          })
          .then(function (res) {
            console.log(res);
          })
          .catch(function (error) {
            console.log(error);
          });
        //현재 대화방에 채팅보내기
        this.stompClient.send("/app/send", JSON.stringify(msg), (res) => {
          console.log(res);
        });
      }
      this.message = "";
    },
    CheckIn(roomId) {
      console.log("checkIn test")
      console.log(roomId)
      this.axios
        .get("/updateCheckIn", {
          params: {
            roomId: roomId,
            checkIn: 1,
            memberId: this.memberId
          },
        })
        .then(function (res) {
          console.log(res)
          console.log("success")
        })
        .catch(function(err){
          console.log(err)
        })
    },
    CheckOut(roomId) {
    
      this.axios
        .get("/updateCheckOut", {
          params: {
            roomId: roomId,
            memberId: this.memberId
          },
        })
    },
    CheckInOut(preRoomId, curentRoomId) {
      this.axios
        .get("/updateCheckInOut", {
          params: {
            preRoomId: preRoomId,
            currentRoomId: curentRoomId,
            memberId: this.memberId
          }
        })
    },
    // 채팅방에 채팅내역 출력
    openRoom(roomNo) {

      var vm = this;
      if (this.roomId != roomNo) {
        this.CheckInOut(this.roomId, roomNo)
        this.roomId = roomNo
      }
      this.$store.state.stayRoomNo= roomNo
      this.messages = [];
      this.targetId = [];
      //안읽은 메세지수 추출
      this.axios
        .post("/getNonReadChat", {
          memberId: this.memberId,
        })
        .then(function (res) {
          for (let i = 0; i < vm.roomList.length; i++) {
            if (vm.roomList[i].roomNo == roomNo) {
              vm.roomList[i].nonReadChat = 0;
              vm.checkIn = 1;
            } else {
              for (let n = 0; n < res.data.length; n++) {
                if (vm.roomList[i].roomNo == res.data[n].roomNo) {
                  vm.roomList[i].nonReadChat = res.data[n].nonReadCount;
                  vm.roomList[i].checkIn = 0;
                }
              }
            }
          }
        })
        .catch(function (err) {
          console.log(err);
        })
        .finally(function (ros) {
          vm.axios
            .post("/updateCheckTime", {
              memberId: vm.memberId,
              roomNo: vm.roomId,
            })
            .then(function (res) {
              console.log("성공");
            });
        });
      //대화상대 추출
      this.axios
        .post("/getTargetId", {
          roomNo: this.roomId,
          memberId: this.memberId,
        })
        .then(function (res) {
          console.log(res);
          vm.targetId = res.data;
        })
        .catch(function (err) {
          console.log(err);
        });
      //같은방 클릭시 재구독 방지
      vm.stompClient.unsubscribe(vm.$store.state.isRoomNo);
      //채팅내역 불러오기
      this.axios
        .get("/getChatList/" + this.roomId, {})
        .then(function (res) {
          for (let i = 0; i < res.data.length; i++) {
            if (vm.memberId == res.data[i].memberId) {
              res.data[i].memberId = true;
            } else {
              res.data[i].memberId = false;
            }
            if (res.data[i].hour >= 12) {
              res.data[i].hour =
                res.data[i].hour - 12 + ":" + res.data[i].minute + " pm";
            } else {
              res.data[i].hour =
                res.data[i].hour + ":" + res.data[i].minute + " am";
            }
          }
          vm.messages = res.data;
       
        })
        .catch(function (error) {
          console.log(error);
        });
      //클릭한 방 접속시 방번호로 구독
      vm.stompClient.subscribe("/topic/room/" + roomNo, function (res) {
        if (res.body == roomNo) {
          vm.$store.state.isRoomNo = res.headers.subscription;
        }
        else {
          let rev = JSON.parse(res.body);
          console.log("rev : "+ rev)
          if (rev.memberId == vm.memberId) {
            rev.memberId = true;
          } else {
            rev.memberId = false;
          }
          if (rev.hour.substr(11, 2) >= 12) {
            rev.hour =
              rev.hour.substr(11, 2) - 12 + ":" + rev.hour.substr(14, 2) + " pm";
          } else {
            rev.hour =
              rev.hour.substr(11, 2) + ":" + rev.hour.substr(14, 2) + " am";
          }
          vm.messages.push(rev);
        
        }
      });
      //구독취소헤더값 가져오기
      this.stompClient.send("/app/getSubscribeId", vm.roomId, (res) => { });
 
    },
    //채팅방 리스트출력
    getRoom() {
      var vm = this;
      vm.roomList = []
      //1:1
      this.axios
        .get("/ChatRoom/" + this.memberId, {})
        .then(function (res) {
          for (let i = 0; i < res.data.length; i++) {
            vm.roomList.push(res.data[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
        .finally(function (ros) {
          //소모임
          vm.axios
            .get("/ChatMoimRoom/" + vm.memberId, {})
            .then(function (res) {
              for (let i = 0; i < res.data.length; i++) {
                vm.roomList.push(res.data[i]);
              }
              console.log(res.data);
            })
            .catch(function (error) {
              console.log(error);
            })
            .finally(function (ros) {
              vm.sortRoom();
              vm.openRoom(vm.$route.query.getRoomId);
            });
        });
    },
    //소켓 구독
    connect() {
      let vm = this
    
      vm.stompClient.subscribe(
        "/queue/" + this.$store.state.id,
        function (res) {
 
          if (res.body == vm.$store.state.id) {
            vm.$store.state.isUser = res.headers.subscription
          }
          else {
            let resContent = JSON.parse(res.body);
            if (vm.roomList.findIndex(i => i.roomNo == resContent.roomNo) < 0) {
              vm.getRoom();
            }
            for (let i = 0; i < vm.roomList.length; i++) {
              if (vm.roomList[i].roomNo == resContent.roomNo) {
                vm.roomList[i].content = resContent.content;
                vm.roomList[i].msgTime = resContent.hour;
                if (vm.roomId != resContent.roomNo)
                  ++vm.roomList[i].nonReadChat;
             
              }
            }
            vm.sortRoom();
          }
        }
      );

      vm.stompClient.send("/app/SubscribeIds", vm.$store.state.id, (res) => { console.log(res) })
 
    },
    scrollDown() {
      let scroll = this.$refs.scroll;
      let scrol = scroll.scrollHeight + 200
      console.log("scrol : " + scrol)
      window.scrollTo(0, scrol);
    }
  },
  watch: {
    messages() {
      this.$nextTick(() => {
        console.log("nextTick TEXST")
        this.scrollDown()
      });
    },
  },
};
</script>
<style scoped>
#container{
  margin-top: 180px; 
  height: 1000px;
}
</style>