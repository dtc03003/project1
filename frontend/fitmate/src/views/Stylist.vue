<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-2">
        <h1>Stylist</h1>
      </div>
      <!-- 드롭다운 -->
      <div class="btn-group col-2">
        <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
          Dropdown
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <li><a class="dropdown-item" href="#">Menu item</a></li>
          <li><a class="dropdown-item" href="#">Menu item</a></li>
          <li><a class="dropdown-item" href="#">Menu item</a></li>
        </ul>
      </div>
      <!-- 검색창 -->
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
      </svg>
      <!-- 스타일리스트 목록 -->
      <the-stylist-list></the-stylist-list>
    </div>


  </div>
</template>

<script>
import TheStylistList from '@/components/Stylist/TheStylistList'

export default {
    name: 'Stylist',
    components:{
        TheStylistList,
    },
    data: function() {
        return {
            dropdownItems: [
                {text:'foo'},
                {text:'noo'}, 
                {text:'too'}
                ],
        }
    },
    methods:{}

}
</script>

<style>

</style>