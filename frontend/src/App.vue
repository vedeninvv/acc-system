<template>
  <v-app>
    <app-navbar></app-navbar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-spacer></v-spacer>
    <app-footer></app-footer>
  </v-app>
</template>

<script>
import AppNavbar from "@/components/AppNavbar";
import AppFooter from "@/components/AppFooter";

export default {
  name: 'App',
  components: {
    AppNavbar,
    AppFooter
  }
}
</script>
