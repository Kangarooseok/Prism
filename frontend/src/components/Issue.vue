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
            label="Description"
            v-model="value.description"
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
        <Date
            label="FailureTime"
            v-model="value.failureTime"
            :editMode="editMode"
        />
        <Date
            label="ResolvedAt"
            v-model="value.resolvedAt"
            :editMode="editMode"
        />
        <healthCheckType
            offline
            label="HealthCheckType"
            v-model="value.healthCheckType"
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
    name: 'Issue',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "issues",
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
