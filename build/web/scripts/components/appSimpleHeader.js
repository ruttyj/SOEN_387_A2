Vue.component('app-simple-header', {
    template: `
        <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app fixed color="blue darken-3" dark >
            <!-- App title and menu button -->
            <v-toolbar-title class="ml-0 title-bar-text" >
                <span class="hidden-sm-and-down pl-4 logo-text">
                    <a href="/home">Book Library</a>
                </span>
            </v-toolbar-title>
        </v-app-bar>`,
    computed:{
    }
});