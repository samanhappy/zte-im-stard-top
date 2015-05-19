{{#each item}}
<tr>
    <td><input type="checkbox" value="{{groupId}}"></td>
    <td>{{inc @index}}</td>
    <td><a href="javascript:;" data-val="{{groupId}}" id="showDetail" title="{{groupName}}">{{groupName}}</a></td>
    <td>{{circleStateFun groupStatus}}</td>
</tr>
{{/each}}