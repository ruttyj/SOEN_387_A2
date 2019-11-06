// Register this component with VueJS
Vue.component('app-page', {
    template: `
        <v-container class="fill-height" fluid >
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="12" md="8" >
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>
                            <v-toolbar-title>
                                Edit Book
                            </v-toolbar-title>
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-form @submit="onSubmit()">

                                <!-- Title -->
                                <v-text-field 
                                    label="Title" 
                                    v-model="title"
                                    :error-messages="getErrorForField('title')"
                                    @input="resetErrorForField('title')"
                                />

                                <!-- ISBN -->
                                <v-text-field 
                                    label="ISBN" 
                                    v-model="isbn"
                                    :error-messages="getErrorForField('isbn')"
                                    @input="resetErrorForField('isbn')"
                                />

                                <!-- Author -->
                                <v-text-field 
                                    label="Author First Name" 
                                    v-model="authorFirstName" 
                                    :error-messages="getErrorForField('authorFirstName')"
                                    @input="resetErrorForField('authorFirstName')"
                                />
                                <v-text-field 
                                    label="Author Last Name" 
                                    v-model="authorLastName"
                                    :error-messages="getErrorForField('authorLastName')"
                                    @input="resetErrorForField('authorLastName')"
                                />

                                <!-- Publisher -->
                                <v-text-field 
                                    label="Publisher Name" 
                                    v-model="publisherName"
                                    :error-messages="getErrorForField('publisherName')"
                                    @input="resetErrorForField('publisherName')"
                                />
                                <v-text-field 
                                    label="Publisher Address" 
                                    v-model="publisherAddress"
                                    :error-messages="getErrorForField('publisherAddress')"
                                    @input="resetErrorForField('publisherAddress')"
                                />

                                <!-- Description -->
                                <v-textarea 
                                    label="Description " 
                                    v-model="description"
                                    :error-messages="getErrorForField('description')"
                                    @input="resetErrorForField('description')"
                                />

                                <!-- Cover Photo -->
                                
                                <template>
                                    <v-hover v-if="largeCoverUrl !== null && !clearCover">
                                        <template v-slot:default="{ hover }">
                                            <v-card class="mx-auto">
                                                <v-img :src="largeCoverUrl" contain aspect-ratio="3"></v-img>
                                        
                                                <v-fade-transition>
                                                    <v-overlay v-if="hover"
                                                        absolute
                                                        color="#1565c0"
                                                    >
                                                        <v-btn @click="toggleClearImage(true)">Clear Image</v-btn>
                                                    </v-overlay>
                                                </v-fade-transition>
                                            </v-card>
                                        </template>
                                    </v-hover>
                                    <v-file-input 
                                        v-else
                                        prepend-icon="attach_file" 
                                        label="Cover Image" 
                                        v-model="file" 
                                    />
                                </template>
                               
                               
                            </v-form>
                        </v-card-text>

                        <!-- Actions -->
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary" @click="onSubmit()">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>



            <v-snackbar v-model="snackbarShowing" :top="true" :timeout="3000"  >
                {{ snackbarText }}
                <v-btn dark text @click="snackbarShowing = false" >
                    Close
                </v-btn>
            </v-snackbar>


        </v-container>`,
    data: () => ({
        snackbarShowing: false,
        snackbarText: '',

        id: '',
        title: '',
        isbn: '',
        description: '',
        authorFirstName: '',
        authorLastName: '',
        publisherName: '',
        publisherAddress: '',
        largeCoverUrl: null,
        file: null,
        clearCover: false,
        newCover: false,
        errors: {},
    }),
    props:{
        pageData:{
            default: function(){
                return {};
            }
        }
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
                if(!this.isInitialized && val !== null){
                    this.id = this.c_book.id;
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
    methods: {
        toggleClearImage(val){
            var newVal;
            if(typeof(val) !== 'undefined'){
                newVal = val;
            } else {
                newVal = !this.clearCover;
            }

            if(newVal){
                this.clearCover = true;
                this.file = null;
            } else {
                this.clearCover = false;
            }
        },

        resetErrorForField(field){
            if(typeof(this.errors[field]) !== 'undefined' && this.errors[field] !== null){
                this.$set(this.errors, field, null);
            }
        },
        getErrorForField(field){
            var result = null;
            if(typeof(this.errors[field]) !== 'undefined'){
                result = this.errors[field];
            }
            return result;
        },
        async onSubmit(){
            try {
                console.log('Submitted & waiting');

                var data = {
                    id: this.id,
                    title: this.title,
                    isbn: this.isbn,
                    description: this.description,
                    authorFirstName: this.authorFirstName,
                    authorLastName: this.authorLastName,
                    publisherName: this.publisherName,
                    publisherAddress: this.publisherAddress,
                    cover: this.file,
                    clearCover: this.clearCover,
                };
    
                // Package multipart request 
                const formData = new FormData();
                Object.keys(data).map(key => {
                    formData.append(key, data[key]);
                })
    
                var response = await axios({
                    method: 'post',
                    url: 'editBook',
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    data: formData,
                });

                if(response.data.status == 'success'){
                    this.snackbarText = "Sucessfully Saved Book";
                    this.snackbarShowing = true;
                } else if(typeof(response.data.errors) !== 'undefined'){
                    this.errors = response.data.errors;
                } 
                
                // if not logged in redirect
                if(response.status == 401){
                    window.location.replace("/login");
                }

                console.log('response', response);
            } catch(e){
                console.log('failed', e);
            }
        }
    }
});