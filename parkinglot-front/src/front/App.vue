<template>
    <div style="height: 100%">
        <main-header/>
        <div class="app-main page-container md-layout-row">
            <hello message="Sh4wn">
            </hello>
        </div>
        <main-footer/>
    </div>
</template>

<script>
    import Hello from "./components/Hello.vue"
    // import MainFooter from "./template/MainFooter.vue";
    export default {
        name: "app",
        components: {
            // 'main-footer' : MainFooter,
            // 'main-header': MainHeader
            'hello': Hello
        }
    }
</script>

<style>
    .app-main {
        height: 90%;
        margin-left: 20%;
        margin-right: 20%;
        font-family: "Roboto Mono", monospace;
    }
    .md-card {
        width: 31.3%;
        margin-top: 10px;
        margin-bottom: 10px;
        display: inline-block;
        vertical-align: top;
    }
    .md-list-item-content {
        max-height: 50px;
    }
    @media screen and (max-width: 600px) {
        .app-main {
            margin-left: 0;
            margin-right: 0;
        }
        .md-card {
            width: 100%;
            margin: 15px 0;
            display: inline-block;
            vertical-align: top;
        }
        .md-app-content.md-card {
            margin-left: 0;
        }
    }
</style>