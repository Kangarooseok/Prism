<template>

    <v-card outlined>
        <v-card-title>
            ResolveSituation
        </v-card-title>

        <v-card-text>
            <String label="CctvId" v-model="value.cctvId" :editMode="editMode"/>
            <String label="HealthCheckId" v-model="value.healthCheckId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="resolveSituation"
            >
                ResolveSituation
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
    name: 'ResolveSituationCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.cctvId = '';
        this.value.healthCheckId = '';
    },
    watch: {
    },
    methods: {
        resolveSituation() {
            this.$emit('resolveSituation', this.value);
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

