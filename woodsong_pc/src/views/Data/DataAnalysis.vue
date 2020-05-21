<template>
    <div id="reports">
        <el-row>
            <el-col :span="12">
                <!-- Elemenet组件 阴影卡片 -->
                <el-card shadow="always">
                    <div class="echarts-num" style="width: 500px;height:500px;"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <!-- Elemenet组件 阴影卡片 -->
                <el-card shadow="always">
                    <div class="echarts-trend" style="width: 500px;height:500px;"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>


</template>
<script>
    // 引入ECharts
    import echarts from 'echarts';
    export default {
        //写了name方便在Vue Devtools谷歌插件应用内找到对应的组件
        name: "DataAnalysis",
        data() {
            return {
                xData:[],
                yData:[],
                x2Data:[],
                y2Data:[],
                codes:''
            }
        },
        mounted(){
            this.showchart(),
                this.useqrcode()
        },
        methods:{
            async showchart(){
                this.$apis.getAssetData().then(response =>{
                    this.xData=response.data.department;
                    this.yData=response.data.count;
                    this.x2Data=response.data.assetClass;
                    this.y2Data=response.data.assetCont;
                    this.charts();
                    this.charts1();
                }).catch(err =>{
                    this.$message.error(err);
                });
            },
            charts(){
                // 基于准备好的dom，初始化echarts实例
                let  myChart = echarts.init(document.querySelector('.echarts-num'));
                // 绘制图表
                myChart.setOption({
                    //添加标题
                    title: {
                        text: '部门资产使用情况'
                    },
                    //添加小贴士
                    tooltip: {},
                    //横坐标
                    xAxis: {
                        type:'category',
                        data: this.xData
                    },
                    //纵坐标
                    yAxis: {
                        type:'value'
                    },
                    //在series属性中，添加图形类型以及数据
                    series: [{
                        data:this.yData,
                        type:'bar'
                    }]
                });

            },
            charts1(){
                // 基于准备好的dom，初始化echarts实例
                let  myChart = echarts.init(document.querySelector('.echarts-trend'));
                myChart.setOption({
                    //添加标题
                    title: {
                        text: '资产类别占比'
                    },
                    //添加小贴士
                    tooltip: {},
                    //横坐标
                    xAxis: {
                        type:'category',
                        data: this.x2Data
                    },
                    //纵坐标
                    yAxis: {
                        type:'value'
                    },
                    //在series属性中，添加图形类型以及数据
                    series: [{
                        data:this.y2Data,
                        type:'line'
                    }]
                });
            }
        }
    };
</script>
<style lang="scss">
    #reports {

    }
</style>







































































































































































