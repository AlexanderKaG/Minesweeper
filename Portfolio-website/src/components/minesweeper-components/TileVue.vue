<script setup>
import { ref } from "vue";

const hello = ref("hello");
</script>

<template>
  {{ hello }}
  <div class="tile"></div>
</template>

<style>
.tile {
  height: 10px;
  width: 10px;
  background-color: black;
}
</style>
