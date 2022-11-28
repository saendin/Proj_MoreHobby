<template>
    <v-container fluid>
        <div id="header-carousel">
            <v-carousel hide-delimiters style="height: 100%">
                <v-carousel-item v-for="i in classInfo.imgAmount" :key="i"
                    :src="require(`@/assets/image/class/thumb/${classId}/${i-1}.jpg`)" style="filter: brightness(75%)">

                </v-carousel-item>
                <v-btn :color="classInfo.jjim == 1 ? 'pink' : 'gray'" icon x-large absolute right
                    @click.stop="clickHeart">
                    <v-icon>{{ classInfo.jjim == 1 ? 'mdi-heart' : 'mdi-heart-outline' }}</v-icon>
                </v-btn>
                <v-row class="fill-height title" align="center" justify="center">
                    <div class="thumbText">
                        <h1>{{ classInfo.className }}</h1>
                    </div>
                </v-row>
            </v-carousel>
            <v-row class="fill-height title" align="center" justify="center">
                <div class="thumbName"
                    @click="$router.push({ path: '/snsUserFeed?memId='+classInfo.memberId }).catch(()=>{$router.go(0)})">
                    {{ classInfo.jobName + ' ' + classInfo.nickname }}
                </div>
            </v-row>
        </div>
        <div>
            <v-row>
                <v-card class="mx-auto" max-width="344" flat justify="center" align="center">
                    <v-card-text>
                        <v-icon size="90" color="#2b2b2b">
                            mdi-youtube
                        </v-icon>
                        <h2>
                            {{ '총 ' + classInfo.currTotal + '회 영상'}}
                        </h2>
                    </v-card-text>
                </v-card>
                <v-divider vertical></v-divider>
                <v-card class="mx-auto" max-width="344" flat justify="center" align="center">
                    <v-card-text>
                        <v-icon size="90" color="#2b2b2b">
                            mdi-restore
                        </v-icon>
                        <h2>
                            {{ '평생 시청'}}
                        </h2>
                    </v-card-text>
                </v-card>
                <v-divider vertical></v-divider>
                <v-card class="mx-auto" max-width="344" flat justify="center" align="left" style="margin-top: 20px">
                    <v-card-text>
                        <v-row align="center">
                            <v-col cols="auto">
                                <div>{{ classInfo.classPrice | comma | won }}</div>
                                <h2 class="largeText">
                                    {{ '월 ' }} {{ classInfo.classPrice/5 | comma | won }}
                                </h2>
                                <div>{{ '* 5개월 할부 시' }}</div>
                            </v-col>
                            <v-col cols="auto">
                                <v-btn depressed dark color="#2255b1" @click="goPayBtn">
                                    수강하기
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-row>
        </div>
        <div style="padding: 120px 0px 20px 0px">
            <v-tabs color="#2255b1" v-model="active_tab">
                <v-tab v-for="tab of tabs" :key="tab.index" @click="$router.push({ path: tab.route })">
                    <h3>{{tab.name}}</h3>
                    <div v-if="tab.index == 3" style="padding-left: 5px">
                        {{classInfo.qnaTotal}}
                    </div>
                    <div v-if="tab.index == 4" style="padding-left: 5px">
                        {{classInfo.reviewTotal}}
                    </div>
                </v-tab>
            </v-tabs>
        </div>
        <div style="padding-bottom: 60px;">
            <router-view />
        </div>
    </v-container>
</template>

<script>
export default {
    name: 'classDetail',
    props: {
        classId: {
            type: String,
            default: 1,
        },
    },
    data() {
        return {
            classInfo: {},
            active_tab: 0,
            tabs: [
                { index: 0, name: '클래스 소개', route: this.$route.path.substring(0, this.$route.path.lastIndexOf("/")) + '/info' },
                { index: 1, name: '커리큘럼', route: this.$route.path.substring(0, this.$route.path.lastIndexOf("/")) + '/course' },
                { index: 3, name: 'QnA', route: this.$route.path.substring(0, this.$route.path.lastIndexOf("/")) + '/qna' },
                { index: 4, name: '후기', route: this.$route.path.substring(0, this.$route.path.lastIndexOf("/")) + '/review' },
            ],
        }
    },
    created() {
        this.initInfo();
    },
    methods: {
        initInfo() {
            this.axios.get('/class/detail/' + this.classId, {
                params: {
                    memberId: this.$store.state.id
                }
            }).then(res => {
                this.classInfo = res.data;
            });
        },
        clickHeart: function () {
            if (!this.$store.state.id) {
                this.$swal('로그인 후 이용하세요!', '', 'info');
                return;
            }


            if (this.classInfo.jjim == 0) {
                this.axios('/class/jjim', {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json; charset=utf-8",
                    },
                    data: JSON.stringify({
                        targetId: this.classId,
                        targetType: 1,
                        memberId: this.$store.state.id
                    })
                }).catch(error => {
                    console.log(error);
                });

                this.classInfo.jjim = 1;

            } else if (this.classInfo.jjim == 1) {
                this.axios('/class/jjim', {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json; charset=utf-8",
                    },
                    data: JSON.stringify({
                        targetId: this.classId,
                        targetType: 1,
                        memberId: this.$store.state.id
                    })
                }).catch(error => {
                    console.log(error);
                });

                this.classInfo.jjim = 0;

            }

        },
        goPayBtn() {
            if(!this.$store.state.id) {
                this.$swal('로그인 후 이용하세요!', '', 'info');
                return;
            } else {
                this.goPay();
            }
            },
        goPay() {
            this.axios("/class/pay/"+this.classId, {
                params: {
                memberId: this.$store.state.id,
                }
            }).then(res => {
                if(!res.data) {
                    this.$router.push({ name: 'classPay', 
                                        params: { classId: this.classId, classInfo: this.classInfo }
                                }).catch(()=>{$router.go(0)});
                } else {
                    this.$swal('이미 수강 중인 강의입니다.', '얼른 학습하러 가보아요✍', 'info');
                }
            })
        },
    },
    components: {

    },
}
</script>

<style scope>
h2,
h3,
h4,
h5,
h6 {
    color: #2b2b2b;
}

.thumbText {
    position: absolute;
    bottom: 0;
    padding-bottom: 20px;
    word-break: keep-all;
    width: 50%;
    text-align: center;
    line-height: 1.3;
    color: white;
    text-shadow: 2px 2px 6px #3b3b3b;
}

.thumbName {
    padding: 18px 0px 55px 0px;
    text-align: center;
    cursor: pointer;
}

.largeText {
    font-size: 2.3em;
    padding: 10px 0px 14px 0px;
}
</style>