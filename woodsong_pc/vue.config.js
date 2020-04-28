module.exports = {
    lintOnSave: false,
    publicPath: './',
    devServer: {
        disableHostCheck: true, //解决127.0.0.1指向其他域名时出现"Invalid Host header"问题
        proxy: {
            '/api': {
                 target: process.env.VUE_APP_API_URL,// 对服务器的接口
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}
