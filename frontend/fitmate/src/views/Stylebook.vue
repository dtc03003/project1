<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <div class="col-6 offset-2">
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
      </div>

      <!-- 스타일리스트 목록 컴포넌트 -->
      <!-- 근데 이거 재활용하면 되는 건가... -->
    </div>


  </div>
</template>

<script>
export default {
    name:'Stylebook',

}
</script>

<style>

</style>