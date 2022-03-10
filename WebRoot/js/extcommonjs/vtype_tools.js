Ext.apply(Ext.form.VTypes, {
 fileTypeError: function(val,field){
   	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|doc|docx|xls|xlsx|zip|rar|txt)$/.test(val))
   	{
   		field.vtypeText='您选择的文件类型不在系统指定范围内!';
   		return false;
	}else{
		field.clearInvalid();
		return true;
	}
 },
 comboxPEmp: function(val,field){
 	var st=field.store;
 	var i=0;
 	for (i=0;i<st.getCount();i++)
 		if (val==st.getAt(i).data.emName)
 			break;
 	if (i>=st.getCount())
 		field.clearValue();
 },
 comboxCEmp: function(val,field){
 	var st=field.store;
 	var i=0;
 	for (i=0;i<st.getCount();i++)
 		if (val==st.getAt(i).data.deptName)
 			break;
 	if (i>=st.getCount())
 		field.clearValue();
 }, 
 comboxErrorLevel: function(val,field){
 	var st=field.store;
 	var i=0;
 	for (i=0;i<st.getCount();i++)
 		if (val==st.getAt(i).data.ename)
 			break;
 	if (i>=st.getCount())
 		field.clearValue();
 },
  comboxErrorid: function(val,field){
 	var st=field.store;
 	var i=0;
 	for (i=0;i<st.getCount();i++)
 		if (val==st.getAt(i).data.errorid)
 			break;
 	if (i>=st.getCount())
 		field.clearValue();
 }, 
 comboxCommon: function(val,field){
 	var st=field.store;
 	var displytext=field.displayField;
 	var i=0;
 	for (i=0;i<st.getCount();i++)
 		if (val==st.getAt(i).get(field.displayField))
 			break;
 	if (i>=st.getCount())
 		field.clearValue();
 },
 dateRange: function(val, field){
		if(field.dateRange){
			var beginId = field.dateRange.begin;
			this.beginField = Ext.get(beginId);
			var endId = field.dateRange.end;
			this.endField = Ext.get(endId);
			
			var beginDate = this.beginField.getValue();
			var endDate = this.endField.getValue();
		}
		if(beginDate <= endDate){
			return true;
		}else{
			if(beginDate!="" && endDate!="")///如果结束时间小于起始时间，并且起止时间都不为空---返回false，其他情况均返回true
			{
				
				return false;
			}
			return true;
			
		}
	},
	//验证失败信息
	dateRangeText: '开始日期不能大于结束日期',
	 timeRange: function(val, field){
		if(field.timeRange){
			var beginId = field.timeRange.begin;
			this.beginField = Ext.get(beginId);
			var endId = field.timeRange.end;
			this.endField = Ext.get(endId);
			
			var beginTime = this.beginField.getValue();
			var endTime = this.endField.getValue();
		}
		if(beginTime <= endTime){
			return true;
		}else{
			if(beginTime!="" && endTime!="")///如果结束时间小于起始时间，并且起止时间都不为空---返回false，其他情况均返回true
			{
				
				return false;
			}
			return true;
			
		}
	},
	
    password : function(val, field) {
        if (field.initialPassField) {
        	var f = field.up('form').getForm().findField(field.initialPassField);
            return (val == f.getValue());
        }
        return true;
    },

    passwordText : '口令二次输入不一致，请重新输入！',
    

    onlyfilesize : function(val, field) {
		if (val.length>0&&val.substring(0,7)!='http://')
		{
			var fso = new ActiveXObject("Scripting.FileSystemObject");   
			var filesize = fso.GetFile(val).size;
			if (filesize>10*1024*1024)
			{
				return false;
			}
		}
		return true;
  	},    
    onlyfilesizeText : '文件大小超过10M，请重新选择文件！',

    filesize : function(val, field) {
//       	var   img   =   new   Image();   
//		img.src   =   val;   
//	    alert("图片大小：高="+hh+"宽="+ww+"文件大小："+img.fileSize+"字节");   
//      alert("fileSize   =   "+   img.fileSize   +"   字节");
		var patn = /\.jpg$|\.jpeg$|\.gif$|\.png$/i;		//判断文件后缀名
    	if (!patn.test(val))
    	{
    		field.vtypeText='您选择的不是图像文件!' 
    		return false;
		}
		if (val.length>0&&val.substring(0,7)!='http://')
		{
			var fso = new ActiveXObject("Scripting.FileSystemObject");   
			var filesize = fso.GetFile(val).size;
	//		alert("filesize:"+filesize+",");
			if (filesize>4*1024*1024)
	//		if (filesize>4*1024)
			{
	    		field.vtypeText='文件大小超过4M，请重新选择文件！' 
				return false;
			}
		}
		return true;
  	},
