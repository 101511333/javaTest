<template>
  <div>
    <div style="padding-bottom: 10px">
      <div style="display: flex; height: 120px">
        <div style="width: 25%; height: 120px">
            <el-card style="height: 100%; width: 100%">
                <div class="frontTypeface">员工数量</div>
                <div class="frontStyle">{{ users }}</div>
            </el-card>
        </div>
        <div style="width: 25%; height: 120px">
          <el-card style="height: 100%; width: 100%">
                <div class="frontTypeface">请假数量</div>
                <div class="frontStyle">{{ leaves }}</div>
          </el-card>
        </div>
        <div style="width: 25%; height: 120px">
          <el-card style="height: 100%; width: 100%">
                <div class="frontTypeface">部门数量</div>
            <div class="frontStyle">{{ departments }}</div>
          </el-card>
        </div>
        <div style="width: 25%; height: 120px">
          <el-card style="height: 100%; width: 100%">
                  <div class="frontTypeface">考勤数量</div>
            <div class="frontStyle">{{ attendances }}</div>
          </el-card>
        </div>

      </div>

    </div>
    <div style="display: flex">
      <div style="width: 50%">
        <el-card>
          <div style="height: 400px; width: 100%" id = "pie">
          </div>
        </el-card>
      </div>
      <div style="flex: 1">
        <el-card>
          <div style="height: 400px; width: 100%" id="line"></div>
        </el-card>
      </div>
    </div>
  </div>


</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
//所定义的option使用Vue2时定义在export default上方

const lineOption = {
  title: {
    text: '员工新增趋势图',
    textStyle: {
      color: '#c23531',
      shadowBlur: 200,
      shadowColor: 'rgba(0, 0, 0, 0.5)'
    }
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'line',
      smooth: true,
      color: '#c23531',
    }
  ]
}

const pieOption = {
  backgroundColor: '#2c343c',
  title: {
    text: '部门人员数量饼图',
    left: 'center',
    top: 20,
    textStyle: {
      color: '#ccc'
    }
  },
  tooltip: {
    trigger: 'item'
  },
  visualMap: {
    show: false,
    min: 80,
    max: 600,
    inRange: {
      colorLightness: [0, 1]
    }
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '55%',
      center: ['50%', '50%'],
      data: [].sort(function (a, b) {
        return a.value - b.value;
      }),
      roseType: 'radius',
      label: {
        color: 'rgba(255, 255, 255, 0.3)'
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      itemStyle: {
        color: '#c23531',
        shadowBlur: 200,
        shadowColor: 'rgba(0, 0, 0, 0.5)'
      },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
}

export default {
  name: "Echarts",
  data(){
    return {
      users: 0,
      leaves:0,
      departments:0,
      attendances:0

    }
  },
  created(){
    this.load();
  },
  methods:{
      load(){

        this.request.get("/user").then(res => {
          this.users = res.data.length
        })

        this.request.get("/leaves").then(res => {
          this.leaves = res.data.length
        })

        this.request.get("/department").then(res => {
          this.departments = res.data.length
        })

        this.request.get("/attendance").then(res => {
          this.attendances = res.data.length
        })
      }
  },
  //等待页面的元素全部加载完成之后再初始化
  mounted(){
    //折线图
    let lineDom = document.getElementById('line');
    let lineChart = echarts.init(lineDom);

   this.request.get('/echarts/getUserEcharts').then(res => {
     lineOption.xAxis.data = res.data?.map(v => v.date) || []
     lineOption.series[0].data = res.data?.map(v => v.userCount) || []
     lineChart.setOption(lineOption);
   })

    //饼图
    let pieDom = document.getElementById('pie');
    let pieChart = echarts.init(pieDom);

    this.request.get('/echarts/getDepartmentData').then(res => {
      const data = Array.from(res.data);
      const seriesArr = [];
      for (let i = 0; i < data.length; i++) {
        const seriesObj = {}
        seriesObj.value = data[i].userCount
        seriesObj.name = data[i].departmentName
        seriesArr.push(seriesObj)
      }
     pieOption.series[0].data = seriesArr
      pieChart.setOption(pieOption);
    })

  }
}



</script>

<style scoped>
.frontStyle{
  padding-top: 5px;
  text-align: center;
  color: darkred;
  font-size: xx-large;
}
.frontTypeface{
  font-size: large;
  color: brown;
}
</style>