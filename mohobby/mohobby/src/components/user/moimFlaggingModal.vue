<template>
<div>
<v-dialog
      v-model="moimFlagModal"
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
        <v-btn text @click="moimFlagging" v-bind="attrs"
          v-on="on">
          <v-icon small color="white">mdi-plus-circle-outline</v-icon>
          <div style="color: white">신고하기</div>
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
              value="mo1"
            ></v-radio>
            <v-radio
              label="홍보성 초대 반복"
              value="mo2"
            ></v-radio>
            <v-radio
              label="청소년에게 음란성 초대글 유포"
              value="mo3"
            ></v-radio>
            <v-radio
              label="다른 소모임에 대한 욕설, 비방"
              value="mo4"
            ></v-radio>
            <v-radio
              label="사이트 목적과 맞지 않는 소모임"
              value="mo5"
            ></v-radio>
            <v-radio
              label="기타"
              value="mo6"
            ></v-radio>
          </v-radio-group>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
            color="blue darken-1"
            text
            @click="moimFlagModal = false"
          >
            Close
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="moimFlagging()"
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
          moimFlagModal: false,
          memberId : this.$store.state.id,
          flagedMoim : 2,
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
        moimFlagging() {
          const vm = this;
          this.moimFlagModal = false;
          if (this.selectedCode == "mo6") {
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
              flagTo : this.flagedMoim,
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