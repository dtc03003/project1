<template>
    <div class="row ">
        <div class="temcontainer">
            <img src="@/assets/eunwoo.jpg" class="item" @click="$bvModal.show('bv-modal-example')">
            <img src="@/assets/stylist.png" class="item">
            <img src="@/assets/yeonwoo.jpg" class="item">
            <img src="@/assets/logo.png" class="item">
            <img src="@/assets/normal.png" class="item">
            <img src="@/assets/study.jpg" class="item">
        </div>
        <b-modal size="lg" id="bv-modal-example" hide-footer>
            <template #modal-title>
            Using <code>$bvModal</code> Methods
            </template>
            <div class="row">
                <div class="col-6">
                    <img src="@/assets/eunwoo.jpg" class="item">
                </div>
                <div class="col-6">
                    <the-modal-comment/>
                </div>
            </div>
        </b-modal>
    </div>
</template>

<script>
import TheModalComment from "@/components/Stylist/TheModalComment"
export default {
    components: {
        TheModalComment,
    }
}
</script>

<style>
.temcontainer {
  display: grid;
  grid-template-columns: 25% 25% 25% 25%;
  padding: 10px;
  background-size: cover;
}
.item {
  width: 100%; height: 90%;
  object-fit: cover;
  padding: 10px;
  font-size: 30px;
  align-self: center;
}
</style>