<template>
  <main>
    <AdminSidebar></AdminSidebar>
    <h3>Q&A게시판 질문 내역</h3>
      <v-col
        class="d-flex"
        cols="6"
        sm="3"
      >
        <v-select
          v-model="selectedClassId"
          :items="classList"
          label="클래스 이름"
          item-text="className"
          item-value="classId"
          outlined
          @change="getQnAList()"
        ></v-select>
      </v-col>
      <template>
        <v-data-table
        :headers="QnAHeaders"
        :items="QnAList"
        sort-by="calories"
        :items-per-page="5"
        class="elevation-1"
      >
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click="answerQnA(item)">
            mdi-pencil
          </v-icon>
        </template>
        small

        <template v-slot:no-data>
          <p>해당하는 내용이 없습니다.</p>
        </template>
        <template v-slot:item.showDetail="{ item }">
          <v-icon v-if="item.classType == 0" @click="$router.push('/class/on/' + item.classId + '/qna')"> mdi-arrow-right-bold-box </v-icon>
          <v-icon v-if="item.classType == 1" @click="$router.push('/class/off/' + item.classId + '/qna')"> mdi-arrow-right-bold-box </v-icon>
        </template>

        <template v-slot:no-data>
          <p>해당하는 내용이 없습니다.</p>
        </template>
      </v-data-table>
        </v-data-table>
      </template>
    <h3>강의 목록</h3>
    <v-row>
      <v-col lg="12">
        <v-data-table
          :headers="classHeaders"
          :items="classList"
          :items-per-page="5"
          class="elevation-1"
        >
        >
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click="updateConfirm(item)">
            mdi-pencil
          </v-icon>
        </template>

        <template v-slot:item.manage="{ item }">
          <v-icon v-if='item.auditStatus === "승인"' 
          @click="$router.push({name: 'classPrepare', params: {classId : item.classId }})">
          mdi-arrow-right-bold-box
          </v-icon>
        </template>

        <template v-slot:no-data>
          <p>해당하는 내용이 없습니다.</p>
        </template>

        <template v-slot:item.showDetail="{ item }">
          <v-icon @click="goToClassInput(item)"> mdi-arrow-right-bold-box </v-icon>
        </template>
        </v-data-table>
      </v-col>
    </v-row>

  </main>
