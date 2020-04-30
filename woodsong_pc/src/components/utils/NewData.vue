<template>
    <div >
        当前时间：{{nowTime}}
    </div>
</template>

<script>
    export default{
        data(){
            return{
                nowTime: ""
            }
        },
        methods:{
            getTime(){
                setInterval(()=>{
                    this.nowtime = new Date().toLocaleString();
                },1000)
            }
        },
        created(){
            this.getTime();
        },
        name:"NewData"
    }
</script>