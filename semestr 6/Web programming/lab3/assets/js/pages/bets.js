const BetsFormInputs = [
    {
        label: 'Client email:',
        name: 'clientEmail',
        placeholder: 'Enter email...',
        styleClasses: [],
        type: "text",
        attributes: new Map([
            ['data-validation', 'true'],
            ['data-validation-params', 'email'],
        ])
    },
    {
        label: 'Bet date:',
        name: 'betDate',
        placeholder: 'Enter race id...',
        styleClasses: [],
        type: "date",
        attributes: new Map([
            ['data-validation', 'true'],
            ['data-validation-params', 'require'],
        ])
    },
]

function onLoadPage() {
    const resultContainer = document.getElementById('bets-result');

    generateNavigationBar(NavigationItems)
    generateForm('bets-form', BetsFormInputs, "Show bets");

    const form = document.getElementById('bets-form');
    form.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const searchQuery = {
            date: formData.get('betDate'),
            client: formData.get('clientEmail'),
        }

        const bets = getBetsByQuery(Bets, searchQuery);
        printResult(resultContainer, bets);
    };

    initInputValidation('bets-form');
}


function getBetsByQuery(bets, searchQuery) {
    return bets.filter(bet => {
        return bet.client === searchQuery.client &&
               new Date(bet.createdAt).toDateString() === new Date(searchQuery.date).toDateString();
    });
}

function printResult(resultContainer, result) {
    resultContainer.innerHTML = '';
    const resultTable = createResultTable(
        result,
        'bets-table',
        'bets-table__header',
        'bets-table__row',
        'bets-table__col',
    );
    resultContainer.append(resultTable);
}