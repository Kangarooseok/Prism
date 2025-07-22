<template>

    <v-card outlined>
        <v-card-title>
            CreateNotification
        </v-card-title>

        <v-card-text>
            <String label="Receiver" v-model="value.receiver" :editMode="editMode"/>
            <String label="Message" v-model="value.message" :editMode="editMode"/>
            <String label="Status" v-model="value.status" :editMode="editMode"/>
            <String label="HealthCheckId" v-model="value.healthCheckId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="createNotification"
            >
                CreateNotification
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
    name: 'CreateNotificationCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.receiver = '';
        this.value.message = '';
        this.value.status = '';
        this.value.healthCheckId = '';
    },
    watch: {
    },
    methods: {
        createNotification() {
            this.$emit('createNotification', this.value);
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

