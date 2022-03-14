package entities;

import java.util.*;
import javax.persistence.*;


/**
 * Race class
 */
@Entity
@Table(name = "race")
@NamedQueries({
        @NamedQuery(name = "Race.findAll", query = "select r from Race r "),
        @NamedQuery(name = "Race.findById", query = "select a from Race a where a.id = :id"),
        @NamedQuery(name = "Race.deleteById", query = "delete from Race a where a.id = :id")
})
public class Race {
    /**
     * Race id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     * Race date
     */
    @Column(name = "date")
    Date date;

    /**
     * Participants Ids of the race
     */
//    @Column(name = "participants")
//    @Type(type = "entities.types.CUSTOM_ARRAY")
//    @Transient
//    public Integer[] participantIds;

    /**
     * Participants of the race
     */
    @Transient
    List<Horse> participants;

    /**
     * Winner of the race;
     */
    @ManyToOne
    @JoinColumn(name = "winner")
    Horse winner;

//    public void setParticipantIds(List<Integer> participantIds) {
//        this.participantIds = participantIds.toArray(Integer[]::new);
//    }
//
//    public List<Integer> getParticipantIds() {
//        return Arrays.stream(participantIds).collect(Collectors.toList());
//    }

    /**
     * Race object constructor
     *
     * @param id             Race id
     * @param date           Race date
     * @param participantIds Participants ids of the race
     */
    public Race(int id, Date date, List<Integer> participantIds, Horse winner) {
        this.id = id;
        this.date = date;
//        this.participantIds = participantIds.toArray(Integer[]::new);
        this.winner = winner;
    }

    /**
     * Race object constructor
     *
     * @param id             Race id
     * @param date           Race date
     * @param participantIds Participant ids of the race
     * @param participants   Participants of the race
     * @param winner         winner of the race
     */
    public Race(int id, Date date, List<Integer> participantIds, List<Horse> participants, Horse winner) {
        this.id = id;
        this.date = date;
        this.participants = participants;
//        this.participantIds = participantIds.toArray(Integer[]::new);
        this.winner = winner;
    }

    /**
     * Race object constructor
     */
    public Race() {
    }

    /**
     * Get Race id
     *
     * @return Race id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Race id
     *
     * @param id Race id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Race date
     *
     * @return Race date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set race date
     *
     * @param date Race date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get Participants of the race
     *
     * @return Participants of the race
     */
    public List<Horse> getParticipants() {
        return participants;
    }

    /**
     * Set Participants of the race
     *
     * @param participants Participants of the race
     */
    public void setParticipants(List<Horse> participants) {
        this.participants = participants;
    }

    /**
     * Get winner of the race
     *
     * @return winner of the race
     */
    public Horse getWinner() {
        return winner;
    }

    /**
     * Set winner of the race
     *
     * @param winner winner of the race
     */
    public void setWinner(Horse winner) {
        this.winner = winner;
    }

    /**
     * Get Participant Ids as ARRAY
     *
     * @return Participant Ids array
     */
//    public Integer[] getParticipantIdsArray() {
//        return this.participantIds;
//    }

    /**
     * Get race object as string
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Race: { id: %d, date: %s, participants: \n\t  %s, \n\t  winner: %s };",
                this.id, this.date, this.participants, this.winner);
    }
}
