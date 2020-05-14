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
        <div>
            <div id='code'></div>
            <canvas id="canvas"></canvas>
        </div>
    </div>


</template>
<script>
    // 引入ECharts
    import echarts from 'echarts';
    import QRCode from 'qrcode'
    export default {
        //写了name方便在Vue Devtools谷歌插件应用内找到对应的组件
        name: "DataAnalysis",
        components:{
            QRCode: QRCode
        },
        data() {
            return {
                xData:[],
                yData:[],
                codes:''
            }
        },
        mounted(){
            this.showchart(),
                this.useqrcode()
        },
        methods:{
            useqrcode(){
                var canvas = document.getElementById('canvas')

                QRCode.toCanvas(canvas, 'http://www.baidu.com', function (error) {

                    if (error) console.error(error)

                    console.log('success!');

                })

            },
            async showchart(){
                this.$apis.getAssetData().then(response =>{
                    this.xData=response.data.department;
                    this.yData=response.data.count;
                    this.charts();
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

            }
            }
    };
</script>
<style lang="scss">
    #reports {

    }
</style>




























































































