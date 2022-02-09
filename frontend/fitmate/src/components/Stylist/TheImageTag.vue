<template>
  <v-chip draggable
    class="m-1"
    style="background-color:blue; color:white;"
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

</style>