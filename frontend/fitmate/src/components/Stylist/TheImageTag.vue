<template>
  <v-chip draggable
    class="m-1"
    id="tag"
  >{{ tag }}
  </v-chip>
</template>

<script>
export default {
  name: 'TheImageTag',
  props:{
    tag:String,
  },
}
</script>

<style>
#tag{
  backface-visibility: visible;
  background-color: rgb(158, 185, 226);
  color: black;
}

</style>