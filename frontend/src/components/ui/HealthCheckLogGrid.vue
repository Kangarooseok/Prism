<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="scheduleHealthCheckDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>상태 점검 예약
                </v-btn>
                <v-dialog v-model="scheduleHealthCheckDialog" width="500">
                    <ScheduleHealthCheck
                        @closeDialog="scheduleHealthCheckDialog = false"
                        @scheduleHealthCheck="scheduleHealthCheck"
                    ></ScheduleHealthCheck>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="cancelHealthCheckSchedule" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>상태 점검 예약 취소
                </v-btn>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>Timestamp</th>
                        <th>IcmpLatencyMs</th>
                        <th>HlsResponseMs</th>
                        <th>CpuPercent</th>
                        <th>MemoryPercent</th>
                        <th>DiskPercent</th>
                        <th>UptimeSeconds</th>
                        <th>IcmpStatus</th>
                        <th>HlsStatus</th>
                        <th>ResourceStatus</th>
                        <th>FaultDetected</th>
                        <th>CreatedAt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="Timestamp">{{ val.timestamp }}</td>
                            <td class="whitespace-nowrap" label="IcmpLatencyMs">{{ val.icmpLatencyMs }}</td>
                            <td class="whitespace-nowrap" label="HlsResponseMs">{{ val.hlsResponseMs }}</td>
                            <td class="whitespace-nowrap" label="CpuPercent">{{ val.cpuPercent }}</td>
                            <td class="whitespace-nowrap" label="MemoryPercent">{{ val.memoryPercent }}</td>
                            <td class="whitespace-nowrap" label="DiskPercent">{{ val.diskPercent }}</td>
                            <td class="whitespace-nowrap" label="UptimeSeconds">{{ val.uptimeSeconds }}</td>
                            <td class="whitespace-nowrap" label="IcmpStatus">{{ val.icmpStatus }}</td>
                            <td class="whitespace-nowrap" label="HlsStatus">{{ val.hlsStatus }}</td>
                            <td class="whitespace-nowrap" label="ResourceStatus">{{ val.resourceStatus }}</td>
                            <td class="whitespace-nowrap" label="FaultDetected">{{ val.faultDetected }}</td>
                            <td class="whitespace-nowrap" label="CreatedAt">{{ val.createdAt }}</td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">HealthCheckLog 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <HealthCheckLog :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">HealthCheckLog 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="CctvId" v-model="selectedRow.cctvId" :editMode="true"/>
                            <Date label="Timestamp" v-model="selectedRow.timestamp" :editMode="true"/>
                            <Number label="IcmpLatencyMs" v-model="selectedRow.icmpLatencyMs" :editMode="true"/>
                            <Number label="HlsResponseMs" v-model="selectedRow.hlsResponseMs" :editMode="true"/>
                            <Number label="CpuPercent" v-model="selectedRow.cpuPercent" :editMode="true"/>
                            <Number label="MemoryPercent" v-model="selectedRow.memoryPercent" :editMode="true"/>
                            <Number label="DiskPercent" v-model="selectedRow.diskPercent" :editMode="true"/>
                            <Number label="UptimeSeconds" v-model="selectedRow.uptimeSeconds" :editMode="true"/>
                            <String label="IcmpStatus" v-model="selectedRow.icmpStatus" :editMode="true"/>
                            <String label="HlsStatus" v-model="selectedRow.hlsStatus" :editMode="true"/>
                            <String label="ResourceStatus" v-model="selectedRow.resourceStatus" :editMode="true"/>
                            <Boolean label="FaultDetected" v-model="selectedRow.faultDetected" :editMode="true"/>
                            <Date label="CreatedAt" v-model="selectedRow.createdAt" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: 'healthCheckLogGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'healthCheckLogs',
        scheduleHealthCheckDialog: false,
    }),
    watch: {
    },
    methods:{
        async scheduleHealthCheck(params){
            try{
                var path = "scheduleHealthCheck".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','ScheduleHealthCheck 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.scheduleHealthCheckDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async cancelHealthCheckSchedule(){
            try{
                var path = "cancelHealthCheckSchedule".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, null)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','CancelHealthCheckSchedule 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>