<template>
    <div>
        <p>
            <button v-on:click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit"></i>
                新增
            </button>
            &nbsp;
            <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="list"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr><#list fieldList as field>
            <th>${field.nameCn}</th></#list>
            <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="${domain} in ${domain}s">
                <#list fieldList as field>
                <td>{{${domain}.${field.nameHump}}}</td>
            </#list>
            <td>
                <div class="hidden-sm hidden-xs btn-group">
                    <button @click="edit(${domain})" class="btn btn-xs btn-info">
                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                    </button>
                    <button @click="del(${domain}.id)" class="btn btn-xs btn-danger">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </button>
                </div>
            </td>
            </tr>
            </tbody>
        </table>

        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <#list fieldList as field>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">${field.nameCn}</label>
                                <div class="col-sm-10">
                                    <input v-model="${domain}.${field.nameHump}" class="form-control">
                                </div>
                            </div>
                        </#list>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button @click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";
    export default {
        name: '${domain}',
        components: {Pagination},
        data: function() {
            return{
                ${domain}: {},
                ${domain}s: []
            }
        },
        mounted: function () {
            let self = this;
            self.$refs.pagination.size = 5
            self.list(1);
        },
        methods: {
            add() {
                let self = this;
                self.${domain} = {};
                $("#form-modal").modal("show")
            },
            edit(${domain}) {
                let self = this;
                self.${domain} = $.extend({},${domain});
                $("#form-modal").modal("show")
            },
            /**
             * 列表查询
             * @param page
             */
            list(page) {
                let self = this;
                Loading.show();
                self.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
                    page: page,
                    size: self.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let res = response.data;
                    self.${domain}s = res.content.list;
                    self.$refs.pagination.render(page, res.content.total)
                })
            },
            /**
             * 点击 保存
             * @param page
             */
            save(page) {
                let self = this;
                //保存校验
                <#--if (!Validator.require(self.${domain}.name,"名称")-->
                    <#--|| !Validator.require(self.${domain}.courseId,"课程ID")-->
                    <#--|| !Validator.length(self.${domain}.courseId,"课程ID",1,8)){-->
                    <#--return;-->
                <#--}-->
                Loading.show();
                self.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save', self.${domain}).then((response) => {
                    Loading.hide();
                    let res = response.data;
                    if (res.success){
                        $("#form-modal").modal("hide");
                        self.list(1);
                        Toast.success("保存成功！");
                    }else {
                        Toast.warning(res.message)
                    }
                })
            },
            /**
             * 点击 删除
             * @param page
             */
            del(id) {
                let self = this;
                Confirm.show("删除${tableNameCn}后不可恢复，确认删除?",function () {
                    Loading.show();
                    self.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/'+id).then((response) => {
                        Loading.hide();
                        let res = response.data;
                        if (res.success){
                            self.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            }
        }
    }
</script>