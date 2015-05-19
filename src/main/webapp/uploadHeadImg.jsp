<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Upload File Test</title>
</head>
<body>
<div>
	<form name="file" method="post" action="uploadHeadImg" enctype="multipart/form-data">
		userId:<input type="text" id="userId" name="userId" />
		</br>
		hashcode:<input type="text" id="hashcode" name="hashcode" size="200"/>
		</br>
		title:<input type="text" id="title" name="title" />
		</br>
		from:<input type="text" id="from" name="from" />
		</br>
		keycode:<input type="text" id="keycode" name="keycode" />
		</br>
		imgpath:<input type="text" id="imgpath" name="imgpath" />
		</br>
		cat:<input type="text" id="cat" name="cat" />
		</br>
		newspaper_index:<input type="text" id="index" name="index" />
		</br>
		<input type="file" name="upfile" size=50 />
		<input type="submit" name="submit" value="提交" />
	</form>
</div>
</body>
</html>