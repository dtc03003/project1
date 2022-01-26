<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2">
        <h1>Stylist</h1>
      </div>
      <!-- 드롭다운 -->
      <div class="btn-group col-2">
        <button class="btn btn-md btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
          Dropdown
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <li><a class="dropdown-item" href="#">문제다</a></li>
          <li><a class="dropdown-item" href="#">문제</a></li>
          <li><a class="dropdown-item" href="#">좀 떠주라</a></li>
        </ul>
      </div>
      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <v-container fluid>
        <v-combobox
          v-model="model"
          :items="items"
          :search-input.sync="search"
          hide-selected
          hint="Maximum of 5 tags"
          label="ex)출근룩, 하객룩 등"
          multiple
          persistent-hint
          small-chips
        >
          <template v-slot:no-data>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>
                  No results matching "<strong>{{ search }}</strong>". Press <kbd>enter</kbd> to create a new one
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-combobox>
      </v-container>

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