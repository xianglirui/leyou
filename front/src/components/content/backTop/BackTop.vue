<template>
  <div class="wraper">
    <el-button circle class="totop" icon="el-icon-caret-top" @click="scrollToTop" v-show="toTopShow"></el-button>
  </div>
</template>
<script>
  export default {
    props: {
      scrollDom: {
        type: String,
        default: ".content-container"
      }
    },
    data() {
      return {
        dom: null,
        toTopShow: false,
        scrollTop: ""
      };
    },
    mounted() {
      this.$nextTick(() => {
        window.addEventListener("scroll", this.handleScroll, true);
      });
    },
    destroyed() {
      window.addEventListener("scroll", this.handleScroll, true);
    },
    methods: {
      handleScroll() {
        this.dom = document.querySelector(this.scrollDom);
        this.scrollTop = this.dom.scrollTop;
        if (this.scrollTop > 100) {
          this.toTopShow = true;
        } else {
          this.toTopShow = false;
        }
      },
      scrollToTop() {
        let timer = null;
        let _this = this;
        //动画，使用requestAnimationFrame代替setInterval
        cancelAnimationFrame(timer);
        timer = requestAnimationFrame(function fn() {
          if (_this.scrollTop > 1000) {
            _this.scrollTop -= 1000;
            _this.dom.scrollTop = _this.scrollTop;
            timer = requestAnimationFrame(fn);
          } else if (_this.scrollTop > 100 && _this.scrollTop <= 1000) {
            _this.scrollTop -= 100;
            _this.dom.scrollTop = _this.scrollTop;
            timer = requestAnimationFrame(fn);
          } else if (_this.scrollTop > 10 && _this.scrollTop <= 100) {
            _this.scrollTop -= 10;
            _this.dom.scrollTop = _this.scrollTop;
            timer = requestAnimationFrame(fn);
          } else if (_this.scrollTop > 0 && _this.scrollTop <= 10) {
            _this.scrollTop -= 2;
            _this.dom.scrollTop = _this.scrollTop;
            timer = requestAnimationFrame(fn);
          } else {
            cancelAnimationFrame(timer);
            _this.toTopShow = false;
          }
        });
      }
    }
  }
</script>
<style scoped>

  .totop{
    height: 50px;width: 50px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    color: #409eff;
    position: fixed;
    left: 80%;
    bottom: 200px;
  }
</style>
