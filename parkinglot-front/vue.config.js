module.exports = {
    // https://cli.vuejs.org/config
    // src: __dirname + '/src/main/vue',
    pluginOptions: {
        sourceDir: __dirname + "/src/vue"
    },
    publicPath: '/front',
    outputDir: __dirname + '/src/main/resources/static/dist',
    indexPath: __dirname + '/src/main/resources/static/index.html',
    /*
    devServer: {
        proxy: 'http://localhost:8000'
    }
     */
}
