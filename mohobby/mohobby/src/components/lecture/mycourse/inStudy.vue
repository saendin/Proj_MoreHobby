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
                <v-card-text @click="goDetail(item)" class="hover">{{ item.className }}</v-card-text>
            </v-list-item-content>
            </v-list-item>
            <v-card-actions>
                <v-progress-linear
                    :value="item.totalViewProgress"
                    color="#2ac187"
                    height="20"
                >
                    {{ item.totalViewProgress }}%
                </v-progress-linear>
                <v-btn
                    color="#2ac187" 
                    class="ml-2"
                    @click.stop="$router.push({ path: '/learn/'+item.lastCurrId, }).catch(()=>{$router.go(0)})"
                    icon
                >
                    <v-icon>mdi-play-circle</v-icon>
                </v-btn>
            </v-card-actions>

        </v-card>
        <v-card
            v-if="itemList.length == 0"
            flat justify="center" 
            align="center" 
            style="padding-top: 50px"
        >
            <h1>🙇</h1>
            <h1>수강중인 강의가 없습니다</h1>
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
            let res  = await this.axios('/class/my', {
                params: {
                    memberId: this.$store.state.id,
                    classStatus: 0,
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
    },
}
</script>

<style scoped>
    .hover{
        cursor: pointer;
    }
</style>