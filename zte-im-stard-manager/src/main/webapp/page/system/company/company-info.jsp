<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业信息</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/jstree/jstree.js"></script>
<script src="${pageContext.request.contextPath}/js/fileupload.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.browser.js"></script>

<script
	src='${pageContext.request.contextPath}/js/datepicker/WdatePicker.js'
	charset="UTF-8"></script>
<style type="text/css">
#photo {
	position: relative;
	left: 0px;
	top: -25px;
	opacity: 0;
	width: 100px;
	height: 25px;
	filter: alpha(opacity = 0);
	cursor: pointer;
}

.bd li {
	border-bottom: 1px solid #e7e8ea;
	background: #f5f6fa;
}
</style>
<script type="text/javascript">
	var currentHeight;
	var currentWidth;
	$(window).resize(
			function() {
				var windowHeight = $(window).height();
				var windowWidth = $(window).width();

				if (currentHeight == undefined || currentHeight != windowHeight
						|| currentWidth == undefined
						|| currentWidth != windowWidth) {
					parent.adjust();
					currentHeight = windowHeight;
					currentWidth = windowWidth;
				}
			});

	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
	}

	function changeLogo() {
		$("#qiye #photoImg").attr("src",
				'${pageContext.request.contextPath}/temp/logo1.jpg');
	}
	
	function isAllowedAttach(sFile, sUploadImagesExt) {
		var sExt = sFile.match(/\.[^\.]*$/);
		if (sExt) {
			sExt = sExt[0].toLowerCase();
		} else {
			return false;
		}
		if (sUploadImagesExt.indexOf(sExt) != -1) {
			return true;
		}
		return false;
	}
	
	
	
	
	//function uploadPic() {
		/* var file = $("#photo").val();
		var fileObj = $("#photo");
		if (file == "") {
			alert("请先选择上传文件");
			return;
		}
		if (!isAllowedAttach(file, ' .jpeg .gif .jpg .png ')) {
			$("#photo").val('');
			//针对ie的clear实现
			fileObj.replaceWith( fileObj = fileObj.clone( true ) );
			alert("图片格式不正确");
			return;
		} */
		
		
		
		/* var uploadP = {
			target : '#output2',
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					$("#qiye #photoImg").attr("src", data.fileUrl);
					$("#qiye #photoUrl").val(data.fileUrl);
					$("#qiye #photo").val('');
				} else if (data.res.reCode && data.res.reCode == '2') {
					parent.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("上传失败");
				}
			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/uploadFile.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#fileForm').unbind('submit');
		$('#fileForm').submit(function() {
			$(this).ajaxSubmit(uploadP);
			return false;
		});
		$("#fileForm").submit(); */

	//}
	function changeDisplay(id, v1) {
		var display = $("#" + id).css("display");
		if (display == 'none') {
			$("#" + id).css("display", "block");
		} else {
			$("#" + id).css("display", "none");
		}
		$("div#guimo #a").remove();
		$("div#qiye #guimo").html(v1);
		$("div#qiye #guimo").append('<i id="a"></i>');
		$("div#guimo #a").addClass("ico-arrow-down");
		//$("div#guimo").click(function() {
		//	changeDisplay('personSizeUl');
		//});
		$("div#qiye #tguimo").val(v1);
	}

	function changeProv(id, v1) {
		var display = $("#" + id).css("display");
		if (display == 'none') {
			$("#" + id).css("display", "block");
		} else {
			$("#" + id).css("display", "none");
		}
		if (v1 && v1 != '') {
			$("div#prov #a").remove();
			$("div#qiye #prov").html(v1);
			$("div#qiye #prov").append('<i id="a"></i>');
			$("div#prov #a").addClass("ico-arrow-down");
			$("div#prov #a").click(function() {
				changeProv('provinceUl');
			});
			$("div#qiye #tprov").val(v1);

			$("div#qiye #city").html('');
			$("div#qiye #city").append('<i id="a"></i>');
			$("div#city #a").addClass("ico-arrow-down");
			$("div#city #a").click(function() {
				changeCity('cityUl');
			});
			$("div#qiye #tcity").val('');

			$.ajax({
				type : "POST",
				url : '${pageContext.request.contextPath}/cityList.do',
				data : {
					prov : v1
				},
				dataType : "json",
				success : function(data) {
					$('#cityUl').empty();
					var citysList = JSON.parse(data.tcitys);
					for ( var key in citysList) {
						$('#cityUl').append(
								"<li><a id='"+key+"'>" + citysList[key]
										+ "</a></li>");
						$("#" + key).click(function() {
							changeCity('cityUl', this.innerHTML);
						});
					}
				}
			});
		}
	}

	function changeProv1(id, v1) {
		var display = $("#" + id).css("display");
		if (display == 'none') {
			//$("#" + id).css("display", "block");
		} else {
			//$("#" + id).css("display", "none");
		}
		if (v1 && v1 != '') {
			$("div#prov #a").remove();
			$("div#qiye #prov").html(v1);
			$("div#qiye #prov").append('<i id="a"></i>');
			$("div#prov #a").addClass("ico-arrow-down");
			$("div#prov #a").click(function() {
				changeProv('provinceUl');
			});
			$("div#qiye #tprov").val(v1);

			//$("div#qiye #city").html('');
			$("div#qiye #city").append('<i id="a"></i>');
			$("div#city #a").addClass("ico-arrow-down");
			$("div#city #a").click(function() {
				changeCity('cityUl');
			});
			//$("div#qiye #tcity").val('');

			$.ajax({
				type : "POST",
				url : '${pageContext.request.contextPath}/cityList.do',
				data : {
					prov : v1
				},
				dataType : "json",
				success : function(data) {
					$('#cityUl').empty();
					if (data.tcitys && data.tcitys != '') {
						var citysList = JSON.parse(data.tcitys);
						for ( var key in citysList) {
							$('#cityUl').append(
									"<li><a id='"+key+"'>" + citysList[key]
											+ "</a></li>");
							$("#" + key).click(function() {
								changeCity('cityUl', this.innerHTML);
							});
						}
					}
				},
				complete : function() {
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据操作出错");
				}
			});
		}
	}

	function changeCity(id, v1) {
		var display = $("#" + id).css("display");
		if (display == 'none') {
			$("#" + id).css("display", "block");
		} else {
			$("#" + id).css("display", "none");
		}
		$("div#city #a").remove();
		$("div#qiye #city").html(v1);
		$("div#qiye #city").append('<i id="a"></i>');
		$("div#city #a").addClass("ico-arrow-down");
		$("div#city #a").click(function() {
			changeCity('cityUl');
		});
		$("div#qiye #tcity").val(v1);
	}

	function load() {
		
		$("#photo").Uploader({
	        action: '${pageContext.request.contextPath}/uploadFile.do',
	        params: "action=uploadpic",
	        maxsize: 1024 * 1024 * 10, //10M
	        exts: ".jpg|.png|.gif|.jpeg|.bmp",
	        callback: function (file, urlpath) {
	        	$("#qiye #photoImg").attr("src", urlpath);
				$("#qiye #photoUrl").val(urlpath);
				$("#qiye #photo").val('');
				//针对ie的clear实现
				var fileObj = $("#photo");
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
	        }
	    });
		
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/toEditTenant.do',
			data : {},
			dataType : "json",
			success : function(data) {
				$("div#qiye #tid").val(data.tid);
				$("div#qiye #tname").val(data.tname);
				$("div#qiye #tpid").html(data.tpid);
				$("div#qiye #tlinkman").val(data.tlinkman);
				$("div#qiye #tmobile").val(data.tmobile);
				$("div#qiye #tmail").val(data.tmail);
				$("div#qiye #ttel").val(data.ttel);
				$("div#qiye #taddress").val(data.taddress);
				$("#qiye #photoImg").attr("src", data.photoImg);
				$("div#qiye #guimo").html(data.tguimo);
				$("div#qiye #tguimo").val(data.tguimo);
				$("div#qiye #guimo").append('<i id="a"></i>');
				$("div#guimo #a").addClass("ico-arrow-down");
				$("div#guimo").click(function() {
					changeDisplay('personSizeUl');
				});

				var scaleList = JSON.parse(data.tscale);
				for ( var key1 in scaleList) {
					$('#personSizeUl').append(
							"<li><a id='"+key1+"'>" + scaleList[key1]
									+ "</a></li>");
					$("#" + key1).click(function() {
						changeDisplay('personSizeUl', this.innerHTML);
					});
				}

				$("div#qiye #prov").html(data.tprov);
				$("div#qiye #tprov").val(data.tprov);
				$("div#qiye #prov").append('<i id="a"></i>');
				$("div#prov #a").addClass("ico-arrow-down");
				$("div#prov #a").click(function() {
					changeProv('provinceUl');
				});

				$("div#qiye #city").html(data.tcity);
				$("div#qiye #tcity").val(data.tcity);
				$("div#qiye #city").append('<i id="a"></i>');
				$("div#city #a").addClass("ico-arrow-down");
				$("div#city #a").click(function() {
					changeCity('cityUl');
				});

				var provsList = JSON.parse(data.tprovs);
				for ( var key2 in provsList) {
					$('#provinceUl').append(
							"<li><a id='"+key2+"'>" + provsList[key2]
									+ "</a></li>");
					$("#" + key2).click(function() {
						changeProv('provinceUl', this.innerHTML);
					});
				}
				changeProv1('provinceUl', data.tprov);
			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			}
		});
	}
	function editTenant() {

		if ($("div#qiye #tname").val() == "") {
			alert('企业名称不能为空');
			return false;
		}
		if ($("div#qiye #tpid").html() == "") {
			alert('企业号不能为空');
			return false;
		}
		if ($("div#qiye #tlinkman").val() == "") {
			alert('联系人不能为空');
			return false;
		}
		var tel = $("div#qiye #tmobile").val();
		if ($("div#qiye #tmobile").val() == "") {
			alert('联系电话不能为空');
			return false;
		}
		if (!/^0?1[3|4|5|8][0-9]\d{8}$/.test(tel)) {
			alert("联系电话格式错误");
			return false;
		}
		var mail = $("div#qiye #tmail").val();
		if ($("div#qiye #tmail").val() == "") {
			alert('电子邮件不能为空');
			return false;
		}
		if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(mail)) {
			alert("电子邮件格式错误");
			return false;
		}
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/editTenant.do',
					data : {
						tid : $("div#qiye #tid").val(),
						tname : $("div#qiye #tname").val(),
						tpid : $("div#qiye #tpid").html(),
						tlinkman : $("div#qiye #tlinkman").val(),
						tmobile : $("div#qiye #tmobile").val(),
						tmail : $("div#qiye #tmail").val(),
						ttel : $("div#qiye #ttel").val(),
						taddress : $("div#qiye #taddress").val(),
						tguimo : $("div#qiye #tguimo").val(),
						tprov : $("div#qiye #tprov").val(),
						tcity : $("div#qiye #tcity").val(),
						photoImg : $('#photoImg').attr('src')
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							location.reload();
						}
					}
				});
	}

	function showLogoSelect() {
		$('#photo').click();
	}
