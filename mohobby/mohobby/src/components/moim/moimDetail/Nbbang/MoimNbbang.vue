<template>
  <div class="container">
    <!-- N빵 생성하기 -->
    <div class="margin">
      <v-card-actions class="mb-16">
        <v-spacer />
        <v-btn @click="makeNbbang()">
          <v-icon>mdi-plus-circle-outline</v-icon>N빵 생성하기
        </v-btn>
      </v-card-actions>
    </div>
    <div v-if="items.length != 0">
      <v-card class="mx-auto mb-8" max-width="800" outlined v-for="(item, idx) in items" :key="item.dutchId"
        @click="Nbbangdetail(item.dutchId)">
        <v-list-item three-line>
          <v-list-item-avatar tile size="60" class="rounded-pill">
            <v-img :src="profile[idx].avatar"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <div class="text-overline mt-6">
              {{ item.memberId }}
              <hr />
              {{ writeDate }}
            </div>
            <v-list-item-subtitle class="content mt-5">
              <v-icon class="mr-1 mb-1 ml-7" color="light-green">mdi-chart-pie</v-icon>
              N빵
            </v-list-item-subtitle>
            <v-card-actions class="ml-13">{{ item.totalPrice }}원 / {{ item.people }}명</v-card-actions>
          </v-list-item-content>
        </v-list-item>

        <v-card-actions>
        </v-card-actions>
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
  data() {
    return {
      items: [],
      profile: [],
      moimId: this.$route.params.moimId,
      //moim : 비공개 0 공개 1
      //moimRight : 비회원 0 회원 1
      moim: 1,
      moimRight: 1,
      writeDate: this.$moment().format('YY-MM-DD'),
      dutchId: 0,
    };
  },
  methods: {
    getNbbangList() {
      let vm = this
      console.log(this.moimId)
      this.axios.get("/moimNbbangList", {
        params: {
          moimId: this.moimId
        }
      })
        .then((resp) => {
          console.log(resp)
          console.log(this.items)
          this.items = resp.data;
        })
        .catch((err) => {
          console.log(this.items)
          console.log(err)
        }).finally((response) => {
          console.log(response)
          for (let i = 0; i < vm.items.length; i++) {
            vm.axios.get("/getImg", {
              params: {
                memberId: vm.items[i].memberId
              }
            }).then((response) => {
              this.profile.push({ avatar: require(`@/assets/image/user/${response.data}`) })
            }).catch((err) => {
              console.log(err)
            })
          }
        })
    },
    makeNbbang: function () {
      this.$router.push({ path: "makeNbbang", params: { moimId: this.moimId } });
    },
    Nbbangdetail: function (dutchId) {
      this.dutchId = dutchId,
      console.log(dutchId);
      console.log(dutchId);
      console.log(dutchId);
      console.log(dutchId);
      console.log(dutchId);
      console.log(this.dutchId);
      console.log(this.dutchId);
      console.log(this.dutchId);
      console.log(this.dutchId);
      console.log(this.dutchId);
      this.$router.push({ name: "nBBangDetail", query: { moimId: this.moimId, dutchId: this.dutchId } })
    }
  },
  created() {
    this.getNbbangList();
  },
};
</script>

<style scoped>
.container {
  width: 85%;
}
.nodata {
  width : 85%;
  height: 300px;
  margin-top : 250px;
  margin-left: 75px;
  font-weight: bold;
  text-align: center;
}
.margin {
  margin-right: 10.5%;
}
</style>