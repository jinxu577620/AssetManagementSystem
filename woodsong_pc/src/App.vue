<template>
    <div id="app">
        <transition name="router-fade" mode="out-in">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    export default {
        name: 'app',
    }
</script>

<style lang="scss">
    @import "./assets/css/main.css";
    @import "./assets/css/color-dark.css";     /*深色主题*/
    .router-fade-enter-active, .router-fade-leave-active {
        transition: opacity .3s;
    }
    .router-fade-enter, .router-fade-leave-active {
        opacity: 0;
    }
</style>
