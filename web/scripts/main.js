new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data: function(){
        return {
            isSidebarOpen: true,
            userData: null,
            menuItems: [
                {   
                    icon: 'add',       
                    text: 'Add Book',
                    href: 'addBook.html',
                },
                {   
                    icon: 'book',       
                    text: 'View Books',
                    href: 'viewBooks.html',
                },
            ],
        };
    },
    mounted(){
        $('#app').removeClass('loading');
    }
})