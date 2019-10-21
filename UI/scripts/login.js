new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    props: {
        source: String,
    },
    data: () => ({
        drawer: null,
    }),
    mounted(){
        $('#app').removeClass('loading');
    }
})