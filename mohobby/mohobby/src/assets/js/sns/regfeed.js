const EventBus = new Vue();

const posts = [
   {
    username: 'socleansofreshh',
    userImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/me_3.jpg',
    postImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/tropical_beach.jpg',
    likes: 36,
    upVoted: false,
    caption: "When you're too ready for summer '18 ☀️",
    filter: 'perpetua'
   },
   {
    username: 'djirdehh',
    userImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/me2.png',
    postImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/downtown.jpg',
    likes: 20,
    upVoted: false,
    caption: 'Views from the six...',
    filter: 'clarendon'
   },
   {
    username: 'puppers',
    userImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/pug_personal.jpg',
    postImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/puppers.jpg',
    likes: 49,
    upVoted: false,
    caption: 'Current mood 🐶',
    filter: 'lofi'
  }
]

const filters = [
  { name: 'normal' }, { name: 'clarendon' }, { name: 'gingham' }, { name: 'moon' }, { name: 'lark' }, { name: 'reyes' }, { name: 'juno' }, { name: 'slumber' }, { name: 'aden' }, { name: 'perpetua' }, { name: 'mayfair' }, { name: 'rise' }, { name: 'hudson' }, { name: 'valencia' }, { name: 'xpro2' }, { name: 'willow' }, { name: 'lofi' }, { name: 'inkwell' }, { name: 'nashville' }
]

Vue.component('instagram-post', {
  template: 
  `
    <div class="instagram-post">
      <div class="header level">
          <div class="level-left">
            <figure class="image is-32x32">
              <img :src="post.userImage" />
            </figure>
            <span class="username">{{post.username}}</span>
          </div>
      </div>
      <div class="image-container"
           :class="post.filter"
           :style="{ backgroundImage: 'url(' + post.postImage + ')' }"
           @dblclick="like">
      </div>
      <div class="content">
        <div class="heart">
          <i class="far fa-heart fa-lg"
            :class="{'fas': !this.post.upVoted,  'fas': this.post.upVoted}"
            @click="like">
          </i>
        </div>
        <p class="likes">{{post.likes}} likes</p>
        <p class="caption"><span>{{post.username}}</span> {{post.caption}}</p>
      </div>
    </div>
  `,
  props: ['post'],
  methods: {
    like() {
      this.post.upVoted ? this.post.likes-- : this.post.likes++;
      this.post.upVoted = !this.post.upVoted;
    }
  }
});

Vue.component('filter-type', {
  template:
  `
   <div class="filter-type">
     <p>{{filter.name}}</p>
    <div class="img"
         :class="filter.name"
         :style="{ backgroundImage: 'url(' + image + ')' }"
         @click="selectFilter">
    </div> 
   </div>
  `,
  props: ['filter', 'image'],
  methods: {
    selectFilter() {
      EventBus.$emit('selectFilter', {filter: this.filter.name});
    }
  }
});

new Vue({
  el: "#app",
  data: {
    posts,
    image: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/downtown.jpg',
    caption: '',
    filterType: 'normal',
    step: 1,
    showDetails: false,
    fileInput: ''
  },
  created () {
    EventBus.$on('selectFilter', (evt) => {
      this.filterType = evt.filter;
    })
  },
  methods: {
    fileUpload(e) {
      const files = e.target.files || e.dataTransfer.files;
      if (!files.length) return;
      this.image = files[0];
      this.createImage();
    },
    createImage() {
      const image = new Image();
      const reader = new FileReader();

      reader.onload = e => {
        this.image = e.target.result;
        this.step = 2;
      };
      reader.readAsDataURL(this.image);
    },
    uploadRandomImage() {
      const randomImages = [
        'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/cat.jpg',
        'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/sushi.jpg',
        'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/velvet_monkey.jpg',
        'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/pineapple.jpg',
        'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/tropical_ocean.jpg'
      ];
      
      this.image = randomImages[Math.floor(Math.random() * randomImages.length)];
      this.step = 2;
    },
    goToHome() {
      this.image = 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/downtown.jpg';
      this.caption = '';
      this.filterType = 'normal';
      this.step = 1;
    },
    sharePost() {
      const post = {
        username: 'codepen',
        userImage: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/1211695/codepen_logo.png',
        postImage: this.image,
        likes: 0,
        caption: this.caption,
        filter: this.filterType
      }
      
      this.posts.unshift(post);
      this.goToHome();
    }
  }
});
