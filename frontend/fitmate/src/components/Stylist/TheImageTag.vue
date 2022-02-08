<template>
  <v-chip draggable
    class="m-1"
    color="indigo"
  >{{tag}}
  </v-chip>
</template>

<script>
export default {
  name: 'TheImageTag',
  props:{
    tag:String,
  }

}
</script>

<style>

</style>