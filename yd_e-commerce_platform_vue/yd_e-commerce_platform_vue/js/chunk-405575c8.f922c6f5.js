(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-405575c8"],{"512e":function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("el-form",{attrs:{inline:!0,model:e.searchForm}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"请输入昵称进行搜索",clearable:""},model:{value:e.searchForm.nickname,callback:function(t){e.$set(e.searchForm,"nickname",t)},expression:"searchForm.nickname"}})],1),r("el-form-item",[r("el-button",{attrs:{icon:"el-icon-search"},on:{click:e.getUserList}},[e._v("搜索")]),r("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.fnOpenAddUserDialog}},[e._v("新增用户")])],1)],1),r("el-table",{staticStyle:{width:"100%"},attrs:{data:e.tableData,"tooltip-effect":"dark",border:"",stripe:""}},[r("el-table-column",{attrs:{prop:"avatar",width:"80",align:"center",label:"头像"},scopedSlots:e._u([{key:"default",fn:function(e){return[r("el-avatar",{attrs:{src:e.row.avatar}})]}}])}),r("el-table-column",{attrs:{prop:"username",label:"用户名",align:"center",width:"100"}}),r("el-table-column",{attrs:{prop:"nickname",label:"昵称",align:"center",width:"100"}}),r("el-table-column",{attrs:{label:"性别",prop:"gender",align:"center",width:"60"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s("0"===t.row.gender?"未知":"1"===t.row.gender?"男":"女"))])]}}])}),r("el-table-column",{attrs:{prop:"mobile",label:"手机号",align:"center",width:"100"}}),r("el-table-column",{attrs:{prop:"email",label:"邮箱",align:"center"}}),r("el-table-column",{attrs:{prop:"roleName",label:"角色名称",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return e._l(t.row.roleName,(function(t,n){return r("el-tag",{key:n,staticStyle:{"margin-left":"1%"},attrs:{type:"info"}},[e._v(e._s(t))])}))}}])}),r("el-table-column",{attrs:{prop:"effective",align:"center",width:"80",label:"状态"},scopedSlots:e._u([{key:"default",fn:function(t){return["1"===t.row.enable?r("el-tag",{attrs:{type:"success"}},[e._v("启用")]):"0"===t.row.enable?r("el-tag",{attrs:{type:"danger"}},[e._v("禁用")]):e._e()]}}])}),r("el-table-column",{attrs:{prop:"createTime",width:"120",align:"center",label:"创建时间"}}),r("el-table-column",{attrs:{prop:"action",align:"center",width:"250"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{attrs:{type:"text"},on:{click:function(r){return e.fnOpenRoleDialog(t.row.id)}}},[e._v("分配角色")]),r("el-button",{attrs:{type:"text"},on:{click:function(r){return e.fnResetPassword(t.row.id)}}},[e._v("重置密码")]),"0"===t.row.enable?r("el-button",{attrs:{type:"text"},on:{click:function(r){return e.fnChangeEnable(t.row.id,"1")}}},[e._v("启用")]):e._e(),"1"===t.row.enable?r("el-button",{attrs:{type:"text"},on:{click:function(r){return e.fnChangeEnable(t.row.id,"0")}}},[e._v("禁用")]):e._e(),r("el-popconfirm",{staticStyle:{"margin-left":"10px"},attrs:{title:"确定要删除吗？"},on:{confirm:function(r){return e.fnDeleteUser(t.row.id)}}},[r("el-button",{attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])},[r("template",{slot:"header"},[r("div",{staticStyle:{display:"inline-block",cursor:"pointer"},on:{click:e.getUserList}},[e._v(" 操作"),r("i",{staticClass:"el-icon-refresh",staticStyle:{"margin-left":"10px"}})])])],2)],1),r("el-pagination",{staticStyle:{float:"right","margin-top":"10px","padding-bottom":"10px"},attrs:{"current-page":e.current,"page-sizes":[10,20,50,100],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),r("el-dialog",{attrs:{title:"添加用户",visible:e.userDialogVisible,width:"35%"},on:{"update:visible":function(t){e.userDialogVisible=t},close:function(t){return e.fnCloseUserDialog("addUserForm")}}},[r("el-form",{ref:"addUserForm",attrs:{model:e.addUserForm}},[r("el-form-item",{attrs:{label:"用户名：",prop:"username","label-width":"80px",rules:[{required:!0,message:"请输入用户名",trigger:"blur"},{validator:e.checkUsername}]}},[r("el-input",{attrs:{placeholder:"请输入用户名（登录账号）",clearable:""},model:{value:e.addUserForm.username,callback:function(t){e.$set(e.addUserForm,"username","string"===typeof t?t.trim():t)},expression:"addUserForm.username"}})],1),r("el-form-item",{attrs:{label:"昵称：",prop:"nickname","label-width":"80px",rules:{required:!0,message:"请输入昵称",trigger:"blur"}}},[r("el-input",{attrs:{placeholder:"请输入昵称",clearable:""},model:{value:e.addUserForm.nickname,callback:function(t){e.$set(e.addUserForm,"nickname","string"===typeof t?t.trim():t)},expression:"addUserForm.nickname"}})],1),r("el-form-item",{attrs:{label:"密码：",prop:"password","label-width":"80px"}},[r("el-input",{attrs:{placeholder:"默认密码为123456",type:"password",clearable:""},model:{value:e.addUserForm.password,callback:function(t){e.$set(e.addUserForm,"password","string"===typeof t?t.trim():t)},expression:"addUserForm.password"}})],1)],1),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){return e.fnCloseUserDialog("addUserForm")}}},[e._v("取 消")]),r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.fnAddUser("addUserForm")}}},[e._v("新 增")])],1)],1),r("el-dialog",{attrs:{title:"分配角色",visible:e.roleDialogVisible,width:"30%"},on:{"update:visible":function(t){e.roleDialogVisible=t}}},[r("el-checkbox-group",{model:{value:e.checkedRoles,callback:function(t){e.checkedRoles=t},expression:"checkedRoles"}},e._l(e.roles,(function(t){return r("el-checkbox",{key:t.id,attrs:{label:t.id}},[e._v(e._s(t.name))])})),1),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:e.fnCloseRoleDialog}},[e._v("取 消")]),r("el-button",{attrs:{type:"primary"},on:{click:e.fnAssignRoles}},[e._v("确 定")])],1)],1)],1)},a=[],s=(r("d9e2"),{name:"user",data:function(){return{searchForm:{nickname:""},tableData:[],current:1,pageSize:10,total:0,addUserForm:{username:"",password:"",nickname:""},userDialogVisible:!1,roleDialogVisible:!1,roles:[],checkedRoles:[],roleUserId:""}},mounted:function(){this.getUserList()},methods:{getUserList:function(){var e=this;this.$axios({method:"post",url:"/user/getUserList",data:{pageNum:this.current,pageSize:this.pageSize,nickname:this.searchForm.nickname}}).then((function(t){200===t.data.code&&(e.total=t.data.data.total,e.tableData=t.data.data.list)}))},fnCloseRoleDialog:function(){this.roleDialogVisible=!1,this.roleUserId=0},fnOpenAddUserDialog:function(){this.userDialogVisible=!0},fnOpenRoleDialog:function(e){var t=this;this.roleDialogVisible=!0,this.roleUserId=e,this.$axios({method:"post",url:"/user/queryUserRole",data:{id:e}}).then((function(e){t.roles=e.data.data.roles,t.checkedRoles=e.data.data.checked}))},fnAddUser:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;var r=t.rsa.encrypt(t.addUserForm.password);t.$axios({method:"post",url:"/user/addUser",data:{username:t.addUserForm.username,nickname:t.addUserForm.nickname,password:r}}).then((function(){t.$message.success("新增成功"),t.userDialogVisible=!1,t.getUserList()}))}))},fnCloseUserDialog:function(e){this.$refs[e].resetFields(),this.userDialogVisible=!1},fnDeleteUser:function(e){var t=this;e!==this.$store.state.userInfo.id?this.$axios({method:"post",url:"/user/deleteUser",params:{id:e}}).then((function(e){200===e.data.code&&(t.$message.success("删除成功"),t.getUserList())})):this.$message.warning("无法删除自己本身")},fnResetPassword:function(e){var t=this;this.$axios({method:"post",url:"/user/resetPassword",data:{id:e}}).then((function(e){t.$message.success(e.data.msg)}))},fnChangeEnable:function(e,t){var r=this;this.$axios({method:"post",url:"/user/changeEnable",data:{id:e,enable:t}}).then((function(e){r.$message.success(e.data.msg),r.getUserList()}))},handleSizeChange:function(e){this.pageSize=e,this.getUserList()},handleCurrentChange:function(e){this.current=e,this.getUserList()},fnAssignRoles:function(){var e=this;this.$axios({method:"post",url:"/user/assignRoles",data:{userId:this.roleUserId,roleIds:this.checkedRoles}}).then((function(t){e.$message.success(t.data.msg),e.roleUserId=0,e.roleDialogVisible=!1,e.getUserList()}))},checkUsername:function(e,t,r){this.$axios({method:"post",url:"/user/checkUsername",params:{username:t}}).then((function(e){return 0!==e.data.data?r(new Error("该用户名已存在")):r()}))}}}),o=s,i=r("2877"),l=Object(i["a"])(o,n,a,!1,null,"842db128",null);t["default"]=l.exports},7156:function(e,t,r){var n=r("1626"),a=r("861d"),s=r("d2bb");e.exports=function(e,t,r){var o,i;return s&&n(o=t.constructor)&&o!==r&&a(i=o.prototype)&&i!==r.prototype&&s(e,i),e}},ab36:function(e,t,r){var n=r("861d"),a=r("9112");e.exports=function(e,t){n(t)&&"cause"in t&&a(e,"cause",t.cause)}},b980:function(e,t,r){var n=r("d039"),a=r("5c6c");e.exports=!n((function(){var e=Error("a");return!("stack"in e)||(Object.defineProperty(e,"stack",a(1,7)),7!==e.stack)}))},c770:function(e,t,r){var n=r("e330"),a=n("".replace),s=function(e){return String(Error(e).stack)}("zxcasd"),o=/\n\s*at [^:]*:[^\n]*/,i=o.test(s);e.exports=function(e,t){if(i&&"string"==typeof e)while(t--)e=a(e,o,"");return e}},d9e2:function(e,t,r){var n=r("23e7"),a=r("da84"),s=r("2ba4"),o=r("e5cb"),i="WebAssembly",l=a[i],c=7!==Error("e",{cause:7}).cause,u=function(e,t){var r={};r[e]=o(e,t,c),n({global:!0,forced:c},r)},d=function(e,t){if(l&&l[e]){var r={};r[e]=o(i+"."+e,t,c),n({target:i,stat:!0,forced:c},r)}};u("Error",(function(e){return function(t){return s(e,this,arguments)}})),u("EvalError",(function(e){return function(t){return s(e,this,arguments)}})),u("RangeError",(function(e){return function(t){return s(e,this,arguments)}})),u("ReferenceError",(function(e){return function(t){return s(e,this,arguments)}})),u("SyntaxError",(function(e){return function(t){return s(e,this,arguments)}})),u("TypeError",(function(e){return function(t){return s(e,this,arguments)}})),u("URIError",(function(e){return function(t){return s(e,this,arguments)}})),d("CompileError",(function(e){return function(t){return s(e,this,arguments)}})),d("LinkError",(function(e){return function(t){return s(e,this,arguments)}})),d("RuntimeError",(function(e){return function(t){return s(e,this,arguments)}}))},e391:function(e,t,r){var n=r("577e");e.exports=function(e,t){return void 0===e?arguments.length<2?"":t:n(e)}},e5cb:function(e,t,r){"use strict";var n=r("d066"),a=r("1a2d"),s=r("9112"),o=r("3a9b"),i=r("d2bb"),l=r("e893"),c=r("7156"),u=r("e391"),d=r("ab36"),f=r("c770"),p=r("b980"),m=r("c430");e.exports=function(e,t,r,h){var g=h?2:1,b=e.split("."),v=b[b.length-1],k=n.apply(null,b);if(k){var U=k.prototype;if(!m&&a(U,"cause")&&delete U.cause,!r)return k;var w=n("Error"),y=t((function(e,t){var r=u(h?t:e,void 0),n=h?new k(e):new k;return void 0!==r&&s(n,"message",r),p&&s(n,"stack",f(n.stack,2)),this&&o(U,this)&&c(n,this,y),arguments.length>g&&d(n,arguments[g]),n}));if(y.prototype=U,"Error"!==v&&(i?i(y,w):l(y,w,{name:!0})),l(y,k),!m)try{U.name!==v&&s(U,"name",v),U.constructor=y}catch(x){}return y}}}}]);