const url ="http://localhost:8080/api/users";
const urlRoles ="http://localhost:8080/api/roles";
const authDataUrl ="http://localhost:8080/api/user";

/////// Vars for table ///////
const homeTab= document.getElementById("usersTable-tab");
const tableBody = document.querySelector("#adminTable tbody");
const firstNameNew= document.getElementById('firstNameNew');
const lastNameNew= document.getElementById('lastNameNew');
const usernameNew= document.getElementById('usernameNew');
const emailNew= document.getElementById('emailNew');
const passwordNew= document.getElementById('passwordNew');
let selectRows ="";
const selectBody= document.getElementById('rolesNew');

/////// Vars to fill header///////
const headerDataInfo = document.getElementById('headerData');

/////// Vars to fill modal ///////
const idToEdit= document.getElementById('idEdit');
const firstNameToEdit= document.getElementById('firstNameEdit');
const lastNameToEdit=document.getElementById('LastNameEdit');
const emailToEdit=document.getElementById('emailEdit');
const usernameToEdit=document.getElementById('usernameEdit');
const passwordToEdit=document.getElementById('passwordEdit');
const rolesToEdit=document.getElementById('rolesEdit');
const idToDelete=document.getElementById('idDelete');

/////// Initialization ///////
document.addEventListener('DOMContentLoaded', function(){
    fillTable();
    fillHeaderData();
    $('#modalDelete').on("hidden.bs.modal", function() {
        $(this).find('form').trigger('reset');
        document.getElementById('rolesDelete').innerHTML="";
    });
    $('#modalEdit').on("hidden.bs.modal", function() {
        $(this).find('form').trigger('reset');
        document.getElementById('rolesEdit').innerHTML="";
    });
});

/////// Get all Roles ///////
fetch (urlRoles)
    .then(res =>res.json())
    .then(data => fillSelect(data));

///////   Fill Roles Select   ///////
const fillSelect = (elements) => {
    elements.forEach(element => {
        let shortName = element.name.replace("ROLE_","");
        selectRows += `
        <option value="${element.name}">${shortName}</option>
        `;
    })
    selectBody.innerHTML=selectRows;
}

/////// Get all Users and fill table ///////
function stringRoles (element) {
    let x = [];
    element.roles.forEach(role => {
        x.push(role.name.replace("ROLE_",""));
    })
    return x.join(" ");
}

async function fillTable () {
    let tableRows ="";
    await fetch (url)
        .then(response => response.json())
        .then(data => data.forEach(user => {
            tableRows += `
            <tr data-id=${user.id}>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.username}</td>
                    <td>${stringRoles(user)}</td>
                <td><button type="button" id = "buttonEdit" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#modalEdit" onclick="fillEditModal()">Edit</button></td>
                <td><button type="button" id = "buttonDelete" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalDelete" onclick="fillDeleteModal()">Delete</button></td>
            </tr>
            `;
        }))
    tableBody.innerHTML=tableRows;
}

/////// Fill header data ///////
async function fillHeaderData () {
    let header='';
    await fetch (authDataUrl)
        .then(res =>res.json())
        .then(user =>{
            header = `
            <p><strong>${user.email}</strong> with roles: ${stringRoles(user)}
            </p>
            `;
        });
    headerDataInfo.innerHTML=header;
}

/////// Add new user ///////
async function addNewUser(){
    let newUser = {
        firstName:firstNameNew.value,
        lastName:lastNameNew.value,
        email:emailNew.value,
        username:usernameNew.value,
        password:passwordNew.value,
        roles:[]
    }

    for (let option of selectBody.options) {
        if (option.selected) {
            await fetch (urlRoles+'/'+option.value)
                .then(res =>res.json())
                .then(role => newUser.roles.push(role));
        }
    }

    await fetch(url,{
        method: 'POST',
        headers : {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    });
    fillTable();
    $('.add-user-form').trigger('reset');
    homeTab.click();
}

/////// Edit User ///////
function fillEditModal() {
    const row = event.target.parentNode.parentNode;
    idToEdit.value=row.children[0].innerHTML;
    firstNameToEdit.value=row.children[1].innerHTML;
    lastNameToEdit.value=row.children[2].innerHTML;
    emailToEdit.value=row.children[3].innerHTML;
    usernameToEdit.value=row.children[4].innerHTML;
    rolesToEdit.innerHTML=selectRows;
    document.getElementById("edit-button").addEventListener('click',editUserFunc,{once:true})
}

let editUserFunc =async function editUser(){
    console.log(idToEdit.value);
    let editUser = {
        id:idToEdit.value,
        firstName:firstNameToEdit.value,
        lastName:lastNameToEdit.value,
        email:emailToEdit.value,
        username:usernameToEdit.value,
        password:passwordToEdit.value,
        roles:[]
    }

    for (let option of rolesToEdit.options) {
        if (option.selected) {
            await fetch (urlRoles+'/'+option.value)
                .then(res =>res.json())
                .then(role => editUser.roles.push(role));
        }
    }

    await fetch(url + '/' + idToEdit.value,{
        method: 'PUT',
        headers : {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(editUser)
    });
    fillTable();
    fillHeaderData();
    $('#modalEdit').modal('toggle');
}

/////// Delete User ///////
function fillDeleteRoles (string) {
    let deleteRows = '';
    let stringSplitted = string.split(" ");
    stringSplitted.forEach(el => {
        deleteRows += `
        <option>${el}</option>
        `;
    })
    return deleteRows;
}

function fillDeleteModal() {
    const row = event.target.parentNode.parentNode;
    idToDelete.value=row.children[0].innerHTML;
    document.getElementById('firstNameDelete').value=row.children[1].innerHTML;
    document.getElementById('lastNameDelete').value=row.children[2].innerHTML;
    document.getElementById('emailDelete').value=row.children[3].innerHTML;
    document.getElementById('usernameDelete').value=row.children[4].innerHTML;
    document.getElementById('rolesDelete').innerHTML=fillDeleteRoles(row.children[5].innerHTML);
    document.getElementById("delete-button").addEventListener('click',deleteUserFunc,{once: true})
}

let deleteUserFunc = async function deleteUser() {
    console.log(idToDelete.value);
    await fetch(url + '/' + idToDelete.value, {
        method: 'DELETE',
        cache: 'reload',
        headers : {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(idToDelete.value)
    })
    fillTable();
    $('#modalDelete').modal('toggle');
}
