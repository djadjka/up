function Message(userNick, mesText, id, photoURL) {
    this.nick = userNick;
    this.mesText = mesText;
    this.changed = false;
    this.del = false;
    this.photoURl = photoURL;
    this.id = id;
}

var messages = [];
var delImgSrc = 'http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png';
var changeImgSrc = 'http://music.privet.ru/img/pics/edit-message-16x16.gif';
var curentNick = '';
var photoURL='' ;
function run() {
    var appContainer = document.getElementsByClassName('container')[0];
    appContainer.addEventListener('click', delegateClickEvent);
    appContainer.addEventListener('keydown', delegateKeydownEvent);
    restore();
}

function restore() {
    curentNick = localStorage.getItem('curentNick');
    photoURL = localStorage.getItem('photoURL');
    document.getElementsByClassName('userName')[0].value = curentNick;
    document.getElementsByClassName('selectPhoto')[0].value = photoURL;
    messages = JSON.parse(localStorage.getItem('messages'));
    printArrMess();
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
    if (evtObj.type === 'click' && evtObj.target.classList.contains('inputButton')) {
        addMyMessage(evtObj);
    }

    if (evtObj.type === 'click' && evtObj.target.classList.contains('delButton')) {
        delMessage(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('changeButton')) {
        changeMessage(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('logInButton')) {
        changeNick(evtObj);
    }
}

function delegateKeydownEvent(evtObj) {

    if (evtObj.type === 'keydown' && evtObj.target.classList.contains('entryField')) {
        if (evtObj.keyCode == 13 && evtObj.shiftKey) {
            addMyMessage(evtObj);
        }
    }
    if (evtObj.type === 'keydown' && evtObj.target.classList.contains('userName')) {
        if (evtObj.keyCode == 13) {
            changeNick(evtObj);
        }
    }
}

function changeNick() {
    curentNick = document.getElementsByClassName('userName')[0].value.trim();
    document.getElementsByClassName('messages')[0].innerHTML = '';
    printArrMess();
    if(document.getElementById('LoginCheckBox').checked){
        localStorage.setItem('curentNick', curentNick);
        localStorage.setItem('photoURL',photoURL);
    }
    else {
        localStorage.setItem('curentNick', '');
        localStorage.setItem('photoURL','');
    }

    photoURL = document.getElementsByClassName('selectPhoto')[0].value.trim();

}

function delMessage(evtObj) {
    var img = '\<div><button disabled>' +
        '\<img disable src="http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png"></div>';
    var delMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
    messages.forEach(function (item) {
        if (item.id == delMsgID) {
            item.del = true;
            item.mesText = '';
        }
    });
    setLocalStorage();
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = '';
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerHTML = img;

}

function changeMessage(evtObj) {
    var todoText = document.getElementsByClassName('entryField')[0];
    if (todoText.value.trim()) {
        var chMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
        messages.forEach(function (item) {
            if (item.id == chMsgID) {
                item.changed = true;
                item.mesText = todoText.value;
            }
        });
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = todoText.value;
        todoText.value = '';
        setLocalStorage();
    }
}

function printArrMess() {
    var items = document.getElementsByClassName('messages')[0];
    messages.forEach(function (item) {
        items.appendChild(createDivMessage(item));
    })
}

function addMyMessage() {
    if (curentNick != '') {
        if (document.getElementsByClassName('entryField')[0].value.trim()) {
            createItem();
            var divMes = createDivMessage(messages[messages.length - 1]);
            var items = document.getElementsByClassName('messages')[0];
            items.appendChild(divMes);
            items.scrollTop += items.scrollHeight;
        }
    }
    else {
        alert('Enter user name');
    }
}

function createItem() {

    var mesText = document.getElementsByClassName('entryField')[0].value;
    document.getElementsByClassName('entryField')[0].value = '';
    messages.push(new Message(curentNick, mesText, generateUUID(), photoURL));
    setLocalStorage();
}

function createDivMessage(message) {
    var divItem = document.createElement('div');
    if (message.nick == curentNick) {
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
    img.setAttribute('src', message.photoURl);
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
    name.appendChild(document.createTextNode(message.nick));
    text.appendChild(document.createTextNode(message.mesText));
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
    if (message.nick == curentNick) {
        imgDelButton.classList.add('delButton');
        imgDelButton.setAttribute('src', delImgSrc);
        delButton.appendChild(imgDelButton);
        divDelButton.appendChild(delButton);
        if (message.del) {
            delButton.setAttribute('disable', 'disable');
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
        else if (message.changed) {
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

function setLocalStorage() {
    localStorage.setItem('messages', JSON.stringify(messages));
    //????????????????????
}
