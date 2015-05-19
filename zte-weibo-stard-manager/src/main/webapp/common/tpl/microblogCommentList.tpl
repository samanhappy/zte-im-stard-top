{{#each item}}
<li>
	<img src="{{user.minipicurl}}" height="36" width="36" class="fl avatar">
	<div class="commentBox">
		<div class="commentContent">
			<p><span>{{user.name}}：</span>{{commentContent}}</p>
			<em>{{createTimeStr}}</em>
		</div>
		<a href="javascript:;" data-val="{{commentId}}" title="删除评论" class="fr commentDelBtn">删除评论</a>
	</div>
</li>
{{/each}}