<template>
    <div>
        <div>
            <section class="test">
                <input type="number" placeholder="금액 입력" v-model="price" >
                <div class="button" @click="PaymentBtn">결제</div>
            </section>
            <section class="test">
                <div class="button" @click="CertBtn">본인인증하기</div> <br />
                <input type="text" placeholder="이름" v-model="name" > <br />
                <input type="text" placeholder="생년월일" v-model="birthday"> <br />
                <input type="text" placeholder="전화번호" v-model="phone"> <br />
            </section>
        </div>
    </div>
</template>

<script>

//import IMP from 'vue-iamport';
const { IMP } = window;
IMP.init("imp46541776");

export default {
    name: "test",
    data() {
        return {
            price: 0,
            name: "",
            birthday: "",
            phone: ""
        };
    },
    created() {
        document.cookie = "safeCookie1=foo; SameSite=Lax";
        document.cookie = "safeCookie2=foo";
        document.cookie = "crossCookie=bar; SameSite=None; Secure";
    },
    methods: {
        CertBtn: function() {
            IMP.certification({
                //merchant_uid: "ORD20180131-0010013" // 주문 번호
            }, rsp => {
                console.log(rsp.imp_uid);
                const imp_uid = rsp.imp_uid;
                if (rsp.success) {
                    // 인증 정보 받기
                    this.axios.get('/iamport/cert/'+imp_uid).then(result => {
                        this.name = result.data.name;
                        this.phone = result.data.phone;
                        this.birthday = result.data.birthday;
                    });
                }
                else {
                    console.log("인증 실패");
                }
            })
        },
        PaymentBtn: function () {
            IMP.request_pay({
                pg: "html5_inicis",
                pay_method: "card",
                merchant_uid: "ORD" + new Date().toISOString().substring(0,10).replace(/-/g,'') + "-" + new Date().getTime(),
                name: "노르웨이 회전 의자",
                amount: this.price,
                buyer_email: "gildong@gmail.com",
                buyer_name: "홍길동",
                buyer_tel: "010-4242-4242",
                buyer_addr: "서울특별시 강남구 신사동",
                buyer_postcode: "01181"
            }, rsp => {
                console.log(rsp);
                if (rsp.success) {
                    console.log("결제 성공");
                }
                else {
                    console.log("결제 실패");
                }
            });
        }
    },
    components: {  }
}
</script>


<style scoped>
.test{ display:flex; justify-content: center; align-items: center; height:100vh; }
.button{ width: 200px; height:40px; background-color:#ffffff; border:1px #a8a8a8 solid; color:black; display:flex; align-items: center; justify-content: center; cursor:pointer; }
</style>