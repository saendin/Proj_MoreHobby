<template>
<v-app>
    <v-container style="max-width: 350px;">
        <v-layout row style="padding: 40px 0px 0px 0px;">
            <v-flex xs12>
            <v-card elevation="0">
                <div>
                <h1 style="text-align: center">
                    출결
                </h1>
                <form v-on:submit.prevent="checkMember">
                    <v-text-field
                    label="아이디"
                    prepend-inner-icon="mdi-account"
                    color="#2255b1"
                    v-model="memberId"
                    ></v-text-field>
                    <v-text-field
                    color="#2255b1"
                    prepend-inner-icon="mdi-lock"
                    type="password"
                    label="비밀번호"
                    v-model="password"
                    >
                    </v-text-field>
                    <v-btn
                    type="submit"
                    color="#F9E000"
                    depressed
                    large
                    block
                    class="mb-3"
                    >
                    Login
                    </v-btn>
                    <br />
                    <div class="d-flex justify-center">

                    <!-- <p style="text-align: center;"> -->
                        <a id="custom-login-btn" @click="kakaoLogin()">
                        <img
                            src="https://www.gb.go.kr/Main/Images/ko/member/certi_kakao_login.png"
                            style="height: 40px;
                            width: 185px;
                            margin-right: 5px;
                            "
                        />
                        </a>
                    </div>
                    <div class="d-flex justify-center">
                        <!-- </p> -->
                        <!-- <p style="text-align: center;"> -->
                        <div id="naverIdLogin"></div>
                    </div>
                </form>
                </div>
            </v-card>
            </v-flex>
        </v-layout>
        </v-container>
    </v-app>
</template>