</script>
</head>
<body onload="load()">
	<!-- 企业管理主体部分 [[ -->
	<div id="qiye" class="c-company">
		<div class="inner" style="height: 80%">
			<input id="tid" name="tid" type="hidden" value="${tid}" />
			<table class="edit-pw com-info" style="margin: 0;">
				<colgroup>
					<col width="153"></col>
					<col></col>
					<col width="260"></col>
					<col width="235"></col>
				</colgroup>
				<!-- 				<i class="e"> -->
				<tr>
					<td><label>企业号:</label></td>
					<td><label id="tpid"></label></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><input id="photoUrl" name="photoUrl" type="hidden" /><label>企业Logo:</label></td>
					<td><img id="photoImg" style="width: 50px; height: 50px;"
						alt="" /></td>
					<td>
						<div class="tr pr40">
							<a style="cursor: pointer;">更换企业Logo </a>
							<form id="fileForm" style="height: 0px;">
								<input type="file" id="photo"
									name="photo" />
							</form>
							<a href="javascript:changeLogo();" style="margin: 0 0 0 15px;">恢复默认Logo</a>
						</div>
					</td>
					<td><div class="tip">上传企业Logo:上传文件的大小不能超过10M格式支持：jpg、png、bmp</div></td>
				</tr>
				<tr>
					<td><label>企业名称:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input id="tname" name="tname" type="text" /><i
								class="e"></i>
						</div> <i class="g-required">*</i></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><label>企业联系人:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input type="text" id="tlinkman" /><i class="e"></i>
						</div> <i class="g-required">*</i></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><label>联系电话:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input type="text" id="tmobile" /><i class="e"></i>
						</div> <i class="g-required">*</i></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><label>电子邮件:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input type="text" id="tmail" /><i class="e"></i>
						</div> <i class="g-required">*</i></td>
					<td></td>
					<td><div class="tip tr">找回密码使用</div></td>
				</tr>
				<tr>
					<td><label>固定电话:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input type="text" id="ttel" /><i class="e"></i>
						</div></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><input id="tguimo" name="tguimo" type="hidden" /> <input
						id="tprov" name="tprov" type="hidden" /> <input id="tcity"
						name="tcity" type="hidden" /><label>企业规模:</label></td>
					<td>
						<!-- 下拉框显示的时候 要设置g-input的z-index为1，否则下拉框会被下面挡住-->
						<div class="g-input" style="z-index: 10;">
							<i class="f"></i>
							<div class="g-drop">
								<div class="hd" id="guimo"></div>
								<ul id="personSizeUl" class="bd"
									style="display: none; width: 242px; padding: 0">
								</ul>
							</div>
							<i class="e"></i>
						</div> <input type="hidden" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><label>所在地区:</label></td>
					<td>
						<div class="g-input">
							<i class="f"></i>
							<div class="g-drop l" style="width: 90px;">
								<div id="prov" class="hd"></div>
								<ul id="provinceUl" class="bd" style="width: 120px; padding: 0">
								</ul>
							</div>
							<div class="g-drop l ml20" style="width: 90px;">
								<div id="city" class="hd"></div>
								<ul id="cityUl" class="bd" style="width: 123px; padding: 0">
								</ul>
							</div>
							<i class="e"></i>
						</div>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><label>通讯地址:</label></td>
					<td><div class="g-input">
							<i class="f"></i><input type="text" id="taddress" /><i class="e"></i>
						</div></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<a href="javascript:editTenant();" class="btn-true mt30">保存</a>
		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
</body>
</html>