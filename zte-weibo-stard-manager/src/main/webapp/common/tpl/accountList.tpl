{{#each item}}
<tr>
    <td><input type="checkbox" value="{{uid}}"></td>
    <td>{{inc @index}}</td>
    <td><a href="javascript:;" data-val="{{uid}}" id="showDetail" title="{{name}}">{{name}}</a></td>
    <!--<td>{{accountStateFun state}}</td> -->
</tr>
{{/each}}