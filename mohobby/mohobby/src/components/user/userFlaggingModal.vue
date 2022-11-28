<template>
<div>
<v-dialog
      v-model="userFlagModal"
      scrollable
      max-width="300px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        >
          신고하기
        </v-btn>
      </template>
      <v-card>
        <v-card-title>Select Country</v-card-title>
        <v-divider></v-divider>
        <v-card-text style="height: 300px;">
          <v-radio-group
            v-model="selectedCode"
            column
          >
            <v-radio
              label="부적절한 게시물 개시"
              value="us1"
            ></v-radio>
            <v-radio
              label="다른 유저에게 욕설, 비방"
              value="us2"
            ></v-radio>
            <v-radio
              label="게시글, 댓글 도배"
              value="us3"
            ></v-radio>
            <v-radio
              label="홍보성 게시물 반복적 개시"
              value="us4"
            ></v-radio>
            <v-radio
              label="기타"
              value="us5"
            ></v-radio>
          </v-radio-group>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
            color="blue darken-1"
            text
            @click="userFlagModal = false"
          >
            Close
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="userFlagging()"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
    name: '',
    components: {},
    data() {
        return {
          selectedCode: '',
          userFlagModal: false,
          memberId : this.$store.state.id,
          flagedUser : 'user11',
          flagReason : "",
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
    methods: {
        userFlagging() {
          const vm = this;
          this.userFlagModal = false;
          if (this.selectedCode == "us5") {
            this.$swal.fire({
                title: '신고 이유를 입력하세요',
                html: `<input type="text" id="flagReason" class="swal2-input" placeholder="신고 이유">`,
                confirmButtonText: '제출하기',
                focusConfirm: false,
                preConfirm: () => {
                  const flagReason = this.$swal.getPopup().querySelector('#flagReason').value
                  if (!flagReason) {
                    this.$swal.showValidationMessage(`신고이유를 입력해 주세요`)
                  }
                  return { flagReason: flagReason }
                }
              }).then((result) => {
                vm.flagReason = result.value.flagReason;
                console.log(vm.flagReason);
                this.insertFlag();
              })
          } else {
              this.insertFlag();
          }
        },
        insertFlag() {
          const vm = this;
          this.axios({
            url: "/flagging",
            method: "post",
            data : {
              flagFrom : this.memberId,
              flagTo : this.flagedUser,
              flagCode : this.selectedCode,
              flagReason : this.flagReason,
            }
          })
            .then(function (response) {
              console.log(vm.flagReason);
              console.log(response);
            })
            .catch(function (error) {
              console.log(error);
            });
        },
        invite() {

        }
    }
}
</script>