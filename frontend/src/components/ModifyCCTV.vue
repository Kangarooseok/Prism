<template>

    <v-card outlined>
        <v-card-title>
            ModifyCctv
        </v-card-title>

        <v-card-text>
            <String label="LocationName" v-model="value.locationName" :editMode="editMode"/>
            <String label="LocationAddress" v-model="value.locationAddress" :editMode="editMode"/>
            <String label="IpAddress" v-model="value.ipAddress" :editMode="editMode"/>
            <String label="HlsAddress" v-model="value.hlsAddress" :editMode="editMode"/>
            <Number label="Longitude" v-model="value.longitude" :editMode="editMode"/>
            <Number label="Latitude" v-model="value.latitude" :editMode="editMode"/>
            <String label="Status" v-model="value.status" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="modifyCctv"
            >
                ModifyCctv
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
    name: 'ModifyCctvCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.locationName = '';
        this.value.locationAddress = '';
        this.value.ipAddress = '';
        this.value.hlsAddress = '';
        this.value.longitude = 0;
        this.value.latitude = 0;
        this.value.status = '';
    },
    watch: {
    },
    methods: {
        modifyCctv() {
            this.$emit('modifyCctv', this.value);
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

