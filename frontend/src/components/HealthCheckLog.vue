<template>
    <div>
        <String
            label="CctvId"
            v-model="value.cctvId"
            :editMode="editMode"
        />
        <Date
            label="Timestamp"
            v-model="value.timestamp"
            :editMode="editMode"
        />
        <Number
            label="IcmpLatencyMs"
            v-model="value.icmpLatencyMs"
            :editMode="editMode"
        />
        <Number
            label="HlsResponseMs"
            v-model="value.hlsResponseMs"
            :editMode="editMode"
        />
        <Number
            label="CpuPercent"
            v-model="value.cpuPercent"
            :editMode="editMode"
        />
        <Number
            label="MemoryPercent"
            v-model="value.memoryPercent"
            :editMode="editMode"
        />
        <Number
            label="DiskPercent"
            v-model="value.diskPercent"
            :editMode="editMode"
        />
        <Number
            label="UptimeSeconds"
            v-model="value.uptimeSeconds"
            :editMode="editMode"
        />
        <String
            label="IcmpStatus"
            v-model="value.icmpStatus"
            :editMode="editMode"
        />
        <String
            label="HlsStatus"
            v-model="value.hlsStatus"
            :editMode="editMode"
        />
        <String
            label="ResourceStatus"
            v-model="value.resourceStatus"
            :editMode="editMode"
        />
        <Boolean
            label="FaultDetected"
            v-model="value.faultDetected"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
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
    name: 'HealthCheckLog',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "healthCheckLogs",
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
