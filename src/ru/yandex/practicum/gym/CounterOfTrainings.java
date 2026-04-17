package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private final Coach coach;
    private final int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CounterOfTrainings other) {
        if (this.count != other.count) {

            return Integer.compare(other.count, this.count);
        }

        return this.coach.getSurname().compareTo(other.coach.getSurname());
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }
}