<template>
    <div>
        <div v-if = "!postId">
            <RegisterForm />
        </div>
        <!-- 수정폼 -->
        <div v-else>
            <EditForm :postId = "postId" />
        </div>
    </div>
</template>
<script>
import RegisterForm from "@/components/sns/Main/RegisterForm";
import EditForm from "@/components/sns/Main/EditForm";
export default {
    name:'snsFeedRegister',
    components: {
        RegisterForm, EditForm
    },

    created(){
        this.postId = this.$route.params.postId;
    },

    data() {
        return{
            regMode : false,
            postId: this.$route.params.postId,
        };
    },
    methods:{
    },
};
</script>