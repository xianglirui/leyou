<template>
  <div class="row">
    <div class="row1">
      <div class="world">
        <div style="width: 100%;background-color: #8c8c8c">
          <ul>
            <li>11</li>
            <li>21</li>
            <li>31</li>
            <li>41</li>
            <li>51</li>
            <li>61</li>
            <li>71</li>
            <li>81</li>
            <li>91</li>
            <li>101</li>
            <li>111</li>
            <li>121</li>
            <li>131</li>
            <li>141</li>
            <li>151</li>
            <li>161</li>
            <li>171</li>
            <li>181</li>
            <li>191</li>
            <li>201</li>
            <li>211</li>
            <li>221</li>
            <li>231</li>
            <li>241</li>
            <li>251</li>
            <li>261</li>
            <li>271</li>
            <li>281</li>
            <li>291</li>
            <li>301</li>
            <li>311</li>
            <li>321</li>
            <li>331</li>
            <li>341</li>
            <li>351</li>
            <li>361</li>
            <li>371</li>
            <li>381</li>
            <li>391</li>
            <li>401</li>
            <li>411</li>
            <li>421</li>
            <li>431</li>
            <li>441</li>
            <li>451</li>
            <li>461</li>
            <li>471</li>
            <li>481</li>
            <li>491</li>
            <li>501</li>
            <li>511</li>
            <li>521</li>
            <li>531</li>
            <li>541</li>
            <li>551</li>
            <li>561</li>
            <li>571</li>
            <li>581</li>
            <li>591</li>
            <li>601</li>
            <li>611</li>
            <li>621</li>
            <li>631</li>
            <li>641</li>
            <li>651</li>
            <li>661</li>
            <li>671</li>
            <li>681</li>
            <li>691</li>
            <li>701</li>
            <li>711</li>
            <li>721</li>
            <li>731</li>
            <li>741</li>
            <li>751</li>
            <li>761</li>
            <li>771</li>
            <li>781</li>
            <li>791</li>
            <li>801</li>
            <li>811</li>
            <li>821</li>
            <li>831</li>
            <li>841</li>
            <li>851</li>
            <li>861</li>
            <li>871</li>
            <li>881</li>
            <li>891</li>
            <li>901</li>
            <li>911</li>
            <li>921</li>
            <li>931</li>
            <li>941</li>
            <li>951</li>
            <li>961</li>
            <li>971</li>
            <li>981</li>
            <li>991</li>
            <li>1001</li>
          </ul>
        </div>

        <div class="comment">
          
        </div>
      </div>
      <div class="tag">
        <div style="width: 100%;margin-bottom: 50px">
          <p style="margin-bottom: 30px;font-weight: bold">他去过的地方</p>
          <el-row v-for="index in 6" :key="6">
            <a href=""  class="go_palce">
              <span style="color: #88d7c4;">{{places[index-1]}}</span>
              <span>（8）</span>
            </a>
            <el-divider></el-divider>
          </el-row>
        </div>

        <div class="recent_blog">
          <p>相关游记</p>
          <el-row v-for="index in 3" :key="3">
            <div class="buju">
              <div>
                <img src="~assets/images/image_1.jpg" width="110px"/>
              </div>
              <div style="width: 60%;">
                <el-link :underline="false" class="info_import">记大龄叔叔两夜一天的IU追星之旅(首尔演唱会攻略向游记)南皇诸天不死不灭九重</el-link>
                <div class="recent_info" style="margin-top:15px;width: 95%;">
                  <div class="recent_info" >
                    <icon-svg icon-class="wo"/>
                    <el-link :underline="false" style="font-size: 12px">长白山上雪白头</el-link>
                  </div>
                  <div class="recent_info">
                    <icon-svg icon-class="aixin_shixin"/>
                    <p>128</p>
                  </div>
                  <div class="recent_info">
                    <icon-svg icon-class="tubiaozhizuo-"/>
                    <p>24</p>
                  </div>
                </div>
              </div>
            </div>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "BlogContent",
    data(){
      return{
        places:['北京','武汉','杭州','深圳','广州','上海']
      }
    }
  }
</script>

<style scoped>
  .row{
    margin:150px auto;
    width: 70%;
    /*transform: none;*/
    /*border: 1px solid red;*/
  }
  .row1{
    display: flex;
    justify-content: space-between;
  }
  .world{
    width: 65%;
  }
  .tag{
    width: 29%;
  }
  .go_palce{
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-bottom: -10px;
    margin-top: -10px;
    color: #c4c4c4;
    /*border: 1px solid red;*/
  }

  .buju{
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 35px;
  }
  .recent_info{
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
  }
  .info_import{
    width: 100%;
    /* 文字分两段显示，超出显示框时，超出部分显示省略号 */
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    /* 文字超出后是否自动换行 */
    /* white-space: nowrap; */
    text-overflow: ellipsis;/* 文字溢出，显示省略号 */
    overflow: hidden;

    font-size: 16px;
    color: black;
  }
  .recent_blog p:first-child{
    margin-bottom: 20px;
    font-weight: bold;
  }
</style>
