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
                <v-btn style="margin-left: 5px;" @click="dispatchToSiteDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>현장 출동 지시
                </v-btn>
                <v-dialog v-model="dispatchToSiteDialog" width="500">
                    <DispatchToSite
                        @closeDialog="dispatchToSiteDialog = false"
                        @dispatchToSite="dispatchToSite"
                    ></DispatchToSite>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="raiseNetworkAlarmDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>네트워크 알람 발생
                </v-btn>
                <v-dialog v-model="raiseNetworkAlarmDialog" width="500">
                    <RaiseNetworkAlarm
                        @closeDialog="raiseNetworkAlarmDialog = false"
                        @raiseNetworkAlarm="raiseNetworkAlarm"
                    ></RaiseNetworkAlarm>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="resolveNetworkIssueDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>네트워크 이슈 해결
                </v-btn>
                <v-dialog v-model="resolveNetworkIssueDialog" width="500">
                    <ResolveNetworkIssue
                        @closeDialog="resolveNetworkIssueDialog = false"
                        @resolveNetworkIssue="resolveNetworkIssue"
                    ></ResolveNetworkIssue>
                </v-dialog>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>CctvId</th>
                        <th>ActionType</th>
                        <th>Status</th>
                        <th>PerformedBy</th>
                        <th>Result</th>
                        <th>CreatedAt</th>
                        <th>UpdatedAt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="CctvId">{{ val.cctvId }}</td>
                            <td class="whitespace-nowrap" label="ActionType">{{ val.actionType }}</td>
                            <td class="whitespace-nowrap" label="Status">{{ val.status }}</td>
                            <td class="whitespace-nowrap" label="PerformedBy">{{ val.performedBy }}</td>
                            <td class="whitespace-nowrap" label="Result">{{ val.result }}</td>
                            <td class="whitespace-nowrap" label="CreatedAt">{{ val.createdAt }}</td>
                            <td class="whitespace-nowrap" label="UpdatedAt">{{ val.updatedAt }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">NetworkAction 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <NetworkAction :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">NetworkAction 수정</div>
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
                            <String label="Status" v-model="selectedRow.status" :editMode="true"/>
                            <String label="PerformedBy" v-model="selectedRow.performedBy" :editMode="true"/>
                            <String label="Result" v-model="selectedRow.result" :editMode="true"/>
                            <Date label="CreatedAt" v-model="selectedRow.createdAt" :editMode="true"/>
                            <Date label="UpdatedAt" v-model="selectedRow.updatedAt" :editMode="true"/>
                            <actionType offline label="ActionType" v-model="selectedRow.actionType" :editMode="true"/>
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
    name: 'networkActionGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'networkActions',
        dispatchToSiteDialog: false,
        raiseNetworkAlarmDialog: false,
        resolveNetworkIssueDialog: false,
    }),
    watch: {
    },
    methods:{
        async dispatchToSite(params){
            try{
                var path = "dispatchToSite".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','DispatchToSite 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.dispatchToSiteDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async raiseNetworkAlarm(params){
            try{
                var path = "raiseNetworkAlarm".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','RaiseNetworkAlarm 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.raiseNetworkAlarmDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async resolveNetworkIssue(params){
            try{
                var path = "resolveNetworkIssue".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','ResolveNetworkIssue 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.resolveNetworkIssueDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>