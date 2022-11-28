<template>
  <div id="app">
    <!-- file input1 -->
    <input
      ref="fileInput"
      type="file"
      multiple
      @input="pickFile">
    <!-- 이미지 미리보기 -->
    <div v-for="(previewImage,i) in previewImages"
      :key="i" 
      class="imagePreviewWrapper2"
      :style="{ 'background-image': `url(${previewImage})` }"
      @click="selectImage()">
    </div>
    <v-file-input class="input" 
              type="file" 
              counter 
              show-size 
              label="이미지 제출(여러개 가능)"
              outlined 
              dense 
              multiple 
              prepend-icon="mdi-camera" 
              style="width: 400px; margin-left: 100px;"
              @change="onImageChange"/>
    <v-img v-for="(item,i) in uploadimageurl" 
                :key="i" 
                :src="item.url"
                contain height="150px" width="200px" 
                style="border: 2px solid #ededed; margin-left:100px;"/>
        
        <!-- 해시태그 등록부분 -->
        <v-app id="inspire">
          <v-container fluid>
            <v-combobox
              v-model="model"
              :filter="filter"
              :hide-no-data="!search"
              :items="items"
              :search-input.sync="search"
              hide-selected
              label="Search for an option"
              multiple
              small-chips
              solo
            >
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
    </v-app>
</div>
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
    uploadimageurl: [],
    imagecnt: 0,
    
  }),

  watch: {
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
    edit (index, item) {
      if (!this.editing) {
        this.editing = item
        this.editingIndex = index
      } else {
        this.editing = null
        this.editingIndex = -1
      }
    },
   //이미지 미리보기
   selectImage () {
          this.$refs.fileInput.click()
      },
      pickFile () {
        let input = this.$refs.fileInput
        let file = input.files
        if (file && file[0]) {
          let reader = new FileReader
          reader.onload = e => {
            this.previewImage = e.target.result
          }
          reader.readAsDataURL(file[0])
          this.$emit('input', file[0])
        }
      },
  },
 }

 </script>
 <style scoped>
  #att_zone {
  width: 660px;
  min-height: 150px;
  padding: 10px;
  border: 1px dotted #00f;
}

#att_zone:empty:before {
  content: attr(data-placeholder);
  color: #999;
  font-size: .9em;
}
</style>
