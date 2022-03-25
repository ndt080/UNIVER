const rootFolder = "lab3/"

const NavigationItems = [
    { title: "Home", link: "index.html", styleClasses: [] },
    { title: "Bets", link: "pages/bets.html", styleClasses: [] },
    { title: "Races", link: "pages/races.html", styleClasses: [] },
    { title: "About", link: "pages/about.html", styleClasses: [] },
    { title: "Github", link: "https://github.com/ndt080/UNIVER/tree/main/semestr%206/Web%20programming/lab3", styleClasses: ["title-semi-18"] },
]

function generateNavigationBar(navigationItems) {
    const navbar = document.getElementById("navbar");
    const navbarButton = document.getElementById("navbar-btn");
    const navbarList = document.createElement("ul");
    navbarList.classList.add("navbar__list", "title-regular-18");

    initNavbarList(navbarList, navigationItems);
    setListToNavbar(navbar, navbarList);

    const logo = document.getElementById("logo");
    logo.addEventListener('click', () => window.location.assign(generateLink("index.html")));

    navbarButton.addEventListener('click', (event) => handleNavButtonTap(event, navbar, navbarButton));
}


function initNavbarList(navbarList, navigationItems) {
    navigationItems.forEach(item => {
        const navItemNode = createNavbarItemNode(item);
        navbarList.appendChild(navItemNode);
    });
}

function handleNavButtonTap(event, navbar, navbarButton) {
    navbarButton.classList.toggle("active");
    navbar.classList.toggle("active");
}

function setListToNavbar(navbar, navbarList) {
    navbar.appendChild(navbarList);
}

function generateLink(link) {
    const origin = window.location.origin;
    return link.split("://")[0]?.includes("http") ? link : `${origin}/${rootFolder}${link}`;
}

function createNavbarItemNode(navigationItem) {
    const navbarItem = document.createElement("div");
    const navbarLink = document.createElement("a");

    navbarItem.classList.add("navbar__item");
    navbarLink.classList.add("navbar__link", ...navigationItem.styleClasses);

    navbarLink.setAttribute("href", generateLink(navigationItem.link));
    navbarLink.textContent = navigationItem.title || "Item";

    navbarItem.appendChild(navbarLink);
    return navbarItem;
}