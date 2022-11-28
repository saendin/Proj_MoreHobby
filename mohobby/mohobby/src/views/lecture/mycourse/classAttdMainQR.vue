<template>
  <v-container fluid style="margin: 20px 40px;">
    <div>
        <div class="button" @click="CreateQR">QR코드 생성하기</div>
    </div>
    <img :src="qrimg" style="width: 400px; height: 400px;" />
  </v-container>
</template>

<script>
export default {
    data() {
        return {
            qrimg: '',
            link: 'http://192.168.0.10:8081/class/attd/login'
        };
    },
    methods: {
        CreateQR: function(){
            this.axios.get('/AttdQR', {
                params: {
                    link: this.link,
                }
            }).then( result => {
                this.qrimg = result.data;
            });
        }
    },
}
</script>

<style>

</style>