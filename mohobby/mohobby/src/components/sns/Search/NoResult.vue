<template>
    <div>
        <v-card class="mx-auto" width="800" min-height="520">
        <v-toolbar color="#2ac187" dark> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ keyword }} 에 대한 검색결과입니다 </v-toolbar>
            <div class="wrap_box">
                <v-img class="mx-auto mt-10 mb-10" width="100" src="@/assets/image/sns/default/warn.jpg" alt="no_result"></v-img>
                <div class="text_box">
                    <div class="text_box_title"><span class="point">'{{ keyword }}'</span> 에 대한 검색결과가 없습니다. </div>
                    <ul>
                        <li>
                        단어의 철자가 정확한지 확인해 주세요🙏
                        </li>
                    </ul>
                </div>
            </div>
        </v-card>
    </div>
</template>
<script>
export default {
    name: "NoResult",
    props: {
        keyword : String,
    },
    data() {
        return {
        }
    },
    watch: {

    },
    created() {
        console.log("검색결과 없음 page");
    },
    methods: {

    }
}

</script>
<style scpoed>
    .wrap_box {
        margin: 80px auto;
    }
    .text_box{
        margin-left: 145px;
        margin-bottom: 200px;
        width: 510px;
        vertical-align: middle;
        color: #a3a3a3ee;
        font-weight: 500;
    }
    .text_box_title{
        margin-left: 20px;
        margin-bottom: 3px;
        color: #858484ee;
    }
    .point{
        color: rgba(255, 94, 0, 0.932);
        font-weight: bold;
    }
    li {
        padding: 1px;
        font-size: 15px;
    }
</style>