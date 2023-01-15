//显示通知：1为成功，2为信息，3为警告，4为失败，1000是延迟关闭时间
//msalert(1,'这是文本内容',1000);
//显示loading，后面两个参数为文字颜色和加载条颜色
//msloading('这里是文本内容','text-info','text-info');
//更新loading文字
//msloadingup('新的文字','文字颜色，必须和显示时的一样');
//关闭loading
//msloadingde();

function msloading(text,textcolor="text-info",loadcolor="text-info",idnr="body"){       //显示加载消息
         lod = $(idnr).lyearloading({
            opacity: 0.4,
            spinnerSize: 'lg',
            spinnerText: text,      //文本文字
            textColorClass: textcolor,      //文本文字颜色
            spinnerColorClass: loadcolor        //加载条颜色
        });
}

function msloadingde(idnr="body"){      //关闭加载消息
            var lod = $(idnr).lyearloading({});
lod.destroy();
}

function msloadingup(text,textcolor="text-info"){         //更新文字
    $("span[class='lyear-loading "+textcolor+"']").html(text);
}

function msalert(icon,text,msds='3000',idnr="body"){
    if(icon==1){
        var csicon='mdi mdi-emoticon-outline';
        var cstype='success';
    }else if(icon==2){
        var csicon='mdi mdi-emoticon-cool-outline';
        var cstype='info';
    }else if(icon==3){
        var csicon='mdi mdi-emoticon-neutral-outline';
        var cstype='warning';
    }else if(icon==4){
        var csicon='mdi mdi-emoticon-dead-outline';
        var cstype='danger';
    }
$.notify({
    icon: csicon,
    title: null,
    message: text
},{
    element: idnr,
    position: null,
    type: cstype,       //成功:success 消息:info 警告:warning 失败:danger 
    allow_dismiss: true,
    newest_on_top: false,
    showProgressbar: false,
    placement: {
    	from: "top",
    	align: "center"
    },          //显示位置
    offset: 20,
    spacing: 10,
    z_index: 1031,
    delay: msds,        //延迟时间
    timer: 1000,
    url_target: '_blank',
    mouse_over: null,
    animate: {
    	enter: 'animated fadeInDown',
    	exit: 'animated fadeOutUp'
    },
    onShow: null,
    onShown: null,
    onClose: null,
    onClosed: null,
    icon_type: 'class',
    template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
    	'<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
    	'<span data-notify="icon"></span> ' +
    	'<span data-notify="title">{1}</span> ' +
    	'<span data-notify="message">{2}</span>' +
    	'<div class="progress" data-notify="progressbar">' +
    		'<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
    	'</div>' +
    	'<a href="{3}" target="{4}" data-notify="url"></a>' +
    '</div>' 
});
}

function msalerts(tp=2,tit='提示',cont='提示信息',fn='',fns='',djs=false){
if(djs!=false){
var clo='cancelAction|'+djs;
}else{
var clo=false;
}
var ico='';
if(tp==1){
tp='green'
ico='mdi-checkbox-marked-circle'
}else if(tp==2){
tp='blue'
ico='mdi-comment-processing'
}else if(tp==3){
tp='orange'
ico='mdi-alert'
}else{
tp='red'
ico='mdi-close-circle'
}
    $.alert({
        title: tit,
        content: cont,
        icon: 'mdi '+ico,
        animation: 'scale',
        closeAnimation: 'scale',
        autoClose: clo,
        draggable: true,
        type:tp,
        buttons: {
            okay: {
                text: '确认',
                btnClass: 'btn-'+tp,
                action: function(){
                    eval(fn);
                }
            },
            cancelAction: {
                text: '取消',
                action: function() {
                    eval(fns);
                }
            }
        }
    });
}