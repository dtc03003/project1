<template>
    <div>
        <v-app>
            <v-dialog v-model="enroll" persistent max-width="600px" flat :close-on-content-click="false" offset-x>
                <v-card min-width="350px" flat>
                    <v-card-title>
                        <span class="text-h5">일정 추가</span>
                        </v-card-title>
                    <v-card-text>
                    <v-container>
                        <v-row>
                            <!--
                                    필요한 내용: 예약자명(닉네임), 예약자이메일, 날짜, 시간
                                -->
                            <v-col cols="12">
                                <v-text-field label="예약자" prepend-icon="mdi-account"></v-text-field>
                                <v-text-field label="예약자이메일" prepend-icon="mdi-email"></v-text-field>
                                <v-text-field label="일정내용" prepend-icon="mdi-pencil"></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field label="일정날짜" prepend-icon="mdi-calendar"></v-text-field>
                                <v-text-field label="시작시간" prepend-icon="mdi-clock"></v-text-field>
                                <v-text-field label="종료시간" prepend-icon="mdi-clock"></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="enroll = false">
                            Close
                        </v-btn>
                        <v-btn color="blue darken-1" text @click="enroll = false">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-app>
    </div>
</template>

<script>
export default {
    name: "SchEvent",
    data() {
        return {
            schedule: [],
        }
    },
    props: {
        enroll : Boolean,
    }
}
</script>

<style>

</style>