<template>
    <div>
      <v-card class="mx-auto" width="800" min-height="520">
        <!-- <v-toolbar v-show="followerLists" color="#2ac187" dark> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {{ this.$store.state.id}} 님의 팔로워 목록입니다 </v-toolbar> -->
          <div class="wrap_box" >
            <v-icon class="ml-8" size="100" color="#b8b8b8">mdi-account-multiple</v-icon>
            <div class="text_box">
              <div class="text_box_title"><span class="point">존재하는 {{keyword}} 목록이 없습니다🙇</span></div>
              <ul>
                <li>
                  지금 바로 모하비 이웃들과 친구를 맺고 소통을 시작해보세요!
                </li>
              </ul>
            </div>
          </div>
        </v-card>
      </div>
</template>
<script>
  export default {
    name: "NoFollow",
    components: {  },
    props:{
        keyword : String,
    },
    data() {
      return {
          memberId : this.$store.state.id,
          users: [], //props 담을 변수
      };
    },
  }
  </script>
  <<style scoped>
      .wrap_box {
        text-align: center;
        margin: 80px auto;
    }
    .text_box{
        margin-left: 145px;
        margin-bottom: 200px;
        width: 510px;
        vertical-align: middle;
        color: #a3a3a3ee;
        font-weight: 500;
        text-align: center;
    }
    .text_box_title{
        margin-left: 20px;
        margin-bottom: 3px;
        color: #858484ee;
    }
    .point{
        color: rgba(255, 94, 0, 0.932);
        font-weight: bold;
    }
    li {
        padding: 1px;
        font-size: 15px;
        list-style: none;
    }
  </style>
  

