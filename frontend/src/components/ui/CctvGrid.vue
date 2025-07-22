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
                <v-btn style="margin-left: 5px;" @click="registerCctvDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>CCTV 등록
                </v-btn>
                <v-dialog v-model="registerCctvDialog" width="500">
                    <RegisterCctv
                        @closeDialog="registerCctvDialog = false"
                        @registerCctv="registerCctv"
                    ></RegisterCctv>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="modifyCctvDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>CCTV 수정
                </v-btn>
                <v-dialog v-model="modifyCctvDialog" width="500">
                    <ModifyCctv
                        @closeDialog="modifyCctvDialog = false"
                        @modifyCctv="modifyCctv"
                    ></ModifyCctv>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="deleteCctv" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>CCTV 삭제
                </v-btn>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>LocationName</th>
                        <th>LocationAddress</th>
                        <th>IpAddress</th>
                        <th>HlsAddress</th>
                        <th>Longitude</th>
                        <th>Latitude</th>
                        <th>CreatedAt</th>
                        <th>UpdatedAt</th>
                        <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="LocationName">{{ val.locationName }}</td>
                            <td class="whitespace-nowrap" label="LocationAddress">{{ val.locationAddress }}</td>
                            <td class="whitespace-nowrap" label="IpAddress">{{ val.ipAddress }}</td>
                            <td class="whitespace-nowrap" label="HlsAddress">{{ val.hlsAddress }}</td>
                            <td class="whitespace-nowrap" label="Longitude">{{ val.longitude }}</td>
                            <td class="whitespace-nowrap" label="Latitude">{{ val.latitude }}</td>
                            <td class="whitespace-nowrap" label="CreatedAt">{{ val.createdAt }}</td>
                            <td class="whitespace-nowrap" label="UpdatedAt">{{ val.updatedAt }}</td>
                            <td class="whitespace-nowrap" label="Status">{{ val.status }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Cctv 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Cctv :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Cctv 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="LocationName" v-model="selectedRow.locationName" :editMode="true"/>
                            <String label="LocationAddress" v-model="selectedRow.locationAddress" :editMode="true"/>
                            <String label="IpAddress" v-model="selectedRow.ipAddress" :editMode="true"/>
                            <String label="HlsAddress" v-model="selectedRow.hlsAddress" :editMode="true"/>
                            <Number label="Longitude" v-model="selectedRow.longitude" :editMode="true"/>
                            <Number label="Latitude" v-model="selectedRow.latitude" :editMode="true"/>
                            <Date label="CreatedAt" v-model="selectedRow.createdAt" :editMode="true"/>
                            <Date label="UpdatedAt" v-model="selectedRow.updatedAt" :editMode="true"/>
                            <String label="Status" v-model="selectedRow.status" :editMode="true"/>
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
    name: 'cctvGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'cctvs',
        registerCctvDialog: false,
        modifyCctvDialog: false,
    }),
    watch: {
    },
    methods:{
        async registerCctv(params){
            try{
                var path = "registerCctv".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','RegisterCCTV 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.registerCctvDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async modifyCctv(params){
            try{
                var path = "modifyCctv".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','ModifyCCTV 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.modifyCctvDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async deleteCctv(){
            try{
                var path = "deleteCctv".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, null)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','DeleteCCTV 성공적으로 처리되었습니다.')
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