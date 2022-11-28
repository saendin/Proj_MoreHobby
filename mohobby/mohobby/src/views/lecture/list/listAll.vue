<template>
    <v-container fluid style="padding: 30px 40px;">
        <div>
            <v-card
                class="d-flex justify-end"
                flat
                tile
            >
                <v-card
                    flat
                    tile
                    style="padding-top: 20px; margin-right: 10px;"
                >
                    <v-btn
                        class="mx-1 selectBtn"
                        fab
                        dark
                        v-for="select in selectList"
                        :key="select.title"
                        :color="select.color"
                        depressed
                        small
                        @click.stop="selectType"
                        :ripple="false"
                    >
                        {{ select.title }}
                    </v-btn>
                </v-card>
                <v-card
                    class="pa-2"
                    tile
                    flat
                >
                    <v-select 
                        :items="listStd" 
                        item-text="title"
                        item-value="value"
                        :menu-props="{ bottom: true, offsetY: true }"
                        attach
                        style="width: 160px"
                        v-model="defaultSelect"
                    />
                </v-card>
            </v-card>
        </div>
        <v-row>
            <v-badge
                overlap
                left
                offset-x="90"
                offset-y="45"
                v-for="item in itemList"
                :key="item.classId"
                :content="item.classType === 0 ? 'ONLINE' : 'OFFLINE'"
                :color="item.classType === 0 ? '#2255b1' : '#F36A3E'"
                style="padding: 15px"
            >
                <div>
                    <v-img
                        lazy-src="https://picsum.photos/id/11/10/6"
                        height="250"
                        width="365"
                        :src="require(`@/assets/image/class/thumb/${item.classId}/0.jpg`)"
                        @click.stop="goDetail(item)"
                        style="padding-top: 6px"
                    >
                
                        <v-btn
                            :color="item.jjim == 1 ? '#ec68a5' : 'gray'"
                            icon
                            small
                            absolute
                            right
                            @click.stop = "clickHeart"
                        >
                            <v-icon>{{ item.jjim == 1 ? 'mdi-heart' : 'mdi-heart-outline' }}</v-icon>
                        </v-btn>
                    </v-img>
                    <v-card-text 
                        class="my-2"
                        style="font-size: 1.1em; padding: 5px 2px; word-break: keep-all; width: 360px "
                    >
                        <span 
                            @click.stop="goDetail(item)" 
                            class="item-title"
                        >
                            {{ item.className }}
                        </span>
                        <div class="my-2 text-subtitle-1">
                            <span 
                                class="item-catg" 
                                @click.stop="$router.push({ path: '/class/list/'+item.keywordId, }).catch(()=>{$router.go(0)})"
                            >
                                {{ item.keywordName }}
                            </span>
                            {{ ' | ' }}
                            <span
                                class="item-name"
                                @click.stop="$router.push({ path: '/snsUserFeed?memId='+item.memberId }).catch(()=>{$router.go(0)})"
                            >
                                {{ item.nickname }}
                            </span>
                        </div>
                    </v-card-text>
                </div>
            </v-badge>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'ClassList',
    props: {
        catg: {
            type: String,
            default: 'all',
        },
    },
    data() {
        return {
            defaultSelect: {
                value: "0"
            },
            listStd: [
                { title: "최신순", value: "0" },
                { title: "인기순", value: "1" },
                { title: "후기순", value: "2" },
            ],
            heart: true,
            selectList: [
                {
                    title: 'All',
                    click: true,
                    color: '#2255b1',
                },
                {
                    title: 'ON',
                    click: false,
                    color: '#9f9f9f',
                },
                {
                    title: 'OFF',
                    click: false,
                    color: '#9f9f9f',
                },
            ],
            collection: [],
            itemList: [],
        }
    },
    created() {
        this.initList();
    },
    methods: {
        initList() {
            this.$store.state.loading = true;
            this.axios.get('/class/' + this.catg, {
                params: {
                    memberId: this.$store.state.id
                }
            }).then( result => {
                this.collection = result.data;
                this.itemList = this.collection;
                this.selectSort();
                this.typeSort();
                this.$store.state.loading = false;
            }).catch(err => {
                this.$store.state.loading = false;
            });
        },
        selectType: function() {
            let current = event.currentTarget;
            let btnList = document.querySelectorAll('.selectBtn');


            for(let i = 0 ; i < btnList.length; i++) {
                if(btnList[i] === current) {
                    this.selectList[i].color='#2255b1';
                    this.selectList[i].click=true;

                } else {
                    this.selectList[i].color='#9f9f9f';
                    this.selectList[i].click=false;
                }
            }

            this.typeSort();

        },
        typeSort() {
            let btnList = document.querySelectorAll('.selectBtn');
            let result;

            for(let i = 0 ; i < btnList.length; i++) {
                if(this.selectList[i].click==true) {
                    result = i;
                }
            }


            if(result==0) {
                this.itemList = this.collection;
            } else if(result==1) {
                this.itemList = [];
                for(let item of this.collection) {
                    if(item.classType == 0) {
                        this.itemList.push(item);
                    }
                }
            } else if(result==2) {
                this.itemList = [];
                for(let item of this.collection) {
                    if(item.classType == 1) {
                        this.itemList.push(item);
                    }
                }
            }
        },
        clickHeart: function() {
            if(!this.$store.state.id) {
                this.$swal('로그인 후 이용하세요!', '', 'info');
                return;
            }

            let current = event.currentTarget.parentElement.parentElement.parentElement.parentElement;
            let currentList = document.querySelectorAll('.v-badge.v-badge--left.v-badge--overlap.theme--light');

            let itemNum;
            let collectionNum;

            for(let i = 0; i < currentList.length; i++) {
                if(currentList[i] === current) {
                    itemNum = i;
                };
            };
            
            for(let i = 0; i < this.collection.length; i++) {
                if(this.collection[i] === this.itemList[itemNum]) {
                    collectionNum = i;
                }
            };


            this.$store.state.loading = true;
            if(this.itemList[itemNum].jjim == 0) {
                this.axios('/class/jjim', {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json; charset=utf-8",
                    },
                    data: JSON.stringify({
                        targetId: this.itemList[itemNum].classId,
                        targetType: 1,
                        memberId: this.$store.state.id
                    })
                }).then(() => {
                    this.$store.state.loading = false;
                }).catch(error => {
                    this.$store.state.loading = false;
                    console.log(error);
                })
            } else if(this.itemList[itemNum].jjim == 1) {
                this.axios('/class/jjim', {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json; charset=utf-8",
                    },
                    data: JSON.stringify({
                        targetId: this.itemList[itemNum].classId,
                        targetType: 1,
                        memberId: this.$store.state.id
                    })
                }).then(() => {
                    this.$store.state.loading = false;
                }).catch(error => {
                    this.$store.state.loading = false;
                    console.log(error);
                })
            }
            this.$store.state.loading = false;
            
            this.itemList[itemNum].jjim = this.collection[collectionNum].jjim == 1 ? 0 : 1;
            this.collection[collectionNum].jjim = this.itemList[itemNum].jjim;
            
        },
        selectSort: function() {
            if(this.defaultSelect == 0) {
                let collectionSort = this.collection;
                let listSort = this.itemList;
                collectionSort.sort((a, b) => b.classId - a.classId);
                listSort.sort((a, b) => b.classId - a.classId);
            } else if(this.defaultSelect == 1) {
                let collectionSort = this.collection;
                let listSort = this.itemList;
                collectionSort.sort((a, b) => b.jjimTotal - a.jjimTotal);
                listSort.sort((a, b) => b.jjimTotal - a.jjimTotal);
            } else if(this.defaultSelect == 2) {
                let collectionSort = this.collection;
                let listSort = this.itemList;
                collectionSort.sort((a, b) => b.reviewTotal - a.reviewTotal);
                listSort.sort((a, b) => b.reviewTotal - a.reviewTotal);
            };
        },
        goDetail: function(item) {
            if(item.classType === 0) {
                this.$router.push({ path: '/class/on/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
            } else if(item.classType === 1) {
                this.$router.push({ path: '/class/off/'+item.classId+'/info', }).catch(()=>{$router.go(0)});
            }
        }
        
    },
    watch: {
        catg: function() {
            this.initList();
            this.selectSort();
        },
        defaultSelect: function() {
            this.selectSort();
        },
    }

}
</script>

<style scoped>    
    .item-title:hover, .item-catg:hover, .item-name:hover, .v-image:hover {
        cursor: pointer;
    }
    
</style>