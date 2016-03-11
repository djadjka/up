function Message(userNick, mesText, id, photoURL, my) {
    this.my = my;
    this.nick = userNick;
    this.mesText = mesText;
    this.changed = false;
    this.del = false;
    this.photoURl = photoURL;
    this.id = id;
}
var messages = [];
function run() {
    var appContainer = document.getElementsByClassName('chat')[0];
    appContainer.addEventListener('click', delegateEvent);
}
function delegateEvent(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('inputButton')) {
        addMyMessage(evtObj);
    }

    if (evtObj.type === 'click' && evtObj.target.classList.contains('delButton')) {

        delMessage(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('changeButton')) {
        changeMessage(evtObj);
    }

}
function delMessage(evtObj) {
    var img = '\<div><button disabled><img disable src="http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png"></div>';
    var delMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
    messages.forEach(function (item) {
        if (item.id == delMsgID) {
            item.del = true;
            item.mesText = '';
        }
    });
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = '';
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerHTML = img;

}
function changeMessage(evtObj) {
    var todoText = document.getElementsByClassName('entryField')[0];
    if (todoText.value) {
        var chMsgID = evtObj.target.parentNode.parentNode.parentNode.parentNode.id;
        messages.forEach(function (item) {
            if (item.id == chMsgID) {
                item.changed = true;
                item.mesText = todoText.value;
            }
        });
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = todoText.value;
        todoText.value = '';
    }
}
function printArrMess() {
    var items = document.getElementsByClassName('messages')[0];

    messages.forEach(function (item) {
        items.appendChild(createDivMessage(item));
    })
}
function addMyMessage() {
    if ((document.getElementsByClassName('userName')[0].value && document.getElementsByClassName('entryField')[0].value)) {
        createItem();
        var divMes = createDivMessage(messages[messages.length - 1]);
        var items = document.getElementsByClassName('messages')[0];
        items.appendChild(divMes);
        items.scrollTop+=items.scrollHeight;
        alert(items.scrollTop);
    }
    else {
        alert('Enter user name and message!');
    }
}
function createItem() {

        var nick = document.getElementsByClassName('userName')[0].value;
        var mesText = document.getElementsByClassName('entryField')[0].value;
        document.getElementsByClassName('entryField')[0].value = '';
        var photoURL = document.getElementsByClassName('selectPhoto')[0].value;
        messages.push(new Message(nick, mesText, messages.length, photoURL, true));

}
function createDivMessage(message) {
    var divItem = document.createElement('div');
    if (message.my) {
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
    if (message.my) {
        imgDelButton.classList.add('delButton');
        imgDelButton.setAttribute('src', 'http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png');
        delButton.appendChild(imgDelButton);
        divDelButton.appendChild(delButton);
        if (message.del) {
            delButton.setAttribute('disable', 'disable');
            right.appendChild(divDelButton);
        }
        else {
            imgChangeButton.classList.add('changeButton');
            imgChangeButton.setAttribute('src', 'http://music.privet.ru/img/pics/edit-message-16x16.gif');
            changeButton.appendChild(imgChangeButton);
            divChangeButton.appendChild(changeButton);
            right.appendChild(divDelButton);
            right.appendChild(divChangeButton);
        }
    }
    else {
        if (message.del) {
            imgDelButton.classList.add('delButton');
            imgDelButton.setAttribute('src', 'http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png');
            delButton.disabled = true;
            delButton.appendChild(imgDelButton);
            divDelButton.appendChild(delButton);
            right.appendChild(divDelButton);
        }
        else if (message.changed) {
            imgChangeButton.classList.add('changeButton');
            changeButton.disabled = true;
            imgChangeButton.setAttribute('src', 'http://music.privet.ru/img/pics/edit-message-16x16.gif');
            changeButton.appendChild(imgChangeButton);
            divChangeButton.appendChild(changeButton);
            right.appendChild(divChangeButton);
        }
    }
    return right;
}

