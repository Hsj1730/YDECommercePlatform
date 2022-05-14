<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    :title="title"
    width="900px"
  >
    <el-form v-show="hidden === false" :inline="true" label-width="80px">
      <el-form-item label="规则名称">
        <el-row :gutter="10">
          <el-col :span="10"
            ><el-button type="primary" @click="hiddenBool"
              >添加新规则</el-button
            >
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <el-form v-show="hidden === true" label-width="80px">
      <el-form-item label="规则名称">
        <el-row :gutter="10">
          <el-col
            v-for="(item, index) in items"
            :key="index"
            :span="5"
            style="position: relative; margin-right: 6px"
          >
            <el-input
              v-model="item.value"
              style="width: 150px"
              placeholder="设置名称"
            />
            <el-button
              v-show="item.attrHidden === true"
              type="text"
              style="
                position: absolute;
                top: -6px;
                right: 17px;
                margin-top: 1px;
                border: none;
                font-size: 14px;
                font-weight: bold;
                line-height: 1.8;
              "
              icon="el-icon-close"
              @click="handleRemove(index)"
            />
            <el-button
              v-show="item.attrHidden === false"
              type="text"
              style="
                position: absolute;
                top: -6px;
                right: 17px;
                margin-top: 1px;
                border: none;
                font-size: 14px;
                font-weight: bold;
                line-height: 1.8;
              "
              icon="el-icon-check"
              @click="attrHiddenBool(item)"
            />
          </el-col>
          <el-col :span="5"
            ><el-button type="primary" @click="handleAdd"
              >添加新规则</el-button
            ></el-col
          >
        </el-row>
      </el-form-item>
      <el-form-item
        v-for="(item, index) in items"
        v-show="item.attrHidden === true"
        :key="index"
        :label="'' + item.value + ':'"
      >
        <el-row :gutter="13">
          <el-col
            v-for="(attr, k) in item.detail"
            :key="attr"
            :span="3"
            :name="attr"
          >
            <el-tag closable @close="attrRemove(item, k)">{{ attr }}</el-tag>
          </el-col>
          <el-col :span="5">
            <el-input
              v-model="item.detailValue"
              style="width: 150px"
              placeholder="设置属性"
            />
          </el-col>
          <el-col :span="5">
            <el-button type="primary" @click="attrAdd(item)">添加</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item v-show="hidden === true">
        <el-row :gutter="24">
          <el-col :span="24"
            ><el-button :loading="loading" type="primary" @click="addGoods"
              >生成</el-button
            ></el-col
          >
        </el-row>
      </el-form-item>
      <template
        v-if="
          items[0].value !== '' && items[0].detail.length > 0 && attrs.length
        "
      >
        <template v-for="(attr, index) in attrs">
          <el-form-item>
            <el-row :gutter="24">
              <template v-for="(item, index) in attr.detail">
                <el-col :span="4" style="margin-right: 2px">
                  {{ index }}:{{ item }}
                </el-col>
              </template>
              <el-col :span="4">
                <span :class="attr.check ? 'check' : ''">金额:</span>&nbsp;
                <el-input
                  v-model="attr.price"
                  placeholder="金额"
                  style="width: 60%"
                  :number="true"
                />
              </el-col>
              <el-col :span="4">
                <span :class="attr.check ? 'check' : ''">库存:</span>&nbsp;
                <el-input
                  v-model="attr.sales"
                  placeholder="库存"
                  style="width: 60%"
                  :number="true"
                />
              </el-col>
              <el-col :span="5">
                <span :class="attr.check ? 'check' : ''">成本价:</span>&nbsp;
                <el-input
                  v-model="attr.cost"
                  placeholder="成本价"
                  style="width: 60%"
                  :number="true"
                />
              </el-col>
              <el-col :span="3" style="margin-right: 2px">
                <div class="demo-upload">
                  <pic-upload
                    v-model="attr.link"
                    :index="index"
                    @showChange="showChange"
                  />
                </div>
              </el-col>
              <el-col :span="2" style="margin-right: 3px">
                <el-button type="primary" @click="removeGoods(index)"
                  >删除</el-button
                >
              </el-col>
            </el-row>
          </el-form-item>
        </template>
        <el-form-item>
          <el-row :gutter="24">
            <el-col :span="2">
              <el-button type="primary" :loading="loading" @click="submit"
                >提交</el-button
              >
            </el-col>
            <el-col :span="2">
              <el-button type="error" @click="clear">清空所有属性</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </template>
    </el-form>
  </el-dialog>
</template>

