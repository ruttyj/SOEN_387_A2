<%-- 
    Document   : singlePage.jsp
    Created on : Nov 2, 2019, 8:44:39 PM
    Author     : ruttyj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                <app-sidebar :menu-items="menuItems" :is-open="isSidebarOpen"></app-sidebar>
                <app-header :is-sidebar-open.sync="isSidebarOpen" :user-data="userData"></app-header>
                <v-content>
                    <app-page/>
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
        <script src="scripts/components/appHeader.js"></script>
        <script src="scripts/components/appSidebar.js"></script>

        <!-- Import page -->
        <script src="scripts/pages/${requestScope["script"]}"></script>

        <!-- Initial App Data -->
        <script>
            INITIAL_DATA = ${requestScope["initalData"]};
        </script>

        <!-- Start app -->
        <script src="scripts/main.js"></script>
    </body>
</html>
