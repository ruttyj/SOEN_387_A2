new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data: function(){
        return {
            isSidebarOpen: true,
            userData: null,
            pageData: null,
            menuItems: [
                {   
                    icon: 'add',       
                    text: 'Add Book',
                    href: 'addBook',
                },
                {   
                    icon: 'book',       
                    text: 'View Books',
                    href: 'home',
                },
            ],
        };
    },
    mounted(){
        $('#app').removeClass('loading');

        // Load intial data into application through the global variable.
        // @WARNING do not use this global varaible anywhere else
        if(typeof(INITIAL_DATA) !== 'undefined'){
            if(typeof(INITIAL_DATA.userData) !== 'undefined'){
                this.userData = INITIAL_DATA.userData;
            }

            if(typeof(INITIAL_DATA.pageData) !== 'undefined'){
                this.pageData = INITIAL_DATA.pageData;
            }
        }
    }
})