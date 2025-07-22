<template>
    <div>
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <Boolean
            label="IsActive"
            v-model="value.isActive"
            :editMode="editMode"
        />
        <alertType
            offline
            label="AlertType"
            v-model="value.alertType"
            :editMode="editMode"
            @change="change"
        />
        <severityLevel
            offline
            label="SeverityLevel"
            v-model="value.severityLevel"
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
    name: 'AlertSubscription',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "alertSubscriptions",
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