<script>
import { Message } from "element-ui";
import PicUpload from "../../../../components/PicUpload";
export default {
  name: "attr",
  components: { PicUpload },
  props: {
    isAttr: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      loading: false,
      dialog: false,
      title: "规则属性",
      items: [
        {
          value: "",
          detailValue: "",
          attrHidden: false,
          detail: [],
        },
      ],
      attrs: [],
      hidden: false,
      id: null,
    };
  },
  methods: {
    clear() {
      this.$confirm(`确定要清空属性数据操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.loading = true;
          this.$axios({
            method: "post",
            url: "/goods/clearAttr/" + this.id,
          })
            .then((res) => {
              if (res.data.code === 200) {
                Message({ message: "操作成功", type: "success" });
                this.getAttrs(this.id);
                this.loading = false;
              }
            })
            .catch(() => {
              this.loading = false;
            });
        })
        .catch(() => {});
    },
    submit() {
      if (!this.checkAttr() || !this.checkGoods()) {
        return;
      }
      for (const attr in this.attrs) {
        this.attrs[attr].check = false;
      }
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goods/setAttrs/" + this.id,
        data: {
          items: this.items,
          attrs: this.attrs,
        },
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.attrs = res.data.data;
            Message({ message: "保存成功", type: "success" });
            this.loading = false;
            this.$parent.init();
          }
        })
        .catch(() => {
          this.loading = false;
        });
      this.dialog = false;
    },
    showChange(showChange) {
      this.attrs[showChange.index].pic = showChange.pic;
      this.attrs[showChange.index].link = showChange.link;
    },
    getAttrs() {
      this.$axios({
        method: "post",
        url: "/goods/getAttrs/" + this.id,
      }).then((res) => {
        if (res.data.code === 200) {
          if (res.data.data) {
            this.hidden = true;
            this.items = res.data.data.attr;
            this.attrs = res.data.data.value;
          } else {
            this.hidden = false;
            this.items = [
              {
                value: "",
                detailValue: "",
                attrHidden: false,
                detail: [],
              },
            ];
            this.attrs = [];
          }
        }
      });
    },
    removeGoods(index) {
      this.attrs.splice(index, 1);
    },
    cancel() {
      this.resetForm();
    },
    resetForm() {
      this.dialog = false;
    },
    hiddenBool() {
      this.hidden = true;
    },
    handleRemove(index) {
      if (this.items.length > 1) {
        this.items.splice(index, 1);
      } else {
        Message({ message: "请设置至少一个规则", type: "error" });
      }
    },
    attrHiddenBool(item) {
      if (item.value === "") {
        Message({ message: "请填写规则名称", type: "error" });
      } else {
        item.attrHidden = true;
      }
    },
    handleAdd() {
      if (!this.checkAttr()) return;
      this.items.push({
        value: "",
        detailValue: "",
        attrHidden: false,
        detail: [],
      });
    },
    checkAttr() {
      let bool = true;
      this.items.map(function (item) {
        if (!bool) return;
        if (!item.value) {
          Message({ message: "请填写规则名称", type: "error" });
          bool = false;
        } else if (!item.detail.length) {
          Message({ message: "请设置规则属性", type: "error" });
          bool = false;
        }
      });
      return bool;
    },
    attrRemove(item, k) {
      if (item.detail.length === 1) {
        Message({ message: "请设置至少一个属性", type: "error" });
        return false;
      }
      item.detail.splice(k, 1);
    },
    attrAdd(item) {
      if (!item.detailValue) {
        return false;
      }
      item.detail.push(item.detailValue);
      item.detailValue = "";
    },
    addGoods() {
      if (this.attrs.length) {
        if (!this.checkGoods()) {
          return;
        }
      }
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goods/isFormatAttr/" + this.id,
        data: {
          items: this.items,
          attrs: this.attrs,
        },
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.attrs = res.data.data;
            this.loading = false;
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
    checkGoods() {
      let bool = true;
      this.attrs.map(function (attr) {
        if (!bool) {
          return;
        }
        if (!Object.keys(attr.detail).length) {
          Message({ message: "请选择至少一个属性", type: "error" });
          bool = false;
        } else if (attr.price != parseFloat(attr.price) || attr.price < 0) {
          Message({ message: "请输入正确的商品价格", type: "error" });
          bool = false;
        } else if (attr.sales != parseInt(attr.sales) || attr.sales < 0) {
          Message({ message: "请输入正确的商品库存", type: "error" });
          bool = false;
        }
      });
      return bool;
    },
  },
};
</script>

<style scoped>
.demo-upload {
  display: block;
  /*//height: 50px;*/
  text-align: center;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
}
.demo-upload img {
  width: 100%;
  height: 100%;
  display: block;
}

.demo-upload-cover {
  display: block;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}
.demo-upload:hover .demo-upload-cover {
  display: block;
}
.demo-upload-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}
</style>
