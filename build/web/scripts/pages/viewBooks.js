// Register this component with VueJS
Vue.component('view-books-page', {
    template: `
        <v-container class="fill-height" >
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="12" md="8" >
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>
                            <v-toolbar-title>
                                Book List
                            </v-toolbar-title>
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-simple-table>
                                <template v-slot:default>
                                    <thead>
                                        <tr>
                                            <th class="text-left">Title</th>
                                            <th class="text-left">ISBN</th>
                                            <th class="text-left">Author</th>
                                            <th class="text-left">Publisher</th>
                                            <th class="text-left">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="item in books" :key="item.id">
                                            <td>{{ item.title }}</td>
                                            <td>{{ item.isbn }}</td>
                                            <td>{{ item.author }}</td>
                                            <td>{{ item.publisher }}</td>
                                            <td>
                                                <!-- View -->
                                                <v-btn icon :href="'viewBook.html?id='+item.id">
                                                    <v-icon>visibility</v-icon>
                                                </v-btn>

                                                <!-- Edit -->
                                                <v-btn icon :href="'editBook.html?id='+item.id">
                                                    <v-icon>edit</v-icon>
                                                </v-btn>

                                                <!-- Delete -->
                                                <v-btn icon @click="deleteBooks([item.id])">
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
        </v-container>`,
    props:{
        // Book Id passed in as a property
        id: {
            default: 0,
        }
    },
    created(){
        this.loadBooks();
    },
    data: function(){
        return {
            books: [],
        }
    },
    methods: {
        loadBooks(){
            var books = [];
            for(var i=0; i < 15; ++i){
                books.push({
                    id: i,
                    title: `book ${i}`,
                    isbn: `isbn_${i}`,
                    description: `description ${i}`,
                    author: `author ${i}`,
                    publisher: `publisher ${i}`,
                    cover: null,
                });
            }
            this.books = books;
        },
        async deleteBooks(ids){
            try {
                console.log('Submitted & waiting');
                var loginResponse = await axios.delete('/addBook', {
                    ids: ids,
                });
                console.log('response', loginResponse);
            } catch(e){
                console.log('failed', e);
            }
        }
    }
});