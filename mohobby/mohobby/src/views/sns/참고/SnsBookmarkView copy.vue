<template>
    <div>
      <SnsSidebar></SnsSidebar>
      <h1>sns 북마크 화면</h1>
      <div id="app">
        <v-app id="inspire">
          <v-row>
            <v-col
              v-for="n in 9"
              :key="n"
              class="d-flex child-flex"
              cols="4"
            >
              <v-img
                :src="`https://picsum.photos/500/300?image=${n * 5 + 10}`"
                :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
                aspect-ratio="1"
                class="grey lighten-2"
              >
                <template v-slot:placeholder>
                  <v-row
                    class="fill-height ma-0"
                    align="center"
                    justify="center"
                  >
                    <v-progress-circular
                      indeterminate
                      color="grey lighten-5"
                    />
                  </v-row>
                </template>
              </v-img>
            </v-col>
          </v-row>
        </v-app>
      </div>
    </div>
    
  </template>
  <script>
    import SnsSidebar from "@/components/sns/Common/SnsSidebar.vue";
    export default {
      name: "BookmarkCollection",
      components: { SnsSidebar },
      data() {
        return {
          sampleData: "",
        };
      },
      setup() {
        
      },
      created() {
    
      },
      mounted() {
    
      },
      unmounted() {
    
      },
      methods: {
    
      },
    };
    </script>
  
  <style></style>
  