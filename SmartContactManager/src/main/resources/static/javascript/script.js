console.log("this is just for amusement purpose");

const togglesidebar=()=>{
if($(".sidebar").is(":visible")){


$(".sidebar").css("display","none");
$(".content").css("margin-left","0%");

}else{


$(".sidebar").css("display","block");
$(".content").css("margin-left","20%");

}
};

//below is  java script for search functionality

const search=()=>{
let query =$("#search-input").val();
//console.log(query);

if(query==""){

 $(".search-result").hide();
 console.log("nothig found niggggggggggggggggaaaaaaaaaa");

}else{
let url =`http://localhost:8080/search/${query}`;
fetch(url)
.then((response)=>{ 
return response.json();
})
.then((data)=>{
    if(data==null){
        console.log("no data");
 
    }else{
        console.log("data assa");

    }
    console.log(data);
    console.log("found your "+query);
   let text =`<div class='list-group'>`

data.forEach((contacts)=>{
    text+=`<a href='/user/${contacts.cid}/contactdetails' class='list-group-item list-group-item-action'>${contacts.email}</a>`
});

   text+=`</div>`
$(".search-result").html(text);
$(".search-result").show();
});


}
};