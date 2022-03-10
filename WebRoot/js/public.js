//���������ĺ���ͨ�á�DateDiff("2004-03-02", "2004-02-25")=6
function DateDiff(sDate1, sDate2) {  //sDate1��sDate2��2002-12-18��ʽ
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);  //ת��Ϊ12-18-2002��ʽ
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);
    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24);  //�����ĺ�����ת��Ϊ����
    return iDays;
}

//?????????????
function isInteger(strNum) {
    if (strNum.search(/^(0|[1-9]\d*)$/) != -1) {
        return true;
    } 
    return false;
}
function isEmail(strEmail) {
    if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) {
        return true;
    } else {
        alert("\u4e0d\u662f\u6709\u6548\u7684EMail\u683c\u5f0f\uff01");
    }
    return false;
}
function checkNumber(obj) {
    var objValue = obj.value;
    if (isNaN(objValue) == 0) {
        return true;
    } else {
        return false;
    }
}

//???? ?? ? lili@si-tech.com.cn
function checkMail(obj) {
    if (obj.value.indexOf("@") == -1 || obj.value.indexOf(".") == -1 || obj.value.length < 6) {
        return false;
    } else {
        return true;
    }
}

//???
function checkEmpty(OBJ) {
    var value = OBJ.value;
    if (value == "" || value == "undefine" || value == "null") {
        return true;
    } else {
    	if(value.length >0)
    	{
    		var valueArray = value.split("[ ]+");
    		trimString(valueArray);
    		if(valueArray.length==1 && valueArray[0]=="")
    			return true;
    	}
        return false;
    }
}
function checkEmptyStr(str)
{
	
    if (str == "" || str == "undefine" || str == "null") {
        return true;
    } else {
    	if(str.length >0)
    	{
    		var valueArray = str.split("[ ]+");
    		trimString(valueArray);
    		if(valueArray.length==1 && valueArray[0]=="")
    			return true;
    	}
        return false;
    }
}
function compareDate(startDate,endDate)
   {
   		if(checkEmptyStr(startDate)||checkEmptyStr(endDate))
   		{
   			return true;
   		}
   		var dateArrays = startDate.split("-");
   		var dateArraye = endDate.split("-");
   		var sdate = new Date(dateArrays[0],dateArrays[1]-1,dateArrays[2]);
   		var edate = new Date(dateArraye[0],dateArraye[1]-1,dateArraye[2]);
   		if(sdate > edate)
   		{
   			return false;
   		}
   		return true;
   }
 function compareTime(starttime,endtime)
 {
 	var timeArrays = starttime.split(":");
 	var timeArraye = endtime.split(":");
 	var stime = new Date(0,0,0,timeArrays[0],timeArrays[1],timeArrays[2]);
 	var etime = new Date(0,0,0,timeArraye[0],timeArraye[1],timeArraye[2]);
 	if(stime >etime)
 	{
 		return false;
 	}	
 }
