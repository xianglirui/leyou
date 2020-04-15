<template>
  <section class="container">
    <el-row>
      <el-select v-model="provinceValue" placeholder="请选择" @change="selectProvimce">
        <el-option
          v-for="(item,index) of provincearr"
          :key="index"
          :label="item.name"
          :value="item.name"
        ></el-option>
      </el-select>
      <el-select v-model="cityValue" placeholder="请选择" @change="selectcity">
        <el-option v-for="(item,index) of cityarr" :key="index" :label="item.name" :value="item.name"></el-option>
      </el-select>
      <el-select placeholder="请选择" v-model="RegionValue"  @change="subNum">
        <el-option
          v-for="(item,index) of regionarr"
          :key="index"
          :label="item.name"
          :value="item.name"
        ></el-option>
      </el-select>
    </el-row>
  </section>
</template>
<script>

  import { province } from '@/assets/js/map'

  export default {

    created () {
      this.provincearr = province
    },
    data () {
      return {
        provincearr:'',
        arr: [1, 3, 4],
        province: [],
        cityarr: [],
        regionarr: [],
        provinceValue: '',
        cityValue: '',
        RegionValue: ''
      }
    },
    mounted () {
    },
    methods: {
      subNum(){
        this.$emit('subNum',this.provinceValue,this.cityValue,this.RegionValue)
      },

      selectProvimce (name) {
        this.cityarr = [];
        this.regionarr = [];
        this.cityValue = '';
        this.RegionValue = '';
        for (let item of this.provincearr) {
          if (name == item.name) {
            // console.log(item)
            this.cityarr = item.children
          }
        }
      },
      selectcity (name) {
        this.regionarr = [];
        this.RegionValue = '';
        for (let item of this.cityarr) {
          if (name == item.name) {
            this.regionarr = item.children
          }
        }

      }
    }
  }
</script>

<style>
  .container {
    width: 470px;
    overflow: hidden;
    /*border: 1px solid red;*/
  }
  .box {
    width: 800px;
    height: 50px;
    line-height: 50px;
    background: red;
    color: #fff;
  }
  .el-select{
    width: 150px;
  }
</style>
