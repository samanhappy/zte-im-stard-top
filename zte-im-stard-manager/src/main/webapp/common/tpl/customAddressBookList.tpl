{{#each item}}
	<li><span>{{paramName}}</span><a href="javascript:;" id="editBtn" class="defineParam" data-val="{{id}}" data-name="{{paramName}}" data-type="{{paramType}}">编辑</a><a href="javascript:;" id="delBtn"  class="defineParam" data-val="{{id}}" data-name="{{paramName}}">删除</a></li>
{{/each}}