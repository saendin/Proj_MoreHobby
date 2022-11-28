<template>
  <main>
    <template>
      <v-row justify="space-around">
        <v-col cols="auto">
          <v-dialog transition="dialog-top-transition" max-width="600">
            <template v-slot:activator="{ on, attrs }" >
              <span v-if="dataList.length==0" color="none" v-bind="attrs" v-on="on">
                <!-- {{dataList.length + text}} -->
                {{  followCnt  }}
              </span>
              <span v-else color="none" v-bind="attrs" v-on="on">
                <!-- {{(dataList.length+1)/2 + text}} -->
                {{  followCnt  }}
              </span>
              <span color="none" v-bind="attrs" v-on="on">
              </span>
            </template>
            <template v-slot:default="dialog">
              <v-card>
                <v-toolbar color="#2255b1" dark>
                  {{ text }} list
                </v-toolbar>
                <v-list three-line>
                  <template v-for="(data, index) in dataList">
                    <v-subheader
                      v-if="data.header"
                      :key="data.header"
                      v-text="data.header"
                    />
                    <v-divider
                      v-else-if="data.divider"
                      :key="data.followId"
                      :inset="data.inset"
                    />
                    <v-list-item v-else :key="index">
                      <v-list-item-avatar style="cursor:pointer;">
                        <v-img v-if="text == 'follower'"
                          :src="require(`@/assets/image/user/${data.profileImg}`)"
                          @click="goUserProfile(data.followerId)" />

                          <v-img v-else-if="text == 'following'"
                          :src="require(`@/assets/image/user/${data.profileImg}`)"
                          @click="goUserProfile(data.followingId)" />
                      </v-list-item-avatar>

                      <v-list-item-content>
                      
                        <v-list-item-title 
                          v-if="text == 'follower' && dataList.length > 0"
                          v-html="data.followerId"
                          @click="goUserProfile(data.followerId)"
                          style="cursor:pointer;"  />

                        <v-list-item-title
                          v-else-if="text == 'following'"
                          v-html="data.followingId"
                          @click="goUserProfile(data.followingId)"
                          style="cursor:pointer;" />

                        <v-list-item-subtitle v-html="data.nickname"/>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-list>
                <v-card-actions class="justify-end">
                  <v-btn text @click="dialog.value = false"> Close </v-btn>
                </v-card-actions>
              </v-card>
            </template>
          </v-dialog>
        </v-col>
      </v-row>
    </template>
  </main>
</template>
<script>
/**
 * 팔로워 누르면 title쪽에 follower가
 * 팔로잉 누르면 title쪽에 follwing이 뜨도록
 *
 * follower닉네임, 아이디 제대로 출력되도록
 *
 */
export default {
  name: "",
  components: {},
  props: {
    text: {
      type: String,
      required: true, //props 무조건 받아야함.
    },
    dataList : {},
    followCnt: String,
    // divider: true,
    // inset: true,
  },
  beforeCreate() {},
  created() {
  },
  beforeMount() {},
  mounted() {
  },
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    goUserProfile(userId) {
      this.$router.push({ path: "/sns-user-feed", query: { userId: userId } }).catch(()=>{});
      this.$router.go(0);
    },

  },
};
</script>
