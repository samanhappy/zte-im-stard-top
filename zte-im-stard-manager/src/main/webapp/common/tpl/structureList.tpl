{{#each members.memberList}}
<tr>
    <td><input type="checkbox" value="{{id}}" name="{{cn}}" ></td>
    <td>{{inc @index}}</td>
    <td>{{uid}}</td>
    <td>{{cn}}</td>
    <td>{{mobile}}</td>
    <td>{{mail}}</td>
    <td>{{deptName}}</td>
    <td>{{roleName}}</td>
    <td>
    {{#ifCond usable 'true'}}
    	启用
	{{else}}
    	停用
	{{/ifCond}}
	</td>
    <td>{{listOptFun id usable cn roleName}}</td>
</tr>
{{/each}}