<template>
        <v-container id="main">


                <!-- 검색창 끝 -->
                <div class="mx-auto" style="width:500px;">
                    <v-text-field class="rounded-xl mx-auto" v-model="keyword" label="해시태그나 유저 아이디를 검색해보세요!" dense
                        outlined append-icon="mdi-magnify" @keydown.enter="search(keyword)" style="height:50px" />

                    <!-- 상단바 HOT해시태그 (키워드검색) -->
                    <div id="chip" style="width:500px;">
                        <v-sheet ref="getHashtag">
                            <v-chip-group active-class="primary--text">
                                <v-chip @click="searchHashtag(item.hashtag)" justify="space-around"
                                    v-for="(item, i) in items" :key="i" color="#1862C9"
                                    class="mx-auto white--text font-weight-bold">
                                    #{{ item.hashtag }}
                                </v-chip>
                            </v-chip-group>
                        </v-sheet>
                    </div>
                </div>
                <div v-show = "member" style="float:right">
                        <!-- 내 피드 버튼 -->
                        <v-btn  @click="goMyFeed(member)"
                                title="내 프로필로"
                                icon
                                style="position:relative; font-size: 1px;" 
                                class="mr-5 mx-auto black--text font-weight-bold">
                                <v-icon color="#2255b1"
                                        class="mx-auto mb-1"
                                        style="position:absolute;"
                                        outline
                                        left>
                                    mdi-robot-love
                                </v-icon>
                            </v-btn>
                            
                            <!-- 글쓰기 버튼 -->
                            <v-btn @click="regFeedForm(member)"
                                    title="글쓰기"
                                    icon 
                                    class="mx-auto black--text font-weight-bold">
                                <v-icon color="#2255b1" 
                                        left>
                                        mdi-lead-pencil
                                </v-icon>
                            </v-btn>
                </div>


                <!-- 검색컴포넌트 
                검색결과가 있을땐 show를 트루로 바꿔서 HotList가 안보이게되도록.
                -->

                <!-- HOT강의리스트 -->
                <div v-if="hotLectureFeeds == true">
                    <div>
                        <h3>추천 만능 재주꾼들 피드</h3>
                        <HotLecturer name="this.items" />
                    </div>


                    <!-- 피드 -->
                    <div v-if="randomFeeds == true">
                        <div>
                            <!-- <div v-if="isFollowList == true" id="btn_more">
                                <v-btn rounded 
                                        small 
                                        dark 
                                        color="#2255b1"
                                        @click="getFollowingList(moreBtn)">more</v-btn>
                            </div> -->
                            <div id="btn_more">
                                <v-btn rounded 
                                        small 
                                        dark 
                                        color="#2255b1"
                                        @click="getAllList(moreBtn);">more</v-btn>
                            </div>
                            <div>
                                <h3>{{ feedsTitle }}</h3>
                            </div>
                            <MainFeeds :feeds="feeds" />

                        </div>

                        <div v-show="exceptFollower == true">
                            <div>
                                <div id="btn_more">
                                    <v-btn rounded 
                                            small 
                                            dark 
                                            color="#2255b1"
                                            @click="getNoFollowingList(moreBtn)">more</v-btn>
                                </div>
                                <div>
                                    <h3>{{ subFeedsTitle }}</h3>
                                </div>
                                <MainFeeds :feeds="exceptFollowerFeeds" />

                            </div>
                        </div>
                    </div>
                </div>

                <!-- 피드더보기 -->
                <div v-if="feedMore == true">
                    <!-- 뒤로가기 -->
                    <v-btn  @click="reload()"
                                        color="grey" 
                                        class="mx-auto black--text font-weight-bold"
                                        icon> 
                                <v-icon xx-large>mdi-keyboard-backspace</v-icon>
                    </v-btn>
                    <div id="go_back">
                        <v-card
                            class="d-flex justify-end"
                            flat
                            tile>
                        
                            <v-card
                                class="pa-2"
                                tile
                                flat>
                            
                                <v-select 
                                    :items="selectList" 
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
                    <div>
                        <div id="btn_more_title">
                            <h3>{{ title }}</h3>
                        </div>
                        <MainFeeds :feeds="moreFeeds" />
                    </div>
                </div>


                <!-- 해시태그 검색페이지 : 해시태그 검색-->
                <div v-if="tagSearch == true">
                    <div>
                        <h3>검색페이지입니다</h3>
                        <MainFeeds :feeds="hashResult" :keyword="temp" />
                    </div>
                </div>

                
                <!-- 유저 검색 페이지-->
                <div v-if="userSearch == true">
                    <UserResult :userResult="userResult" :followType="followType" :keyword="temp" />
                </div>

                <div v-if="noResult == true">
                    <NoResult :keyword="temp" />
                </div>
         
        </v-container>


