<template>
     <v-container fluid style="margin: 20px 40px;">
        <v-tabs color="#2255b1" v-model="active_tab">
            <v-tab
                v-for="tab of tabs"
                :key="tab.index"
                @click="$router.push({ path: tab.route })"
            >
                <h3>{{ tab.name }}</h3>
                <div v-if="tab.index == 3" style="padding-left: 5px">
                    {{ classInfo.qnaTotal }}
                </div>
                <div v-if="tab.index == 4" style="padding-left: 5px">
                    {{ classInfo.reviewTotal }}
                </div>
            </v-tab>
        </v-tabs>
        <div style="padding-top: 20px;">
            <router-view />
        </div>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            tabs: [
                {
                    index: 0,
                    name: "진행중인 강의",
                    route: "/class/my/instudy",
                },
                {
                    index: 1,
                    name: "종료된 강의",
                    route: "/class/my/endstudy",
                },
                {
                    index: 2,
                    name: "수료증", 
                    route: "/class/my/certificate",
                },
            ],
        };
    },
}
</script>

<style>

</style>