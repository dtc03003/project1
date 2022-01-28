<template>
    <div>
        <b-container class="bv-example-row mt-3">
            <b-row>

                <b-col>
                    <img id="stylist" src="@/assets/stylist.png" width="300" height="300">
                </b-col>
                <b-col cols="9">
                    <b-tabs content-class="mt-3" fill pills card>
                        <b-tab class="mx-1" title="Style"><Stylepage/></b-tab>
                        <b-tab title="Review"><Stylepage/></b-tab>
                        <b-tab title="Schedule" active><Schedule/></b-tab>
                        <b-tab title="About"><Stylepage/></b-tab>
                    </b-tabs>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import Schedule from "@/components/portfolio/Schedule.vue";
import Stylepage from "@/components/portfolio/Stylepage"

export default {
    name: "Portfolio",
    components: {
        Schedule,
        Stylepage
    }
}
</script>

<style>
.nav-pills .nav-link:not(.active) {
    background-color: #FFFFFF !important;
    color: #000000 !important;
}
.nav-pills .nav-link.active, .nav-pills .show>.nav-link {
    background-color: #7e7fb9 !important;
    color: #fff !important;
}
.nav-fill .nav-item .nav-link, .nav-justified .nav-item .nav-link {
    width: 90% !important;
}
.card-header {
    background-color: #FFFFFF !important;
    border-bottom: 1px solid rgba(0,0,0,.125);
}
</style>