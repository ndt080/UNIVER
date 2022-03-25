const RacesFormInputs = [
    {
        label: 'Race date:',
        name: 'raceDate',
        placeholder: 'Enter race date...',
        styleClasses: [],
        type: "date",
        attributes: new Map([
            ['data-validation', 'true'],
            ['data-validation-params', 'require'],
        ])
    },
]

function onLoadPage() {
    const resultContainer = document.getElementById('races-result');

    generateNavigationBar(NavigationItems)
    generateForm('races-form', RacesFormInputs, "Show races");
    generateForm('all-races-form', [], "Show all races");

    const racesForm = document.getElementById('races-form');
    racesForm.onsubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const searchQuery = {date: formData.get('raceDate'),}

        const races = getRacesByQuery(Races, searchQuery);
        printResult(resultContainer, races);
    };

    const allRacesForm = document.getElementById('all-races-form');
    allRacesForm.onsubmit = (e) => {
        e.preventDefault();
        printResult(resultContainer, Races);
    };

    initInputValidation('races-form');
}


function getRacesByQuery(races, searchQuery) {
    return races.filter(race => {
        return new Date(race.date).toDateString() === new Date(searchQuery.date).toDateString();
    });
}

function printResult(resultContainer, result) {
    resultContainer.innerHTML = '';
    const resultTable = createResultTable(
        result,
        'races-table',
        'races-table__header',
        'races-table__row',
        'races-table__col',
    );
    resultContainer.append(resultTable);
}