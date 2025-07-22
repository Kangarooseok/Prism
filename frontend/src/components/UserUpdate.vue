<template>

    <v-card outlined>
        <v-card-title>
            UserUpdate
        </v-card-title>

        <v-card-text>
            <String label="Name" v-model="value.name" :editMode="editMode"/>
            <String label="Email" v-model="value.email" :editMode="editMode"/>
            <String label="Role" v-model="value.role" :editMode="editMode"/>
            <String label="AssignedTeam" v-model="value.assignedTeam" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="userUpdate"
            >
                UserUpdate
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
    name: 'UserUpdateCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.name = '';
        this.value.email = '';
        this.value.role = '';
        this.value.assignedTeam = '';
    },
    watch: {
    },
    methods: {
        userUpdate() {
            this.$emit('userUpdate', this.value);
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

