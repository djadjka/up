function Message(userNick, mesText, id, photoURL) {
    this.method;
    this.author = userNick;
    this.text = mesText;
    this.photoURL = photoURL;
    this.id = id;
}
var mainUrl = '/chat';
var messages = [];
var token = 'TN11EN';
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
    ajax('GET', mainUrl + '?token=' + token, null, function (responseText) {
        if (isConnected) {
            var newMes = JSON.parse(responseText).messages;
            token = JSON.parse(responseText).token;
            updatePage(newMes);
        }
    });
    function whileConnected() {
        isConnected = setTimeout(function () {
            ajax('GET', mainUrl + '?token=' + token, null, function (responseText) {
                if (isConnected) {
                    var newMes = JSON.parse(responseText).messages;
                    token = JSON.parse(responseText).token;
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
    curentNick = document.cookie.split('=')[1];
    var text = document.createElement('h1');
    text.appendChild(document.createTextNode('Hello '+ curentNick));
    document.getElementsByClassName('helloUser')[0].appendChild(text);

}
function generateUUID() {
    var d = new Date().getTime();
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

function exit() {
    document.cookie = 'login' + "=" + "; expires=Thu, 01 Jan 1970 00:00:01 GMT";
    document.location.href = 'login.jsp';
}
function delegateClickEvent(evtObj) {
    var classList = evtObj.target.classList;
    if (evtObj.type === 'click' && classList.contains('inputButton')) {
        addMyMessage(evtObj);
    }

    if (evtObj.type === 'click' && classList.contains('delButton')) {
        delMessage(evtObj);
    }
    if (evtObj.type === 'click' && classList.contains('changeButton')) {
        updateMessage(evtObj);
    }
    if (evtObj.type === 'click' && classList.contains('exitButton')) {
        exit();
    }
}

function delegateKeydownEvent(evtObj) {
    var classList = evtObj.target.classList;
    if (evtObj.type === 'keydown' && classList.contains('entryField')) {
        if (evtObj.keyCode == 13 && evtObj.shiftKey) {
            addMyMessage(evtObj);
        }
    }
}


function delMessage(evtObj) {
    var delMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
    ajax('DELETE', mainUrl, '{"id":"' + delMsgID + '"}', function () {
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = '';
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerHTML = htmlImgDel;
    })
}

function updateMessage(evtObj) {
    var todoText = document.getElementsByClassName('entryField')[0];
    if (todoText.value === '') {
        return;
    }
    var chMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
    ajax('PUT', mainUrl, '{"id":"' + chMsgID + '", "text":"' + todoText.value + '"}', function () {
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = todoText.value;
        todoText.value = '';
    });

}


function addMyMessage() {
    if (!document.getElementsByClassName('entryField')[0].value.trim()) {
        return;
    }
    var mesText = document.getElementsByClassName('entryField')[0].value;
    var message = new Message(curentNick, mesText, generateUUID(), photoURL);
    ajax('POST', mainUrl, JSON.stringify(message), function () {
        document.getElementsByClassName('entryField')[0].value = '';
    });
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
        if (message.method === 'DELETE') {
            delButton.disabled = true;
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
        if (message.method === 'DELETE') {
            right.innerHTML = htmlImgDel;
        }
        else if (message.method === 'PUT') {
            right.innerHTML = htmlImgChe;
        }
    }
    return right;
}


function updatePage(newMes) {
    for (var i = 0; i < newMes.length; i++) {
        if (newMes[i].method === 'POST') {
            var divMes = createDivMessage(newMes[i]);
            messages.push(newMes[i]);
            var items = document.getElementsByClassName('messages')[0];
            items.appendChild(divMes);
            items.scrollTop += items.scrollHeight;
        }
        else if (newMes[i].method === 'PUT') {
            var div = document.getElementById(newMes[i].id);
            for (var j = 0; j < messages.length; j++) {
                if (messages[j].id === newMes[i].id) {
                    messages[j].method = 'PUT';
                    messages[j].text = newMes[i].text;
                    div.childNodes[1].childNodes[1].innerHTML = newMes[i].text;
                    if (curentNick !== messages[j].author) {
                        div.childNodes[2].innerHTML = htmlImgChe;
                    }
                    break;
                }
            }
        }
        else {
            var div = document.getElementById(newMes[i].id);
            div.childNodes[1].childNodes[1].innerHTML = '';
            div.childNodes[2].innerHTML = htmlImgDel;
            for (var j = 0; j < messages.length; j++) {
                if (messages[j].id === newMes[i].id) {
                    messages[j].method = 'DELETE';
                    messages[j].text = '';
                }

            }
        }

    }
}

function ajax(method, url, data, continueWith) {
    var xhr = new XMLHttpRequest();
    xhr.open(method || 'GET', url, true);
    xhr.onload = function () {
        if (xhr.readyState !== 4) {
            return;
        }

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
