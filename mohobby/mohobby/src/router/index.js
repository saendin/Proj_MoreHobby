import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  // admin
  {
    path: "/adminuser",
    name: "adminuser",
    component: () => import("./../views/admin/AdminUserView"),
  },
  {
    path: "/adminmoim",
    name: "adminmoim",
    component: () => import("./../views/admin/AdminMoimView"),
  },
  {
    path: "/adminclass",
    name: "adminClass",
    component: () => import("./../views/admin/AdminClassView"),
  },
  {
    path: "/adminchallenge",
    name: "adminChallenge",
    component: () => import("./../views/admin/AdminChallengeView"),
  },
  {
    path: "/challengepay",
    name: "challengePay",
    component: () => import("./../views/admin/challengePayView"),
  },
  {
    path: "/adminsns",
    name: "adminsns",
    component: () => import("./../views/admin/AdminSnsView"),
  },
  {
    path: "/classPrepare",
    name: "classPrepare",
    component: () => import("@/views/admin/ClassPrepareView"),
  },
  // main
  {
    path: "/",
    name: "main",
    component: () => import("./../views/main/MainView"),
  },
  // user
  {
    path: "/login",
    name: "login",
    component: () => import("./../views/user/LoginView"),
  },
  {
    path: "/moimFlagging",
    name: "moimFlagging",
    component: () => import("./../components/user/moimFlaggingModal"),
  },
  {
    path: "/userFlagging",
    name: "userFlagging",
    component: () => import("./../components/user/userFlaggingModal"),
  },
  {
    path: "/register",
    name: "register",
    props: true,
    component: () => import("./../views/user/RegisterView"),
  },
  {
    path: "/mainsearch",
    name: "mainsearch",
    props: true,
    component: () => import("./../views/main/MainSearchView"),
  },
  {
    path: "/mypageprofile",
    name: "mypageprofile",
    props: true,
    component: () => import("./../views/user/MypageProfileView"),
  },
  {
    path: "/mypagesns",
    name: "mypagesns",
    props: true,
    component: () => import("./../views/user/MypageSnsView"),
  },
  {
    path: "/mypageclass",
    name: "mypageclass",
    props: true,
    component: () => import("./../views/user/MypageClassView"),
  },
  {
    path: "/mypagemoim",
    name: "mypagemoim",
    props: true,
    component: () => import("./../views/user/MypageMoimView"),
  },
  {
    path: "/mypagechallenge",
    name: "mypagechallenge",
    props: true,
    component: () => import("./../views/user/MypageChallengeView"),
  },
  {
    path: "/edituser",
    name: "edituser",
    props: true,
    component: () => import("./../views/user/EditUserView"),
  },
  // sns
  {
    path: "/sns-main",
    name: "snsmain",
    component: () => import("@/views/sns/SnsMainView"),
  },
  {
    path: "/sns-feed-detail",
    name: "snsFeedDetail",
    component: () => import("@/views/sns/SnsFeedDetailView"),
    props: true,
  },
  {
    path: "/sns-feed-register",
    name: "snsFeedRegister",
    component: () => import("@/views/sns/SnsFeedRegister"),
  },
  {
    path: "/sns-user-feed",
    name: "snsUserFeed",
    component: () => import("@/views/sns/SnsUserFeed"),
  },
  {
    path: "/sns-follow-mgmnt",
    name: "snsFollowMgmnt",
    component: () => import("@/views/sns/management/FollowMgmntView"),
  },
  {
    path: "/sns-feed-mgmnt",
    name: "snsFollowMgmnt",
    component: () => import("@/views/sns/management/FollowMgmntView"),
  },
  {
    path: "/sns-bookmark",
    name: "snsBookmark",
    component: () => import("@/views/sns/bookmark/SnsBookmarkView"),
  },
  {
    path: "/sns-bookmark-detail",
    name: "snsBookmarkDetail",
    component: () => import("@/views/sns/bookmark/SnsBookmarkDetailView"),
  },
  // lecture
  {
    path: "/class",
    name: "classmain",
    component: () => import("./../views/lecture/ClassMainView"),
    children: [
      {
        path: "test",
        name: "ClassTest",
        component: () => import("./../views/lecture/test/ClassTestView"),
        children: [
          {
            path: "OpenBankingTest",
            name: "OpenBankingTest",
            component: () =>
              import("./../components/lecture/test/OpenBankingTest"),
          },
          {
            path: "QuillEditorTest",
            name: "QuillEditorTest",
            component: () =>
              import("./../components/lecture/test/QuillEditorTest"),
          },
          {
            path: "iamportTest",
            name: "iamportTest",
            component: () => import("./../components/lecture/test/iamportTest"),
          },
          {
            path: "AccountRealNameTest",
            name: "AccountRealNameTest",
            component: () =>
              import("./../components/lecture/test/AccountRealNameTest"),
          },
          {
            path: "AttdQRTest",
            name: "AttdQRTest",
            component: () => import("./../components/lecture/test/AttdQRTest"),
          },
          {
            path: "KakaoMapTest",
            name: "KakaoMapTest",
            component: () =>
              import("./../components/lecture/test/KakaoMapTest"),
          },
          {
            path: "VideoTest",
            name: "VideoTest",
            component: () => import("./../components/lecture/test/VideoPlayer"),
          },
          {
            path: "printTest",
            name: "printTest",
            component: () => import("./../components/lecture/test/printTest"),
          },
        ],
      },
      {
        path: "list/:catg",
        name: "ClassList",
        component: () => import("./../views/lecture/list/listAll"),
        props: true,
      },
      {
        path: "on/:classId",
        name: "classOnlineDetail",
        component: () => import("./../views/lecture/list/classOnlineDetail"),
        props: true,
        children: [
          {
            path: "info",
            name: "classInfoOnline",
            component: () => import("./../components/lecture/detail/classInfo"),
            props: true,
          },
          {
            path: "course",
            name: "classCourseOnline",
            component: () =>
              import("./../components/lecture/detail/classCourse"),
            props: true,
          },
          {
            path: "qna",
            name: "classQnaOnline",
            component: () => import("./../components/lecture/detail/classQna"),
            props: true,
          },
          {
            path: "review",
            name: "classReviewOnline",
            component: () =>
              import("./../components/lecture/detail/classReview"),
            props: true,
          },
        ],
      },
      {
        path: "off/:classId",
        name: "classOfflineDetail",
        component: () => import("./../views/lecture/list/classOfflineDetail"),
        props: true,
        children: [
          {
            path: "info",
            name: "classInfoOffline",
            component: () => import("./../components/lecture/detail/classInfo"),
            props: true,
          },
          {
            path: "course",
            name: "classCourseOffline",
            component: () =>
              import("./../components/lecture/detail/classCourse"),
            props: true,
          },
          {
            path: "qna",
            name: "classQnaOffline",
            component: () => import("./../components/lecture/detail/classQna"),
            props: true,
          },
          {
            path: "review",
            name: "classReviewOffline",
            component: () =>
              import("./../components/lecture/detail/classReview"),
            props: true,
          },
          {
            path: "location",
            name: "classLocationOffline",
            component: () =>
              import("./../components/lecture/detail/classLocation"),
            props: true,
          },
        ],
      },
      {
        path: "pay",
        name: "classPay",
        component: () => import("./../views/lecture/pay/classPay"),
        props: true,
      },
      {
        path: "pay/success",
        name: "classPaySuccess",
        component: () => import("./../views/lecture/pay/classPaySuccess"),
        props: true,
      },
      {
        path: "pay/fail",
        name: "classPayFail",
        component: () => import("./../views/lecture/pay/classPayFail"),
        props: true,
      },
      {
        path: "my",
        name: "mycourse",
        component: () => import("./../views/lecture/mycourse/classMycourseMain"),
        children: [
          {
            path: "instudy",
            name: "instudy",
            component: () => import("./../components/lecture/mycourse/inStudy"),
          },
          {
            path: "endstudy",
            name: "endstudy",
            component: () => import("./../components/lecture/mycourse/endStudy"),
          },
          {
            path: "certificate",
            name: "certificatestudy",
            component: () => import("./../components/lecture/mycourse/certificate"),
          },
        ],
      },
      {
        path: "attd",
        name: "myattd",
        component: () => import("./../views/lecture/mycourse/classAttdMain.vue"),
      },
      {
        path: "attd/login",
        name: "attdLogin",
        component: () => import("./../views/lecture/mycourse/classAttdLogin"),
        props: true,
      },
      {
        path: "open",
        name: "classOpenUp",
        component: () => import("./../views/lecture/open/ClassInputView"),
      },
      {
        path: 'mynote',
        name: "MyNoteMain",
        component: () => import("./../views/lecture/note/ClassNoteMain"),
        children: [
          {
            path: "home",
            name: "MyNoteHome",
            component: () => import("./../views/lecture/note/ClassNoteHome"),
            meta: {
              breadCrumb: [
                {
                  text: 'Home'
                }
              ]
            },
          },
          {
            path: "folder/:currId",
            name: "MyNoteFolder",
            component: () => import("./../views/lecture/note/ClassNoteFolder"),
            meta: {
              breadCrumb(VueRouter) {
                const currId = VueRouter.params.currId;
                return [
                  {
                    text: 'Home',
                    to: { name: 'MyNoteHome' }
                  },
                  {
                    text: currId,
                  }
                ]
              }
            },
          },
          {
            path: "action",
            name: "MyNote",
            component: () => import("./../views/lecture/note/ClassNote"),
            meta: {
              breadCrumb(VueRouter) {
                const currId = VueRouter.params.currId;
                return [
                  {
                    text: 'Home',
                    to: { name: 'MyNoteHome' }
                  },
                  {
                    text: currId,
                    to: {
                      name: 'MyNoteFolder',
                      params: {
                        currId: currId
                      }
                    }
                  },
                  {
                    text: 'MyNote'
                  }
                ]
              }
            },
          },
        ]
      },
      {
        path: "myboard",
        name: "ClassMyBoard",
        component: () => import("./../views/lecture/board/ClassMyBoard")
      },
    ],
  },
  // 강의 영상
  {
    path: "/learn/:currId",
    name: "learnVideo",
    component: () => import("./../views/lecture/video/learnMainView"),
    props: true,
  },
  // 수료증 프린트
  {
    path: "/class/my/cert/print",
    name: "certificatePrint",
    component: () => import("./../views/lecture/mycourse/certificatePrint"),
    props: true,
  },
  // moim
  {
    path: "/moimMain",
    name: "moimMain", 
    component: () => import("./../views/moim/MoimMainView"),
  },
  {
    //소모임 생성
    path: "/moimRegisterIn",
    name: "moimRegisterIn",
    component: () => import("./../views/moim/MoimRegisterInView"),
  },
  {
    //게시글
    path: "/moimDetail/:moimId/:moimName/:boardType",
    component: () => import("@/views/moim/MoimDetailView"),
    props: true,
    children: [
      {
        //게시판
        path: "",
        name: "moimBoard",
        component: () => import("@/views/moim/board/MoimBoardView"),
        props: true,
      },
      {
        path: "moimJoin",
        name: "moimJoin",
        component: () => import("@/views/moim/setting/MoimJoinView"),
        props: true,
      },
      {
        //게시물 단건조회
        path: "moimPost",
        name: "moimPost",
        component: () => import("./../views/moim/board/MoimPostView"),
        props: true,
      },
      {
        path: "moimboardwrite",
        name: "moimboardwrite",
        component: () => import("@/views/moim/board/MoimBoardWrite"),
        props: true,
      },
      {
        path: "BoardUpdate",
        name: "BoardUpdate",
        component: () => import("@/views/moim/board/BoardUpdateView"),
        props: true,
      },
      {
        //공지사항
        path: "moimNotice",
        name: "moimNotice",
        component: () => import("./../views/moim/notice/MoimNoticeView"),
        props: true,
      },
      {
        //일정
        path: "moimSchedule",
        name: "moimSchedule",
        component: () => import("./../views/moim/schedule/MoimScheduleView"),
        props: true,
      },
      {
        //투표
        path: "moimVote",
        name: "moimVote",
        component: () => import("./../views/moim/vote/MoimVoteView"),
        props: true,
      },
      {
        //투표 생성
        path: "makeVote",
        name: "makeVote",
        component: () => import("./../views/moim/vote/MakeVoteView"),
      },
      {
        //투표 디테일 메인
        path: "voteDetail",
        name: "voteDetail",
        component: () => import("./../views/moim/vote/VoteDetailView"),
      },
      {
        //N빵 메인
        path: "moimNbbang",
        name: "moimNbbang",
        component: () => import("./../views/moim/Nbbang/MoimNbbangView"),
        props: true,
      },
      {
        //N빵 생성
        path: "makeNbbang",
        name: "makeNbbang",
        component: () => import("./../views/moim/Nbbang/MakeNbbangView"),
        props: true,
      },
      {
        //N빵 디테일
        path: "nBBangDetail",
        name: "nBBangDetail",
        component: () => import("./../views/moim/Nbbang/NbbangDetailView"),
        props: true,
      },
      {
        //소모임 관리하기
        path: "moimSetting",
        name: "moimSetting",
        component: () => import("./../views/moim/setting/MoimSettingView"),
      },
      {
        //소모임 기본설정
        path: "registerSetting",
        name: "registerSetting",
        component: () => import("./../views/moim/setting/RegisterSettingView"),
      },
    ],
  },
  {
    path: "/chat",
    name: "chat",
    component: () => import("./../views/chat/ChatView"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
