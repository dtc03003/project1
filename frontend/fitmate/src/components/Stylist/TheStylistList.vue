<template>
  <div>
    <!-- <h1>스타일리스트 목록</h1> -->
    <the-stylist-list-item></the-stylist-list-item>
      
  </div>
</template>

<script>
import TheStylistListItem from '@/components/Stylist/TheStylistListItem'
import { mapGetters, mapState } from 'vuex'

export default {
  name: 'TheStylistList',
  components:{
    TheStylistListItem,
  },
  data: function() {
    return {}
  },
  methods: {
    getStyles: function() {
      this.$store.dispatch('getStyles')
    },
  },
  computed: {
    ...mapGetters([

    ]),
    ...mapState([

    ])
  },
  created:function() {
    this.getStyles()
  }

}
</script>

<style>

</style>