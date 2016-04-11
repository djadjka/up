function Message(userNick, mesText, id, photoURL) {
    this.author = userNick;
    this.text = mesText;
    this.update = false;
    this.del = false;
    this.photoURL = photoURL;
    this.id = id;
}
var mainUrl = 'http://192.168.0.107:8080/chat';
var messages = [];
var delImgSrc = 'http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png';
var changeImgSrc = 'http://music.privet.ru/img/pics/edit-message-16x16.gif';
var curentNick = '';
var photoURL = '';
var htmlImgDel = '\<div><button disabled>' +
    '\<img disable src="' + delImgSrc + '"></div>';
var htmlImgChe = '\<div><button disabled>' +
    '\<img disable src="' + changeImgSrc + '"></div>';
var isConnected = void 0;
function run() {
    var appContainer = document.getElementsByClassName('container')[0];
    appContainer.addEventListener('click', delegateClickEvent);
    appContainer.addEventListener('keydown', delegateKeydownEvent);
    Connect();
}


function Connect() {
    restoreUser();
    if (isConnected) {
        return;
    }
    ajax('GET', mainUrl + '?token=TN11EN', null, function (responseText) {
        messages = JSON.parse(responseText).messages;
        printArrMess();
    });
    function whileConnected() {
        isConnected = setTimeout(function () {
            ajax('GET', mainUrl + '?token=TN11EN', null, function (responseText) {
                if (isConnected) {
                    var newMes = JSON.parse(responseText).messages;
                    updatePage(newMes);
                    whileConnected();
                }
            });
        }, seconds(1));
    }

    whileConnected();
}
function seconds(value) {
    return Math.round(value * 1000);
}
function restoreUser() {
    if (localStorage.getItem('curentNick') || localStorage.getItem('photoURL')) {
        curentNick = localStorage.getItem('curentNick');
        photoURL = localStorage.getItem('photoURL');
        document.getElementsByClassName('userName')[0].value = curentNick;
        document.getElementsByClassName('selectPhoto')[0].value = photoURL;
    }
}
function generateUUID() {
    var d = new Date().getTime();
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

function delegateClickEvent(evtObj) {
    var classList1 = evtObj.target.classList;
    if (evtObj.type === 'click' && classList1.contains('inputButton')) {
        addMyMessage(evtObj);
    }

    if (evtObj.type === 'click' && classList1.contains('delButton')) {
        delMessage(evtObj);
    }
    if (evtObj.type === 'click' && classList1.contains('changeButton')) {
        updateMessage(evtObj);
    }
    if (evtObj.type === 'click' && classList1.contains('logInButton')) {
        changeNick(evtObj);
    }
}

function delegateKeydownEvent(evtObj) {
    var classList = evtObj.target.classList;
    if (evtObj.type === 'keydown' && classList.contains('entryField')) {
        if (evtObj.keyCode == 13 && evtObj.shiftKey) {
            addMyMessage(evtObj);
        }
    }
    if (evtObj.type === 'keydown' && classList.contains('userName')) {
        if (evtObj.keyCode == 13) {
            changeNick(evtObj);
        }
    }
}

function changeNick() {
    curentNick = document.getElementsByClassName('userName')[0].value.trim();
    alert(curentNick);
    redrawing();
    if (document.getElementById('LoginCheckBox').checked) {
        localStorage.setItem('curentNick', curentNick);
        localStorage.setItem('photoURL', photoURL);
    }
    else {
        localStorage.setItem('curentNick', '');
        localStorage.setItem('photoURL', '');
    }

    photoURL = document.getElementsByClassName('selectPhoto')[0].value.trim();

}

function delMessage(evtObj) {
    var delMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;

    ajax('DELETE', mainUrl, '{"id":"' + delMsgID + '"}', function () {
        messages.forEach(function (item) {
            if (item.id == delMsgID) {
                item.del = true;
                item.text = '';
                return;
            }
        });
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = '';
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerHTML = htmlImgDel;
    })
}

function updateMessage(evtObj) {
    var todoText = document.getElementsByClassName('entryField')[0];
    var currentMessage;
    var chMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
    if (todoText.value.trim()) {
        messages.forEach(function (item) {
            if (item.id === chMsgID) {
                currentMessage = item;
                return;
            }
        });
        if (currentMessage.text == todoText.value) {
            todoText.value = '';
            return;
        }
        ajax('PUT', mainUrl, '{"id":"' + chMsgID + '", "text":"' + todoText.value + '"}', function () {
            currentMessage.update = true;
            currentMessage.text=todoText;
            evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = todoText.value;
            todoText.value = '';
        });

    }
}

function printArrMess() {
    var items = document.getElementsByClassName('messages')[0];
    for (var i = 0; i < messages.length; i++) {
        items.appendChild(createDivMessage(messages[i]));
    }
    items.scrollTop += items.scrollHeight;
}

function addMyMessage() {
    if (curentNick != '') {
        if (!document.getElementsByClassName('entryField')[0].value.trim()) {
            return;
        }
        var mesText = document.getElementsByClassName('entryField')[0].value;
        var message = new Message(curentNick, mesText, generateUUID(), photoURL);
        ajax('POST', mainUrl, JSON.stringify(message), function () {
            document.getElementsByClassName('entryField')[0].value = '';
            messages.push(message);
            var divMes = createDivMessage(messages[messages.length - 1]);
            var items = document.getElementsByClassName('messages')[0];
            items.appendChild(divMes);
            items.scrollTop += items.scrollHeight;
        });
    }
    else {
        alert('Enter user name');
    }
}

function createDivMessage(message) {
    var divItem = document.createElement('div');
    if (message.author == curentNick) {
        divItem.classList.add('myMessage');
    }
    else {
        divItem.classList.add('message');
    }
    divItem.setAttribute('id', message.id.toString());
    divItem.appendChild(createLeft(message));
    divItem.appendChild(createCenter(message));
    divItem.appendChild(createRight(message));
    return divItem;
}

function createLeft(message) {
    var left = document.createElement('div');
    var img = document.createElement('img');
    img.classList.add('photo');
    img.setAttribute('src', message.photoURL);
    left.classList.add('leftColumn');
    left.appendChild(img);
    return left;
}

function createCenter(message) {
    var center = document.createElement('div');
    var name = document.createElement('div');
    name.classList.add('name');
    var text = document.createElement('div');
    center.classList.add('centerColumn');
    name.appendChild(document.createTextNode(message.author));
    text.appendChild(document.createTextNode(message.text));
    center.appendChild(name);
    center.appendChild(text);
    return center;

}

function createRight(message) {
    var right = document.createElement('div');
    right.classList.add('rightColumn');
    var divDelButton = document.createElement('div');
    var delButton = document.createElement('button');
    var imgDelButton = document.createElement('img');
    var divChangeButton = document.createElement('div');
    var changeButton = document.createElement('button');
    var imgChangeButton = document.createElement('img');
    if (message.author == curentNick) {
        imgDelButton.classList.add('delButton');
        imgDelButton.setAttribute('src', delImgSrc);
        delButton.appendChild(imgDelButton);
        divDelButton.appendChild(delButton);
        if (message.del) {
            delButton.setAttribute.disabled = true;
            right.appendChild(divDelButton);
        }
        else {
            imgChangeButton.classList.add('changeButton');
            imgChangeButton.setAttribute('src', changeImgSrc);
            changeButton.appendChild(imgChangeButton);
            divChangeButton.appendChild(changeButton);
            right.appendChild(divDelButton);
            right.appendChild(divChangeButton);
        }
    }
    else {
        if (message.del) {
            imgDelButton.classList.add('delButton');
            imgDelButton.setAttribute('src', delImgSrc);
            delButton.disabled = true;
            delButton.appendChild(imgDelButton);
            divDelButton.appendChild(delButton);
            right.appendChild(divDelButton);
        }
        else if (message.update) {
            imgChangeButton.classList.add('changeButton');
            changeButton.disabled = true;
            imgChangeButton.setAttribute('src', changeImgSrc);
            changeButton.appendChild(imgChangeButton);
            divChangeButton.appendChild(changeButton);
            right.appendChild(divChangeButton);
        }
    }
    return right;
}


function redrawing() {
    messages.forEach(function (item) {
        var div = document.getElementById(item.id);
        if (item.author == curentNick) {
            div.classList.remove('message');
            div.classList.add('myMessage');
            div.replaceChild(createRight(item), div.lastChild);

        }
        else {
            div.classList.remove('myMessage');
            div.classList.add('message');
            div.replaceChild(createRight(item), div.lastChild);
        }
    });
}
function updatePage(newMes) {
    for (var i = 0; i < messages.length; i++) {
        var div = document.getElementById(messages[i].id);
        if (messages[i].del !== newMes[i].del) {
            div.childNodes[1].childNodes[1].innerHTML = '';
            div.childNodes[2].innerHTML = htmlImgDel;
            messages[i].del = true;
            messages[i].text = '';
        }
        else if (newMes[i].update && messages[i].text !== newMes[i].text) {
            messages[i].update = true;
            messages[i].text = newMes[i].text;
            div.childNodes[1].childNodes[1].innerHTML = newMes[i].text;
            if (curentNick !== newMes[i].author) {
                div.childNodes[2].innerHTML = htmlImgChe;
            }
        }
    }
    if (messages.length < newMes.length) {
        for (var i = messages.length; i < newMes.length; i++) {
            var divMes = createDivMessage(newMes[i]);
            messages.push(newMes[i]);
            var items = document.getElementsByClassName('messages')[0];
            items.appendChild(divMes);
            items.scrollTop += items.scrollHeight;
        }
    }
}

function ajax(method, url, data, continueWith) {
    var xhr = new XMLHttpRequest();
    xhr.open(method || 'GET', url, true);
    xhr.onload = function () {
        if (xhr.readyState !== 4)
            return;

        if (xhr.status != 200) {
            return;
        }
        continueWith(xhr.responseText);
    };
    xhr.onerror = function () {
        document.getElementsByClassName('error')[0].style.display = 'block';
    };
    xhr.send(data);
}
