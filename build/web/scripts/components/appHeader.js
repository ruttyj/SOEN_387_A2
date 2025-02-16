Vue.component('app-header', {
    template: `
        <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app fixed color="blue darken-3" dark >
            
            <!-- App title and menu button -->
            <v-toolbar-title class="ml-0 title-bar-text" >
                <v-icon @click.stop="c_isSidebarOpen = !c_isSidebarOpen">menu</v-icon>
                <span class="hidden-sm-and-down pl-4 logo-text">
                    <a href="/home">Book Library</a>
                </span>
            </v-toolbar-title>


            <v-spacer></v-spacer>


            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on">
                        <v-icon>person</v-icon>
                    </v-btn>
                </template>

                <v-list>

                    <v-list-item>
                            <v-list-item-icon>
                                <v-icon>person</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>{{c_username}}</v-list-item-title>
                            </v-list-item-content>
                    </v-list-item>

                    
                    <!-- Display menu if logged in -->
                    <template v-if="c_isLoggedIn">
                        <v-list-item href="logout">
                            <v-list-item-icon>
                                <v-icon>exit_to_app</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>Logout</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </template>

                    <!-- Display menu if NOT logged in -->
                    <template v-else>
                        <v-list-item href="/login">
                            <v-list-item-icon>
                                <v-icon>vpn_key</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>Login</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </template>



                </v-list>

            </v-menu>

        </v-app-bar>`,
    props: {
        isSidebarOpen: {
            type: Boolean,
            default: function(){
                return false;
            }
        },
        userData:{
            default: function(){
                return null;
            }
        }, 
    },
    computed:{
        c_isSidebarOpen:{
            get(){
                return this.isSidebarOpen;
            },
            set(val){
                console.log('isSidebarOpen', val);
                this.$emit('update:isSidebarOpen', val);
            },
        },

        c_isLoggedIn(){
            return this.userData !== null;
        },


        c_username(){
            if(this.userData !== null){
                return this.userData.username;
            }
            return '';
        }
    }
});