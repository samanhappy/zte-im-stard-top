{{#each item}}
<tr>
    <td><input type="checkbox" value="{{id}}"></td>
    <td>{{contentType}}</td>
    <td>{{type}}</td>
    <td>{{sender}}</td>
    <td>{{receiver}}</td>
    <td>{{time}}</td>
    <td>{{base2Str content}}</td>
    <td>{{operation}}</td>
</tr>
{{/each}}