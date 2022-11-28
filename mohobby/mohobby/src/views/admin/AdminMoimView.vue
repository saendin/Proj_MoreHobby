<template>
  <main>
    <AdminSidebar></AdminSidebar>
    <div style="margin-left: 60px; width: 1000px">
        <v-card
    max-width="475"
    class="mx-auto"
  >
    <v-toolbar
      color="teal"
      dark
    >
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>신고코드</v-toolbar-title>
    </v-toolbar>



      <v-list-item-group
      >
        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo1</v-list-item-title>
              <v-list-item-subtitle>부적절한 게시물 개시</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo2</v-list-item-title>
              <v-list-item-subtitle>홍보성 초대 반복</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo3</v-list-item-title>
              <v-list-item-subtitle>청소년에게 음란성 초대글 유포</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo4</v-list-item-title>
              <v-list-item-subtitle>다른 소모임에 대한 욕설, 비방</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

         <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo5</v-list-item-title>
              <v-list-item-subtitle>사이트 목적과 맞지 않는 소모임</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

         <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title @click="getSelectedCode($event)">mo6</v-list-item-title>
              <v-list-item-subtitle>기타</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
      <br />
      <h3>신고된 소모임</h3>
      <br />
      <v-data-table
        :headers="headers"
        :items="moimFlagList"
        sort-by="calories"
        class="elevation-1"
      >
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click="editFlagMoim(item)">
            mdi-pencil
          </v-icon>
          <v-icon @click="deleteFlagMoim(item)"> mdi-delete </v-icon>
        </template>

        <template v-slot:no-data>
          <v-btn color="primary"> Reset </v-btn>
        </template>
        <template v-slot:item.showDetail="{ item }">
          <v-icon @click="goToMoim(item)"> mdi-arrow-right-bold-box </v-icon>
        </template>

        <template v-slot:no-data>
          <p>해당하는 모임이 없습니다.</p>
        </template>
      </v-data-table>
    </div>
  </main>
</template>
<script>
import AdminSidebar from "../../components/admin/AdminSidebar.vue";
export default {
  name: "",
  components: { AdminSidebar },
  data() {
    return {
      dialog: false,
      dialogDelete: false,
      headers: [
        {
          text: "신고번호",
          align: "start",
          sortable: false,
          value: "flagId",
        },
        { text: "신고자", value: "flagFrom" },
        { text: "신고소모임Id", value: "flagTo" },
        { text: "신고소모임이름", value: "moimName" },
        { text: "신고코드", value: "flagCode" },
        { text: "신고이유 (g)", value: "flagReason" },
        { text: "관리자 승인여부", value: "adminConfirm" },
        { text: "신고결과", value: "flagResult" },
        { text: "수정하기", value: "actions", sortable: false },
        { text: "상세보기", value: "showDetail", sortable: false },
      ],
      desserts: [],
      editedIndex: -1,
      editedItem: {},
      defaultItem: {
        name: "",
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0,
      },
      moimFlagList: [],
      moimOneInfo: [],
      moimOpen: 0,
    };
  },
  beforeCreate() {},
  created() {
    this.getFlagedMoim();
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  computed: {},
  watch: {},

  methods: {
    getSelectedCode(event) {
      console.log(event.target.textContent);
      const vm = this;
      this.axios({
        url: "/searchCodeList/" + event.target.textContent,
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].adminConfirm == 0) {
              response.data[i].adminConfirm = "미승인";
            } else if (response.data[i].adminConfirm == 1) {
              response.data[i].adminConfirm = "승인";
            }
            if (response.data[i].flagResult == 0) {
              response.data[i].flagResult = "통과";
            } else if (response.data[i].flagResult == 1) {
              response.data[i].flagResult = "패널티";
            }
          }
          vm.moimFlagList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    ,
    getFlagedMoim() {
      const vm = this;
      this.axios({
        url: "/adminflagmoim",
        method: "get",
      })
        .then(function (response) {
          console.log(response);
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].adminConfirm == 0) {
              response.data[i].adminConfirm = "미승인";
            } else if (response.data[i].adminConfirm == 1) {
              response.data[i].adminConfirm = "승인";
            }
            if (response.data[i].flagResult == 0) {
              response.data[i].flagResult = "통과";
            } else if (response.data[i].flagResult == 1) {
              response.data[i].flagResult = "패널티";
            }
          }
          vm.moimFlagList = response.data;
          console.log(vm.moimFlagList);
          console.log(vm.moimFlagList);
          console.log(vm.moimFlagList);
          console.log(vm.moimFlagList);
          console.log(vm.moimFlagList);

        })
        .catch(function (error) {
          console.log(error);
        });
    },
    editFlagMoim(item) {
      this.editedItem = Object.assign({}, item);

      const vm = this;

      (async () => {
        const { value: fruit } = await this.$swal.fire({
          title: "소모임 신고 결과를 결정해 주세요",
          input: "select",
          inputOptions: {
            0: "통과",
            1: "패널티",
          },
          inputPlaceholder: "소모임 신고 결과",
          showCancelButton: true,
          inputValidator: (value) => {
            return new Promise((resolve) => {
              if (value) {
                if (value == 1) {
                  this.moimOpen = 1;
                } else if (value == 0) {
                  this.moimOpen = 0;
                }
                this.axios({
                  url: "/updateFlag",
                  method: "put",
                  data: {
                    flagResult: value,
                    adminConfirm: 1,
                    flagId: item.flagId,
                  },
                })
                  .then(function (response) {
                    console.log(response);
                    vm.$swal.fire("모임 신고결과 수정이 완료되었습니다");
                    vm.updateMoimOpen(item.flagTo);
                    vm.getFlagedMoim();
                  })
                  .catch(function (error) {
                    console.log(error);
                    console.log("모임 신고결과 수정 실패");
                  });
              } else {
              }
            });
          },
        });
      })();
    },
    updateMoimOpen(flagTo) {
      this.axios({
        url: "/updateMoimOpen",
        method: "put",
        data: {
          moimOpen: this.moimOpen,
          moimId: flagTo,
        },
      })
        .then(function (response) {
          console.log(response);
          console.log("모임 오픈여부 수정 성공");
        })
        .catch(function (error) {
          console.log(error);
          console.log("모임 오픈여부 수정 실패");
        });
    },
    deleteFlagMoim(item) {
      const vm = this;
      this.editedIndex = this.moimFlagList.indexOf(item);
      this.editedItem = Object.assign({}, item);

      this.axios({
        url: "/flagging/" + item.flagId,
        method: "delete",
      })
        .then(function (response) {
          console.log(response);
          vm.$swal.fire("모임 신고결과 식제가 완료되었습니다");
          vm.getFlagedMoim();
        })
        .catch(function (error) {
          console.log(error);
          console.log("모임 신고결과 식제 실패");
        });
    },

    async goToMoim(item) {
      this.editedItem = Object.assign({}, item);
      await this.getOneMoim(this.editedItem.flagTo);
      if (this.moimOneInfo.moimOpen == 1) {
        this.$swal.fire("관리자에 의해 접근 금지된 모임입니다.");
      } else {
        this.$router.push({
          name: "moimBoard",
          params: { moimId: Number(this.editedItem.flagTo), boardType: 1 },
        });
      }
    },

    async getOneMoim(moimId) {
      const vm = this;
      await this.axios
        .get("/moimOneInfo/" + moimId, {})
        .then((response) => {
          vm.moimOneInfo = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>