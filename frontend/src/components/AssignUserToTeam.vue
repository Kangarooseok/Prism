<template>

    <v-card outlined>
        <v-card-title>
            AssignUserToTeam
        </v-card-title>

        <v-card-text>
            <String label="AssignedTeam" v-model="value.assignedTeam" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="assignUserToTeam"
            >
                AssignUserToTeam
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
    name: 'AssignUserToTeamCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.assignedTeam = '';
    },
    watch: {
    },
    methods: {
        assignUserToTeam() {
            this.$emit('assignUserToTeam', this.value);
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

