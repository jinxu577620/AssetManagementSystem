<template>
        <span>{{LocationProvince}}{{LocationCity}}</span>
</template>

<script>
    import BMap from 'BMap'
    export default{
        data(){
            return{
                LocationProvince:"",    //给渲染层定义一个初始值
                LocationCity:""     //给渲染层定义一个初始值
            }
        },
        mounted(){
            this.city()    //触发获取城市方法
        },
        methods:{
            city(){    //定义获取城市方法
                const geolocation = new BMap.Geolocation();
                var _this = this
                geolocation.getCurrentPosition(function getinfo(position){
                    let city = position.address.city;             //获取城市信息
                    let province = position.address.province;     //获取省份信息
                    _this.LocationProvince = province
                    _this.LocationCity = city
                }, function(e) {
                    _this.LocationCity = "定位失败"
                }, {provider: 'baidu'});
            }
        },
        name :"NewBase"
    }
</script>

<style scoped>
</style>