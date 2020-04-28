<template>
    <div class="demo" :style="imgStyle">
        <video-player
                class="video-player vjs-custom-skin"
                ref="videoPlayer"
                :playsinline="true"
                :options="playerOptions"
                @play="onPlayerPlay($event)"
                @pause="onPlayerPause($event)"
                @ended="onPlayerEnded($event)"
        ></video-player>
    </div>
</template>

<script>
    import 'video.js/dist/video-js.css'
    import 'vue-video-player/src/custom-theme.css'
    import { videoPlayer } from 'vue-video-player'
    export default {
        name: "MyVueVideoPlayer",
        components: {
            videoPlayer
        },
        props: {
            sourceUrl:{
                type: String,
                default: ''
            },
            videoImg:{
                type: String,
                default: ''
            },
            playerWidth: {
                type: Number,
                default: 600
            },
            playerHeight: {
                type: Number,
                default: 340
            },
        },
        data() {
            return {
                playerOptions : {
                    playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
                    autoplay: true, //如果true,浏览器准备好时开始播放。
                    muted: false, // 默认情况下将会消除任何音频。
                    loop: false, // 导致视频一结束就重新开始。
                    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
                    language: 'zh-CN',
                    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
                    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
                    sources: [{
                        type: "",//这里的种类支持很多种：基本视频格式、直播、流媒体等，具体可以参看git网址项目
                        src: this.sourceUrl //url地址
                    }],
                    poster: this.videoImg, //你的封面地址
                    // width: document.documentElement.clientWidth, //播放器宽度
                    notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
                    controlBar: {
                        timeDivider: true,
                        durationDisplay: true,
                        remainingTimeDisplay: false,
                        fullscreenToggle: true  //全屏按钮
                    }
                }
            };
        },
        mounted() {
            console.log('this is current player instance object', this.player)
        },
        computed: {
            player() {
                return this.$refs.videoPlayer.player
            },
            imgStyle() {
                return {
                    'width': this.playerWidth + "px",
                    'height': this.playerHeight + "px",
                }
            }
        },
        methods: {
            // 重新设置视频
            resetVideo(videoUrl, videoImg){
                this.playerOptions.sources[0].src = videoUrl;
                this.playerOptions.poster = videoImg
            },

            // listen event
            onPlayerPlay(player) {
                console.log("播放", player.currentTime());
            },
            onPlayerPause(player) {
                console.log("暂停", player.currentTime());
            },
            onPlayerEnded(player){
                console.log("结束");
                console.log(player.currentTime());
            },
            // ...player event

            // or listen state event
            playerStateChanged(playerCurrentState) {
                console.log('player current update state', playerCurrentState)
            },

            // player is ready
            playerReadied(player) {
                console.log('the player is readied', player)
                // you can use it to do something...
                // player.[methods]
            }
        }
    }
</script>

<style lang="scss" scoped>

    .demo{
        display: inline-block;
        width: 600px;
        height: 340px;
        text-align: center;
        line-height: 100px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
        margin-right: 4px;
    }

</style>
