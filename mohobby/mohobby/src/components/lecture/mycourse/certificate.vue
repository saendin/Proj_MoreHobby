<template>
    <v-container>
        <v-card
            max-width="344"
            outlined
            v-for="(item, i) in itemList"
            :key="i"
        >
            <v-list-item three-line>
            <v-list-item-content>
                <v-img
                    lazy-src="https://picsum.photos/id/11/10/6"
                    height="200"
                    width="250"
                    :src="require(`@/assets/image/class/thumb/${item.classId}/0.jpg`)"
                    style="padding-top: 6px"
                    @click="goDetail(item)"
                    class="hover"
                >
                </v-img>
                <v-card-text class="hover" @click="goDetail(item)">{{ item.className }}</v-card-text>
                <v-card-text> {{ $moment(item.startDate).format('YYYY/MM/DD') }} ~ {{ $moment(item.endDate).format('YYYY/MM/DD') }} </v-card-text>
            </v-list-item-content>
            </v-list-item>
            <v-card-actions>
                <v-btn v-if="item.certyn == 'y'" outlined color="#2ac187" @click="openPrint(item)">발급</v-btn>
                <v-btn v-if="item.certyn == 'n'" outlined disabled>발급불가</v-btn>
            </v-card-actions>

        </v-card>
        <v-card
            v-if="itemList.length == 0"
            flat justify="center" 
            align="center" 
            style="padding-top: 50px"
        >
            <h1>🙇</h1>
            <h1>수료증을 발급할 수 있는 강의가 없습니다</h1>
        </v-card>
    </v-container>
</template>

<script>
export default {
    data() {
        return{
            itemList: [],
        }
    },
    async mounted() {
        await this.getItemList();
    },
    methods: {
        async getItemList() {
            let res  = await this.axios('/class/my/cert', {
                params: {
                    memberId: this.$store.state.id,
                }
            })

            this.itemList = res.data;
        
            
        },
        goDetail(item) {
            if(item.classType === 0) {
                this.$router.push({ path: '/class/on/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
            } else if(item.classType === 1) {
                this.$router.push({ path: '/class/off/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
            }
        },
        openPrint(item) {
            let routeUrl = this.$router.resolve({
                path: "/class/my/cert/print",
                query: {
                    className: item.className,
                    classType: item.classType,
                    startDate: item.startDate,
                    endDate: item.endDate,
                    memberName: this.$store.state.user.memberName,
                    birth: this.$store.state.user.birth,
                }
            });
            window.open(routeUrl .href, '_blank');
        },
    },
}
</script>

<style scoped>
    .hover{
        cursor: pointer;
    }
</style>