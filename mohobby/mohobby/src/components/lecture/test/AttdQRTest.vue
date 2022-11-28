<template>
    <div>
        <div>
            <input type="text" placeholder="연결할 주소를 입력하세요." v-model="link" />
            <div class="button" @click="CreateQR">QR코드 생성하기</div>
        </div>
        <img :src="qrimg" style="width: 400px; height: 400px;" />
    </div>
</template>

<script>


export default {
    data() {
        return {
            qrimg: '',
            link: ''
        };
    },
    created() {
    },
    methods: {
        CreateQR: function(){
            console.log(this.link);
            this.axios.get('/AttdQR', {
                params: {
                    link: this.link
                }
            }).then( result => {
                this.qrimg = result.data;
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