var eventBus = new Vue({})
/**
 * [data description]
 * 
 *
   inputConfig:{
		label:"名称",
		field:"name"
	}
 */
Vue.component('mc-form-input',{
	template:`
		<span class="mc-form-input">
			<el-form-item :label="inputconfig.label">
				<el-input @change="inputChange($event)" :placeholder="'请输入'+inputconfig.label"></el-input>
			</el-form-item>
		</span>
	`,
	props:['inputconfig'],
	data: function() {
		return {
			
		}
	},
	methods:{
		inputChange:function(event){
			var data = {}
			data['field'] = this.inputconfig['field'];
			data['value'] = event;
			this.$emit('inputChange', data);
		}
	}
})

Vue.component('mc-form-date-picker',{
	template:`
		<span class="mc-form-date-picker">
			<el-form-item :label="datePickerConfig.label">
				<el-date-picker @change="datePickerChange($event)"
					v-model="value"
					type="date"
					value-format="yyyy-MM-dd HH:mm:ss"
					placeholder="选择日期">
				</el-date-picker>
			</el-form-item>
		</span>
	`,
	props:['datePickerConfig'],
	data:function(){
		return {
			value:null
		}
	},
	methods:{
		datePickerChange:function(event){
			var data = {}
			data['field'] = this.datePickerConfig['field'];
			data['value'] = event;
			this.$emit('datePickerChange', data);
		}
	}
})

Vue.component('mc-form-select',{
	template:`
		<span class="mc-form-select">
			<el-form-item v-for="obj in selectConfig" :key="obj.index" :label="obj.label">
				<el-select v-model="obj['value']"
					filterable 
					clearable 
					@change="selectChange($event, obj.index)" 
					v-bind:placeholder="'请选择'+obj.label">
					<el-option
						v-for="item in obj['options']"
						:key="item[obj['optionValue']]"
						:label="item[obj['optionLabel']]"
						:value="item[obj['optionValue']]">
					</el-option>
				</el-select>
			</el-form-item>
		</span>
	`,
	props:['selectConfig'],
	mounted: function () {
		var _this = this;
		axios({
			url:this.selectConfig[0]["url"],
			method: 'post',
		}).then(function(res){
			if(res.data.status!=200){
				alert("请求数据失败")
			}else{
				_this.selectConfig[0]['options'] = res.data.data
			}
		});
		this.length = this.selectConfig.length;
	},
	data: function() {
		return {
			length:0,
		}
	},
	methods:{
		selectChange:function(event,index){
			var _this = this;
			//清空操作
			if(event==""||event==null){
				for(var i=index+1;i<this.length;i++){
					this.selectConfig[i]['value']= null;
					this.selectConfig[i]['options']= [];
				}
			}else if(this.length != (index+1)){
				axios({
					url:_this.selectConfig[index+1]["url"]+"/"+event,
					method: 'post',
				}).then(function(res){
					if(res.data.status!=200){
						alert("请求数据失败")
					}else{
						_this.selectConfig[index+1]['value'] = null;
						_this.selectConfig[index+1]['options'] = res.data.data
					}
				});
			}
			var data = [];
			for(var i in this.selectConfig){
				var item_data = {}
				item_data['field'] = this.selectConfig[i]['field'];
				item_data['value'] = this.selectConfig[i]['value'];
				data.push(item_data);
			}
			this.$emit('selectChange', data);
		}
	}
})

