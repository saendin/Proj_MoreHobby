<template>
    <div style="margin: 20px 40px;">
        결제하기
        <v-stepper v-model="e1" flat>
            <v-stepper-header>
                    <v-stepper-step step="3" color="#2ac187">
                        결제하기
                    </v-stepper-step>
                </v-stepper-header>
            <v-stepper-items>
                <v-stepper-content step="1">
                    <v-card
                    class="mb-12"
                    color="grey lighten-1"
                    height="200px"
                    ></v-card>

                    <v-btn
                    color="primary"
                    @click="e1 = 2"
                    >
                    Continue
                    </v-btn>

                    <v-btn text>
                    Cancel
                    </v-btn>
                </v-stepper-content>

                <v-stepper-content step="2">
                    <v-card
                    class="mb-12"
                    color="grey lighten-1"
                    height="200px"
                    ></v-card>

                    <v-btn
                    color="primary"
                    @click="e1 = 3"
                    >
                    Continue
                    </v-btn>

                    <v-btn text>
                    Cancel
                    </v-btn>
                </v-stepper-content>

                <v-stepper-content step="3">
                    <v-card
                        class="mt-12"
                        outlined
                    >
                        <div class="ma-5">
                            <h2 class="pb-5">클래스 정보</h2>
                            <v-row class="d-flex align-center">
                                <v-col cols="2">
                                    <v-img
                                        lazy-src="https://picsum.photos/id/11/10/6"
                                        height="150"
                                        width="150"
                                        :src="require(`@/assets/image/class/thumb/${classId}/1.jpg`)"
                                    />
                                </v-col>
                                <v-col cols="10">
                                    <v-row>
                                        <v-icon 
                                            :color="classInfo.classType == 0 ? '#2ac187' : 'red'"
                                        >
                                            mdi-record
                                        </v-icon> 
                                        <h4 
                                            :style="classInfo.classType == 0 ? 'color: #2ac187' : 'color: red'"
                                        >
                                            {{classInfo.classType == 0 ? 'ONLINE' : 'OFFLINE'}}
                                        </h4>
                                    </v-row>
                                    <v-row class="pt-1">
                                        <h2>{{classInfo.className}}</h2>
                                    </v-row>
                                    <v-row class="pt-6">
                                        <h3>{{classInfo.jobName + ' ' + classInfo.nickname}}</h3>
                                    </v-row>
                                </v-col>
                            </v-row>
                        </div>
                    </v-card>
                    <v-card
                        class="mt-5"
                        outlined
                        v-if="needsPickList.length != 0"
                    >
                        <div class="ma-5">
                            <h2 class="pb-5">주문 정보</h2>
                            <v-row class="d-flex align-center">
                                
                            </v-row>
                        </div>
                    </v-card>
                    <v-card
                        class="mt-5"
                        outlined
                    >
                        <div class="ma-5">
                            <h2 class="pb-13">구매자 정보</h2>
                            <v-row>
                                <v-col cols="3">
                                    <h3>이름</h3>
                                </v-col>
                                <v-col cols="9">
                                    <h3>{{$store.state.user.memberName}}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col cols="3">
                                    <h3>이메일</h3>
                                </v-col>
                                <v-col cols="9">
                                    <h3>{{$store.state.user.email}}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col cols="3">
                                    <h3>휴대폰번호</h3>
                                </v-col>
                                <v-col cols="9">
                                    <h3>{{$store.state.user.phoneNum}}</h3>
                                </v-col>
                            </v-row>
                        </div>
                    </v-card>
                    <v-card
                        class="mt-5"
                        outlined
                    >
                        <div class="ma-5">
                            <h2 class="pb-1">결제 금액</h2>
                            <h4 class="pb-7">도서 산간 지역은 추가 배송료가 적용될 수 있습니다.</h4>
                            <v-row>
                                <v-col class="d-flex justify-start">
                                    <h3>수강 금액</h3>
                                </v-col>
                                <v-col class="d-flex justify-end">
                                    <h3>{{ classInfo.classPrice | comma | won }}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col class="d-flex justify-start">
                                    <h3>총 준비물 금액</h3>
                                </v-col>
                                <v-col class="d-flex justify-end">
                                    <h3>{{ needsPickList.length == 0 ? '없음' : (needsPickPrice | comma | won) }}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col class="d-flex justify-start">
                                    <h3>배송비</h3>
                                </v-col>
                                <v-col class="d-flex justify-end">
                                    <h3>{{ needsPickList.length == 0 ? '없음' : '무료' }}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col class="d-flex justify-start align-center my-0 py-0">
                                    <h2>최종 가격 </h2> <h5> 무이자 할부 가능</h5>
                                </v-col>
                                <v-col class="d-flex justify-end">
                                    <h5>5개월 할부 시 월 {{ totalPrice / 5 | comma | won }}</h5>
                                </v-col>
                            </v-row>
                            <v-row class="my-0 py-0">
                                <v-col class="d-flex justify-end">
                                    <h2>{{ totalPrice | comma | won }}</h2>
                                </v-col>
                            </v-row>
                        </div>
                    </v-card>
                    <v-card
                        class="mt-5"
                        outlined
                    >
                        <div class="ma-5">
                            <h2 class="pb-5">결제 방식</h2>
                        </div>
                        <v-row class="pb-10">
                            <v-card class="ma-auto pa-auto"  width="120" outlined align="center" @click="payBtn('html5_inicis', 'card')">
                                카드결제
                            </v-card>
                            <v-card class="ma-auto pa-auto"  width="120" outlined align="center" @click="payBtn('html5_inicis', 'vbank')">
                                무통장입금
                            </v-card>
                            <v-card class="ma-auto pa-auto"  width="120" outlined align="center" @click="payBtn('kakaopay', 'card')">
                                카카오페이
                            </v-card>
                            <v-card class="ma-auto pa-auto"  width="120" outlined align="center" @click="payBtn('smilepay', 'card')">
                                스마일페이
                            </v-card>
                            <v-card class="ma-auto pa-auto"  width="120" outlined align="center" @click="payBtn('tosspay', 'card')">
                                토스페이
                            </v-card>
                        </v-row>
                    </v-card>

                    <v-btn text>
                    Cancel
                    </v-btn>
                </v-stepper-content>
            </v-stepper-items>
        </v-stepper>
    </div>
