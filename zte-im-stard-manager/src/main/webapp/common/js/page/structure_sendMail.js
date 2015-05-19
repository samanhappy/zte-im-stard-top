define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //关闭操作
    $('#dialogOkBtn').on('click', function(){
        top.layer.closeAll();
    });
});