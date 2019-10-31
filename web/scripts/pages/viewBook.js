// Register this component with VueJS
Vue.component('view-book-page', {
    template: `
        <v-container class="fill-height" fluid >
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="12" md="8">
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>
                            <v-toolbar-title>
                                View Book
                            </v-toolbar-title>
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-form @submit="saveBook()">

                                <!-- Title -->
                                <v-text-field label="Title" v-model="title" readonly/>

                                <!-- ISBN -->
                                <v-text-field label="ISBN" v-model="isbn" readonly/>

                                <!-- Author -->
                                <v-text-field label="Author" v-model="author" readonly/>

                                <!-- Publisher -->
                                <v-text-field label="Publisher" v-model="publisher" readonly/>

                                <!-- Description -->
                                <v-textarea label="Description " v-model="description" readonly/>
                               
                            </v-form>
                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>`,
    props:{
        // Book Id passed in as a property
        id: {
            default: 0,
        }
    },
    data: () => ({
        title: '',
        isbn: '',
        description: '',
        author: '',
        publisher: '',
        cover: null,
    }),
    methods: {
        async loadBook(){
            
        },
    }
});