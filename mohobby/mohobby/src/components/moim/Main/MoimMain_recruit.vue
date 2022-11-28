<template>
  <div class="container">
    <i class="fa-solid fa-thumbs-up fa-5x"></i>
    <h3>소모임 멤버를 모집합니다!</h3>
    <br />
    <div id="first"></div>
    <div
      class="box"
      @click="box(idx)"
      v-for="(item, idx) in items"
      :key="item.title"
    >
      <v-card class="mx-3" max-width="550" outlined>
        <v-list-item three-line>
          <v-list-item-content>
            <div class="text-overline mb-4">
              {{ item.moimName }}
            </div>
            <v-list-item-subtitle>{{ item.moimInfo }}</v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-avatar tile size="80" class="rounded">
            <img :src="require(`@/assets/image/moim/${item.moimImg}`)" />
          </v-list-item-avatar>
        </v-list-item>
        <v-card-actions>
          <span class="people"> {{ item.regCnt }}명 참여중 </span>
          <v-spacer></v-spacer>
          <v-chip outlined rounded text>
            {{ item.moimRegion }}
          </v-chip>
          <v-chip outlined rounded text>
            {{ item.moimCatg }}
          </v-chip>
        </v-card-actions>
      </v-card>
    </div>
    <div class="moreMoim">
      <!--비회원 소모임 모집 -->
      <v-card-actions v-if="moimRight === '0'">
        <v-spacer></v-spacer>
        <div class="text-center">
          <v-dialog v-model="noneuser" width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-btn text>
                <v-chip color="#D66B43" outlined v-bind="attrs" v-on="on">
                  <v-icon left> mdi-plus </v-icon>
                  새로운 소모임 모집하기
                </v-chip>
              </v-btn>
            </template>
            <v-card>
              <br /><br />
              <v-card-text class="font-weight-bold">
                소모임을 만들기 위해서는 먼저 로그인을 해주세요!.
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn depressed @click="noneuser = false"> 닫기 </v-btn>
                <v-btn color="success" depressed @click="join">
                  로그인하러 가기
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </v-card-actions>
      <!-- 회원 소모임 버튼 -->
      <v-card-actions v-else>
        <v-spacer></v-spacer>
        <v-btn text>
          <v-chip color="#D66B43" outlined @click="select">
            <v-icon left> mdi-plus </v-icon>
            새로운 소모임 모집하기
          </v-chip>
        </v-btn>
      </v-card-actions>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    rsearch: String,
    keyWord: String,
  },
  data() {
    return {
      search: "",
      items: [],
      moimRight: "2",
      noneuser: false,
      moimOneInfo: [],
    };
  },
  created() {
    this.getList();
  },
  watch: {
    rsearch() {
      this.searchList();
    },
    keyWord() {
      this.searchList();
    },
  },
  methods: {
    getList() {
      this.axios
        .get("/moimRecruitMember")
        .then((resp) => {
          this.items = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    searchList() {
      this.axios
        .get("/moimAllSearch", {
          params: {
            Search: this.rsearch,
            Category: this.keyWord,
          },
        })
        .then((resp) => {
          console.log(resp);
          console.log(this.items);
          if (resp.data[0] == null) {
            document.querySelector("#first").style.display = "block";
            document.querySelector("#first").innerText =
              "일치하는 검색결과가 없습니다.";
            this.items = resp.data;
          } else {
            document.querySelector("#first").style.display = "none";
            document.querySelector("#first").innerText = "";
            this.items = resp.data;
          }
        })
        .catch(function (error) {
          console.log(this.items);
          console.log(error);
        });
    },
    select: function () {
      if (this.moimRight !== "0") {
        this.$router.push({ path: "moimRegisterIn" });
      }
    },
    join() {
      this.$router.push({ path: "login" });
    },
    async box(idx) {
      await this.getOneMoim(idx);
      if (this.moimOneInfo.moimOpen == 1) {
        this.$swal.fire("관리자에 의해 접근 금지된 모임입니다.");
      } else {
        this.$router.push({
          name: "moimBoard",
          params: { moimId: this.items[idx].moimId,
                    moimName: this.items[idx].moimName,
                    boardType: 1 },
        });
      }
    },
    // 접근 유효성 검사할때 사용할 모임 단건 조회
    async getOneMoim(idx) {
      const vm = this;
      await this.axios
        .get("/moimOneInfo/" + this.items[idx].moimId, {})
        .then((resp) => {
          vm.moimOneInfo = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  width: 1000px;
}
.box {
  float: left;
  width: 50%;
  margin-bottom: 15px;
  cursor: pointer;
}
.people {
  border-left: 2px solid rgb(34, 85, 177);
  padding-left: 10px;
}
.moreMoim {
  float: right;
  width: 1000px;
}
#first {
  text-align: center;
  display: none;
  height: 200px;
  margin-top: 150px;
}
</style>