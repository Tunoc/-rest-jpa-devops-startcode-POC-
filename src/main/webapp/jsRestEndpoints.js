let url = "http://localhost:8080/jpareststarter/api/movie/all";

let btn1 = document.getElementById("getAllButton");
btn1.addEventListener("click", fetchingAll);


function fetchingAll(evt) {
    evt.preventDefault();
    const finalURL = url;
    fetch(finalURL)
        .then(resp => resp.json())
        .then(data => {
            let test = "<tr><th>" + data.value + "</th></tr>"
            console.log(test);
            document.getElementById("divFrame").innerHTML = data.value;
            console.log("data", data.value);
        });
}