</template>
<script>

import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";
import HotLecturer from "@/components/sns/Main/HotLecturer.vue";
import MainFeeds from "@/components/sns/Main/MainFeeds.vue";
import UserResult from "@/components/sns/Management/User.vue";
import NoResult from "@/components/sns/Search/NoResult.vue";


export default {

    name: "snsMain",

    components: { SnsSidebar, HotLecturer, MainFeeds, UserResult, NoResult },

    data() {
        return {
            offsetTop: 0,
            firstIdx: 0,
            feeds: [],
            exceptFollowerFeeds: [],
            allFeed: [],
            hashResult: [],
            userResult: [],
            items: [], //HOT해시태그

            // searchResult: "",//검색창에서 받아온 결과

            keyword: "", //v-model키워드값
            temp: "", //임시 키워드 저장소
            member: this.$store.state.id,

            //컴포넌트 v-if조건
            tagSearch: false, //해시태그검색페이지
            userSearch: false, //유저검색페이지
            hotLectureFeeds: false, //HOT LIST 페이지
            randomFeeds: false, //ALL LIST 페이지
            noResult: false, //검색결과 없음 페이지
            feedMore: false, //moreBtn 페이지

            exceptFollower: false, //팔로워 이외의 사람들

            isFollowList: false,

            moreBtn: "moreBtn",

            feedsTitle: "",
            subFeedsTitle: "",
            title: "",

            orderCond : "",

            followType: Number,

            defaultSelect: {
                value: "0"
            },
            selectList: [
                { title: "정렬기준", value: "0" },
                { title: "인기순", value: "1" },
                { title: "최신순", value: "2" },
            ],
        }
    },
    
    created() {

        this.getHotHashtags();// 상단바 Hot해시태그
        this.hotLectureFeeds = true;
        this.randomFeeds = true;

        this.getAllList(); //전체리스트
            



        //디테일에서 들어온 해시태그 키워드
        if (this.$route.params.detailHashtag) {
            this.keyword = this.$route.params.detailHashtag;
            this.search(this.keyword);

            this.tagSearch = true;
            this.hotLectureFeeds = false;
            this.randomFeeds = false;
            this.userSearch = false;
            this.feedMore = false;
        }

    },

    watch: {
        //정렬
        defaultSelect(defaultSelect){
            if(defaultSelect == 0) {
                console.log("defaultSelect0");
                this.orderCond = "";
            } else if(defaultSelect == 1) { //인기순
                console.log("defaultSelect1");
                this.orderCond = "sortLike";
            } else if(defaultSelect == 2) { //최신순
                console.log("defaultSelect2");
                this.orderCond = "sortId";
            };
            this.getAllList(this.moreBtn, this.orderCond);
            this.getNoFollowingList(this.moreBtn,this.orderCond);
        }
    },

    methods: {


        reload(){
            this.$router.go(0);
        },


        //AllList조회
        getAllList(moreBtn, orderCond) {
            console.log(orderCond);
            this.axios('/sns/main/allFeeds', {
                params: {
                    memberId : this.member,
                    moreBtn : moreBtn,
                    orderCond : orderCond,
                }
            })
            .then(res => {
                if (moreBtn){
                    this.moreFeeds = res.data;
                    this.feedMore = true;
                    this.hotLectureFeeds = false;
                    this.randomFeeds = false;
                    if(orderCond == "sortId"){
                        this.title = "최신 재주꾼들의 피드입니다 :)";
                        console.log("최신피드더보기 호출 성공");
                    }
                    else if(orderCond == "sortLike"){
                        this.title = "인기 재주꾼들의 피드입니다 :)";
                        console.log("인기피드더보기 호출 성공");
                    }
                    else{
                        this.title = "전체 재주꾼들의 피드입니다 :)";
                        console.log("랜덤피드더보기 호출 성공");
                    }
                } else{
                    this.feeds = res.data;
                    console.log("전체랜덤피드 호출 성공");
                }
                if(this.member){
                    this.feedsTitle = "내가 팔로우 하는 견습생들의 피드를 둘러보세요!";
                    this.getNoFollowingList();
                    console.log("팔로잉목록 호출 성공");
                } else {
                    this.feedsTitle = "재주 견습생들의 피드를 구경해보세요!";
                }
            }).catch(err => {
                console.log(err);
            });
        },


        //전체 피드 페이징조회
        /*getAllListPaging() {
            let vm = this
            this.axios.get("/sns/main/allFeed", {
                params: {
                    firstIdx: this.firstIdx
                }
            }).then(function (res) {
                console.log(res.data)
                for (let i = 0; i < res.data.length; i++) {
                    vm.feeds.push(res.data[i])
                }
                vm.firstIdx = vm.firstIdx + 20
            }).catch(function (err) {
                console.log(err)
            })
        },
        */

        /*
            로그인 한 회원
            팔로잉 있을 떄 -> 팔로잉 피드 우선으로 먼저 출력.(제목 : 내가 팔로우 하는 회원들 중 내가 확인하지 않은 게시물만)
                          -> 팔로잉 피드 제외한 나머지 출력.(제목 : 내가 팔로우 하지 않은 회원들 중 내가 확인하지 않은 게시물만)

            팔로잉 없을 때 -> 전체 피드 출력(제목 : 전체 재주 견습생들 피드 둘러보기)

        */
        //팔로잉 피드리스트 조회
        /*
        getFollowingList(moreBtn) {
            console.log("moreBtn: "+moreBtn);
            this.axios('/sns/main/followingFeeds/' + this.member, {
                params: {
                    moreBtn : moreBtn,
                }
            })
                .then(res => {
                    this.feeds = res.data;
                    if(this.feeds.length < 1) { //피드값이 없으면 전체리스트 호출
                        this.getAllList();
                        console.log("팔로잉목록이 없습니다");
                    }
                    else{
                        if(moreBtn && moreBtn != null && moreBtn != ""){
                            this.moreFeeds = res.data;
                            this.feedMore = true;
                            this.hotLectureFeeds = false;
                            this.randomFeeds = false;
                            this.title = "내가 팔로우 하는 견습생들의 피드입니다 :)";
                            console.log("팔로잉목록 더보기 호출 성공");
                        }
                        else{
                            this.isFollowList = true;
                            this.feedsTitle = "내가 팔로우 하는 견습생들의 피드를 둘러보세요!";
                            this.getNoFollowingList();
                            console.log("팔로잉목록 호출 성공");
                        }
                    }
                }).catch(err => {
                    console.log(err);
                });

        },
        */


        //No팔로잉 리스트 조회
        getNoFollowingList(moreBtn,orderCond){
            console.log("moreBtn: "+moreBtn);
            this.axios('/sns/main/nofollowingFeeds/' + this.member,{
                params: {
                    moreBtn : moreBtn,
                    orderCond : orderCond,
                }
            })
                .then(res => {
                    this.exceptFollowerFeeds = res.data;
                    if(this.exceptFollowerFeeds.length > 0) { //피드값이 없으면 전체리스트 호출
                        this.exceptFollower = true;
                        this.subFeedsTitle = "다른 재주 견습생들의 피드를 구경해보세요!";
                        console.log("NO팔로잉목록 호출 성공");
                    }
                    if(moreBtn && moreBtn != null && moreBtn != ""){
                        this.moreFeeds = res.data;
                        this.feedMore = true;
                        this.hotLectureFeeds = false;
                        this.randomFeeds = false;
                        this.exceptFollower = false;
                        this.title = "다른 재주 견습생들의 피드도 구경해보세요!";
                        console.log("NO팔로잉목록 더보기 호출 성공");
                    }

                }).catch(err => {
                    console.log(err);
                });

        },


        //상단바 - top6해시태그 리스트
        getHotHashtags() {

            this.axios('/sns/main/hashtag')
                .then(res => {
                    this.items = res.data;
                }).catch(err => {
                    console.log(err);
                });

        },


        

        /*
            검색창
              1. '#'가 포함된 검색어 -> 해시태그 검색
              2. 그 외 유저검색
        */
        search(keyword) {

            if (keyword.includes("#")) {
                if(keyword == "#"){
                    this.searchHashtag(keyword);
                }else {
                    let hashtag = keyword.slice(1);
                    this.searchHashtag(hashtag);
                }
            } else { 
                this.searchMem(keyword); 
            }
        },


        //1. 해시태그 검색
        searchHashtag(hashtag) {
            this.temp = "'" + hashtag + "'에 대한 검색결과입니다";

            this.axios('/sns/search/hashtag', {
                params: {
                    hashtag: hashtag
                }
            }).then(res => {

                this.hashResult = res.data;
                this.keyword = "";

                //페이지 노출여부 컨트롤
                if (this.hashResult.length < 1) {
                    this.noResult = true;
                    this.tagSearch = false;
                    this.userSearch = false;
                    this.hotLectureFeeds = false;
                    this.randomFeeds = false;
                    this.feedMore = false;

                } else {
                    this.tagSearch = true;
                    this.noResult = false;
                    this.userSearch = false;
                    this.hotLectureFeeds = false;
                    this.randomFeeds = false;
                    this.feedMore = false;
                }

            }).catch(err => {
                console.log(err);
            });
        },


        //2. 유저검색
        searchMem(keyword) {

            this.temp = "'" + keyword + "'님에 대한 검색결과입니다";
            this.axios('/sns/search/user', {
                params: {
                    memberId: keyword
                }
            }).then(res => {
               
                this.userResult = res.data;
                console.log("res.data: ", res.data)
                this.followType = 3;
                this.keyword = "";

                //페이지 노출여부 컨트롤
                if (this.userResult.length < 1) {
                    this.noResult = true;
                    this.userSearch = false;
                    this.tagSearch = false;
                    this.hotLectureFeeds = false;
                    this.randomFeeds = false;
                    this.feedMore = false;

                } else {
                    this.userSearch = true;
                    this.tagSearch = false;
                    this.hotLectureFeeds = false;
                    this.randomFeeds = false;
                    this.noResult = false;
                    this.feedMore = false;
                }

            }).catch(err => {
                console.log(err);
            });
        },





        //데이터 저장시 배열 선언하여 각각의 정보들을 배열의 요소로 추가하고 한 이름에 대한 정보들은 여러 항목이 있기에 객체로 저장한다.

        //1. 데이터 저장하기
        //enter를 하게 되면 or 등록 버튼을 click을 하게되면 저장이 되어야 함.
        // localStorage.setItem(key, value); //저장할이름, 저장할 내용
        // localStorage.setItem("searchKeyword", JSON.stringify(this.search));


        // objArr = [{num:1, title:'test', contents:'blahblah'}, ...];

        //반복가능한 객체가 아니어서 키와 값 전체를 얻으려면 배열처럼 다뤄야한다.
        // for(let i=0; i<localStorage.length; i++) {
        //     let key = localStorage.key(i);
        //     alert(`${key}: ${localStorage.getItem(key)}`);
        // }

        //데이터 불러오기. 데이터 조회
        //검색창 바디를 클릭하면 기록리스트를 불러온다. 해당 키에 값이 없는 경우 ""로 할당한다.
        // localStorage.getItem(key);
        // userInfo: JSON.parse(localStorage.getItem("searchKeyword")) || "",

        // 키에 해당되는 데이터 삭제
        // localStorage.removeItem(key); 

        // 저장소 데이터 전체 삭제
        // localStorage.clear(); 


        //자바코드를 이용해 삭제해야함.
        //자동으로 삭제되지 않으나 캐시를 사용하는경우 삭제됨

        //window객체의 localStorage에 위치한다.

        //#을 붙여 검색해보세요 -> 해시태그
        // console.log(temp.indexOf('#'))
        // if(temp.indexOf('#') == true)
        //유저검색

        // this.axios('/sns/search/hashtag', {
        //       params : {
        //           hashtag : getHashtag
        //       }
        //   }).then(res => {
        //       this.feeds = res.data;
        //       console.log("검색 성공!");
        //       this.showHashtag = getHashtag;
        //       this.show = true;
        //       this.main = false;
        //       if (this.feeds.length === 0){
        //         this.noResult = true;
        //         this.main = false;
        //       }

        //   }).catch(err =>{
        //       console.log(err);
        //   });


        //글 등록 이동
        select: function () {
            if (this.member) {
                this.$router.push({ path: 'sns-feed-register' })
            }
        },

        //글등록버튼
        regFeedForm(member) {
            if (member == "" || member == null) {
                this.$swal('로그인부터 부탁드립니다🙏');
                this.$router.push({ path: 'login' });
            } else {
                this.$router.push({ path: 'sns-feed-register' });
            }
        },

        //내 피드로 이동
        goMyFeed(member) {
            if (member == undefined || member == null || member == "") {
                return;
            }
            this.$router.push({ name: 'snsUserFeed', query: { userId: member } });
        },

        // 내려오면 api를 호출하여 아래에 더 추가,


    }
};
</script>
  
  



<style scoped lang="css" src="@/assets/css/sns/SnsMain.css" />