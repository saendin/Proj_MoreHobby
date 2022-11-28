<template>
  <div class="container">
    <v-card class="mx-auto" max-width="1000">
      <!-- 프로필 -->
      <div>
        <div class="flex">
          <v-avatar class="ml-10 mr-4 mb-8" color="grey darken-1" size="64">
            <v-img
              aspect-ratio="25"
              src="data:image/jpeg;base64,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"
            >
            </v-img>
          </v-avatar>
          <div class="user text-overline">
            {{ writer }}<br />{{ date | moment("YYYY-MM-DD HH:mm") }}
          </div>
        </div>
      </div>

      <!-- 수정, 삭제 버튼-->
      <v-card-actions v-if="writer == memberId" class="mr-15 mb-8">
        <v-spacer></v-spacer>
        <v-btn small outlined color="error" @click="deleteNbbang()">삭제</v-btn>
      </v-card-actions>

      <v-row>
        <v-col cols="12" sm="6" offset-sm="3">
          <v-card class="mb-10 pa-5">
            <v-list two-line>
              <div class="">
                <template v-for="(item, index) in items">
                  <v-subheader
                    v-if="item.header"
                    :key="item.header"
                    class="mr-5"
                  >
                    <v-icon class="mr-1 mt-1 ml-3" color="light-green"
                      >mdi-chart-pie</v-icon
                    >
                    {{ item.header }}
                    {{ totalPrice }}/{{ people }}명
                  </v-subheader>
                  <v-divider
                    v-else-if="item.divider"
                    :key="index"
                    :inset="item.inset"
                  ></v-divider>
                  <v-list-item v-else :key="item.name">
                    <v-list-item-avatar>
                      <img :src="item.avatar" />
                    </v-list-item-avatar>

                    <v-list-item-content>
                      <v-list-item-title
                        v-html="item.moneyTarget"
                      ></v-list-item-title>
                      <v-list-item-subtitle
                        v-html="item.money"
                      ></v-list-item-subtitle>
                    </v-list-item-content>
                    <div class="checkBox">
                      <v-checkbox
                        v-if="item.writer == memberId"
                        id="select"
                        v-model="item.calcCheck"
                        color="light-green"
                      ></v-checkbox>
                    </div>
                  </v-list-item>
                </template>
              </div>
            </v-list>
          </v-card>
          <v-row justify="center">
            <v-dialog v-model="dialog" persistent max-width="500">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="mb-7"
                  color="light-green"
                  dark
                  v-bind="attrs"
                  v-on="on"
                  @click="changeCheck()"
                >
                  완료
                </v-btn>
              </template>
            </v-dialog>
          </v-row>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      memberId: this.$store.state.id,
      dialog: false,
      writer: "",
      dutchId: this.$route.query.dutchId,
      moimId: this.$route.query.moimId,
      items: [],
      oneNbbangList: [],
      checked: [],
      totalPrice: "",
      people: "",
    };
  },
  created() {
    this.getOneNbbangList();
  },
  mounted() {},
  methods: {
    changeCheck() {
      for (let i = 1; i < this.items.length; i++) {
        if (this.items[i].calcCheck == true) {
          this.items[i].calcCheck = 1;
        } else if (this.items[i].calcCheck == false) {
          this.items[i].calcCheck = 0;
        }
        this.axios.put("/updateCalc", {
              dutchId: this.dutchId,
              memberId: this.items[i].moneyTarget,
              calcCheck: this.items[i].calcCheck,
          })
          .then((resp) => {
            console.log(resp);
            this.$swal("n빵 체크가 완료되었습니다.");
          })
          .catch((err) => {
            console.log(err);
          });
      }
      this.$router.push("moimNbbang");
    },
    getOneNbbangList() {
      const vm = this;
      this.axios({
        url: "/nbbangDetail/" + this.moimId + "/" + this.dutchId,
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          vm.oneNbbangList = response.data;
          vm.totalPrice = response.data[1].totalPrice;
          vm.people = response.data[1].people;
          vm.writer = response.data[1].memberId;
          vm.date = response.data[1].writeDate;
          vm.calcCheck = response.data[1].calcCheck;
          vm.makeShowList();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    makeShowList() {
      this.items.push({ header: "N빵" });
      for (let i = 0; i < this.oneNbbangList.length; i++) {
        this.items.push({
          avatar: require(`@/assets/image/user/${this.oneNbbangList[i].profileImg}`),
          writeDate: this.oneNbbangList[i].writeDate,
          writer: this.oneNbbangList[i].memberId,
          money: this.oneNbbangList[i].calcPrice,
          moneyTarget: this.oneNbbangList[i].moneyTarget,
          calcCheck: this.oneNbbangList[i].calcCheck,
        });
      }
    },
    deleteNbbang() {
      this.$swal({
        title: "정말 삭제할까요?",
        text: "삭제를 원하지 않으면 취소버튼을 눌러주세요!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#2ac187",
        cancelButtonColor: "#d33",
        cancelButtonText: "취소",
        confirmButtonText: "네, 삭제할게요!",
      }).then((result) => {
        if (result.isConfirmed) {
          let vm = this;
          this.axios
            .delete("/delNbbang", {
              params: {
                dutchId: this.dutchId,
              },
            })
            .then((resp) => {
              console.log("N빵 삭제 결과" + resp);
              this.$swal("삭제 완료!", "n빵을 삭제하였습니다.", "success");
              this.$router.push({ name: "moimNbbang" });
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 30px;
  height: 1000px;
}

.user {
  display: inline-block;
  margin-left: 5px;
  margin-top: 5%;
}

.checkBox {
  float: right;
}
</style>
