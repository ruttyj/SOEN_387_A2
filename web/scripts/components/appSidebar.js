Vue.component('app-sidebar', {
    template: `
        <v-navigation-drawer
            v-model="isOpen"
            :clipped="$vuetify.breakpoint.lgAndUp"
            app
        >
            <v-list dense>
                <template v-if="userData">
                    <template v-for="item in menuItems">
                        <v-row
                            v-if="item.heading"
                            :key="item.heading"
                            align="center"
                        >
                            <v-col cols="6">
                                <v-subheader v-if="item.heading">
                                    {{ item.heading }}
                                </v-subheader>
                            </v-col>
                            <v-col cols="6" class="text-center">
                                <a href="#!" class="body-2 black--text">EDIT</a>
                            </v-col>
                        </v-row>
                        <v-list-item
                            v-else
                            :key="item.text"
                            :href="item.href"
                        >
                            <v-list-item-action>
                                <v-icon>{{ item.icon }}</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title>
                                    {{ item.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </template>
                </template>
            </v-list>
        </v-navigation-drawer>`,
    props: {
        isOpen: {
            type: Boolean,
            default: function(){
                return [];
            }
        },
        menuItems: {
            type: Array,
            default: function(){
                return [];
            }
        },
        userData: {
            default: function(){
                return {}
            }
        }
    },
});