function checkStrLength(obj_input, strlen) {
    var str = obj_input.value;
    if (str.length > strlen) {
        alert("\u8f93\u5165\u7684\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e" + strlen + "!");
        obj_input.value = str.substr(0, str.length - 1);
    }
}
function popwindow(htmlurl, state) {
    window.open(htmlurl, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no" + state);
}
function popWindowScroll(htmlurl, state) {
    window.open(htmlurl, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes" + state);
}
function popWindowCenter(htmlurl, wid, heig) {
    scWidth = screen.Width / 2 - wid / 2;
    scHeight = screen.Height / 2 - heig / 2;
    window.open(htmlurl, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function popWindowCenterQuestion(htmlurl, wid, heig) {
    scWidth = screen.Width / 2 - wid / 2;
    scHeight = screen.Height / 2 - heig / 2;
    window.open(htmlurl, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function popWindowCenterNormal(htmlurl, wid, heig) {
    scWidth = screen.Width / 2 - wid / 2;
    scHeight = screen.Height / 2 - heig / 2;
    window.open(htmlurl, "", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function SetCwinHeight(idstr) {
    var cwin = document.getElementById(idstr);
    if (document.getElementById) {
        if (cwin && !window.opera) {
            if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight) {
                cwin.height = cwin.contentDocument.body.offsetHeight;
            } else {
                if (cwin.Document && cwin.Document.body.scrollHeight) {
                    cwin.height = cwin.Document.body.scrollHeight;
                }
            }
        }
    }
}

//<!--   -->
//<!--   ???????????????? string  ???????? key????????   -->
//<!--   ???????????? ??????string ?????? asdf.do?group=33443&df=4    -->
//<!--         key   ?????? group   -->
//<!--         ???????????? 33443   -->
function spit(string, key) {
    var index = string.indexOf("?");
    var value = "";
    var result = "";
    if (index == -1) {
        return "-1";
    } else {
        value = string.substr(index + 1, string.length);
    }
    var aryReturn = getAryData(value, "&");
    for (var i = 0; i < aryReturn.length; i++) {
        value = aryReturn[i];
        index = value.indexOf("=");
        if (value.substr(0, index) == key) {
            result = value.substr(index + 1, value.length);
            break;
        }
    }
    return result;
}



//<!--   ???????????????????? dd;dsds;bb;sds; ??title ?????? ???????????????title??????????????-oSrc ???????????? textarea ??????>oDest   -->
function spitAndInsert(oSrc, oDest) {
    var aryReturn = getAryData(oSrc.value, ";");
    for (var i = 0; i < aryReturn.length; i++) {
        oDest.value = oDest.value + aryReturn[i] + "\n";
    }
}

//<!--  -->
//<!--   ???????????????? string   -->
//<!--   ?????? key ????????????????????????   -->
function getAryData(string, key) {
    var aryReturn = new Array();
    var aryReturn = string.split(key);
    return aryReturn;
}
function moveSelected(oSrc, oDest) {
    for (x in oSrc.childNodes) {
        var node = oSrc.childNodes[x];
        if (!node.selected) {
            continue;
        }
        addUniqueNode(node, oDest);
    }
}
function insert(oDest, name, value) {
    var oNewNode = document.createElement("option");
    oNewNode.innerText = name;
    oNewNode.value = value;
    addUniqueNode(oNewNode, oDest);
}
function addUniqueNode(node, oDest) {
    var oNewNode = document.createElement("option");
    var nodeExist = false;
    for (y in oDest.children) {
        if (node.value == oDest.children[y].value) {
            nodeExist = true;
            break;
        }
    }
    if (!nodeExist) {
        var newNode = node.cloneNode(true);
        oDest.appendChild(newNode);
    }
}
function removeSelected(oSelect) {
    for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
        var node = oSelect.childNodes(i);
        if (node.selected) {
            oSelect.removeChild(node);
        }
    }
}
function removeAll(oSelect) {
    for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
        oNode = oSelect.childNodes(i);
        oSelect.removeChild(oNode);
    }
}
function selectOrg(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("course_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ";";
            oValue.value = oValue.value + ret[x].id + ";";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgM(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("major_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgC(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("majorcourse_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
           // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgR(oValue, oView) {
    var num = Math.random();
    var idAndName = new Array(oValue.value, spitToString(oView));
    var ret = window.showModalDialog("../../../page/system/roleallot/role_allot.jsp", idAndName, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}

function selectOrgQuarters(rootPath,oValue, oView) {
    var num = Math.random();
    var idAndName = new Array(oValue.value, spitToString(oView));
    var ret = window.showModalDialog(rootPath + "/page/quarters/course_select_box.jsp", idAndName, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}

function selectOrgWithPerson(rootPath, oValue, oView, personType) {
    var num = Math.random();
    var idAndName = new Array(oValue.value, spitToString(oView));
    var ret = window.showModalDialog(rootPath + "/page/system/person/persontree/person_select_box.jsp?personType=" + personType, idAndName, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgG(oValue, oView) {
    var num = Math.random();
    var idAndName = new Array(oValue.value, spitToString(oView));
    var ret = window.showModalDialog("../../../page/exam/appointpaper/group.jsp", idAndName, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgZ(rootPath, oValue, oView) {
    var num = Math.random();
    var idAndName = new Array(oValue.value, spitToString(oView));
    var ret = window.showModalDialog(rootPath + "/page/university/courseforclazz/course_allot.jsp", idAndName, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgE(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("examination_paper_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function trimString(objArray) {
    var size = objArray.length;
    for (var i = 0; i < size; i++) {
        objArray[i] = objArray[i].replace(/\s/g, "");
    }
}
function spitToString(objTextArea) {
    if (objTextArea) {
        var strHTML = objTextArea.value;
        var aryReturn = strHTML.split("\n");
        trimString(aryReturn);
        var result = "";
        for (var i = 0; i < aryReturn.length - 1; i++) {
            result = result + aryReturn[i] + ",";
        }
        result = result + aryReturn[aryReturn.length - 1];
        return result;
    } else {
        return "";
    }
}
function selectOrgA(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("examination_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
           // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgU(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("user_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
          // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function selectOrgUG(oValue, oView) {
    var num = Math.random();
    var ret = window.showModalDialog("userGroup_allot.jsp", oValue.value, "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
    var i = 0;
    if (ret != null) {
        oValue.value = "";
        oView.value = "";
        oView.title = "";
        for (x in ret) {
            i++;
            oView.title = oView.title + ret[x].name + ",";
            oValue.value = oValue.value + ret[x].id + ",";
         // if(i<3){
            oView.value = oView.value + ret[x].name + "\n";
         // }else if(i==3){
         //    oView.value = oView.value+"...";
         // }else{
             
         // }
        }
        if (i == 0) {
            oValue.value = "";
            oView.value = "";
        }
    }
}
function Element(id, name, type) {
    this.id = id;
    this.name = name;
    this.type = type;
}
function buildReturnValue(oSelect) {
    var ret = new Array();
    for (x in oSelect.children) {
        if (oSelect.children[x].value == null) {
            continue;
        }
        var o = new Element(oSelect.children[x].value, oSelect.children[x].innerText, 0);
        ret.push(o);
    }
    return ret;
}
function buildValue(oSelect) {
    var ret = "";
    for (x in oSelect.children) {
        if (oSelect.children[x].value == null) {
            continue;
        }
        var o = oSelect.children[x].value;
        ret = ret + o + ",";
    }
    return ret;
}
function showselect(url) {
    window.showModalDialog(url, "55555", "dialogHeight: 400px; dialogWidth: 710px; dialogTop: px; dialogLeft: px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
}
function Base64() {
	   
    // private property  
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
   
    // public method for encoding  
    this.encode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = _utf8_encode(input);  
        while (i < input.length) {  
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);  
            chr3 = input.charCodeAt(i++);  
            enc1 = chr1 >> 2;  
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
            enc4 = chr3 & 63;  
            if (isNaN(chr2)) {  
                enc3 = enc4 = 64;  
            } else if (isNaN(chr3)) {  
                enc4 = 64;  
            }  
            output = output +  
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +  
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);  
        }  
        return output;  
    }  
   
    // public method for decoding  
    this.decode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3;  
        var enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");  
        while (i < input.length) {  
            enc1 = _keyStr.indexOf(input.charAt(i++));  
            enc2 = _keyStr.indexOf(input.charAt(i++));  
            enc3 = _keyStr.indexOf(input.charAt(i++));  
            enc4 = _keyStr.indexOf(input.charAt(i++));  
            chr1 = (enc1 << 2) | (enc2 >> 4);  
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);  
            chr3 = ((enc3 & 3) << 6) | enc4;  
            output = output + String.fromCharCode(chr1);  
            if (enc3 != 64) {  
                output = output + String.fromCharCode(chr2);  
            }  
            if (enc4 != 64) {  
                output = output + String.fromCharCode(chr3);  
            }  
        }  
        output = _utf8_decode(output);  
        return output;  
    }  
   
    // private method for UTF-8 encoding  
    _utf8_encode = function (string) {  
        string = string.replace(/\r\n/g,"\n");  
        var utftext = "";  
        for (var n = 0; n < string.length; n++) {  
            var c = string.charCodeAt(n);  
            if (c < 128) {  
                utftext += String.fromCharCode(c);  
            } else if((c > 127) && (c < 2048)) {  
                utftext += String.fromCharCode((c >> 6) | 192);  
                utftext += String.fromCharCode((c & 63) | 128);  
            } else {  
                utftext += String.fromCharCode((c >> 12) | 224);  
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);  
                utftext += String.fromCharCode((c & 63) | 128);  
            }  
   
        }  
        return utftext;  
    }  
   
    // private method for UTF-8 decoding  
    _utf8_decode = function (utftext) {  
        var string = "";  
        var i = 0;  
        var c = c1 = c2 = 0;  
        while ( i < utftext.length ) {  
            c = utftext.charCodeAt(i);  
            if (c < 128) {  
                string += String.fromCharCode(c);  
                i++;  
            } else if((c > 191) && (c < 224)) {  
                c2 = utftext.charCodeAt(i+1);  
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));  
                i += 2;  
            } else {  
                c2 = utftext.charCodeAt(i+1);  
                c3 = utftext.charCodeAt(i+2);  
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));  
                i += 3;  
            }  
        }  
        return string;  
    }  
}  
