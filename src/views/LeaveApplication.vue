<template>
  <div style="display: flex">
    <div style="width: 40%">
        <el-card class="LeaveApplication_forApplication">
            <div style="text-align: center; font-size: 20px">请假申请</div>

          <div style="display: flex;justify-content: center;align-items: center;padding-top: 50px">

            <div @click="addLeave">
              <el-card  class="add_application">
                <img src="../assets/add.png" style="width: 58px"  alt="">
              </el-card>
            </div>

          </div>

        </el-card>


      <div style="padding-top: 10px">
          <el-card>
            <div id="he-plugin-standard"></div>
          </el-card>
      </div>

    </div>

    <div style="flex: 1; padding-left: 10px">
      <div >
          <span class="my_application" >我的请假申请</span>
        <div style="margin: 10px 0">
          <el-input style="width: 200px" placeholder="请输入原因" suffix-icon="el-icon-search" v-model="reason"></el-input>
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>

          <el-card>
            <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" >
              <el-table-column prop="employeeName" label="员工姓名"></el-table-column>
              <el-table-column prop="type" label="请假类型"></el-table-column>
              <el-table-column prop="startTime"  label="开始时间"></el-table-column>
              <el-table-column prop="endTime"  label="结束时间"></el-table-column>
              <el-table-column prop="reason" label="请假原因"></el-table-column>
              <el-table-column prop="auditor" label="审批人"></el-table-column>
              <el-table-column prop="status" label="请假状态"></el-table-column>


              <el-table-column label="操作"  width="180" align="center">
                <template slot-scope="scope">
                  <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                  <el-popconfirm
                      class="ml-5"
                      confirm-button-text='确定'
                      cancel-button-text='我再想想'
                      icon="el-icon-info"
                      icon-color="red"
                      title="您确定删除吗？"
                      @confirm="del(scope.row.id)"
                  >
                    <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
            <div style="padding: 10px 0">
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-sizes="[2, 5, 10, 20]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
              </el-pagination>
            </div>
          </el-card>
      </div>
    </div>


    <el-dialog title="请假申请" :visible.sync="dialogAddLeave" width="40%" :close-on-click-modal="false">
      <el-form label-width="120px" size="small" :model="form" style="width: 80%;  margin: 0 auto">
        <el-form-item label="员工姓名">
          <el-input v-model="form.employeeName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请假类型">
          <el-select v-model="form.type" clearable placeholder="请选择类型" style="width: 100%">
            <el-option  v-for="item in types" :key="item.name" :label="item.name" :value="item.flag"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请假原因">
          <el-input type="textarea" v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker type="datetime" v-model="form.startTime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker type="datetime" v-model="form.endTime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item  label="请假天数">
          <el-input v-model="form.days" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddLeave = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name:"LeaveApplication",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      reason:"",
      form:{},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      dialogAddLeave:false,
      types: [
        {
          name: "事假",
          flag: "事假"
        },
        {
          name: "病假",
          flag: "病假"
        },
        {
          name: "婚嫁",
          flag: "婚嫁"
        },
        {
          name: "产假",
          flag: "产假"
        },
        {
          name: "出差",
          flag: "出差"
        },
      ]
    }
  },

  created() {
    this.load()
  },

  methods: {

    load(){
      this.request.get("/leaves/myPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          reason: this.reason
        }}).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },

    handleEdit(row) {
      console.log(row)
      this.addLeave()
      this.form = row
    },

    reset(){
      this.reason = ""
      this.load()
    },

    addLeave(){
      this.dialogAddLeave = true
      this.load()
    },

    save(){
      this.request.post("/leaves/leavesAdd",this.form).then(res => {
        if (res.code === '200'){
          this.$message.success("员工请假信息更新成功")
          this.form = {}
          this.dialogAddLeave = false
          this.load()
        }
      })

    }
  },


  mounted() {
    const scriptElement = document.createElement("script");
    scriptElement.innerHTML = `
      WIDGET = {
        "CONFIG": {
          "layout": "1",
          "width": "450",
          "height": "150",
          "background": "1",
          "dataColor": "FFFFFF",
          "key": "0824ea2cba2f4e07bd10815028904a37"
        }
      }
    `;
    document.head.appendChild(scriptElement);
    const widgetScript = document.createElement("script");
    widgetScript.src = "https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0";
    document.head.appendChild(widgetScript);
  }
};
</script>


<style>
.LeaveApplication_forApplication{
  background-color: #0093E9;
  background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
  height: 300px;
}
.my_application{
  font-size: 20px;
  color: dodgerblue;
  font-family: 'Droid Sans',serif
}
.add_application{
  border-radius: 50%;
  height: 100px;
  width: 100px;
  cursor: pointer;

}
</style>