<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <div class="col-6 offset-4">
        <v-container fluid>
          <v-combobox
            v-model="model"
            :items="items"
            :search-input.sync="search"
            hide-selected
            hint="Maximum of 5 tags"
            label="ex)출근룩, 하객룩"
            multiple
            persistent-hint
            small-chips
          >
 
          </v-combobox>
        </v-container>
      </div>

      <!-- 스타일리스트 목록 컴포넌트 -->
      <!-- 근데 이거 재활용하면 되는 건가... -->
      <h6>컴포넌트 만들까말까 고민중... 아무튼 여기는 사진 뜨는 곳</h6>
      <p>모달은 재활용 예정</p>
    </div>


  </div>
</template>

<script>
export default {
    name:'Stylebook',
    data: () => ({
      items: ['Gaming', 'Programming', 'Vue', 'Vuetify'],
      model: ['Vuetify'],
      search: null,
    }),
}
</script>

<style>

</style>