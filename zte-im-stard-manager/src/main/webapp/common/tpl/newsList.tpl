{{#each item}}
<tr>
    <td class="title"><a href="show.jsp?id={{id}}">{{hideNewsTitle title}}</a></td>
    <td>{{newsOptFun id}}</td>
    <td>{{author}}</td>
    <td>{{type}}</td>
    <td>{{dateFormat date}}</td>
    <td>已发布</td>
</tr>
{{/each}}