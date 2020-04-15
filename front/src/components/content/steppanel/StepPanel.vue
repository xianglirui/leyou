<template>
  <div class="step">
    <el-steps :active="active" finish-status="success" :space="200">
      <el-step title="步骤 1"></el-step>
      <el-step title="步骤 2"></el-step>
      <el-step title="步骤 3"></el-step>
    </el-steps>

    <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
  </div>
</template>

<script>
  export default {
    name: "StepPanel",
    props:{
      descriptions:{
        type:Object,
        default(){
          return {}
        }
      }
    },
    data() {
      return {
        active: 0,
      };
    },

    methods: {
      next() {
        if (this.active++ > 2) this.active = 0;
      }
    }
  }
</script>

<style scoped>

</style>
