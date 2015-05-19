{{#each item}}
<tr>
    <td><input type="checkbox" name="{{name}}" value="{{id}}" class="checkbox"></td>
    <td><img src="{{iconUrl}}" height="46" width="46"></td>
    <td>{{name}}</td>
    <td>{{os}}</td>
    <td>{{version}}</td>
    <td>{{clientActiveFun isActive}}</td>
    <td>{{clientUpdateFun forceUpdate}}</td>
    <td>{{clientActiveLinkFun id isActive}}<a href="{{apkUrl}}" target="_blank">下载</a></td>
</tr>
{{/each}}