<template>
  <div class="m-1 d-inline-flex">
    <img :src="thumbnail" alt="" height="160px">
  </div>
</template>

<script>
export default {
  name: 'TheStylebookImage',
  data:function() {
    return {}
  },
  props:{
    thumbnail:String,
  }

}
</script>

<style>

</style>