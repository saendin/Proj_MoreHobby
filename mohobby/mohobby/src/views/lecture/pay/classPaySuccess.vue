<template>
    <div style="margin: 150px 40px;">
        <v-container class="fill-height d-flex justify-center align-center">
            <v-row>
                <v-col class="d-flex justify-end" cols="5">
                    <v-icon size="150" color="#2ac187">mdi-checkbox-marked-circle-outline</v-icon>
                </v-col>
                <v-col>
                    <h1>수강신청이 완료되었습니다.</h1>
                    <h4>주문내역 및 배송에 관한 안내는 주문조회를 통하여 확인 가능합니다.</h4>
                    <h4>주문번호 : {{ resultInfo.merchant_uid }}</h4>
                    <h4>주문일자 : {{ $moment(date).format('YYYY-MM-DD HH:mm:ss') }}</h4>
                </v-col>
            </v-row>
            <v-row v-if="resultInfo.pay_method != 'vbank'">
                <v-col class="d-flex justify-center align-center">
                    <v-btn x-large outlined>바로 학습하러 가기 <v-icon class="ml-3" color="#2ac187">mdi-arrow-right</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
            <div v-if="resultInfo.pay_method == 'vbank'">
                <v-divider v-if="resultInfo.pay_method == 'vbank'" style="margin: 80px 0px;"></v-divider>
                <v-col>
                    <h1>가상계좌 정보</h1>
                    <h4>은행 : {{ resultInfo.vbank_name }}</h4>
                    <h4>예금주 : {{ resultInfo.vbank_holder }}</h4>
                    <h4>계좌번호 : {{ resultInfo.vbank_num }}</h4>
                </v-col>
            </div>
        </v-container>
    </div>
</template>

<script>
export default {
    props: {
        resultInfo: {
            type: Object
        },
        classId: {
            type: String
        },
        date: {
            type: Date
        },
        classInfo: {
            type: Object
        }
    },
    created() {
        console.log(this.resultInfo);
    }
}
</script>

<style>

</style>