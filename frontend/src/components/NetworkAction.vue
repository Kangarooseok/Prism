<template>
    <div>
        <String
            label="CctvId"
            v-model="value.cctvId"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <String
            label="PerformedBy"
            v-model="value.performedBy"
            :editMode="editMode"
        />
        <String
            label="Result"
            v-model="value.result"
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
        <actionType
            offline
            label="ActionType"
            v-model="value.actionType"
            :editMode="editMode"
            @change="change"
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
    name: 'NetworkAction',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "networkActions",
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
