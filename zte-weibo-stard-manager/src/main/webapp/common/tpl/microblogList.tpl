{{#each item}}
<tr>
    <td><input type="checkbox" value="{{twitterId}}"></td>
    <td class="miroblogCol"><a href="../../microblog/showDetail.do?twitterId={{twitterId}}">{{searchContent}}</a></td>
    <td>{{twitterType}}</td>
    <td>{{userName}}</td>
    <td>{{createTimeStr}}</td>
    <td>{{forwardNum}}</td>
    <td>{{commentNum}}</td>
    <td>{{supportNum}}</td>
</tr>
{{/each}}