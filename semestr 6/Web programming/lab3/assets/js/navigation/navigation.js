const NavigationItems = [
    { title: "Home", link: "", styleClasses: [] },
    { title: "Bets", link: "", styleClasses: [] },
    { title: "About", link: "", styleClasses: [] },
    { title: "Github", link: "", styleClasses: [] },
]


window.onload = function () {
    const navbar = document.getElementById("navbar");
    const navbarList = document.createElement("ul");

    navbarList.classList.add("navbar__list", "title-regular-18");

    NavigationItems.forEach(item => {
        const navItemNode = createNavbarItemNode(item);
        navbarList.appendChild(navItemNode);
    });
    navbar.appendChild(navbarList);
};


function createNavbarItemNode(navigationItem) {
    const navbarItem = document.createElement("div");
    const navbarLink = document.createElement("a");

    navbarItem.classList.add("navbar__item");
    navbarLink.classList.add("navbar__link", ...navigationItem.styleClasses);

    navbarLink.setAttribute("href", navigationItem.link || "#" );
    navbarLink.textContent = navigationItem.title || "Item";

    navbarItem.appendChild(navbarLink);

    return navbarItem;
}