</template>
<script>
import AdminSidebar from "../../components/admin/AdminSidebar.vue";
export default {
  name: "",
  components: { AdminSidebar },
  data() {
    return {
              QnAHeaders: [
          {
            text: '강의번호',
            align: 'classId',
            sortable: false,
            value: 'classId',
          },
          { text: '게시글 번호', value: 'boardId' },
          { text: '강의명', value: 'className' },
          { text: '질문작성자', value: 'memberId' },
          { text: '질문내용', value: 'title' },
          { text: '답변내용', value: 'content' },
          { text: '답변여부', value: 'replyCheck' },
          { text: "수정하기", value: "actions", sortable: false },
          { text: "상세보기", value: "showDetail", sortable: false },
        ],
      tags: ["전체", "심사대기", "심사중", "승인", "미승인"],
      classHeaders: [
        {
          text: "강의번호",
          align: "start",
          sortable: false,
          value: "classId",
        },
        { text: "카테고리", value: "categoryName" },
        { text: "신청자", value: "memberId" },
        { text: "강의명", value: "className" },
        { text: "관리자승인여부", value: "auditStatus" },
        { text: "반려이유", value: "auditReturn" },
        { text: "수정하기", value: "actions", sortable: false },
        { text: "상세보기", value: "showDetail", sortable: false },
        { text: "관리하기", value: "manage", sortable: false },
      ],
      classList: [],
      beforeComfirmList: [],
      comfirmingList: [],
      approveList: [],
      notApproveList: [],
      QnAList : [],
      selectedClassId : "",
      selectedQnABoard : {},
      selectedClass : "",
    };
  },
  beforeCreate() {},
  created() {
    this.getAllClass();
    this.getQnAAllList();
  },
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    // 승인 신청한 클래스 전체 가지고오기
    getAllClass() {
      const vm = this;
      this.axios({
        url: "/adminClassList",
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          vm.classList = response.data;

          for (let i = 0; i < response.data.length; i++) {
            console.log(
              "response.data[i].auditStatus" + response.data[i].auditStatus
            );
            if (response.data[i].auditStatus === 0) {
              vm.classList[i].auditStatus = "심사대기";
              vm.beforeComfirmList.push(vm.classList[i]);
            } else if (response.data[i].auditStatus === 1) {
              vm.classList[i].auditStatus = "심사중";
              vm.comfirmingList.push(vm.classList[i]);
            } else if (response.data[i].auditStatus === 2) {
              vm.classList[i].auditStatus = "승인";
              vm.approveList.push(vm.classList[i]);
            } else if (response.data[i].auditStatus === 3) {
              vm.classList[i].auditStatus = "미승인";
              vm.notApproveList.push(vm.classList[i]);
            }
          }

          console.log(vm.classList);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // qna리스트 전체가지고 오기
    getQnAAllList() {
      const vm = this;
      this.axios({
        url: "/QnAAllList/",
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          vm.QnAList = response.data;

          for (let i = 0; i < response.data.length; i++) {
            console.log(
              "response.data[i].replyCheck" + response.data[i].replyCheck
            );
            if (response.data[i].replyCheck === 0) {
              vm.QnAList[i].replyCheck = "미처리";
            } else if (response.data[i].replyCheck === 1) {
              vm.QnAList[i].replyCheck = "처리";
            }
          }

          console.log(vm.classList);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 선택한 클래스의 qna리스트 가지고 오기
    getQnAList() {
      const vm = this;
      this.axios({
        url: "/QnAList/" + this.selectedClassId,
        method: "get",
      })
        .then(function (response) {
          console.log(response.data);
          vm.QnAList = response.data;

          for (let i = 0; i < response.data.length; i++) {
            console.log(
              "response.data[i].replyCheck" + response.data[i].replyCheck
            );
            if (response.data[i].replyCheck === 0) {
              vm.QnAList[i].replyCheck = "미처리";
            } else if (response.data[i].replyCheck === 1) {
              vm.QnAList[i].replyCheck = "처리";
            }
          }

          console.log(vm.classList);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // QnA 답변하기
    answerQnA(item) {
      this.selectedQnABoard = Object.assign({}, item);

      const vm = this;
      this.$swal.fire({
      title: '답변을 입력하세요',
      html: `<input type="text" id="content" class="swal2-input" placeholder="답변">`,
      confirmButtonText: '답변하기',
      cancelButtonText: '취소하기',
      focusConfirm: false,
      preConfirm: () => {
        const content = this.$swal.getPopup().querySelector('#content').value;
        if (!content) {
          this.$swal.showValidationMessage(`답변이 등록되지 않았습니다.`)
        }
        return { content: content }
      }
    }).then((result) => {
      const content = result.value.content;
      vm.axios({
        url: "/QnAUpdate",
        method: "put",
        data: {
           content: result.value.content,
           boardId: item.boardId,
        },
      })
        .then(function (response) {
            console.log(response);
            vm.$swal.fire('답변이 등록되었습니다.');
            vm.getAllClass();
        })
        .catch(function (error) {
          console.log(error);
        });
      })
    },

    // 강의 승인 여부 업데이트
    updateConfirm(item) {
      this.selectedClass = Object.assign({}, item);
      const vm = this;
      (async () => {
      const { value: confirm } = await this.$swal.fire({
        title: 'Select field validation',
        input: 'select',
        inputOptions: {
          0 : '심사대기',
          1: '심사중',
          2: '승인',
          3: '미승인'
        },
        inputPlaceholder: '관리자 승인 여부를 선택하세요',
        showCancelButton: true,
        confirmButtonText: '제출하기',
        cancelButtonText: '취소하기',
        inputValidator: (value) => {
          return new Promise((resolve) => {
            if (value == 3) {
              this.$swal.fire({
                title: '미승인 이유를 입력하세요',
                html: `<input type="text" id="auditReturn" class="swal2-input" placeholder="미승인 이유를 입력하세요">`,
                confirmButtonText: '제출하기',
                cancelButtonText: '취소하기',
                focusConfirm: false,
                preConfirm: () => {
                  const auditReturn = this.$swal.getPopup().querySelector('#auditReturn').value
                  if (!auditReturn) {
                    this.$swal.showValidationMessage(`미승인 이유가 입력되지 않았습니다.`)
                  }
                  return { auditReturn: auditReturn }
                }
              }).then((result) => {
                this.axios({
                  url: "/adminAuditClass",
                  method: "put",
                  data: {
                    auditStatus : value,
                    auditReturn : result.value.auditReturn,
                    classId : this.selectedClass.classId,
                  }
                })
                  .then(function (response) {
                    vm.$swal.fire('승인 여부 수정이 완료되었습니다.');
                    vm.getAllClass();
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
              })
            } else {
              this.axios({
                  url: "/adminAuditClass",
                  method: "put",
                  data: {
                    auditStatus : value,
                    classId : this.selectedClass.classId,
                  }
                })
                  .then(function (response) {
                    vm.$swal.fire('승인 여부 수정이 완료되었습니다.');
                    vm.getAllClass();
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
            }
          })
        }
      })
      if (fruit) {
        this.$swal.fire('You selected: ' + fruit)
      }
      })()
    },
    // 해당 클래스의 classInput으로 이동
    goToClassInput(item) {

    }
  },
};
</script>