//    filesizeText : '文件大小超过4M，请重新选择文件！',
        
    IDCard:function(_v){
    	var area = { 
		   11 : "北京", 
		   12 : "天津", 
		   13 : "河北", 
		   14 : "山西", 
		   15 : "内蒙古", 
		   21 : "辽宁", 
		   22 : "吉林", 
		   23 : "黑龙江", 
		   31 : "上海", 
		   32 : "江苏", 
		   33 : "浙江", 
		   34 : "安徽", 
		   35 : "福建", 
		   36 : "江西", 
		   37 : "山东", 
		   41 : "河南", 
		   42 : "湖北", 
		   43 : "湖南", 
		   44 : "广东", 
		   45 : "广西", 
		   46 : "海南", 
		   50 : "重庆", 
		   51 : "四川", 
		   52 : "贵州", 
		   53 : "云南", 
		   54 : "西藏", 
		   61 : "陕西", 
		   62 : "甘肃", 
		   63 : "青海", 
		   64 : "宁夏", 
		   65 : "新疆", 
		   71 : "台湾", 
		   81 : "香港", 
		   82 : "澳门", 
		   91 : "国外" 
	  } 
	  var Y, JYM; 
	  var S, M; 
	  var idcard_array = new Array(); 
	  idcard_array = _v.split(""); 
	  // 地区检验 
	  if (area[parseInt(_v.substr(0, 2))] == null){ 
	   this.IDCardText="身份证号码地区非法!!,格式例如:32"; 
	   return false; 
	  } 
	  // 身份号码位数及格式检验
    	
    	switch (_v.length) { 
    	case 15 : 
		  			 if ((parseInt(_v.substr(6, 2)) + 1900) % 4 == 0 
		      || ((parseInt(_v.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(_v 
		        .substr(6, 2)) + 1900) 
		        % 4 == 0)) { 
		     ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性 
		    } else { 
		     ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性 
		    } 
		    if (ereg.test(_v)) 
		     return true; 
		    else{ 
		     this.IDCardText="身份证号码出生日期超出范围,格式例如:19860817"; 
		     return false; 
		    } 
		    break;
		 case 18 : 
				    // 18位身份号码检测 
				    // 出生日期的合法性检查 
				    // 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
				    // 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
				    if (parseInt(_v.substr(6, 4)) % 4 == 0 
				      || (parseInt(_v.substr(6, 4)) % 100 == 0 && parseInt(_v 
				        .substr(6, 4)) 
				        % 4 == 0)) { 
				     ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式 
				    } else { 
				     ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式 
				    } 
				    if (ereg.test(_v)) {// 测试出生日期的合法性 
				     // 计算校验位 
				     S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) 
				       * 7 
				       + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) 
				       * 9 
				       + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) 
				       * 10 
				       + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) 
				       * 5 
				       + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) 
				       * 8 
				       + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) 
				       * 4 
				       + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) 
				       * 2 
				       + parseInt(idcard_array[7]) 
				       * 1 
				       + parseInt(idcard_array[8]) 
				       * 6 
				       + parseInt(idcard_array[9]) * 3; 
				     Y = S % 11; 
				     M = "F"; 
				     JYM = "10X98765432"; 
				     M = JYM.substr(Y, 1);// 判断校验位 
				     //alert(idcard_array[17]); 
				     if (M == idcard_array[17]) { 
				      return true; // 检测ID的校验位 
				     } else { 
				      this.IDCardText="身份证号码末位校验位校验出错,请注意x的大小写,格式例如:201X"; 
				      return false; 
				     } 
				    } else { 
				     this.IDCardText="身份证号码出生日期超出范围,格式例如:19860817"; 
				     return false; 
				    } 
				    break; 
		default : 
		    this.IDCardText="身份证号码位数不对,应该为15位或是18位"; 
		    return false; 
		    break; 
    	}
    },
    IDCardText:"该输入项目必须是身份证号码格式，例如：32082919860817201X", 
	IDCardMask : /[0-9xX]/i,
    TimeVtype:function(_time, field){
    	var begin = Ext.get(field.TimeVtype.begin).getValue();  //得到obj的id，由id由Ext.get(obj.id).getValue()得到值
    	//var end = Ext.get(field.TimeVtype.end).getValue();
    	if(begin.length==4)
    	{
    		begin = '0'+begin;
    	}
    	if(_time.length==4)
    	{
    		_time = '0'+_time;
    	}
    	var begintime = Date.parse('0000-00-00 '+begin);  //注意：Date.parse方法要有日期。
    	var endtime = Date.parse('0000-00-00 '+_time);
    	if(begintime <= endtime){
    		//this.TimeVtypeText="-###-"+begintime+"&&"+endtime;
			return true;
		}else{
			if(begin!="" && _time!="")
			{
				//this.TimeVtypeText="--"+begin+"&&"+_time;
				return false;
			}
			return true;
			
		}
     },
     TimeVtypeText:"该输入项目必须大于起始时间",
     mobile: function(_v) {
    	return /^1[358][0-9]\d{8}$/.test(_v);
	},
	mobileText:"该输入项目必须是手机号码格式，例如：13485135075",
	mobileMask:/[0-9]/i
});