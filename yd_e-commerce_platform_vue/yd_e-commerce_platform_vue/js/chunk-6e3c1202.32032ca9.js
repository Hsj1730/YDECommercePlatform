(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6e3c1202","chunk-5d803486","chunk-272b8287"],{7156:function(e,t,r){var a=r("1626"),s=r("861d"),i=r("d2bb");e.exports=function(e,t,r){var o,n;return i&&a(o=t.constructor)&&o!==r&&s(n=o.prototype)&&n!==r.prototype&&i(e,n),e}},"858d":function(e,t,r){"use strict";r("8db4")},"8db4":function(e,t,r){},ab36:function(e,t,r){var a=r("861d"),s=r("9112");e.exports=function(e,t){a(t)&&"cause"in t&&s(e,"cause",t.cause)}},b980:function(e,t,r){var a=r("d039"),s=r("5c6c");e.exports=!a((function(){var e=Error("a");return!("stack"in e)||(Object.defineProperty(e,"stack",s(1,7)),7!==e.stack)}))},c27d:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"app-container",staticStyle:{"margin-top":"-20px"}},[r("el-row",{attrs:{gutter:20}},[r("el-col",{staticStyle:{"margin-bottom":"10px"},attrs:{xs:24,sm:24,md:8,lg:6,xl:5}},[r("el-card",{staticClass:"box-card"},[r("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[r("span",[e._v("个人信息")])]),r("div",[r("div",{staticStyle:{"text-align":"center"}},[r("el-upload",{staticClass:"avatar-uploader",attrs:{"show-file-list":!1,"on-success":e.handleSuccess,"on-error":e.handleError,"before-upload":e.beforeUpload,"on-exceed":e.handleExceed,headers:e.headers,action:this.$axios.defaults.baseURL+"/user/uploadAvatar"}},[r("img",{staticClass:"avatar",attrs:{src:e.user.avatar,alt:"",title:"点击上传头像"}})])],1),r("ul",{staticClass:"user-info"},[r("li",[r("div",{staticStyle:{height:"100%"}},[r("svg-icon",{attrs:{"icon-class":"login"}}),e._v(" 登录账号 "),r("div",{staticClass:"user-right"},[e._v(e._s(e.user.username))])],1)]),r("li",[r("svg-icon",{attrs:{"icon-class":"user1"}}),e._v(" 用户昵称 "),r("div",{staticClass:"user-right"},[e._v(e._s(e.user.nickname))])],1),r("li",[r("svg-icon",{attrs:{"icon-class":"user1"}}),e._v(" 性别 "),r("div",{staticClass:"user-right"},[e._v(" "+e._s("0"===e.user.gender?"未知":"1"===e.user.gender?"男":"女")+" ")])],1),r("li",[r("svg-icon",{attrs:{"icon-class":"phone"}}),e._v(" 手机号码 "),r("div",{staticClass:"user-right"},[e._v(e._s(e.user.mobile))])],1),r("li",[r("svg-icon",{attrs:{"icon-class":"email"}}),e._v(" 用户邮箱 "),r("div",{staticClass:"user-right"},[e._v(e._s(e.user.email))])],1),r("li",[r("svg-icon",{attrs:{"icon-class":"anq"}}),e._v(" 安全设置 "),r("div",{staticClass:"user-right"},[r("a",{on:{click:function(t){e.$refs.pass.dialog=!0}}},[e._v("修改密码")]),e._v("   "),r("a",{on:{click:function(t){e.$refs.email.dialog=!0}}},[e._v("修改邮箱")])])],1)])])])],1),r("el-col",{attrs:{xs:24,sm:24,md:16,lg:18,xl:19}},[r("el-card",{staticClass:"box-card"},[r("el-tabs",{on:{"tab-click":e.handleClick},model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},[r("el-tab-pane",{attrs:{label:"用户资料",name:"first"}},[r("el-form",{ref:"form",staticStyle:{"margin-top":"10px"},attrs:{model:e.form,size:"small","label-width":"65px"}},[r("el-form-item",{attrs:{label:"昵称",prop:"nickname",rules:{required:!0,message:"请输入用户昵称",trigger:"blur"}}},[r("el-input",{staticStyle:{width:"35%"},attrs:{clearable:""},model:{value:e.form.nickname,callback:function(t){e.$set(e.form,"nickname","string"===typeof t?t.trim():t)},expression:"form.nickname"}}),r("span",{staticStyle:{color:"#c0c0c0","margin-left":"10px"}},[e._v("用户昵称不作为登录使用")])],1),r("el-form-item",{attrs:{label:"手机号",prop:"mobile",rules:[{required:!0,trigger:"blur",message:"请输入手机号"},{validator:e.checkMobile}]}},[r("el-input",{staticStyle:{width:"35%"},attrs:{oninput:"value=value.replace(/[^\\d]/g,'')",maxLength:"11",clearable:""},model:{value:e.form.mobile,callback:function(t){e.$set(e.form,"mobile","string"===typeof t?t.trim():t)},expression:"form.mobile"}}),r("span",{staticStyle:{color:"#c0c0c0","margin-left":"10px"}},[e._v("手机号码不能重复")])],1),r("el-form-item",{attrs:{label:"性别"}},[r("el-radio-group",{staticStyle:{width:"200px"},model:{value:e.form.gender,callback:function(t){e.$set(e.form,"gender",t)},expression:"form.gender"}},[r("el-radio",{staticStyle:{display:"none"},attrs:{label:"0"}},[e._v("未知")]),r("el-radio",{attrs:{label:"1"}},[e._v("男")]),r("el-radio",{attrs:{label:"2"}},[e._v("女")])],1)],1),r("el-form-item",[r("el-button",{attrs:{size:"mini",type:"primary"},on:{click:e.saveUserInfo}},[e._v("保存配置")])],1)],1)],1),r("el-tab-pane",{attrs:{label:"操作日志",name:"second"}},[r("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%"},attrs:{data:e.logData}},[r("el-table-column",{attrs:{prop:"name",label:"行为",align:"center"}}),r("el-table-column",{attrs:{prop:"ip",label:"IP",width:"150",align:"center"}}),r("el-table-column",{attrs:{prop:"browser",label:"浏览器",width:"100",align:"center"}}),r("el-table-column",{attrs:{prop:"status",label:"请求状态",width:"100",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[200===t.row.status?r("el-tag",{attrs:{type:"success"}},[e._v("成功")]):r("el-tag",{attrs:{type:"danger"}},[e._v("失败")])]}}])}),r("el-table-column",{attrs:{prop:"executeTime",label:"请求耗时",align:"center",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.executeTime<=300?r("el-tag",[e._v(e._s(t.row.executeTime)+"ms")]):t.row.executeTime<=1e3?r("el-tag",{attrs:{type:"warning"}},[e._v(e._s(t.row.executeTime)+"ms")]):r("el-tag",{attrs:{type:"danger"}},[e._v(e._s(t.row.executeTime)+"ms")])]}}])}),r("el-table-column",{attrs:{align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.createTime))])]}}])},[r("template",{slot:"header"},[r("div",{staticStyle:{display:"inline-block",cursor:"pointer"},on:{click:e.getUserLogList}},[e._v(" 请求日期"),r("i",{staticClass:"el-icon-refresh",staticStyle:{"margin-left":"10px"}})])])],2)],1),r("el-pagination",{staticStyle:{float:"right","margin-top":"10px","padding-bottom":"10px"},attrs:{"current-page":e.current,"page-sizes":[10,20,50,100],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)],1)],1)],1),r("update-pass",{ref:"pass"}),r("update-email",{ref:"email",attrs:{email:e.user.email}})],1)},s=[],i=(r("b0c0"),r("ac1f"),r("00b4"),r("d9e2"),r("4360")),o=r("fcc6"),n=r("fcfc"),l=r("5f87"),c={name:"userCenter",components:{updatePass:o["default"],updateEmail:n["default"]},data:function(){return{user:{},activeName:"first",form:{},headers:{Authorization:Object(l["a"])()},current:1,pageSize:10,total:0,logData:[],loading:!1}},mounted:function(){this.getUserInfo()},methods:{handleExceed:function(){this.$message({type:"error",message:"最多支持1个附件上传"})},beforeUpload:function(e){var t=e.name,r=/^.+(.png|.jpg|.jpeg|.bmp|.gif)$/;if(!r.test(t))return this.$message.error("上传图片只能是 JPG、JPEG、PNG、GIF 格式!"),!1;var a=e.size,s=this.settings.fileSize;return!(a>s)||(this.$message.error("最大上传"+s/1024/1024+"M"),!1)},getUserLogList:function(){var e=this;this.loading=!0,this.$axios({method:"post",url:"/log/getUserLogList",data:{userId:this.user.id,pageNum:this.current,pageSize:this.pageSize}}).then((function(t){e.loading=!1,e.logData=t.data.data.list,e.total=t.data.data.total})).catch((function(){e.loading=!1}))},handleSizeChange:function(e){this.pageSize=e,this.getUserLogList()},handleCurrentChange:function(e){this.current=e,this.getUserLogList()},handleClick:function(e){"second"===e.name&&this.getUserLogList()},saveUserInfo:function(){var e=this;this.$refs["form"]&&this.$refs["form"].validate((function(t){t&&e.$axios({method:"post",url:"/user/saveUserInfo",data:e.form}).then((function(){e.$message.success("保存成功"),e.getUserInfo()}))}))},handleSuccess:function(){this.$message.success("头像修改成功"),this.getUserInfo()},handleError:function(e){var t=JSON.parse(e.message);this.$message.error(t.message)},getUserInfo:function(){var e=this;this.$axios({method:"post",url:"/user/getUserDetailedInfo"}).then((function(t){e.user=t.data.data,e.form={id:e.user.id,nickname:e.user.nickname,mobile:e.user.mobile,gender:e.user.gender},i["a"].commit("setUserInfo",e.user)}))},checkMobile:function(e,t,r){return this.dataValidate.validateMobile(t)?(t!==this.user.mobile&&this.$axios({method:"post",url:"/user/checkMobile",params:{mobile:t}}).then((function(e){return 0!==e.data.data?r(new Error("该手机号已存在")):r()})),r()):r(new Error("请输入正确的手机号码"))}}},u=c,d=(r("858d"),r("2877")),f=Object(d["a"])(u,a,s,!1,null,null,null);t["default"]=f.exports},c770:function(e,t,r){var a=r("e330"),s=a("".replace),i=function(e){return String(Error(e).stack)}("zxcasd"),o=/\n\s*at [^:]*:[^\n]*/,n=o.test(i);e.exports=function(e,t){if(n&&"string"==typeof e)while(t--)e=s(e,o,"");return e}},d9e2:function(e,t,r){var a=r("23e7"),s=r("da84"),i=r("2ba4"),o=r("e5cb"),n="WebAssembly",l=s[n],c=7!==Error("e",{cause:7}).cause,u=function(e,t){var r={};r[e]=o(e,t,c),a({global:!0,forced:c},r)},d=function(e,t){if(l&&l[e]){var r={};r[e]=o(n+"."+e,t,c),a({target:n,stat:!0,forced:c},r)}};u("Error",(function(e){return function(t){return i(e,this,arguments)}})),u("EvalError",(function(e){return function(t){return i(e,this,arguments)}})),u("RangeError",(function(e){return function(t){return i(e,this,arguments)}})),u("ReferenceError",(function(e){return function(t){return i(e,this,arguments)}})),u("SyntaxError",(function(e){return function(t){return i(e,this,arguments)}})),u("TypeError",(function(e){return function(t){return i(e,this,arguments)}})),u("URIError",(function(e){return function(t){return i(e,this,arguments)}})),d("CompileError",(function(e){return function(t){return i(e,this,arguments)}})),d("LinkError",(function(e){return function(t){return i(e,this,arguments)}})),d("RuntimeError",(function(e){return function(t){return i(e,this,arguments)}}))},e391:function(e,t,r){var a=r("577e");e.exports=function(e,t){return void 0===e?arguments.length<2?"":t:a(e)}},e5cb:function(e,t,r){"use strict";var a=r("d066"),s=r("1a2d"),i=r("9112"),o=r("3a9b"),n=r("d2bb"),l=r("e893"),c=r("7156"),u=r("e391"),d=r("ab36"),f=r("c770"),m=r("b980"),p=r("c430");e.exports=function(e,t,r,g){var h=g?2:1,b=e.split("."),v=b[b.length-1],w=a.apply(null,b);if(w){var x=w.prototype;if(!p&&s(x,"cause")&&delete x.cause,!r)return w;var y=a("Error"),k=t((function(e,t){var r=u(g?t:e,void 0),a=g?new w(e):new w;return void 0!==r&&i(a,"message",r),m&&i(a,"stack",f(a.stack,2)),this&&o(x,this)&&c(a,this,k),arguments.length>h&&d(a,arguments[h]),a}));if(k.prototype=x,"Error"!==v&&(n?n(k,y):l(k,y,{name:!0})),l(k,w),!p)try{x.name!==v&&i(x,"name",v),x.constructor=k}catch(_){}return k}}},fcc6:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticStyle:{display:"inline-block"}},[r("el-dialog",{attrs:{visible:e.dialog,"close-on-click-modal":!1,"before-close":e.cancel,title:e.title,"append-to-body":"",width:"500px"},on:{"update:visible":function(t){e.dialog=t},close:e.cancel}},[r("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"88px"}},[r("el-form-item",{attrs:{label:"旧密码",prop:"oldPass"}},[r("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on",clearable:""},model:{value:e.form.oldPass,callback:function(t){e.$set(e.form,"oldPass",t)},expression:"form.oldPass"}})],1),r("el-form-item",{attrs:{label:"新密码",prop:"newPass"}},[r("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on",clearable:""},model:{value:e.form.newPass,callback:function(t){e.$set(e.form,"newPass",t)},expression:"form.newPass"}})],1),r("el-form-item",{attrs:{label:"确认密码",prop:"confirmPass"}},[r("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on",clearable:""},model:{value:e.form.confirmPass,callback:function(t){e.$set(e.form,"confirmPass",t)},expression:"form.confirmPass"}})],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),r("el-button",{attrs:{type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)],1)},s=[],i=(r("d9e2"),r("5f87")),o={name:"updatePass",data:function(){var e=this,t=function(t,r,a){r?e.form.newPass!==r?a(new Error("两次输入的密码不一致")):a():a(new Error("请再次输入密码"))},r=function(t,r,a){var s=e.dataValidate.validatePass(r,e.settings.level);""===s?a():a(new Error(s))};return{dialog:!1,title:"修改密码",form:{oldPass:"",newPass:"",confirmPass:""},rules:{oldPass:[{required:!0,message:"请输入旧密码",trigger:"blur"}],newPass:[{required:!0,message:"请输入新密码",trigger:"blur"},{min:6,max:18,message:"长度在 6 到 18 个字符",trigger:"blur"},{validator:r,trigger:"blur"}],confirmPass:[{required:!0,validator:t,trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},resetForm:function(){this.dialog=!1,this.$refs["form"].resetFields(),this.form={oldPass:"",newPass:"",confirmPass:""}},doSubmit:function(){var e=this;this.$refs["form"].validate((function(t){if(!t)return!1;e.$axios({method:"post",url:"/user/updatePass",data:{id:e.$store.state.userInfo.id,oldPass:e.rsa.encrypt(e.form.oldPass),newPass:e.rsa.encrypt(e.form.newPass)}}).then((function(t){200===t.data.code&&(e.$message.success("密码修改成功，请重新登录"),setTimeout((function(){e.$axios.post("/logout").then((function(t){Object(i["b"])(),sessionStorage.clear(),e.$message.success(t.data.msg),e.$store.commit("resetState"),e.$router.push("/login")}))}),2e3))}))}))}}},n=o,l=r("2877"),c=Object(l["a"])(n,a,s,!1,null,"1f314337",null);t["default"]=c.exports},fcfc:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticStyle:{display:"inline-block"}},[r("el-dialog",{attrs:{visible:e.dialog,"close-on-click-modal":!1,"before-close":e.cancel,title:e.title,"append-to-body":"",width:"475px"},on:{"update:visible":function(t){e.dialog=t},close:e.cancel}},[r("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"88px"}},[r("el-form-item",{attrs:{label:"新邮箱",prop:"email"}},[r("el-input",{staticStyle:{width:"320px"},attrs:{clearable:""},model:{value:e.form.email,callback:function(t){e.$set(e.form,"email",t)},expression:"form.email"}})],1),r("el-form-item",{attrs:{label:"当前密码",prop:"password"}},[r("el-input",{staticStyle:{width:"320px"},attrs:{type:"password",clearable:""},model:{value:e.form.password,callback:function(t){e.$set(e.form,"password",t)},expression:"form.password"}})],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),r("el-button",{attrs:{type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)],1)},s=[],i=(r("d9e2"),{name:"updateEmail",props:{email:{type:String,required:!0,default:function(){return""}}},data:function(){var e=this,t=function(t,r,a){""===r||null===r?a(new Error("新邮箱不能为空")):r===e.email?a(new Error("新邮箱不能与旧邮箱相同")):e.dataValidate.validEmail(r)?a():a(new Error("邮箱格式错误"))};return{dialog:!1,title:"修改邮箱",form:{password:"",email:""},user:{email:"",password:""},time:60,rules:{password:[{required:!0,message:"当前密码不能为空",trigger:"blur"}],email:[{required:!0,validator:t,trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs["form"].validate((function(t){if(!t)return!1;e.$axios({method:"post",url:"/user/resetEmail",data:{id:e.$store.state.userInfo.id,password:e.rsa.encrypt(e.form.password),email:e.form.email}}).then((function(t){200===t.data.code&&(e.resetForm(),e.$message.success("邮箱修改成功"),e.$parent.getUserInfo())}))}))},resetForm:function(){this.dialog=!1,this.$refs["form"].resetFields(),this.form={password:"",email:""}}}}),o=i,n=r("2877"),l=Object(n["a"])(o,a,s,!1,null,"7eb9b2b7",null);t["default"]=l.exports}}]);