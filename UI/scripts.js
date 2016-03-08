function Message(userNick, mesText, id, photoURL) {

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
    appContainer.addEventListener('click', delegateEvent);
}
function delegateEvent(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('inputButton')) {
        onAddButtonClick(evtObj);
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
            item.mesText='';
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
                item.mesText=todoText.value;
            }
        });
        evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML = todoText.value;
        todoText.value = '';
    }
}
function onAddButtonClick() {
    var todoText = document.getElementsByClassName('entryField')[0];
    addTodo(todoText.value);
    todoText.value = '';
}
function addTodo(value) {
    if (!value) {
        return;
    }
    var item = createItem(value);
    var items = document.getElementsByClassName('messages')[0];
    items.appendChild(item);
}

function createItem(text) {
    if ((document.getElementsByClassName('userName')[0].value)) {
        var divItem = document.createElement('div');
        var nick = document.getElementsByClassName('userName')[0].value.trim();
        var photo = document.getElementsByClassName('selectPhoto')[0].value;
        messages.push(new Message(nick, text, messages.length , photo));
        divItem.classList.add('myMessage');
        divItem.setAttribute('id', (messages.length - 1).toString());
        divItem.appendChild(createLeft());
        divItem.appendChild(createCenter(text));
        divItem.appendChild(createRight());
        return divItem;
    }
    else {
        alert('Enter user name!');
    }
}
function createLeft() {
    var left = document.createElement('div');
    var img = document.createElement('img');
    img.classList.add('photo');
    img.setAttribute('src', messages[messages.length - 1].photoURl);
    left.classList.add('leftColumn');
    left.appendChild(img);
    return left;
}
function createCenter(text) {
    var center = document.createElement('div');
    var name = document.createElement('div');
    var message = document.createElement('div');
    center.classList.add('centerColumn');
    name.appendChild(document.createTextNode(messages[messages.length - 1].nick));
    message.appendChild(document.createTextNode(text));
    center.appendChild(name);
    center.appendChild(message);
    return center;

}
function createRight() {
    var right = document.createElement('div');
    right.classList.add('rightColumn');
    var divDelButton = document.createElement('div');
    var delButton = document.createElement('button');
    var imgDelButton = document.createElement('img');
    imgDelButton.classList.add('delButton');
    imgDelButton.setAttribute('src', 'http://s1.iconbird.com/ico/1212/264/w16h161355246842delete6.png');
    var divChangeButton = document.createElement('div');
    var changeButton = document.createElement('button');
    var imgChangeButton = document.createElement('img');
    imgChangeButton.classList.add('changeButton');
    imgChangeButton.setAttribute('src', 'http://music.privet.ru/img/pics/edit-message-16x16.gif');
    delButton.appendChild(imgDelButton);
    divDelButton.appendChild(delButton);
    changeButton.appendChild(imgChangeButton);
    divChangeButton.appendChild(changeButton);
    right.appendChild(divDelButton);
    right.appendChild(divChangeButton);
    return right;
}