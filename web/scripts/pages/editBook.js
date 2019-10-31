// Register this component with VueJS
Vue.component('edit-book-page', {
    template: `
        <v-container class="fill-height" fluid >
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="12" md="8">
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>
                            <v-toolbar-title>
                                Edit Book
                            </v-toolbar-title>
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-form @submit="saveBook()">

                                <!-- Title -->
                                <v-text-field label="Title" v-model="title" />

                                <!-- ISBN -->
                                <v-text-field label="ISBN" v-model="isbn" />

                                <!-- Author -->
                                <v-text-field label="Author" v-model="author" />

                                <!-- Publisher -->
                                <v-text-field label="Publisher" v-model="publisher" />

                                <!-- Description -->
                                <v-textarea label="Description " v-model="description" />

                                <!-- Cover Photo -->
                                <v-file-input prepend-icon="attach_file" label="Cover Image" ref="converImageFile" v-model="cover" />
                               
                            </v-form>
                        </v-card-text>

                        <!-- Actions -->
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary" @click="saveBook()">Save</v-btn>
                        </v-card-actions>
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
        async saveBook(){
            try {
                console.log('Submitted & waiting');
                var loginResponse = await axios.post('/addBook', {
                    id: this.id,
                });
                console.log('response', loginResponse);
            } catch(e){
                console.log('failed', e);
            }
        }
    }
});