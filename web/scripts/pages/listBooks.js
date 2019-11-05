// Register this component with VueJS
Vue.component('app-page', {
    template: `
        <v-container class="fill-height" >
            <h1>Welcome {{c_username}}!</h1>
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="12" md="12" >
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>

                            <template v-if="selectedRows.length">
                                <v-toolbar-title>
                                    {{selectedRows.length}} {{selectedRows.length == 1 ? 'item' : 'items'}} selected
                                </v-toolbar-title>
                                <v-spacer></v-spacer>
                                <v-btn icon @click="promptDeleteItems(selectedRows)">
                                    <v-icon>delete</v-icon>
                                </v-btn>
                            </template>

                            <template v-else>
                                <v-toolbar-title>
                                    Book List
                                </v-toolbar-title>
    
                                <v-spacer></v-spacer>
                                <v-btn icon href="/addBook" title="Add Book">
                                    <v-icon>add</v-icon>
                                </v-btn>
                            </template>
                            
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-simple-table>
                                <template v-slot:default>
                                    <thead>
                                        <tr>
                                            <th class="text-left">
                                                <span @click="clickMasterCheck" >
                                                    <v-checkbox readonly :input-value="c_allSelected" :indeterminate="c_someSelected && !c_allSelected"/>
                                                </span>
                                            </th>
                                            <th class="text-left">Image</th>
                                            <th class="text-left">Title</th>
                                            <th class="text-left">ISBN</th>
                                            <th class="text-left">Author</th>
                                            <th class="text-left">Publisher</th>
                                            <th class="text-left">Description</th>
                                            <th class="text-right actions-column">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="item in c_books" :key="item.id">
                                            <td class="check-column">
                                                <v-checkbox
                                                    v-model="selectedRows"
                                                    :value="item.id"
                                                />
                                            </td>
                                            <td class="image-column">
                                                <v-img
                                                    v-if="item.cover && item.cover.thumbnailUrl"
                                                    :src="item.cover.thumbnailUrl"
                                                    contain
                                                />
                                            </td>
                                            <td>{{ item.title }}</td>
                                            <td>{{ item.isbn }}</td>
                                            <td>{{ item.author.firstName + " " + item.author.lastName }}</td>
                                            <td>{{ item.publisher.name }}</td>
                                            <td>{{ item.description }}</td>
                                            <td class="text-right">
                                                <!-- View -->
                                                <v-btn icon :href="'viewBook?id='+item.id">
                                                    <v-icon>visibility</v-icon>
                                                </v-btn>

                                                <!-- Edit -->
                                                <v-btn icon :href="'editBook.html?id='+item.id">
                                                    <v-icon>edit</v-icon>
                                                </v-btn>

                                                <!-- Delete -->
                                                <v-btn icon @click="promptDeleteItems([item.id])">
                                                    <v-icon>delete</v-icon>
                                                </v-btn>
                                            </td>
                                        </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>

                            
                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>



            <!-- Confirm Delete Dialog -->
            <v-dialog
                v-model="showConfirmDeleteDialog"
                width="500"
            >
                <v-card>
                    <v-card-title class="headline primary white--text" primary-title>
                        Confirm Delete
                    </v-card-title>

                    <template v-if="deleteProcessing">
                        <div class="pa-4 complete-center">
                            <v-progress-circular indeterminate color="primary"/>
                        </div>
                    </template>
                    <template v-else>
                        <div class="pa-4">
                            Are you sure you want to delete {{deleteItems.length}} {{deleteItems.length == 1 ? 'item' : 'items'}}?
                        </div>
                    </template>

                    <v-divider></v-divider>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="secondary" @click="cancelDeleteItems" :disabled="deleteProcessing">
                            Cancel
                        </v-btn>
                        <v-btn color="primary" @click="confirmDeleteItems" :disabled="deleteProcessing">
                            Confirm
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>


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
        },
        userData:{
            default: function(){
                return null;
            }
        },
    },
    data: function(){
        return {
            selectedRows: [],
            deleteItems: [],
            deleteProcessing: false,
            showConfirmDeleteDialog: false,
        }
    },
    methods: {

        promptDeleteItems(items){
            this.deleteItems = items;
            this.showConfirmDeleteDialog = true;
        },


        cancelDeleteItems(){
            this.showConfirmDeleteDialog = false;
        },
        confirmDeleteItems(){
            this.deleteBooks(this.deleteItems);
            this.showConfirmDeleteDialog = false;
        },


        async deleteBooks(ids){
            this.deleteProcessing = true;
            try {
                console.log('Submitted & waiting');
                var request = {
                    method: 'post', // @TODO change this to delete
                    url: 'deleteBooks',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    data: simpleQueryString.stringify({
                        ids: ids,
                    }),
                };
                console.log('request', request);
                var response = await axios(request);

                if(response.data.status == 'success'){
                    location.reload();
                }

                // if not logged in redirect
                if(response.status == 401){
                    window.location.replace("/login");
                }
                console.log('response', response);
            } catch(e){
                console.log('failed', e);
            }
            this.deleteProcessing = false;
            
        },

        clickMasterCheck(){
            if(this.c_allSelected || this.c_someSelected){
                this.selectedRows = [];
            } else {
                this.selectedRows = this.c_books.map(item => item.id);
            }
        }
    },
    computed: {
        c_books(){
            if(this.pageData !== null && Array.isArray(this.pageData.books)){
                return this.pageData.books;
            }
            return [];
        },
        c_allSelected(){
            return this.selectedRows.length == this.c_books.length;
        },
        c_someSelected(){
            return this.selectedRows.length != 0;
        },
        c_username(){
            if(this.userData !== null){
                return this.userData.username;
            }
            return '';
        },
    }
});