// Register this component with VueJS
Vue.component('app-page', {
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
                            <template v-if="c_book">

                                <!-- Title -->
                                <v-text-field label="Title" v-model="title" readonly disabled/>

                                <!-- ISBN -->
                                <v-text-field label="ISBN" v-model="isbn" readonly disabled/>

                                <!-- Author -->
                                <v-text-field label="Author First Name" v-model="authorFirstName" readonly disabled/>
                                <v-text-field label="Author Last Name" v-model="authorLastName" readonly disabled/>

                                <!-- Publisher -->
                                <v-text-field label="Publisher Name" v-model="publisherName" readonly disabled/>
                                <v-text-field label="Publisher Address" v-model="publisherAddress" readonly disabled/>

                                <!-- Description -->
                                <v-textarea label="Description " v-model="description" readonly disabled/>

                                <!-- Image -->
                                <v-img 
                                    v-if="largeCoverUrl"
                                    :src="largeCoverUrl" 
                                    contain 
                                    class="full-width"
                                />
                            </template>

                            <template v-else>
                                {{c_message}}
                            </template>
                           
                        </v-card-text>
    
                        <!-- Actions -->
                        <v-card-actions v-if="c_book">
                            <v-spacer></v-spacer>
                            <v-btn color="primary" :href="'/editBook?id='+c_book.id">Edit</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>`,
    props:{
        // Book Id passed in as a property
        id: {
            default: 0,
        },
        pageData:{
            default: function(){
                return null;
            }
        }
    },
    data: () => ({
        isInitialized: 0,
        title: '',
        isbn: '',
        description: '',
        authorFirstName: '',
        authorLastName: '',
        publisherName: '',
        publisherAddress: '',
        largeCoverUrl: null,
    }),
    methods: {
        async loadBook(){
            
        },
    },
    computed:{
        c_book(){
            console.log('whoop', this.pageData);
            if(this.pageData != null){
                if(typeof(this.pageData.book) !== 'undefined'){
                    return this.pageData.book;
                }
            }
            return null;
        },
        c_message(){
            if(this.pageData != null){
                if(typeof(this.pageData.message) !== 'undefined'){
                    return this.pageData.message;
                }
            }
            return null;
        },
    },
    watch:{
        c_book:{
            immediate: true,
            handler(val){
                console.log("changed", val);
                if(!this.isInitialized && val !== null){
                    console.log("I DID IT");
                    this.title = this.c_book.title;
                    this.isbn = this.c_book.isbn;
                    this.description = this.c_book.description;

                    if(this.c_book.author){
                        this.authorFirstName = this.c_book.author.firstName;
                        this.authorLastName = this.c_book.author.lastName;
                    }

                    if(this.c_book.publisher){
                        this.publisherName = this.c_book.publisher.name;
                        this.publisherAddress = this.c_book.publisher.address;
                    }

                    if(this.c_book.cover){
                        this.largeCoverUrl = this.c_book.cover.largeUrl;
                    }
                }
            }
        }
    },
    
});