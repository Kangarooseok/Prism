<template>

    <v-card outlined>
        <v-card-title>
            ResolveNetworkIssue
        </v-card-title>

        <v-card-text>
            <String label="CctvId" v-model="value.cctvId" :editMode="editMode"/>
            <actionType offline label="ActionType" v-model="value.actionType" :editMode="editMode" @change="change"/>
            <String label="PerformedBy" v-model="value.performedBy" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="resolveNetworkIssue"
            >
                ResolveNetworkIssue
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
    name: 'ResolveNetworkIssueCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.cctvId = '';
        this.value.actionType = {};
        this.value.performedBy = '';
    },
    watch: {
    },
    methods: {
        resolveNetworkIssue() {
            this.$emit('resolveNetworkIssue', this.value);
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

