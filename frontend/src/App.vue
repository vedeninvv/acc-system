<template>
  <v-app>
    <the-navbar/>

    <v-main>
      <v-container fluid>
        <app-breadcrumbs></app-breadcrumbs>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-spacer/>
    <the-footer/>
  </v-app>
</template>

<script>
import TheNavbar from "@/components/TheNavbar";
import TheFooter from "@/components/TheFooter";
import AppBreadcrumbs from "@/components/AppBreadcrumbs";

export default {
  name: 'App',
  components: {
    TheNavbar,
    TheFooter,
    AppBreadcrumbs
  }
}
</script>

<style>
.disable-el {
  pointer-events: none;
  opacity: 0.5;
}
</style>
