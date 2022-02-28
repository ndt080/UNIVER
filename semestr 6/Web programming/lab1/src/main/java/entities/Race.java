package entities;

import java.util.*;

/**
 * Race class
 */
public class Race {
    /**
     * Race id
     */
    int id;
    /**
     * Race date
     */
    Date date;
    /**
     * Participants Ids of the race
     */
    List<Integer> participantIds;
    /**
     * Participants of the race
     */
    List<Horse> participants;
    /**
     * Winner of the race;
     */
    Horse winner;

    /**
     * Race object constructor
     * @param id Race id
     * @param date Race date
     * @param participantIds Participants ids of the race
     */
    public Race(int id, Date date, List<Integer> participantIds, Horse winner) {
        this.id = id;
        this.date = date;
        this.participantIds = participantIds;
        this.winner = winner;
    }

    /**
     * Race object constructor
     * @param id Race id
     * @param date Race date
     * @param participantIds Participant ids of the race
     * @param participants Participants of the race
     * @param winner winner of the race
     */
    public Race(int id, Date date, List<Integer> participantIds, List<Horse> participants, Horse winner) {
        this.id = id;
        this.date = date;
        this.participants = participants;
        this.participantIds = participantIds;
        this.winner = winner;
    }

    /**
     * Get Race id
     * @return Race id
     */
    public int getId() { return id; }
    /**
     * Set Race id
     * @param id Race id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get Race date
     * @return Race date
     */
    public Date getDate() { return date; }
    /**
     * Set race date
     * @param date Race date
     */
    public void setDate(Date date) { this.date = date; }

    /**
     * Get Participants of the race
     * @return Participants of the race
     */
    public List<Horse> getParticipants() { return participants; }
    /**
     * Set Participants of the race
     * @param participants Participants of the race
     */
    public void setParticipants(List<Horse> participants) { this.participants = participants; }

    /**
     * Get Participant ids of the race
     * @return Participant ids of the race
     */
    public List<Integer> getParticipantIds() { return this.participantIds; }
    /**
     * Set Participant ids of the race
     * @param participants Participant ids of the race
     */
    public void setParticipantIds(List<Integer> participants) { this.participantIds = participants; }

    /**
     * Get winner of the race
     * @return winner of the race
     */
    public Horse getWinner() { return winner; }
    /**
     * Set winner of the race
     * @param winner winner of the race
     */
    public void setWinner(Horse winner) { this.winner = winner; }

    /**
     * Get Participant Ids as ARRAY
     * @return Participant Ids array
     */
    public Integer[] getParticipantIdsArray() {
        return this.participantIds.toArray(Integer[]::new);
    }

    /**
     * Get race object as string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Race: { id: %d, date: %s, participants: \n\t  %s, \n\t  winner: %s };",
                this.id, this.date, this.participants, this.winner);
    }
}
