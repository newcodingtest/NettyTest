var clock = document.querySelector('.h1-clock');

function getTime(){
    const time = new Date();
    const year = time.getFullYear();
    const month = time.getMonth();
    const date = time.getDate();
    const day = time.getDay(); //0~6, 일~토
    
    var stringDay = null;
    
    if(day==0){
    	stringDay='일';
    }else if(day==1){
    	stringDay='월';
    }else if(day==2){
    	stringDay='화';
    }else if(day==3){
    	stringDay='수';
    }else if(day==4){
    	stringDay='목';
    }else if(day==5){
    	stringDay='금';
    }else if(day==6){
    	stringDay='토';
    }
    
    const hour = time.getHours();
    const minutes = time.getMinutes();
    const seconds = time.getSeconds();
    clock.innerHTML = year+" 년도"+ month+" 월"+date+" 일"+"("+stringDay+")"+`${hour<10 ? `0${hour}`:hour}:${minutes<10 ? `0${minutes}`:minutes}:${seconds<10 ? `0${seconds}`:seconds}`;

}
function init(){
    setInterval(getTime, 1000);
}


$(function () {

init();
});