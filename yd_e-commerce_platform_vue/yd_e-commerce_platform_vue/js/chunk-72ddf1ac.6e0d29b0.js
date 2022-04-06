(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-72ddf1ac","chunk-36ab4744"],{"0c53":function(e,t,r){},"3aa4":function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"app-container"},[r("div",{staticClass:"head-container",staticStyle:{"margin-top":"-20px"}},[r("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{clearable:"",placeholder:"输入搜索内容"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.toQuery.apply(null,arguments)}},model:{value:e.query.value,callback:function(t){e.$set(e.query,"value",t)},expression:"query.value"}}),r("el-select",{staticClass:"filter-item",staticStyle:{width:"150px","padding-left":"10px"},attrs:{clearable:"",placeholder:"类型"},model:{value:e.query.type,callback:function(t){e.$set(e.query,"type",t)},expression:"query.type"}},e._l(e.queryTypeOptions,(function(e){return r("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1),r("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{size:"mini",type:"success",icon:"el-icon-search"},on:{click:e.toQuery}},[e._v("搜索")]),r("el-button",{staticClass:"filter-item",attrs:{type:"danger",size:"mini",icon:"el-icon-refresh"},on:{click:e.reset}},[e._v("重置")])],1),r("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%"},attrs:{data:e.tableData,size:"small"}},[r("el-table-column",{attrs:{prop:"id",align:"center",width:"80",label:"商品id"}}),r("el-table-column",{ref:"table",attrs:{align:"center",width:"150",prop:"image",label:"商品图片"},scopedSlots:e._u([{key:"default",fn:function(e){return[r("a",{staticStyle:{color:"#42b983"},attrs:{href:e.row.image,target:"_blank"}},[r("img",{staticClass:"el-avatar",attrs:{src:e.row.image,alt:"点击打开"}})])]}}])}),r("el-table-column",{attrs:{prop:"storeName",align:"center",label:"商品名称"}}),r("el-table-column",{attrs:{prop:"barCode",align:"center",width:"100",label:"商品编码"}}),r("el-table-column",{attrs:{prop:"cateName",align:"center",width:"100",label:"分类名称"}}),r("el-table-column",{attrs:{prop:"price",align:"center",width:"100",label:"商品价格",sortable:""}}),r("el-table-column",{attrs:{prop:"sales",align:"center",width:"100",label:"销量",sortable:""}}),r("el-table-column",{attrs:{prop:"stock",align:"center",width:"100",label:"库存",sortable:""}}),r("el-table-column",{attrs:{label:"状态",width:"100",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("div",{on:{click:function(r){return e.onSale(t.row.id,t.row.isShow)}}},["1"===t.row.isShow?r("el-tag",{staticStyle:{cursor:"pointer"}},[e._v("已上架")]):r("el-tag",{staticStyle:{cursor:"pointer"},attrs:{type:"info"}},[e._v("已下架")])],1)]}}])}),r("el-table-column",{attrs:{align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{staticStyle:{"margin-right":"10px"},attrs:{size:"mini",type:"primary",icon:"el-icon-edit"},on:{click:function(r){return e.edit(t.row.id)}}}),r("el-popover",{ref:t.row.id,attrs:{placement:"top",width:"180"}},[r("p",[e._v("确定删除本条数据吗？")]),r("div",{staticStyle:{"text-align":"right",margin:"0"}},[r("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(r){e.$refs[t.row.id].doClose()}}},[e._v("取消")]),r("el-button",{attrs:{loading:e.delLoading,type:"primary",size:"mini"},on:{click:function(r){return e.subDelete(t.row.id)}}},[e._v("确定")])],1),r("el-button",{attrs:{slot:"reference",type:"danger",icon:"el-icon-delete",size:"mini"},slot:"reference"})],1)]}}])},[r("template",{slot:"header"},[r("div",{staticStyle:{display:"inline-block",cursor:"pointer"},on:{click:e.toQuery}},[e._v(" 操作"),r("i",{staticClass:"el-icon-refresh",staticStyle:{"margin-left":"10px"}})])])],2)],1),r("el-pagination",{staticStyle:{float:"right","margin-top":"10px","padding-bottom":"10px"},attrs:{"current-page":e.page.current,"page-sizes":[10,20,50,100],"page-size":e.page.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.page.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),r("goods-info",{ref:"goodsInfoForm",attrs:{"is-edit":e.isEdit}})],1)},o=[],i=r("8844"),l={name:"unOnSale",components:{goodsInfo:i["default"]},data:function(){return{query:{value:"",type:"storeName"},queryTypeOptions:[{key:"storeName",display_name:"商品名称"},{key:"cateName",display_name:"分类名称"},{key:"barCode",display_name:"商品编码"}],loading:!1,delLoading:!1,isEdit:!0,tableData:[],page:{current:1,pageSize:10,total:0}}},created:function(){this.init()},methods:{init:function(){this.page={current:1,pageSize:10,total:0},this.toQuery()},reset:function(){this.query={value:"",type:"storeName"},this.init()},toQuery:function(){var e=this;this.loading=!0,this.$axios({method:"post",url:"/goods/getGoodsList",data:{query:this.query,pageNum:this.page.current,pageSize:this.page.pageSize,isShow:"0"}}).then((function(t){e.loading=!1,e.tableData=t.data.data.list,e.page.total=t.data.data.total,e.page.current=t.data.data.pageNum,e.page.pageSize=t.data.data.pageSize})).catch((function(){e.loading=!1}))},handleSizeChange:function(e){this.page.pageSize=e,this.toQuery()},handleCurrentChange:function(e){this.page.current=e,this.toQuery()},edit:function(e){var t=this;this.isEdit=!0,this.$refs.goodsInfoForm.dialog=!0,this.$refs.goodsInfoForm.getCateTree(),this.$axios({method:"post",url:"/goods/getGoodsInfo/"+e}).then((function(e){t.$refs.goodsInfoForm.form=e.data.data}))},onSale:function(e,t){var r=this;this.$confirm("确定进行[ ".concat("1"===t?"下架":"上架"," ]操作?"),"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){r.$axios({method:"post",url:"/goods/setGoodsIsShow/"+e,params:{status:"1"===t?"0":"1"}}).then((function(){r.$message({message:"操作成功",type:"success",duration:1e3,onClose:function(){r.toQuery()}})}))}))}}},s=l,n=r("2877"),c=Object(n["a"])(s,a,o,!1,null,"b3cc4a92",null);t["default"]=c.exports},8844:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-dialog",{staticStyle:{"padding-bottom":"5vh"},attrs:{"append-to-body":!0,"close-on-click-modal":!1,"before-close":e.cancel,visible:e.dialog,top:"5vh",title:e.isEdit?"编辑商品":"新增商品",width:"800px"},on:{"update:visible":function(t){e.dialog=t}}},[r("el-form",{ref:"form",attrs:{model:e.form,inline:!0,rules:e.rules,size:"small","label-width":"80px"}},[r("el-form-item",{attrs:{label:"商品分类",prop:"cateId"}},[r("tree-select",{staticStyle:{width:"250px"},attrs:{options:e.cateTree,normalizer:e.normalizer,placeholder:"选择商品分类(只能选择二级分类)"},model:{value:e.form.cateId,callback:function(t){e.$set(e.form,"cateId","string"===typeof t?t.trim():t)},expression:"form.cateId"}})],1),r("br"),r("el-form-item",{attrs:{label:"商品名称",prop:"storeName"}},[r("el-input",{staticStyle:{width:"250px"},attrs:{clearable:""},model:{value:e.form.storeName,callback:function(t){e.$set(e.form,"storeName",t)},expression:"form.storeName"}})],1),r("el-form-item",{attrs:{label:"关键字",prop:"keyword"}},[r("el-input",{staticStyle:{width:"250px"},attrs:{clearable:""},model:{value:e.form.keyword,callback:function(t){e.$set(e.form,"keyword",t)},expression:"form.keyword"}})],1),r("el-form-item",{attrs:{label:"单位名",prop:"unitName"}},[r("el-input",{staticStyle:{width:"250px"},attrs:{clearable:""},model:{value:e.form.unitName,callback:function(t){e.$set(e.form,"unitName","string"===typeof t?t.trim():t)},expression:"form.unitName"}})],1),r("el-form-item",{attrs:{label:"商品条码",prop:"barCode"}},[r("el-input",{staticStyle:{width:"250px"},attrs:{disabled:!0},model:{value:e.form.barCode,callback:function(t){e.$set(e.form,"barCode","string"===typeof t?t.trim():t)},expression:"form.barCode"}})],1),r("el-form-item",{attrs:{label:"商品图片",prop:"image"}},[r("material-list",{attrs:{type:"image",num:1,width:150,height:150},model:{value:e.form.imageArr,callback:function(t){e.$set(e.form,"imageArr","string"===typeof t?t.trim():t)},expression:"form.imageArr"}})],1),r("br"),r("el-form-item",{attrs:{label:"轮播图",prop:"sliderImage"}},[r("material-list",{attrs:{type:"image",num:4,width:150,height:150},model:{value:e.form.sliderImageArr,callback:function(t){e.$set(e.form,"sliderImageArr","string"===typeof t?t.trim():t)},expression:"form.sliderImageArr"}})],1),r("el-form-item",{attrs:{label:"商品简介",prop:"storeInfo"}},[r("el-input",{staticStyle:{width:"500px"},attrs:{rows:"2",type:"textarea"},model:{value:e.form.storeInfo,callback:function(t){e.$set(e.form,"storeInfo",t)},expression:"form.storeInfo"}})],1),r("el-form-item",{attrs:{label:"商品描述",prop:"description"}},[r("el-input",{staticStyle:{width:"500px"},attrs:{rows:"4",type:"textarea"},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1),r("el-form-item",{attrs:{label:"商品价格",prop:"price"}},[r("el-input-number",{attrs:{min:0,max:99999999.99,precision:2,step:10,"controls-position":"right"},model:{value:e.form.price,callback:function(t){e.$set(e.form,"price",t)},expression:"form.price"}})],1),r("el-form-item",{attrs:{label:"成本价",prop:"cost"}},[r("el-input-number",{attrs:{min:0,max:99999999.99,precision:2,step:10,"controls-position":"right"},model:{value:e.form.cost,callback:function(t){e.$set(e.form,"cost",t)},expression:"form.cost"}})],1),r("el-form-item",{attrs:{label:"邮费",prop:"postage"}},[r("el-input-number",{attrs:{min:0,max:99999999.99,precision:2,step:10,"controls-position":"right"},model:{value:e.form.postage,callback:function(t){e.$set(e.form,"postage",t)},expression:"form.postage"}})],1),r("el-form-item",{attrs:{label:"排序",prop:"sort"}},[r("el-input-number",{attrs:{min:1},model:{value:e.form.sort,callback:function(t){e.$set(e.form,"sort",t)},expression:"form.sort"}})],1),r("el-form-item",{attrs:{label:"销量",prop:"sales"}},[r("el-input-number",{attrs:{min:0,step:10},model:{value:e.form.sales,callback:function(t){e.$set(e.form,"sales",t)},expression:"form.sales"}})],1),r("el-form-item",{attrs:{label:"库存",prop:"stock"}},[r("el-input-number",{attrs:{min:0,step:10},model:{value:e.form.stock,callback:function(t){e.$set(e.form,"stock",t)},expression:"form.stock"}})],1),r("el-form-item",{attrs:{label:"上架",prop:"isShow"}},[r("el-radio-group",{model:{value:e.form.isShow,callback:function(t){e.$set(e.form,"isShow",t)},expression:"form.isShow"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1),r("el-form-item",{attrs:{label:"热卖单品",prop:"isHot"}},[r("el-radio-group",{model:{value:e.form.isHot,callback:function(t){e.$set(e.form,"isHot",t)},expression:"form.isHot"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1),r("el-form-item",{attrs:{label:"促销单品",prop:"isBenefit"}},[r("el-radio-group",{model:{value:e.form.isBenefit,callback:function(t){e.$set(e.form,"isBenefit",t)},expression:"form.isBenefit"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1),r("el-form-item",{attrs:{label:"精品推荐",prop:"isBest"}},[r("el-radio-group",{model:{value:e.form.isBest,callback:function(t){e.$set(e.form,"isBest",t)},expression:"form.isBest"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1),r("el-form-item",{attrs:{label:"首发新品",prop:"isNew"}},[r("el-radio-group",{model:{value:e.form.isNew,callback:function(t){e.$set(e.form,"isNew",t)},expression:"form.isNew"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1),r("el-form-item",{attrs:{label:"优品推荐",prop:"isGood"}},[r("el-radio-group",{model:{value:e.form.isGood,callback:function(t){e.$set(e.form,"isGood",t)},expression:"form.isGood"}},[r("el-radio",{attrs:{label:"1"}},[e._v("是")]),r("el-radio",{staticStyle:{width:"90px"},attrs:{label:"0"}},[e._v("否")])],1)],1)],1),r("div",{staticClass:"dialog-footer",staticStyle:{"margin-right":"50px"},attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),r("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},o=[],i=(r("a15b"),r("d3b7"),r("159b"),r("ca17")),l=r.n(i),s=(r("542c"),r("2075")),n={name:"goodsInfo",components:{treeSelect:l.a,materialList:s["a"]},props:{isEdit:{type:Boolean,required:!0,default:!1}},data:function(){return{loading:!1,dialog:!1,cateTree:[],form:{id:null,image:"",sliderImage:"",barCode:"",imageArr:[],sliderImageArr:[],storeName:"",unitName:"",storeInfo:"",keyword:"",cateId:null,price:0,cost:0,postage:0,sort:1,sales:0,stock:0,isShow:"1",isHot:"0",isBenefit:"0",isBest:"0",isNew:"0",isGood:"0",description:""},rules:{cateId:[{required:!0,message:"请选择商品分类",trigger:"blur"}],storeName:[{required:!0,message:"请输入商品名称",trigger:"blur"}],unitName:[{required:!0,message:"请输入商品单位",trigger:"blur"}],keyword:[{required:!0,message:"请输入商品关键字",trigger:"blur"}],storeInfo:[{required:!0,message:"请填写商品简介",trigger:"blur"}]}}},watch:{"form.imageArr":function(e){this.form.image=e.join(",")},"form.sliderImageArr":function(e){e&&(this.form.sliderImage=e.join(","))}},methods:{doSubmit:function(){var e=this;this.$refs["form"].validate((function(t){return!!t&&(0===e.form.imageArr.length?(e.$message.error("请添加商品图片"),!1):0===e.form.sliderImageArr.length?(e.$message.error("请添加商品轮播图"),!1):(e.form.image=e.form.imageArr[0].url,e.form.sliderImage="",e.form.sliderImageArr.forEach((function(t){e.form.sliderImage+=t.url+","})),e.form.sliderImage.length>0&&(e.form.sliderImage=e.form.sliderImage.substr(0,e.form.sliderImage.length-1)),e.loading=!0,void e.$axios({method:"post",url:"/goods/saveGoodsInfo",data:e.form}).then((function(t){200===t.data.code&&(e.$message.success("保存成功"),e.resetForm(),e.$parent.init()),e.loading=!1})).catch((function(){e.loading=!1}))))}))},cancel:function(){this.resetForm()},resetForm:function(){this.dialog=!1,this.$refs["form"].resetFields(),this.form={id:null,image:"",sliderImage:"",barCode:"",imageArr:[],sliderImageArr:[],storeName:"",unitName:"",storeInfo:"",keyword:"",cateId:null,price:0,cost:0,postage:0,sort:1,sales:0,stock:0,isShow:"1",isHot:"0",isBenefit:"0",isBest:"0",isNew:"0",isGood:"0",description:""}},getCateTree:function(){var e=this;this.$axios({method:"post",url:"/goodsCategory/getGoodsCategoryList/"}).then((function(t){e.cateTree=t.data.data}))},normalizer:function(e){return null!=e.children&&"null"!==e.children&&0!==e.children.length||delete e.children,{id:e.id,label:e.cateName,children:e.children}}}},c=n,m=(r("aa95"),r("2877")),d=Object(m["a"])(c,a,o,!1,null,"ce0e4b92",null);t["default"]=d.exports},aa95:function(e,t,r){"use strict";r("0c53")}}]);