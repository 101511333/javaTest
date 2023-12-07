<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
      <b>您好！{{ user.nickname }}</b>
    </div>
    <el-card>
      欢迎使用企业员工信息管理系统！
      <el-divider />
      <div style="display: flex">
        <div style="width: 50%;border-radius: 10px">
          <el-carousel height="450px" :interval="10000">
            <el-carousel-item v-for="item in imgs" :key="item">
              <img :src="item" alt="" style="width: 100%">
            </el-carousel-item>
          </el-carousel>
        </div>

        <div style="flex: 1">
          <el-card>
            <div style="text-align: center; font-size: 20px">企业员工信息管理系统介绍</div>
            <div>企业信息员工管理系统是一种基于计算机技术和数据库管理的高效工具，旨在帮助企业实现对员工信息的快速、准确和安全的管理。这种系统不仅能够提供员工基本信息的录入与查询功能，还具备员工考勤、工资管理、培训记录、绩效评估等功能，为企业管理者提供全面和准确的员工数据支持。

              首先，企业信息员工管理系统可以极大地提高企业内部的管理效率。通过该系统，管理人员能够快速查找和获取员工的个人信息、工作经历、薪资数据等，无需翻阅繁琐的纸质文件或跟踪多个电子表格。系统还可以自动生成各种报表和分析图表，帮助管理者进行数据分析和决策，提升企业的运营效益。

              其次，企业信息员工管理系统能够提升企业内部的人力资源管理水平。系统可以记录员工的入职、离职、转岗等关键信息，建立完善的人事档案和员工数据库。管理人员可以随时更新和补充员工信息，保持数据的及时性和准确性。系统还可以根据不同部门和岗位的需求，进行员工的招聘和调配，帮助企业更好地配置人力资源，提高组织的协同和运转效率。

              此外，企业信息员工管理系统还支持员工自助服务功能，实现了员工与管理者之间的信息互动。员工可以通过系统自行查询和更新个人信息、休假申请、奖惩记录等，减少人力资源部的工作负担，提高办公效率。系统还提供了内部通讯功能，方便员工之间及员工与管理层之间的沟通和交流。

              总之，企业信息员工管理系统是一种高效、智能化的工具，对于现代企业来说，它不仅能够优化内部流程、提升人力资源管理水平，还能够提高员工的满意度和企业的竞争力。通过该系统，企业可以实现员工数据的集中化管理，提高信息化水平，有效推动企业的发展和创新。</div>
          </el-card>
        </div>

      </div>
    </el-card>

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      imgs:[
        'https://img2.baidu.com/it/u=427065972,1475610106&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1689267600&t=c53532c3b1350f427d121f34bd59f5f9',
        'https://img2.baidu.com/it/u=3396343863,2405941289&fm=253&fmt=auto&app=138&f=JPEG?w=749&h=500',
        'https://img0.baidu.com/it/u=3159501665,3894027662&fm=253&fmt=auto&app=138&f=JPEG?w=658&h=467'
      ]
    }
  }
}
</script>
