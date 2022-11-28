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



      <v-list-item-group>
        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title value="us1" @click="getSelectedCode($event)">us1</v-list-item-title>
              <v-list-item-subtitle>부적절한 게시물 개시</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title value="us2" @click="getSelectedCode($event)">us2</v-list-item-title>
              <v-list-item-subtitle>다른 유저에게 욕설, 비방</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content >
              <v-list-item-title value="us3" @click="getSelectedCode($event)">us3</v-list-item-title>
              <v-list-item-subtitle>게시글, 댓글 도배</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title value="us4" @click="getSelectedCode($event)">us4</v-list-item-title>
              <v-list-item-subtitle>홍보성 게시물 반복적 개시</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

         <v-list-item>
          <template>
            <v-list-item-content>
              <v-list-item-title value="us5" @click="getSelectedCode($event)">us5</v-list-item-title>
              <v-list-item-subtitle>기타</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>

      <br />
      <h3>신고된 유저</h3>
      <br />
      <v-data-table
        :headers="headers"
        :items="flagUserList"
        sort-by="calories"
        class="elevation-1"
      >
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click="editFlagUser(item)">
            mdi-pencil
          </v-icon>
          <v-icon @click="deleteFlagUser(item)"> mdi-delete </v-icon>
        </template>
        small

        <template v-slot:no-data>
          <v-btn color="primary"> Reset </v-btn>
        </template>
        <template v-slot:item.showDetail="{ item }">
          <v-icon @click="goToUserProfile(item)"> mdi-arrow-right-bold-box </v-icon>
        </template>
        small

        <template v-slot:no-data>
          <v-btn color="primary"> Reset </v-btn>
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
        { text: "신고유저", value: "flagTo" },
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
      flagUserList: [],
      selectedFlagUserList : [],
      userOneInfo: [],
      role : 0,
      settings: [],
      selectedCode : "",
    };
  },
  beforeCreate() {},
  created() {
    this.getFlagedUser();
  },
  beforeMount() {},
  mounted() {
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  computed: {
  },
  watch: {},

  methods: {
    getSelectedCode(event) {
      console.log(event.target.textContent);
      console.log("clicked");
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
          vm.flagUserList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    ,showSettings() {
      console.log(this.settings);
    }
    ,getFlagedUser() {
      const vm = this;
      this.axios({
        url: "/admimflaguser",
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
          vm.flagUserList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    editFlagUser(item) {
      this.editedItem = Object.assign({}, item);

      const vm = this;

      (async () => {
        const { value: fruit } = await this.$swal.fire({
          title: "유저 신고 결과를 결정해 주세요",
          input: "select",
          inputOptions: {
            0: "통과",
            1: "패널티",
          },
          inputPlaceholder: "유저 신고 결과",
          showCancelButton: true,
          inputValidator: (value) => {
            return new Promise((resolve) => {
              if (value) {
                if (value == 1) {
                  this.role = 4;
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
                    vm.$swal.fire("유저 신고결과 수정이 완료되었습니다");
                    vm.updateUserBlock(item.flagTo);
                    vm.getFlagedUser();
                  })
                  .catch(function (error) {
                    console.log(error);
                    console.log("유저 신고결과 수정 실패");
                  });
              } else {
              }
            });
          },
        });
      })();
    },
    updateUserBlock(memberId) {
      this.axios({
        url: "/memberupdaterole",
        method: "put",
        data: {
          role: this.role,
          memberId: memberId,
        },
      })
        .then(function (response) {
          console.log(response);
          console.log("유저 block여부 수정 성공");
        })
        .catch(function (error) {
          console.log(error);
          console.log("유저 block여부 수정 실패");
        });
    },
    deleteFlagUser(item) {
      const vm = this;
      this.editedIndex = this.moimFlagList.indexOf(item);
      this.editedItem = Object.assign({}, item);

      this.axios({
        url: "/flagging/" + item.flagId,
        method: "delete",
      })
        .then(function (response) {
          console.log(response);
          vm.$swal.fire("유저 신고결과 식제가 완료되었습니다");
          vm.getFlagedUser();
        })
        .catch(function (error) {
          console.log(error);
          console.log("유저 신고결과 식제 실패");
        });
    },

    async goToUserProfile(item) {
      this.editedItem = Object.assign({}, item);
      await this.getOneUser(this.editedItem.flagTo);
      if (this.userOneInfo.role == 4) {
        this.$swal.fire("관리자에 의해 접근 금지된 유저 프로필입니다.");
      } else {
         this.$router.push({ path: "/snsUserFeed", query: { userId: this.editedItem.flagTo } });
      }
    },

    async getOneUser(memberId) {
      const vm = this;
      await this.axios({
            url: "/member/" + memberId,
            method: "get",
          })
            .then(function (response) {
              vm.userOneInfo = response.data;
            })
            .catch(function (error) {
              console.log(error);
            });
    },
  },
};
</script>