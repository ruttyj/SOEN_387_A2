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
                                Add Book
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
                                <v-file-input 
                                    prepend-icon="attach_file" 
                                    label="Cover Image" 
                                    ref="fileInput" 
                                    v-model="file"
                                />
                               
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
        </v-container>`,
    data: () => ({
        title: '',
        isbn: '',
        description: '',
        authorFirstName: '',
        authorLastName: '',
        publisherName: '',
        publisherAddress: '',
        largeCoverUrl: null,
        file: null,
        errors: {},
    }),
    methods: {
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
                    title: this.title,
                    isbn: this.isbn,
                    description: this.description,
                    authorFirstName: this.authorFirstName,
                    authorLastName: this.authorLastName,
                    publisherName: this.publisherName,
                    publisherAddress: this.publisherAddress,
                    cover: this.file,
                };

                // Package multipart request 
                const formData = new FormData();
                Object.keys(data).map(key => {
                    formData.append(key, data[key]);
                })

                var response = await axios({
                    method: 'post',
                    url: 'addBook',
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    data: formData,
                });

                if(response.data.status == 'success'){
                    window.location.href = '/viewBook?id='+response.data.id;
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
        },
    }
});