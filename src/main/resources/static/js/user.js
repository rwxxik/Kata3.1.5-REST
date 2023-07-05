const url = 'http://localhost:8080/api/user';
let tableBody = document.querySelector('#userTable tbody');
let headerData = document.getElementById('headerData');

//Initialization
document.addEventListener('DOMContentLoaded', function () {
    fillUserPage();
});

function stringRoles(element) {
    let roleArray = [];
    element.roles.forEach(role => {
        roleArray.push(role.name.replace("ROLE_", ""));
    })
    return roleArray.join(", ");
}

async function fillUserPage() {
    let tableRow = '';
    let header = '';
    await fetch(url)
        .then(response => response.json())
        .then(user => {

            header = `
            <p><strong>${user.email}</strong> with roles: ${stringRoles(user)}
            </p>
            `;

            tableRow += `
                <tr data-id=${user.id}>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.username}</td>
                    <td>${stringRoles(user)}</td>
                </tr>
            `;
        })
    tableBody.innerHTML = tableRow;
    headerData.innerHTML = header;
}
