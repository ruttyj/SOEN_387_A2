<%-- 
    Document   : singlePage.jsp
    Created on : Nov 2, 2019, 8:44:39 PM
    Author     : ruttyj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="styles/material-icons/material-icons.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
        <link href="libs/vuetify2/vuetify.min.css" rel="stylesheet">
        <link href="styles/global.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    </head>
    <body>
        <!-- Display app here -->
        <div id="app" class="loading">
            <v-app id="inspire">
                <app-simple-header :is-sidebar-open.sync="isSidebarOpen" :user-data="userData"/></app-simple-header>
                <v-content>
                    <div class="complete-center full-height">
                        <div class="http-status-code">404</div>
                    </div>
                </v-content>
            </v-app>
        </div>

        <!-- Import Libraries -->
        <script src="libs/axios/axios.min.js"></script>
        <script src="libs/simpleQueryString/simpleQueryString.min.js"></script>
        <script src="libs/jquery/jquery-3.4.1.slim.min.js"></script>
        <script src="libs/vue/vue.js"></script>
        <script src="libs/vuetify2/vuetify.js"></script>

        <!-- Import components -->
        <script src="scripts/components/appSimpleHeader.js"></script>

        <!-- Start app -->
        <script src="scripts/main.js"></script>
    </body>
</html>