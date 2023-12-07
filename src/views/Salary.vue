<template>
  <div>
    <h2>员工查询工资</h2>
    <div style="padding-top: 40px;text-align: center">{{user.nickname}}员工,欢迎您!!</div>
    <div style="padding-top: 50px">
      <el-card>
        <div style="text-align: center">
          <h2 >您此次的工资结算如下</h2>
          <div style="padding-top: 10px; color: darkorchid">您的基本工资为{{user.salary}}元人民币</div>
          <div style="color: red">缺勤次数为（注意：每次缺勤扣除工资100元）{{count.absentType}}次</div>
          <div style="color: orange">迟到次数为（注意：每次缺勤扣除工资50元）{{count.late}}次</div>
          <div style="color: orange;">早退次数为（注意：每次缺勤扣除工资30元）{{count.earlyLate}}次</div>
          <div style="color: green;">加班次数为（注意：每次加班增加奖金50元）{{count.overtime}}次</div>
          <h2 style="color: purple;">最后你可获得的薪资为</h2>
          <h2 style="color: dodgerblue;">{{ endSalary }}元</h2>
        </div>
      </el-card>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
    user: {},
    count:{},
    endSalary:0,
    };
  },
  methods: {
    loadUser(){
      this.request.get("/user/mySalary").then(res => {
        this.user = res.data;
      })
    },
    loadAttendance(){
      this.request.get("/attendance/findCount").then(res => {
        this.count = res.data;
        this.endSalary = this.user.salary - res.data.absentType * 100
                                          - res.data.late * 50
                                          - res.data.earlyLate * 30
                                          + res.data.overtime * 50
      })
    },
  },
  created(){
    this.loadUser()
    this.loadAttendance()

  }
};
</script>

<style scoped>

</style>