class Bet {
    constructor(id, client, race, horse, amount, coefficient, createdAt) {
        this.id = id;
        this.client = client;
        this.race = race;
        this.horse = horse;
        this.amount = amount;
        this.coefficient = coefficient;
        this.createdAt = createdAt;
    }
}

class Race {
    constructor(id, date, winner, participants) {
        this.id = id;
        this.date = date;
        this.participants = participants;
        this.winner = winner;
    }

    toString() {
        return `#${this.id}: ${this.participants}`;
    }
}

const Races = [
    new Race(0, new Date('2022-03-28 12:20:05.000000'), 'Star Lord', ['Star Lord', 'Glory Vase', 'Persian King', 'Stradivarius', 'Persian King']),
    new Race(1, new Date('2022-03-26 12:18:54.000000'), 'Glory Vase', ['Star Lord', 'Glory Vase', 'Persian King', 'Stradivarius', 'Persian King']),
    new Race(2, new Date('2022-03-28 12:20:05.000000'), 'Persian King', ['Star Lord', 'Glory Vase', 'Persian King', 'Stradivarius', 'Persian King']),
    new Race(3, new Date('2022-03-23 15:10:14.000000'), 'Stradivarius', ['Star Lord', 'Glory Vase', 'Persian King', 'Stradivarius', 'Persian King']),
    new Race(4, new Date('2022-03-23 15:10:14.000000'), 'Persian King', ['Star Lord', 'Glory Vase', 'Persian King', 'Stradivarius', 'Persian King']),
]

const Bets = [
    new Bet(0, 'ndt.080@yandex.by', Races[0], 'Star Lord', 55.5, 1.4, new Date('2022-03-23 12:20:05.000000')),
    new Bet(1, 'vasya@mail.ru', Races[0], 'Star Lord', 85.5, 2.1, new Date('2022-03-23 11:20:05.000000')),
    new Bet(2, 'ndt.080@yandex.by', Races[1], 'Persian King', 60.0, 1.12, new Date('2022-03-21 14:40:05.000000')),
    new Bet(3, 'ndt.080@yandex.by', Races[3], 'Star Lord', 100.7, 1.98, new Date('2022-03-23 15:11:05.000000')),
]