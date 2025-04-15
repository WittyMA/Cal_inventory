/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



document.querySelector("form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent the form from submitting immediately

    const appName = document.getElementById("app_name").value;

    // Check for duplicate app_name
    try {
        const response = await fetch(`ApplicationServlet?checkAppName=${encodeURIComponent(appName)}`);
        const isDuplicate = await response.json();

        if (isDuplicate) {
            alert("Application name already exists. Please use a different name.");
        } else {
            this.submit(); // Submit the form if no duplicate is found
        }
    } catch (error) {
        console.error("Error checking for duplicate app name:", error);
        alert("Error checking application name. Please try again.");
    }
});

