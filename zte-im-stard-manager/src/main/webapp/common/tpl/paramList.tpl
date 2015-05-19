{{#each item}}
<div class="formitm">
	<label class="lab">{{paramName}}:</label>
	<div class="ipt">
		<i class="f"></i>
		<input type="text" id="{{id}}" name="{{id}}" class="txt" autocomplete="off">
		<i class="e"></i>
	</div>
</div>
<div class="formitm">
	<label class="lab">&nbsp;</label>
</div>
{{/each}}