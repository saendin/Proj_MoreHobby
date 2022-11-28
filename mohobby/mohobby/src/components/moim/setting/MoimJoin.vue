<template>
    <div class="container">
        <h2 class="mt-8">소모임 가입신청자</h2>
        <hr class="mt-10 mb-16">
        <v-card class="mx-auto mb-8" max-width="800" outlined>
            <v-list-item three-line>
                <v-row class="pa-5">
                    <v-col cols="12" sm="6" md="1" class="mt-3">
                        <v-avatar class="mb-4" color="grey darken-1" size="64">
                            <v-img aspect-ratio="30"
                                src="https://dimg.donga.com/ugc/CDB/WEEKLY/Article/5a/d0/5c/e0/5ad05ce00110d2738de6.jpg">
                            </v-img>
                        </v-avatar>
                    </v-col>
                    <v-col cols="12" sm="6" md="4" class="mt-8 ml-5">
                        <div>user1</div>
                    </v-col>
                    <v-col cols="12" sm="6" md="6" class="mt-8 ml-10">
                        <v-spacer></v-spacer>
                        <div class="select">
                            <v-btn
                            color="error"
                            class="mr-5"
                            >
                                거절
                            </v-btn>
                            <v-btn
                            color="success">
                                수락
                            </v-btn>
                        </div>
                    </v-col>
                </v-row>

            </v-list-item>
        </v-card>
    </div>
</template>
<script>
export default {
    date() {
        return {

        }
    }
}
</script>
<style scoped>
.container {
    width: 85%;
}

.select {
    float: right
}
</style>