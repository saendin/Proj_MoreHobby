<template>
  <v-container class="scroll" ref="scroll">
    <h1 id="chat-user"> {{ this.$store.state.user.nickName }} 님의 채팅방입니다 </h1>
    <div class="room">
      <v-col cols="12" sm="3" class="flex-grow-1 flex-shrink-0" style="border-right: 1px solid; cursor: pointer;">
        <template v-for="(item, index) in roomList">
          <div v-if="item.checkIn == 0" class="chat-room-list" v-on:click.prevent.stop="openRoom(item.roomNo)"
            style="background-color:#ffff;">
            <div></div>
            <v-avatar>
              <v-img v-if="item.roomNo > 10000" :src="require(`@/assets/image/user/${item.profileImg}`)" height="100px"
                width="50px" border-radius:10px></v-img>
              <v-img v-else :src="require(`@/assets/image/moim/${item.profileImg}`)" height="100px" width="50px"
                border-radius:10px></v-img>
            </v-avatar>
            <div class="chat-room-list-content">
              <v-text id="chat-nickname">
                {{ item.nickName }}
              </v-text>
              <v-text>
                {{ item.content }}
              </v-text>
              <v-badge v-if="item.nonReadChat != 0" offset-x="-220" offset-y="-15" color="red"
              :content="item.nonReadChat">
            </v-badge>
            </div>
            
          </div>
          <div v-else class="chat-room-list" v-on:click.prevent.stop="openRoom(item.roomNo)"
            style="background-color:#bdbdbd">
            <v-avatar>
              <v-img v-if="item.roomNo > 10000" :src="require(`@/assets/image/user/${item.profileImg}`)" height="100px"
                width="50px" border-radius:10px></v-img>
              <v-img v-else :src="require(`@/assets/image/moim/${item.profileImg}`)" height="100px" width="50px"
                border-radius:10px></v-img>
            </v-avatar>
            <div class="chat-room-list-content">
              <v-text id="chat-nickname">
                {{ item.nickName }}
                {{ item.nickname }}
              </v-text>
              <v-text>
                {{ item.content }}
              </v-text>
            </div>

          </div>
          <hr>
        </template>
        <!-- 채팅내역 -->
      </v-col>

      <v-col cols="auto" class="flex-grow-1 flex-shrink-0 pa-3 " style="background-color: #e9e9ec">
        <div> 
          <template v-for="(msg, i) in messages">
            <div>
              <!-- 내 채팅 -->
              <div class="admin-chat" v-if="i == 0">
                <p>
                  {{ msg.content }}
                </p>
              </div>
              <div v-else-if="msg.memberId" :class="{ 'd-flex flex-row-reverse mr-2 mt-4': msg.memberId }">
                <div style="  display: flex;">
                  <div class="my-chat-Time pr-1" style="  margin-top: auto;">
                    {{ msg.hour }}
                  </div>
                  <div class="my-chat" style>
                    {{ msg.content }}
                  </div>

                </div>
              </div>
              <!-- 상대 채팅 -->
              <div v-else :class="{ 'd-flex mr-2 mt-6': !msg.memberId }">
                <v-avatar class="mr-2">
                  <v-img :src="require(`@/assets/image/user/${msg.profileImg}`)" border-radius:10px></v-img>
                </v-avatar>
                <div>
                  <v-text class="ml-4">
                    {{ msg.nickname}}
                  
                  </v-text>
                  <v-row class="mt-2">
                    <div style="  display: flex;">
                      <div class="target-chat" style="margin-top: auto;">
                        {{ msg.content }}
                      </div>
                      <div class="target-chat-Time pl-1 ">
                        {{ msg.hour }}
                      </div>
                    </div>
                  </v-row>
                </div>
              </div>
            </div>
          </template>

        </div>
        <!-- 글 입력폼 -->
        <v-card-text class="flex-shrink-1">
          <v-text-field filled dense class="messages" v-model="message" label="메시지를 입력해주세요" type="text" no-details
            @keyup.enter="send()" hide-details>
          </v-text-field>
        </v-card-text>
      </v-col>
    </div>
  </v-container>
</template>

<script>
export default {

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
    //this.sortRoom()
    //this.CheckIn(this.roomId)
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
        .catch(function (err) {
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
      else (
        this.CheckIn(roomNo)
      )
      this.$store.state.stayRoomNo = roomNo
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
              vm.roomList[i].checkIn = 1;
            } else {
              vm.roomList[i].checkIn = 0;
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
          console.log("rev : " + rev.memberId)
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
      this.stompClient.send("/app/getSubscribeId", vm.roomId, (res) => { });
      this.stompClient.send("/app/getSubscribeId", this.$store.state.id, (res) => { });

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

              if (vm.$route.query.getRoomId) {
                vm.openRoom(vm.$route.query.getRoomId);
              }
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
                console.log(resContent.content.length)
                if (resContent.content.length > 10) {
                  vm.roomList[i].content = resContent.content.slice(0, 10) + "..."
                }
                else {
                  vm.roomList[i].content = resContent.content
                }
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
#container {
  height: 100vh;
}

#chat-user {
  margin-left: 10px;
}

.room {
  overflow-y: auto;
  display: flex;
  max-height: 2000vh;

}

.admin-chat {
  text-align: center
}

.chat-room-list {
  display: flex;
  padding: 10px;
}
.chat-room-list:hover{
  background-color: gold;
}

.chat-room-list-content {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}

#chat-nickname {
  font-weight: bolder;
}


.chat-content {
  background-color: #B2C7D9;
}

.my-chat {
  position: relative;
  background: #2255b1;
  border-radius: .4em;
  padding: 5px 15px;
  color: white;
  font-size: 1.3em;
  max-width: 50vh;
}

.my-chat:after {
  content: '';
  position: absolute;
  right: 0;
  top: 35%;
  width: 0;
  height: 0;
  border: 0.563em solid transparent;
  border-left-color: #2255b1;
  border-right: 0;
  border-top: 0;
  margin-top: -0.281em;
  margin-right: -0.562em;
}

.my-chat-Time {
  margin-right: 5px;
  margin-top: 15px;
}

.target-chat {
  position: relative;
  background: white;
  border-radius: .4em;
  padding: 5px 15px;
  margin-left: 20px;
  color: black;
  font-size: 1.3em;
  max-width: 50vh;
}

.target-chat:after {
  content: '';
  position: absolute;
  left: 0;
  top: 35%;
  width: 0;
  height: 0;
  border: 0.563em solid transparent;
  border-right-color: white;
  border-left: 0;
  border-top: 0;
  margin-top: -0.281em;
  margin-left: -0.562em;
}
.target-chat-Time {
  margin-left: 5px;
  margin-top: 15px;
}
.messages {
  background-color: #ffffff;
}
</style>