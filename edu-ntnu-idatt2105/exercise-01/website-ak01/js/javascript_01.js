/* js/javascript_01.js */


/* Microsoft and Google Inspired WebCompatNot */
window.onload = function() {
    if(/Chrome/.test(navigator.userAgent) && /Google Inc/.test(navigator.vendor)){
        document.body.innerHTML = '';

        const message = document.createElement('div');
        message.innerHTML = '<h1> UNSUPPORTED BROWSER</h1>' +
                            '<p>This website does not support your current browser. ' +
                            '<p>Please use a modern, up-to-date browser.</p>';
        message.style.textAlign = 'center';
        message.style.marginTop = '20px';

        document.body.appendChild(message);

        document.body.style.height = '100vh';
        document.body.style.display = 'flex';
        document.body.style.justifyContent = 'center';
        document.body.style.alignItems = 'center';
        document.body.style.fontSize = '20px';
    }
};


/* Show-Hide content button */
function show_hide_element_toggle() {
    var x = document.getElementById("visitor1millPrize");
    if (x.style.display === "" || x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}


/*
function show_hide_element_toggle() {
    var x = document.getElementById("visitor1millPrize");
    x.style.visibility = "hidden";
    x.style.visibility = "visible";
}
*/



/* Name Generator */
function capFirstName(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

function generateSingleName() {
    var first_name = ["trine", "aslak", "kåre", "baktus", "sand", "stein", "Ubba", "Svein", "Asterix", "Obelix", "Pytagoras", "Kamela"];
    var middle_words = ["av", "von", "aus", ""];
    var last_name = ["sandpapir", "steinsprang", "snøspade", "Isflak", "Albin", "Schnitzel", "Kabelsko", "Tå", "Skanke", "Loevenskiold", "Frohner-Hauhen"];

    var name = capFirstName(first_name[getRandomInt(0, first_name.length)])
        + " " + middle_words[getRandomInt(0, middle_words.length)]
        + " " + capFirstName(last_name[getRandomInt(0, last_name.length)]);

    return name;
}

function generateNameList() {
    var count = document.getElementById("nameCount").value;
    count = Math.max(1, Math.min(count, 10));

    var list = document.getElementById("nameList");
    list.innerHTML = ''; // Clear existing list
    for (let i = 0; i < count; i++) {
        var name = generateSingleName();
        var listItem = document.createElement("li");
        listItem.innerText = name;
        list.appendChild(listItem);
    }
}

/* Increment number */
document.addEventListener("DOMContentLoaded", (event) => {
    const plus = document.querySelector(".plus");
    const minus = document.querySelector(".minus");
    const number = document.querySelector(".number");

    let a = 1;
    plus.addEventListener("click", ()=>{
        a++;
        a = (a < 10) ? "0" + a : a;
        number.innerText = a;
        console.log(a);
    })

    minus.addEventListener("click", ()=>{
        if(a > 1) {
            a--;
            a = (a < 10) ? "0" + a : a;
            number.innerText = a;
            console.log(a);
        }
    })
})