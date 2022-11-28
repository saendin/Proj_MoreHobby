<template>
  <v-card
    class="mx-auto"
    max-width="80%"
    outlined
  >
  <!-- 파일 업로드 -->
  <v-container fluid>
    <v-file-input
        class="mx-auto" 
        label="이미지 파일을 등록해주세요!(jpg,png,jpeg 형식만 가능)"
        type="file"
        filled
        prepend-icon="mdi-camera"
        counter
        show-size
        dense
        multiple
        @change="onImageChange"
      />
  </v-container>
    <!-- 이미지 미리보기 -->
    <!-- <v-container fluid> -->
    <div style="display:inline-flex; margin-left: 10px;">
      <v-img v-for="(item,i) in uploadimageurl" 
            :key="i" 
            :src="item.url"
            aspect-ratio="4/3"
            height="150px" 
            width="200px"
            lazy-src
            error 
            style="margin-right: 10px;"
      />
    </div>
    <!-- </v-container> -->
    <!-- content -->
    <v-container fluid>
      <v-textarea
        name="content"
        auto-grow
        placeholder="내용을 입력해주세요!"
        value=""
      ></v-textarea>
    </v-container>
    <!-- 해시태그 -->
    <v-container fluid>
            <v-combobox
              v-model="model"
              :hide-no-data="!search"
              :items="items"
              :search-input.sync="search"
              hide-selected
              label="Search for an option"
              multiple
              small-chips
              solo
            >
            <!-- :filter="filter" -->
          <template v-slot:no-data>
            <v-list-item>
              <span class="subheading">Create</span>
              <v-chip
                :color="`${colors[nonce - 1]} lighten-3`"
                label
                small
              >
                {{ search }}
              </v-chip>
            </v-list-item>
          </template>
          <template v-slot:selection="{ attrs, item, parent, selected }">
            <v-chip
              v-if="item === Object(item)"
              v-bind="attrs"
              :color="`${item.color} lighten-3`"
              :input-value="selected"
              label
              small
            >
              <span class="pr-2">
                {{ item.text }}
              </span>
              <v-icon
                small
                @click="parent.selectItem(item)"
              >
                $delete
              </v-icon>
            </v-chip>
          </template>
          <template v-slot:item="{ index, item }">
            <v-text-field
              v-if="editing === item"
              v-model="editing.text"
              autofocus
              flat
              background-color="transparent"
              hide-details
              solo
              @keyup.enter="edit(index, item)"
            ></v-text-field>
            <v-chip
              v-else
              :color="`${item.color} lighten-3`"
              dark
              label
              small
            >
              {{ item.text }}
            </v-chip>
            <v-spacer></v-spacer>
            <v-list-item-action @click.stop>
              <v-btn
                icon
                @click.stop.prevent="edit(index, item)"
              >
                <v-icon>{{ editing !== item ? 'mdi-pencil' : 'mdi-check' }}</v-icon>
              </v-btn>
            </v-list-item-action>
          </template>
        </v-combobox>
      </v-container>
      <v-btn @click="uploadImage">uploadImage</v-btn>
  </v-card>
</template>
<script>
 
 export default {
  data: () => ({
    activator: null,
    attach: null,
    colors: ['green', 'purple', 'indigo', 'cyan', 'teal', 'orange'],
    editing: null,
    editingIndex: -1,
    items: [
      { header: 'Select an option or create one' },
      {
        text: '오운완',
        color: 'blue',
      },
      {
        text: '오수완',
        color: 'red',
      },
    ],
    nonce: 1,
    menu: false,
    model: [
      {
        text: 'Foo',
        color: 'blue',
      },
    ],
    x: 0,
    search: null,
    y: 0,
    previewImages: [], //이미지업로드1
    uploadimageurl: [], //이미지업로드2
    imagecnt: 0,//업로드한 이미지개수 axious시에 넘겨줌
  }),
  watch: {
    //해시태그
    model (val, prev) {
      if (val.length === prev.length) return

      this.model = val.map(v => {
        if (typeof v === 'string') {
          v = {
            text: v,
            color: this.colors[this.nonce - 1],
          }

          this.items.push(v)

          this.nonce++
        }
        return v
      })
    },
  },
  methods: {
    //해시태그수정
    edit (index, item) {
      if (!this.editing) {
        this.editing = item
        this.editingIndex = index
      } else {
        this.editing = null
        this.editingIndex = -1
      }
    },
     //이미지2
     //이미지 조건 처리
    fileChange: function (e) {
      const file = e.target.files;
      let validation = true;
      let message = '';

      if (file.length > 5) {
          validation= false;
          message = `파일은 5개 까지만 등록 가능합니다.`
      }

      if (file[0].size > 1024 * 1024 * 2) {
          message = `${message}, 파일은 용량은 2MB 이하만 가능합니다.`;
          validation = false;
      }

      if (file[0].type.indexOf('image') < 0) {
          message = `${message}, 이미지 파일만 업로드 가능합니다.`;
          validation = false;
      }

      if (validation) {
          this.file = file
      }else {
          this.file = '';
          alert(message);
      }
    },
    onImageChange(file) {	// v-file-input 변경시
      if (!file) {
        console.log(file);
        return;
      }
      const formData = new FormData();	// 파일을 전송할때는 FormData 형식으로 전송
      console.log(formData)
      this.uploadimageurl = [];		// uploadimageurl은 미리보기용으로 사용
      console.log(this.uploadimageurl[0])
      file.forEach((item) => {
        console.log(item.name)//name:파일명, size:바이트(인듯),type:image/png
        formData.append('filelist', item)	// formData의 key: 'filelist', value: 이미지
        const reader = new FileReader();
        console.log(reader)//
        reader.onload = (e) => {
          console.log({url: e.target.result})
          this.uploadimageurl.push({url: e.target.result});
          // e.target.result를 통해 이미지 url을 가져와서 uploadimageurl에 저장
        };
        reader.readAsDataURL(item);
      });
    },
    uploadImage() {
      this.axios({
          url: "/sns/myfeed",	// 이미지 저장을 위해 back서버와 통신
          method: "POST",
          headers: {'Content-Type': 'multipart/form-data'},	// 이걸 써줘야 formdata 형식 전송가능
          data: formData,
        }).then(res => {
          console.log(res.data.message);
          this.imagecnt = file.length;	// 이미지 개수 저장
        }).catch(err => {
          alert(err);
        });
    },
 }
}
 </script>
 <style scoped>
 .imagePreviewWrapper2 {
    width: 250px;
    height: 250px;
    display: block;
    cursor: pointer;
    margin: 0 auto 30px;
    background-size: cover;
    background-position: center center;
 }
</style>