</template>
<script>
const { IMP } = window;
IMP.init("imp46541776");

export default {
    name: 'classPay',
    props: {
        classId: {
            type: Number,
        },
        classInfo: {
            type: Object,
        },
    },
    data() {
        return {
            e1: 3,
            needsList: [],
            needsPickList: [],
            needsPickPrice: '',
            dlvyInfo: {},
            payInfo: {},
            totalPrice: this.classInfo.classPrice,
        }
    },
    created() {
        this.getNeedsList();
        document.cookie = "safeCookie1=foo; SameSite=Lax";
        document.cookie = "safeCookie2=foo";
        document.cookie = "crossCookie=bar; SameSite=None; Secure";
    },
    methods: {
        getNeedsList() {
            this.axios('/class/pay/needs/'+this.classId)
            .then(res => {
                if(res.data.length == 0) {
                    this.e1 = 3;
                } else {
                    this.needsList = res.data;
                }
            })
        },
        payBtn(pg, method) {
            let vm = this;
            let payName = this.classInfo.className;
            let date = new Date();
            if(payName.length > 8) {
                payName = payName.substring(0, 8) + '...';
            }
            if(this.needsPickList.length != 0) {
                payName += (' 외 ' + this.needsPickList.length + '건');
            }

            IMP.request_pay({
                pg: pg,
                pay_method: method,
                merchant_uid: "ORD" + date.toISOString().substring(0,10).replace(/-/g,'') + "-" + date.getTime(),
                name: payName,
                amount: 1000,
                buyer_email: vm.$store.state.user.email,
                buyer_name: vm.$store.state.user.userName,
                buyer_tel: vm.$store.state.user.phoneNum,
                buyer_addr: vm.dlvyInfo.addr,
                buyer_postcode: vm.dlvyInfo.postcode
            }, rsp => {
                let data = rsp;
                if (rsp.success) {
                    this.insertPay(data);

                    this.$router.push({ name: 'classPaySuccess', 
                                        params: { resultInfo: data, classId: this.classId, date: date, classInfo: this.classInfo }
                    }).catch(()=>{$router.go(0)});
                }
                else {
                    this.$router.push({ name: 'classPayFail', 
                                        params: { resultInfo: data, classId: this.classId, date: date,}
                    }).catch(()=>{$router.go(0)});
                }
            });
        },

        insertPay(payResult) {
            payResult.memberId = this.$store.state.id;
            payResult.classId = this.classId;

            this.axios.post('/class/pay', payResult).then(res => {
                console.log('res', res)
                console.log('res.data', res.data);
            })
        },

    }
}
</script>
<style>
    
</style>