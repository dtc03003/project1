<template>
  <div>
    <!-- <h1>메인페이지-사이트소개</h1> -->
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      :controls="true"
      :indicators="true"
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333"
    >
      <!-- Slides with image only -->
      <b-carousel-slide
        v-for="carousel in carouselSrcList"
        :key="carousel.id"
        :img-src="carousel.img"
      ></b-carousel-slide>
    </b-carousel>
    <p class="mt-4">
      Slide #: {{ slide }}<br />
      Sliding: {{ sliding }}
    </p>
    <!-- <p>{{ memberStore.state.memberInfo }}</p> -->
  </div>
</template>

<script>
  // import HelloWorld from '../components/HelloWorld'
  // import memberStore from '@/store/modules/memberStore'
  import { mapActions, mapState } from "vuex";
  // import axios from 'axios'
  // import {FITMATE_BASE_URL} from '@/config'

  const carouselStore = "carouselStore";

  export default {
    name: "Home",
    data() {
      return {
        slide: 0,
        sliding: null,
        // memberStore
      };
    },
    computed: {
      ...mapState(carouselStore, ["carouselSrcList"]),
    },
    methods: {
      ...mapActions(carouselStore, ["getCarouselImgSrc"]),
      // onSlideStart(slide) {
      //   this.sliding = true;
      //   // console.log(slide);
      // },
      // onSlideEnd(slide) {
      //   this.sliding = false;
      //   // console.log(slide);
      // },
    },
    created() {
      this.getCarouselImgSrc();
      console.log(this.carouselSrcList);
    },
    components: {},
  };
</script>
