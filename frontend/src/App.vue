<template>
  <v-app>
    <the-navbar/>

    <v-main>
      <v-container fluid>
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

export default {
  name: 'App',
  components: {
    TheNavbar,
    TheFooter
  }
}
</script>

<style>
.disable-el {
  pointer-events: none;
  opacity: 0.5;
}
</style>
