// Register this component with VueJS
Vue.component('login-page', {
    template: `
        <v-container class="fill-height" fluid >
            <v-row align="center" justify="center" >
                <v-col cols="12" sm="8" md="4" >
                    <v-card class="elevation-12">

                        <!-- Title -->
                        <v-toolbar color="primary" dark flat>
                            <v-toolbar-title>Login</v-toolbar-title>
                            <v-spacer></v-spacer>
                        </v-toolbar>

                        <!-- Form -->
                        <v-card-text>
                            <v-form @submit="onSubmit()">

                                <!-- Username -->
                                <v-text-field
                                    v-model="username"
                                    label="Login"
                                    name="login"
                                    prepend-icon="person"
                                    type="text"
                                />

                                <!-- Password -->
                                <v-text-field
                                    v-model="password"
                                    label="Password"
                                    prepend-icon="lock"
                                    type="password"
                                />

                            </v-form>
                        </v-card-text>

                        <!-- Actions -->
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary" @click="onSubmit()">Login</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>`,
    data: () => ({
        username: '',
        password: '',
    }),
    methods: {
        async onSubmit(){
            try {
                console.log('Submitted & waiting');
                var loginResponse = await axios.post('/login', {
                    username: this.username, 
                    password: this.password,
                });
                console.log('loginResponse', loginResponse);
            } catch(e){
                console.log('failed', e);
            }
        }
    }
});