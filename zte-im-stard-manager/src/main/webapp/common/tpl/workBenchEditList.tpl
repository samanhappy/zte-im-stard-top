{{#each item}}
<li id="li{{@index}}" class="dropItem" index="{{@index}}">
	<img width="180px" height="100px" src="{{imgUrl}}"/>
	<i class="delBtn" index="{{@index}}"></i>
	<input type="hidden" name="workImg" value="{{imgUrl}}">
</li>
{{/each}}