Vue.component('mc-search-card',{
	template:`
		<div class="mc-search-card">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>查询条件</span>
				</div>
				<div class="content">
					<el-form :inline="true" class="demo-form-inline">
						<span v-for="item in queryConditions" :key="item.field">
							<mc-form-input @inputChange="inputChange" :inputconfig="item" v-if="item.type==1"></mc-form-input>
							<mc-form-select @selectChange="selectChange" :selectConfig="item.selectConfig" v-if="item.type==2"></mc-form-select>
							<mc-form-date-picker @datePickerChange="datePickerChange" :datePickerConfig="item" v-if="item.type==3"></mc-form-date-picker>
						</span>
						<el-form-item>
							<el-button v-if="buttons.hasOwnProperty('search')" :type="buttons.search.type" @click="search">{{buttons.search.label}}</el-button>
						</el-form-item>
					</el-form>
				</div>
			</el-card>
		</div>
	`,
	props:['searchConfig'],
	mounted: function () {
		this.queryConditions = this.searchConfig.queryConditions;
		this.pageSize = this.searchConfig.pageSize;
		this.buttons = this.searchConfig.buttons;
		//初始化查询参数
		this.queryParam={}
		var condition = this.queryConditions;
		var objCondition={
			page:1,
			pageSize:this.pageSize
		}
		for(var index in condition){
			var item = condition[index];
			if(item['type']==1){
				objCondition[item['field']] = null;
			}else if(item['type']==2){
				for(var i in item['selectConfig']){
					objCondition[item['selectConfig'][i]['field']] = null;
				}
			}
		}
		this.queryParam = objCondition;
	},
	data: function () {
		return {
			queryParam:{},
			queryConditions:[],
			buttons:{},
			pagination:{
				page:1,
                total:0
			},
			pageSize:10,
		}
	},
	methods:{
		inputChange:function(data){
			this.queryParam[data['field']]=data['value'];
		},
		datePickerChange:function(data){
			this.queryParam[data['field']]=data['value'];
		},
		selectChange:function(data){
			for(var i in data){
				this.queryParam[data[i]['field']]=data[i]['value'];
			}
		},
		search:function(){
			eventBus.$emit('search', this.queryParam);
		}
	}

})

Vue.component('mc-dialog',{
	template:`
		<el-dialog
			:title="dialogConfig.title"
			:visible.sync="dialogConfig.dialogVisible"
			:width="dialogConfig.width">
			<span>这是一段信息</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogConfig.dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="dialogConfig.dialogVisible = false">确 定</el-button>
			</span>
		</el-dialog>
	`,
	props:['dialogConfig'],
	mounted: function() {

	},
	data: function() {
		return {
			
		}
	},
	methods:{
		
	}
});

Vue.component('mc-search-table',{
	template:`
		<div class="mc-search-table">
			<el-card class="box-card">
				<el-table fit stripe :data="tableData" border>
					<el-table-column v-for="item in columns" :key="item.field"
						:label="item.label"
						>
						<template slot-scope="scope">
							<span v-html="myFormatter(scope,scope.row[item.field],item.formatter)"></span>
						</template>
					</el-table-column>
					<el-table-column label="操作" v-if="buttons">
						<template slot-scope="scope">
							<el-button :key="index" v-for="(item,index) in buttons" size="mini" @click="item.click(scope,tableData,tableConfig.dialogConfig)" :type="item.type" >{{item.label}}</el-button>
						</template>
					</el-table-column>
				</el-table>
				<el-pagination :page-size="queryParam.pageSize" :page="pagination.page" :total="pagination.total" @current-change="handleCurrentChange"></el-pagination>
			</el-card>
		</div>
	`,
	props:['tableConfig'],
	mounted: function() {
		this.columns = this.tableConfig.columns;
		this.urls = this.tableConfig.urls;
		this.buttons= this.tableConfig.buttons;
		this.goPage(1);
		eventBus.$on('search', function(data) {
            this.queryParam=data;
            this.goPage(1);
        }.bind(this));
	},
	data: function() {
		return {
			queryParam:{
				"pageSize":10
			},
			tableData:[],
			columns:[],
			buttons:[],
			pagination:{
				page:1,
                total:0
			},
			urls:{}
		}
	},
	methods:{
		goPage:function(page){
            this.queryParam.page = page;
            this.queryByParam();
        },
        myFormatter:function(scope,value,fn){
        	if(fn){
        		return fn(scope,value);
        	}else{
        		return value;
        	}
        },
        //查询
        queryByParam:function(){
        	var _this = this;
        	axios({
				url:_this.urls['list'],
				method: 'post',
				data: this.queryParam
			}).then(function(res){
				if(res.data.status!=200){
					alert("请求数据失败")
				}else{
					_this.tableData=res.data.data.list;
					_this.pagination.total=res.data.data.total;
					_this.pagination.page=res.data.data.pageNum;
				}
			});		
        },
        handleCurrentChange(page){
        	this.goPage(page);
        }
	}
})

Vue.component('mc-search-page',{
	template:`
		<div class="mc-search-page">
			<mc-search-card :searchConfig="pageconfig.searchConfig"></mc-search-card>
			<mc-search-table :tableConfig="pageconfig.tableConfig" style="margin-top:20px;"></mc-search-table>
			<mc-dialog :dialogConfig="pageconfig.tableConfig.dialogConfig"></mc-dialog>
		</div>
	`,
	props:['pageconfig'],
	mounted: function() {

	},
	data: function() {
		return {
			
		}
	},
	methods:{
		
	}
})