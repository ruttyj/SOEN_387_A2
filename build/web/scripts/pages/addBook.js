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
                                <v-text-field label="Title" v-model="title"/>

                                <!-- ISBN -->
                                <v-text-field label="ISBN" v-model="isbn"/>

                                <!-- Author -->
                                <v-text-field label="Author First Name" v-model="authorFirstName"/>
                                <v-text-field label="Author Last Name" v-model="authorLastName"/>

                                <!-- Publisher -->
                                <v-text-field label="Publisher Name" v-model="publisherName"/>
                                <v-text-field label="Publisher Address" v-model="publisherAddress"/>

                                <!-- Description -->
                                <v-textarea label="Description " v-model="description"/>

                                <!-- Cover Photo -->
                                <v-file-input prepend-icon="attach_file" label="Cover Image" ref="converImageFile" v-model="coverFile" />
                               
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
        title: 'some title',
        isbn: 'some isbn',
        description: 'some description',
        authorFirstName: 'some authorFirstName',
        authorLastName: 'some authorLastName',
        publisherName: 'some publisherName',
        publisherAddress: 'some publisherAddress',
        largeCoverUrl: null,
        coverFile: null,
    }),
    methods: {
        async onSubmit(){

            try {
                console.log('Submitted & waiting');
                var response = await axios({
                    method: 'post',
                    url: 'addBook',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    data: simpleQueryString.stringify({
                        title: this.title,
                        isbn: this.isbn,
                        description: this.description,
                        authorFirstName: this.authorFirstName,
                        authorLastName: this.authorLastName,
                        publisherName: this.publisherName,
                        publisherAddress: this.publisherAddress,
                    }),
                });

                if(response.data.status == 'success'){
                    //location.reload();
                } 
                
                // if not logged in redirect
                if(response.status == 401){
                    window.location.replace("login.html");
                }

                console.log('response', response);
            } catch(e){
                console.log('failed', e);
            }
        }
    }
});