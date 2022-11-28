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
                    <v-btn
                        small
                        absolute
                        right
                        @click.stop = "writeReview(item)"
                        v-if="item.reviewyn == 0"
                    >
                        후기작성하기
                    </v-btn>
                    <v-btn
                        :hover="false"
                        :ripple="false"
                        small
                        absolute
                        right
                        @click.stop = ""
                        v-if="item.reviewyn != 0"
                    >
                        후기작성완료
                    </v-btn>
                </v-img>
                <v-card-text class="hover" @click="goDetail(item)">{{ item.className }}</v-card-text>
                <v-card-text> {{ $moment(item.startDate).format('YYYY/MM/DD') }} ~ {{ $moment(item.endDate).format('YYYY/MM/DD') }} </v-card-text>
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
            </v-card-actions>

        </v-card>
        <v-card
            v-if="itemList.length == 0"
            flat justify="center" 
            align="center" 
            style="padding-top: 50px"
        >
            <h1>🙇</h1>
            <h1>수강을 완료한 강의가 없습니다</h1>
        </v-card>
        <v-dialog
            v-model="dialog"
            width="500"
        >
            <v-card>
                <v-card-title class="text-center d-flex justify-center grey lighten-2">
                    <h5>솔직한 후기평을 남겨주세요!</h5>
                </v-card-title>

                <div class="d-flex justify-center my-5">
                    <v-rating 
                        empty-icon="mdi-star-outline" 
                        full-icon="mdi-star" 
                        half-icon="mdi-star-half-full"
                        half-increments length="5" 
                        v-model="star" 
                        large color="#ffcb02"
                        background-color="#ffcb02"
                    />
                </div>
                <div class="mx-5">
                    <v-text-field
                        placeholder="제목을 입력하세요"
                        v-model="title"
                        outlined
                    ></v-text-field>
                </div>
                <div class="mx-5">
                    <v-textarea
                        outlined
                        label="내용을 입력하세요"
                        v-model="content"
                    ></v-textarea>
                </div>
                
                
                
                <v-divider></v-divider>

                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="#2255b1"
                    text
                    @click="submitBtn"
                >
                    등록
                </v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
    </v-container>
</template>

<script>

export default {
    data() {
        return{
            itemList: [],
            dialog: false,
            star: 0,
            title: "",
            content: "",
            target: {},
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
                    classStatus: 1,
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
        writeReview(item) {
            this.target = item;
            this.dialog = true;
        },
        submitBtn() {
            if(this.star == 0) {
                this.$swal('별점을 입력하세요!', '', 'info');
            }else if(this.title == '') {
                this.$swal('제목을 입력하세요!', '', 'info');
            }else if (this.content == '') {
                this.$swal('내용을 입력하세요!', '', 'info');
            } else {
                this.axios('/class/board', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=utf-8",
                },
                data: JSON.stringify({
                    memberId: this.$store.state.id,
                    classId: this.target.classId,
                    boardType: 4,
                    title: this.title,
                    content: this.content,
                    rate: this.star,
                    nickname: this.$store.state.user.nickName,
                })
                }).then( res => {
                if(res.status == 200) {
                    this.dialog = false;
                    this.getItemList();
                    this.title="";
                    this.content="";
                    this.target={};
                }
                }).catch( err => console.log(err) )
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