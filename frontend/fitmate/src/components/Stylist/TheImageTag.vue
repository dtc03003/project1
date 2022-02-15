<template>
  <v-chip draggable
    class="m-1"
    :style="'background-color:'+color+';'"
    id="tag"
  >{{ tag }}
  </v-chip>
</template>

<script>
import _ from 'lodash'
export default {
  name: 'TheImageTag',
  props:{
    tag:String,
  },
  data:function() {
    return {
      color: ''
    }
  },
  created() {
    this.color =  _.sample([ 'conflowerblue','beige', 'lemonchiffon', 'lightcyan', 'lightgoldenrodyellow','lavender', 'lavenderblush'])
  },
  methods:{
    // 태그 누르면 검색하러 가야하는데 아직 구현 안 됨
    gotoSearch:function(){
      this.$router.push({name:'Stylebook'})
    }
  }
}
</script>

<style>
#tag{
  backface-visibility: visible;
  font-family: 'LeferiPoint-WhiteObliqueA';
  font-weight: bold;
}

</style>