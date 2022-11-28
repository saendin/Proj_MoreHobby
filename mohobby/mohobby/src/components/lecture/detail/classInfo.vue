<template>
    <div id="classInfoInput" v-html="content">

    </div>
</template>

<script>


export default {
    name: 'classInfo',
    data() {
        return {
            path: '',
            content: '<span>상세내용입니다<span>',
        }
    },
    props: {
        classId: {
            type: String,
            default: 1,
        },
    },
    created() {
        this.readInfo();
    },
    methods: {
        readInfo() {

            this.axios.get('/readClassInfo', {
                params: {
                    classId: this.classId
                }
            }).then(res => {
                this.content = res.data;
            }).catch(err => {
                console.log(err);
            }).finally(() => {

            })



        },
        readPath() {

        },
    },
}
</script>

<style scoped>

</style>