<script>
export default {
    data() {
        return {
            id: this.$route.query.id,
            currId: this.$route.query.currId,
            type: this.$route.query.type,
            connect: this.$route.query.connect,
            time: this.$route.query.time,
            memberId: '',
            password: '',
            loginInfo: null,
            email: '',
            token: '',
            now: this.$moment().format('YYYY-MM-DD HH:mm:ss'),
            attdType: {
                0: 'ATTD_DATE',     //출석
                1: 'LEAVE_DATE',    //조퇴
                2: 'OUT_DATE',      //외출
                3: 'RECORD_TYPE',   //결석
                4: 'EXIT_DATE',     //퇴실
                5: 'COMEBACK_DATE'  //복귀
            },
            naverLogin: null,
        }
    },
    mounted() {
            // login페이지 띄울때 무조건 로그아웃
        this.$store.commit("setIsLoginFalse");
        this.$store.commit("logout");
        this.$store.commit("setUserData", null);

        this.naverLogin = new window.naver.LoginWithNaverId({
        clientId: "qtK4gDKw7gcdHhTwYZpV",
        callbackUrl: "http://localhost:8080/login",
        isPopup: false, // 팝업을 통한 연동 처리여부
        loginButton: {
            color: "green",
            type: 3,
            height: 40,
            width: 150,
        }, // 로그인 버튼의 타입을 지정
        });

        // 설정 정보를 초기화하고 연동을 준비
        this.naverLogin.init();

        this.naverLogin.getLoginStatus((status) => {
        const vm = this;
        if (status) {
            console.log(status);
            console.log(this.naverLogin.user);

            // 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크
            var email = this.naverLogin.user.getEmail();
            if (email == "undifined" || email == null) {
            alert("이메일은 필수 정보입니다. 정보 제공을 동의해주세요.");
            // 사용자 정보 재동의를 위해 다시 네이버 동의 페이지로 이동함
            this.naverLogin.reprompt();
            return;
            }
            vm.email = email;
            vm.checkMemberByEmail();
        } else {
            console.log("callback 처리에 실패하였습니다.");
        }
        });
    },
    created() {
        document.querySelector("header").innerHTML='';
        this.checkTimeLimit();
    },
    methods: {
        kakaoLogin() {
            const vm = this;
            window.Kakao.Auth.loginForm({
                scope: "profile_nickname, account_email, gender, birthday",
                success: function (response) {
                window.Kakao.API.request({
                    // 사용자 정보 가져오기
                    url: "/v2/user/me",
                    success: (res) => {
                    const kakao_account = res.kakao_account;
                    console.log(kakao_account);

                    vm.token = kakao_account.access_token;
                    vm.email = kakao_account.email;

                    vm.checkMemberByEmail();
                    },
                });
                },
                fail: function (error) {
                console.log(error);
                },
            });
        },
        checkMember() {
            if(this.memberId == '' || this.password == '') {
                this.$swal({
                    icon: "info",
                    title: "아이디, 비밀번호를 입력하세요.",
                });
            } else {
                const vm = this;
                this.axios({
                    url: "/membercheck",
                    method: "post",
                    data: {
                    memberId: vm.memberId,
                    password: vm.password,
                    },
                }).then(function (response) {
                    if (response.data != "" && response.data.memberId != "admin") {
                        if (response.data.delDate != null) {
                            vm.$swal.fire("탈퇴한 회원입니다.");
                            return;
                        }
                        
                        if(vm.memberId == vm.id) {
                            vm.setAttdStatus();
                        } else {
                            vm.$swal.fire({
                                icon: "error",
                                title: "출석 정보가 일치하지 않습니다.",
                            });
                        }
    
                        vm.memberId = '';
                        vm.password = '';
    
                    } else {
                        vm.$swal.fire({
                            icon: "error",
                            title: "아이디, 비밀번호가 일치하지 않습니다.",
                        });
                    }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }

        },
        checkMemberByEmail() {
            const vm = this;
            this.axios({
                url: "/memberEmail/" + this.email,
                method: "get",
            }).then(function (response) {
                if (response.data !== "" && response.data.constructor === Object) {
                    //this.$store.commit("setId", this.memberId);
                    if (response.data.delDate != null) {
                        vm.$swal.fire("탈퇴한 회원입니다.");
                        return;
                    }
                    this.$router.push("/");
                } else {
                    vm.$swal.fire({
                        icon: "error",
                        title: "등록되지 않은 계정입니다.",
                    });
                }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        setAttdStatus() {
            let req = {
                memberId: this.id,
                currId: this.currId,
                type: this.type,
                column: this.attdType[`${this.type}`],
                value: this.type == 3 ? '3' : this.now,
            };
            let time = this.time + ':00';
            let nowTime = this.$moment(this.now).format('HH:mm');
            if(this.type == 0 && time < nowTime) {
                req.lateCheck=1;
                this.$swal('출석이 완료되었습니다!', '지각이지만.. 화이팅!', 'success');
            } else if (this.type == 1) {
                this.$swal('조퇴가 완료되었습니다!', '', 'success');
            } else if (this.type == 2) {
                this.$swal('외출이 완료되었습니다!', '', 'success');
            } else if (this.type == 4) {
                this.$swal('퇴실이 완료되었습니다!', '', 'success');
            } else if (this.type == 5) {
                this.$swal('복귀가 완료되었습니다!', '', 'success');
            }

            this.send(req);
        },
        send(req) {
            this.axios.post('/class/attd', req);
        },
        checkTimeLimit() {
            let now = this.now;
            let connect = this.connect.substr(0, 4)+'-'+this.connect.substr(4, 2)+'-'+this.connect.substr(6, 2)+' '
                            +this.connect.substr(8, 2)+':'+this.connect.substr(10, 2)+':'+this.connect.substr(12, 2);
            let income = this.$moment(connect).format('YYYY-MM-DD HH:mm:ss');
            let limit = this.$moment(income).add(5, "m").format('YYYY-MM-DD HH:mm:ss');
            if(!(limit>now)) {
                document.body.innerHTML = '';
                this.$swal('시간이 초과되었습니다!', '출결QR을 재발급 받으세요.', 'error');
            };
        },
    }
}
</script>

<style>

</style>