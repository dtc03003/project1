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
    this.color =  _.sample(['aliceblue', 'conflowerblue','beige', 'lemonchiffon', 'lightcyan', 'lightgoldenrodyellow', 'lavender', 'lavenderblush'])
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