

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
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerHTML ='';
    evtObj.target.parentNode.parentNode.parentNode.parentNode.childNodes[2].innerHTML =img;
}
function changeMessage(evtObj) {
    var todoText = document.getElementsByClassName('entryField')[0];

    if (todoText.value) {
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
    var divItem = document.createElement('div');

    divItem.classList.add('myMessage');
    divItem.appendChild(createLeft());
    divItem.appendChild(createCenter(text));
    divItem.appendChild(createRight());

    return divItem;
}
function createLeft() {
    var left = document.createElement('div');
    var img = document.createElement('img');
    img.classList.add('photo');
    img.setAttribute('src',document.getElementsByClassName('selectPhoto')[0].value);
    left.classList.add('leftColumn');
    left.appendChild(img);
    return left;
}
function createCenter(text) {
    if ((document.getElementsByClassName('userName')[0].value)) {
        var center = document.createElement('div');
        var name = document.createElement('div');
        var message = document.createElement('div');
        center.classList.add('centerColumn');
        name.appendChild(document.createTextNode(document.getElementsByClassName('userName')[0].value.trim()));
        message.appendChild(document.createTextNode(text));
        center.appendChild(name);
        center.appendChild(message);
        return center;
    }
    else {
        alert('Enter user name!');
    }
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