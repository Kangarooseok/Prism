<template>

    <v-card outlined>
        <v-card-title>
            SubscribeToAlert
        </v-card-title>

        <v-card-text>
            <String label="UserId" v-model="value.userId" :editMode="editMode"/>
            <alertType offline label="AlertType" v-model="value.alertType" :editMode="editMode" @change="change"/>
            <severityLevel offline label="SeverityLevel" v-model="value.severityLevel" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="subscribeToAlert"
            >
                SubscribeToAlert
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'SubscribeToAlertCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.userId = '';
        this.value.alertType = {};
        this.value.severityLevel = {};
    },
    watch: {
    },
    methods: {
        subscribeToAlert() {
            this.$emit('subscribeToAlert', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

