<template>
<v-container>
  <v-expansion-panels
      v-model="panel"
      multiple
      flat
      
    >
      <v-expansion-panel
        v-for="(item,i) in items"
        :key="i"
      >
        <v-expansion-panel-header>
          <v-row justify="start" align="center">
            <v-chip label color="#2b2b2b" dark>{{ i+1 }}</v-chip>
            <div style="padding-left: 10px"><h3>{{ item.chapName }}</h3></div>
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-list
            dense
            nav
          >
            <v-list-item
              v-for="(child,j) in item.currList"
              :key="j"
              link
              @click.stop="pushCheck(child)"
            >
              <div style="font-weight: bold; color:#AAABB7; padding-right: 30px;">{{ j+1 | idx }}</div>
              <!--<div v-if="child.preview == 1" style="margin-right:20px; padding:3px 7px; background-color: #dfeff1; color:#188ba7; border-radius: 5px; font-size: 0.9em;">{{ '미리보기' }}</div>-->
              <v-list-item-content>
                <h3 style="font-size: 1.1em;">{{ child.partName }}</h3>
              </v-list-item-content>
              <div style="font-weight: bold; color:#AAABB7;">{{ child.videoLength | runtime }}</div>
            </v-list-item>
          </v-list>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
</v-container>
</template>

<script>
export default {
  props: {
    classId: {
      type: String,
    }
  },
  created() {
    this.getChapList();
    this.getAttendCheck();
  },
  data() {
    return {
      attend: false,
      panel: [],
      items: [],
    }
  },
  methods: {
    pushCheck(item) {
      if(!this.$store.state.id) {
        this.$swal('로그인 후 이용하세요!', '', 'info');
        return;
      };
      if(!this.attend) {
        this.$swal('강의 결제 후 이용하세요!', '', 'info');
        return;
      };
      this.$router.push({ path: '/learn/'+item.currId, }).catch(()=>{$router.go(0)});
    },
    getAttendCheck() {
      this.axios('/class/pay/'+this.classId, {
        params: {
          memberId: this.$store.state.id,
        },
      }).then(res => {
        if(res.status == 200) {
          if(res.data.classId != null) {
            this.attend = true;
          }
        }
      })
    },
    getChapList() {
      this.axios('/class/chapterList', {
        params: {
          classId: Number(this.classId),
        }
      }).then(res => {
        if(res.status == 200) {
          this.items = res.data;
          for(let i = 0; i < this.items.length; i++) {
            this.panel.push(i);
          };
        }
      })
    },
  },
}
</script>

<style scoped>
  .v-expansion-panel-header{
    border: 1px solid #e0e0e0;
    background-color: #F9F9FB;
  }

</style>