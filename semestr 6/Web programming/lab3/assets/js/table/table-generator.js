function createResultTable(
    bets,
    tableClass = 'table',
    headerClass = 'header',
    rowClass = 'row',
    colClass = 'col',
) {
    const table = document.createElement('table');
    const header = createResultTableHeader(bets[0], headerClass, colClass)
    const rows = bets?.map(bet => createResultTableRow(bet, rowClass, colClass));

    table.classList.add(tableClass);
    table.append(header, ...rows);
    return table;
}

function createResultTableHeader(bet, headerClass, colStyleClass) {
    const header = document.createElement('tr');
    const colKeys = Object.keys(bet);

    const cols = colKeys.map(key => {
        const col = document.createElement('th');
        col.style.textTransform = 'uppercase';
        col.textContent = key.toString();
        col.classList.add(colStyleClass);
        return col;
    })

    header.classList.add(headerClass);
    header.append(...cols);
    return header;
}

function createResultTableRow(bet, rowClass, colClass) {
    const row = document.createElement('tr');
    const colValues = Object.values(bet);

    const cols = colValues.map(value => {
        const col = document.createElement('td');
        col.classList.add(colClass);

        if(value instanceof Date) {
            col.textContent = new Date(value).toDateString();
        } else {
            col.textContent = value.toString();
        }

        return col;
    })

    row.classList.add(rowClass);
    row.append(...cols);
    return row;
}
