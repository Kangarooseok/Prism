<template>
    <div>
        <String
            label="LocationName"
            v-model="value.locationName"
            :editMode="editMode"
        />
        <String
            label="LocationAddress"
            v-model="value.locationAddress"
            :editMode="editMode"
        />
        <String
            label="IpAddress"
            v-model="value.ipAddress"
            :editMode="editMode"
        />
        <String
            label="HlsAddress"
            v-model="value.hlsAddress"
            :editMode="editMode"
        />
        <Number
            label="Longitude"
            v-model="value.longitude"
            :editMode="editMode"
        />
        <Number
            label="Latitude"
            v-model="value.latitude"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <Date
            label="UpdatedAt"
            v-model="value.updatedAt"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Cctv',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "cctvs",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
