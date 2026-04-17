package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());//сохраняем занятие в расписании
        }
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);

        if (!dayMap.containsKey(time)) {
            dayMap.put(time, new ArrayList<>());
        }
        dayMap.get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> trainingSessions = new ArrayList<>();
        if (!timetable.containsKey(dayOfWeek)) {
            return trainingSessions;
        }
        for (List<TrainingSession> list : timetable.get(dayOfWeek).values()) {
            trainingSessions.addAll(list);
        }
        return trainingSessions;
    }


    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> trainingSessions = new ArrayList<>();
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        List<TrainingSession> sessionAtTime = dayMap.get(timeOfDay);

        if (sessionAtTime == null) {
            return new ArrayList<>();
        } else {
            return sessionAtTime;
        }
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counts = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();

                    counts.put(coach, counts.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : counts.entrySet()) {

            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }


        Collections.sort(result);

        return result;
    }
}

