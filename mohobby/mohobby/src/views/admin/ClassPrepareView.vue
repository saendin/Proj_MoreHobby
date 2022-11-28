<template>
    <main>
     <AdminSidebar></AdminSidebar>
     <div class="container">
        <h3>수료증 발급 여부를 선택하세요</h3>
        <hr>
        <v-radio-group
        v-model="certAble"
        row
        >
        <v-radio style="margin-right: 100px;"
            label="발급하기"
            value="1"
        >
        </v-radio>
        <v-radio
            label="발급하지 않기"
            value="0"
        ></v-radio>
        </v-radio-group>
        <br>
        <div v-if="certAble == 1">
            <h3>수료증 발급 기준을 선택해 주세요</h3>
            <hr>
            <v-radio-group
                v-model="certStandard"
                column
                >
                <v-radio
                    label="100%"
                    value="100"
                ></v-radio>
                <v-radio
                    label="80%"
                    value="80"
                ></v-radio>
                <v-radio
                    label="60%"
                    value="60"
                ></v-radio>
                <v-radio
                    label="40%"
                    value="40"
                ></v-radio>
            </v-radio-group>
        </div>
            <br>
            <h3>준비물을 확인하고 상품을 등록해 주세요</h3>
            <hr>
            <br>
            <span class="label">유저가 입력한 준비물</span>
            <input type="text" class="input" style="width: 1000px;" :value="classNeeds">
            <span class="label">준비물 연계하기</span> 
            <!-- 플러스 버튼 -->  
            <br>
            <v-btn
                class="mx-2"
                dark
                color="#2ac187"
                @click="addProdForm"
                >
                <v-icon dark>
                    mdi-plus
                </v-icon>
                </v-btn>
                <br>
                <br>
                <form id="needs" v-on:submit.prevent> 
                    <NeedsForm v-for="i in count" :key="i" :i="i-1" :classId=classId></NeedsForm>
                </form>
                <v-btn
                elevation="2"
                color="#2ac187"
                @click="register"
                >등록하기</v-btn>
     </div> 
    </main>
</template>
<script>
import AdminSidebar from "../../components/admin/AdminSidebar.vue";
import NeedsForm from "@/components/admin/NeedsForm.vue";

export default {
    name: '',
    components: {
        AdminSidebar, NeedsForm
    },
    data() {
        return {
            classId : this.$route.params.classId,
            certAble : 0,
            classNeeds : "",
            count : 0,
            certStandard : 0,
    }
},
    beforeCreate() {},
    created() {
        
    },
    beforeMount() {},
    mounted() {
      this.getClassNeeds();
    },
    beforeUpdate() {},
    updated() {},
    beforeUnmount() {},
    unmounted() {},
    methods: {
        getClassNeeds() {
            const vm = this;
            console.log(this.$route.params.classId);
            this.axios({
                url: "/applyNeeds/" + this.classId,
                method: "get",
                })
                .then(function (response) {
                    console.log(response.data);
                    vm.classNeeds = response.data.needs;
                    console.log(response.data.needs);
                    console.log(vm.classNeeds);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        addProdForm() {
            this.count++;
        },
        updateCert() {
             this.axios({
                url: "/updateCert",
                method: "put",
                data : {
                    certAble : this.certAble,
                    certStandard : this.certStandard,
                    classId : this.classId
                }
                })
                .then(function (response) {
                    console.log(response.data);
                    
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        register() {
            this.updateCert();
            this.registerNeeds();

        },
        registerNeeds() {
            let formData = new FormData(needs);

            this.axios.post('/insertNeeds', formData)
            .then((response) => {
                console.log(response);
                this.$swal.fire('등록이 완료되었습니다.');
                this.$router.push('/adminclass');
            }, (error) => {
                console.log(error);
            });
        }
    }
}
</script>
<style scoped>
.container {
    margin: 20px;
}

.label {
  font-size: 17px;
  display: block;
}

.input {
  padding: 10px;
  width: 1200px;
  border: 1px solid lightgrey;
  margin-top: 10px;
  margin-bottom: 25px;
}
</style>