// Register this component with VueJS
Vue.component('app-page', {
    template: `
        <div class="pa-4">
            <h1>Welcome {{c_username}}!</h1>
        </div>`,
    data: function(){
        return {
            
        }
    },
    props: {
        'user-data': {
            default: function(){
                return {};
            },
        }
    },
    computed:{
        c_username(){
            if(this.userData !== null){
                return this.userData.username;
            }
            return '';
        }
    }
});